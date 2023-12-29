// see https://www.devglan.com/spring-boot/spring-websocket-integration-example-without-stomp
package nl.pieterleek.festivalmuntjes.notifications;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class NotificationDistributor extends TextWebSocketHandler {
    // a subscription map of client WebSocket sessions per topic
    Map<String, Map<String, WebSocketSession>> sessionsByTopic = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // we are happy not to track all sessions separately
        System.out.printf("Connection established for session=%s\n", session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) {
        System.out.printf("%s error occured at sender %s\n", throwable, session);
    }

    /**
     * Handles an incoming websocket message from the given fromSession
     * Simple message protocol:
     * subscribe topic         the fromSession subscribes for being notified about a given topic
     * unsubscripic topic      the fromSession unsubscribes from being notified about a given topic
     * notify topic            the fromSession wants to notify all sessions that have subscribed for the given topic
     * There is no further information content passed along with the notification about a topic
     * The notification acts like an alarm on the topic: something has happened.
     * It is up to the clients to undertake further initiative to find out how to follow up on the alarm.
     *
     * @param fromSession
     * @param message
     */
    @Override
    public void handleTextMessage(WebSocketSession fromSession, TextMessage message) {
        String command = message.getPayload();
        // take the topic from the first argument after the command
        String topic = command.split(" ")[1];
        if (command.startsWith("notify ")) {                    // syntax:  notify topic
            // notify all subscribing sessions about this topic, except fromSession itself
            this.notify(topic, fromSession);
        } else if (command.startsWith("subscribe ")) {          // syntax:  subscribe topic
            this.subscribe(topic, fromSession);
        } else if (command.startsWith("unsubscribe ")) {        // syntax:  unsubscribe topic
            this.unsubscribe(topic, fromSession);
        } else {
            System.out.printf("Unsupported message '%s' from %s", command, fromSession);
        }
    }

    private void subscribe(String topic, WebSocketSession session) {
        // subscribe the session for notifications about the given topic argument
        Map<String, WebSocketSession> sessionsMap = this.sessionsByTopic.get(topic);
        if (sessionsMap == null) {
            // add the first subscribing session for this topic
            this.sessionsByTopic.put(topic, new HashMap<>(Map.of(session.getId(), session)));
        } else {
            // add the session to the current map of sessions interested in this topic, if not done earlier
            sessionsMap.putIfAbsent(session.getId(), session);
        }
    }

    private void unsubscribe(String topic, WebSocketSession session) {
        // TODO unsubcribe the session from the given topic in sessionsByTopic, if still subscribed
        //  if no sessions are left with interest in the given topic, then remove the topic altogether from the subscription map

    }

    public void notify(String topic, WebSocketSession fromSession) {
        // get all sessions that have subscribed to the given topic
        Map<String, WebSocketSession> sessionsMap = this.sessionsByTopic.get(topic);
        if (sessionsMap == null) return;        // no notifications required

        // send a notification message to every subscribed session
        //  except the fromSession, if any, which has initiated the notification
        //  also clean-up from the map any sessions that throw errors (lost connection)
        System.out.printf("Notifying %d sessions about change in topic %s", sessionsMap.size(), topic);

        Set<WebSocketSession> lostSessions = new HashSet<>();
        TextMessage message = new TextMessage("notify " + topic);
        for (WebSocketSession session : sessionsMap.values()) {
            try {
                if (fromSession == null || !session.getId().equals(fromSession.getId()))
                    session.sendMessage(message);
            } catch (Exception e) {
                // remember this session as a lost session
                lostSessions.add(session);
                System.out.printf("Communication %s:'%s' on %s to %s", e.getClass().getName(), e.getMessage(), topic, session);
            }
        }
        // TODO clean-up any lost sessions that were collected into lostSessions
        //  also clean-up any topic that has no subscribed sessions anymore

    }

    public void notify(String topic) {
        // notify everybody, without discarding any originating session
        this.notify(topic, null);
    }
}
