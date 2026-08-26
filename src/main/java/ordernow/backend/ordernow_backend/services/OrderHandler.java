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
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.repositories.UserRepository;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderHandler extends TextWebSocketHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private ObjectMapper objectMapper;

    private final Map<Long, List<WebSocketSession>> sessionsPerRestaurant = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = obtainTokenFromUrl(session);

        if (token != null) {
            try {
                String username = jwtService.extractUsername(token);

                if (username != null) {
                    User user = userRepository.findByUsername(username).orElse(null);

                    if (user != null && user.getRestaurant() != null) {
                        Long restaurantId = user.getRestaurant().getIdRestaurant();

                        sessionsPerRestaurant
                            .computeIfAbsent(restaurantId, k -> new CopyOnWriteArrayList<>())
                            .add(session);

                        session.getAttributes().put("restaurantId", restaurantId);
                        System.out.println("WebSocket conectado con éxito para el restaurante ID: " + restaurantId);
                        return; 
                    }
                }
            } catch (Exception e) {
                System.err.println("Error procesando token en WebSocket: " + e.getMessage());
            }
        }

        session.close(CloseStatus.POLICY_VIOLATION);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long restaurantId = (Long) session.getAttributes().get("restaurantId");

        if (restaurantId != null && sessionsPerRestaurant.containsKey(restaurantId)) {
            sessionsPerRestaurant.get(restaurantId).remove(session);
        }
    }

    public void notifyNewOrder(Long restaurantId, Order newOrder) {
        List<WebSocketSession> sessionList = sessionsPerRestaurant.get(restaurantId);

        if (sessionList != null && !sessionList.isEmpty()) {
            try {
                String jsonMessage = objectMapper.writeValueAsString(OrderResponseDTO.fromEntity(newOrder));
                TextMessage message = new TextMessage(jsonMessage);

                for (WebSocketSession session : sessionList) {
                    if (session.isOpen()) {
                        session.sendMessage(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay pantallas conectadas para el restaurante ID: " + restaurantId);
        }
    }

    private String obtainTokenFromUrl(WebSocketSession session) {
        if (session.getUri() != null) {
            String query = session.getUri().getQuery();
            if (query != null && query.contains("token=")) {
                return query.split("token=")[1].split("&")[0];
            }
        }
        return null;
    }
}