package com.application.middlewares;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class HttpUpgradeMiddleware implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {        
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();

        if (headers.containsKey("Sec-WebSocket-Protocol")) {
            List<String> subProtocols = headers.get("Sec-WebSocket-Protocol");

            String theSubprotocol = "";

            try {
                String subProto = subProtocols.get(0).toLowerCase();
                if (subProto.contains("proto-v3")) {
                    theSubprotocol = "proto-v3";
                } else if (subProto.contains("proto-v2")) {
                    theSubprotocol = "proto-v2";
                } else if (subProto.contains("proto")) {
                    theSubprotocol = "proto";
                }
            } catch (Exception e) {
                // void
            }

            if (theSubprotocol.isEmpty()) {
                response.setStatusCode(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
                return Mono.error(new ResponseStatusException(
                        HttpStatus.HTTP_VERSION_NOT_SUPPORTED,
                        "Unsupported Proto version. WebSocket subprotocol supported are: proto, proto-v2, proto-v3"));
            }

            return chain.filter(exchange);
        }

        if (!headers.containsKey("Upgrade")) {
            response.setStatusCode(HttpStatus.UPGRADE_REQUIRED);
            return response.setComplete();
        }

        response.setStatusCode(HttpStatus.BAD_REQUEST);

        // Return an empty response to end the processing
        return response.setComplete();
    }
}
