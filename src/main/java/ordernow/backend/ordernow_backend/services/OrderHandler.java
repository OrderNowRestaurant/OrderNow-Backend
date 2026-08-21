package ordernow.backend.ordernow_backend.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import ordernow.backend.ordernow_backend.dtos.OrderResponseDTO;
import ordernow.backend.ordernow_backend.entities.Order;

@Service
public class OrderHandler extends TextWebSocketHandler {

    @Autowired
    private JwtService jwtService;

    private final Map<String, List<WebSocketSession>> sessionPerUser = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = obtainTokenFromUrl(session);

        if (token != null) {
            try {
                String username = jwtService.extractUsername(token);

                if (username != null && !username.isEmpty()) {
                    sessionPerUser
                        .computeIfAbsent(username, k -> new CopyOnWriteArrayList<>())
                        .add(session);

                    session.getAttributes().put("username", username);
                    return;
                }
            } catch (Exception e) {
                System.err.println("Error al validar token en WebSocket: " + e.getMessage());
            }
        }

        session.close(CloseStatus.POLICY_VIOLATION);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");

        if (username != null && sessionPerUser.containsKey(username)) {
            sessionPerUser.get(username).remove(session);
        }
    }

    public void notifyNewOrder(String usernameRestaurante, Order newOrder) {
        List<WebSocketSession> sessionList = sessionPerUser.get(usernameRestaurante);

        if (sessionList != null && !sessionList.isEmpty()) {
            TextMessage message = new TextMessage(OrderResponseDTO.fromEntity(newOrder).toString());
            for (WebSocketSession session : sessionList) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String obtainTokenFromUrl(WebSocketSession session) {
        if (session.getUri() != null) {
            String query = session.getUri().getQuery();

            if (query != null && query.contains("token=")) {
                return query.split("token=")[1].split("&")[0];
            }
        };
        
        return null;
    }
}