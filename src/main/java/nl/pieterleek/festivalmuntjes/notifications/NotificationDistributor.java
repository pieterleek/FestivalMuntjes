// see https://www.devglan.com/spring-boot/spring-websocket-integration-example-without-stomp
package nl.pieterleek.festivalmuntjes.notifications;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class NotificationDistributor extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();

    synchronized void addSession(WebSocketSession sess) {
        this.sessions.add(sess);
    }

    synchronized void removeSession(WebSocketSession sess) {
        this.sessions.remove(sess);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        String receivedMessage = (String) message.getPayload();
        session.sendMessage(new TextMessage("Received: " + receivedMessage));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        addSession(session);
        System.out.println("Connection established for session=" + session);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        removeSession(session);
        System.out.println("Connection closed for session=" + session);
    }

    public void broadcastToAll()  {
        for (WebSocketSession sess : sessions) {
            try {
                sess.sendMessage(new TextMessage("Update from server"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}