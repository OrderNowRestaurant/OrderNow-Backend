package ordernow.backend.ordernow_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import ordernow.backend.ordernow_backend.services.OrderHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private OrderHandler orderHandler;
	
	@Override 
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(orderHandler, "/ws-order")
			.setAllowedOrigins("http://localhost:4200", "http://localhost:5173", "http://127.0.0.1:4200") 
			.setAllowedOriginPatterns("*");
	}

}
