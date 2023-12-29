package nl.pieterleek.festivalmuntjes.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnnouncementDistributor extends TextWebSocketHandler {
    public final static Logger logger = LoggerFactory.getLogger(AnnouncementDistributor.class);
    List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info("Number of active sessions: {}", this.sessions.size());
        for(WebSocketSession webSocketSession : sessions) {
            try {
                logger.info("Sending message to " + webSocketSession.getId());
                webSocketSession.sendMessage(new TextMessage(message.getPayload()));
            } catch(IOException e) {
                logger.info("Communication with session with id " + session.getId() + " failed - removing it from the list");
                sessions.remove(webSocketSession);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // the messages will be broadcast to all users.
        sessions.add(session);
        logger.info("Session with id " + session.getId() + " was added");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        logger.info("Session with id " + session.getId() + " was removed");
    }
}
