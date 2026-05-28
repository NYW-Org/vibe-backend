package org.vibe.handler;

import lombok.RequiredArgsConstructor;
import org.vibe.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtHandShakeHandler implements HandshakeInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String query = request.getURI().getQuery();

        if (query == null || !query.contains("token=")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getBody().write("Token is missing".getBytes());
            return false;
        }

        String token = query.split("token=")[1];

        if (!jwtService.isValid(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getBody().write("Invalid token".getBytes());
            return false;
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
