package io.qman.festivalcoins.notifications;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is a handler for WebSocket messages.
 * It is annotated as a Component, meaning it's a Spring-managed bean.
 * It extends TextWebSocketHandler, which is a Spring class for handling WebSocket messages.
 */
@Component
public class NotificationDistributor extends TextWebSocketHandler {

    // A thread-safe list of WebSocket sessions
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    /**
     * This method is called after a WebSocket connection is established.
     * It adds the session to the list of sessions.
     * @param session the WebSocketSession that was established
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    /**
     * This method is called after a WebSocket connection is closed.
     * It removes the session from the list of sessions.
     * @param session the WebSocketSession that was closed
     * @param status the CloseStatus indicating why the connection was closed
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    /**
     * This method is used to broadcast a message to all connected sessions.
     * It sends a TextMessage with the content "Update from server" to each session.
     */
    public void broadcastToAll() {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage("Update from server"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}