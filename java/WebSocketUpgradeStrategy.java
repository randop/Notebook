package com.application.strategies;

import com.application.utils.Logger;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Log4j2
@Service
public class WebSocketUpgradeStrategy implements HandshakeInterceptor {

    private final Supplier<WebsocketServerSpec.Builder> specBuilderSupplier;

    @Nullable private Integer maxFramePayloadLength;

    @Nullable private Boolean handlePing;

    public WebSocketUpgradeStrategy() {
        this(WebsocketServerSpec::builder);
    }

    public WebSocketUpgradeStrategy(Supplier<WebsocketServerSpec.Builder> builderSupplier) {
        Assert.notNull(builderSupplier, "WebsocketServerSpec.Builder is required");
        this.specBuilderSupplier = builderSupplier;
    }

    public WebsocketServerSpec getWebsocketServerSpec() {
        return buildSpec(null);
    }

    WebsocketServerSpec buildSpec(@Nullable String subProtocol) {
        WebsocketServerSpec.Builder builder = this.specBuilderSupplier.get();
        if (subProtocol != null) {
            builder.protocols(subProtocol);
        }
        if (this.maxFramePayloadLength != null) {
            builder.maxFramePayloadLength(this.maxFramePayloadLength);
        }
        if (this.handlePing != null) {
            builder.handlePing(this.handlePing);
        }
        return builder.build();
    }

    String negotiateSubProtocol(HttpHeaders headers) {
        if (headers.containsKey("Sec-WebSocket-Protocol")) {
            List<String> subProtocols = headers.get("Sec-WebSocket-Protocol");
            try {
                String subProto = subProtocols.get(0).toLowerCase();
                if (subProto.contains("proto-v3")) {
                    return "proto-v3";
                } else if (subProto.contains("proto-v2")) {
                    return "proto-v2";
                } else if (subProto.contains("proto")) {
                    return "proto";
                }
            } catch (Exception e) {
                // void
            }
        }
        return null;
    }

    @Override
    public Mono<Void> upgrade(
            ServerWebExchange exchange,
            WebSocketHandler handler,
            @Nullable String subProtocol,
            Supplier<HandshakeInfo> handshakeInfoFactory) {

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        ServerHttpResponse response = exchange.getResponse();
        HttpServerResponse reactorResponse = ServerHttpResponseDecorator.getNativeResponse(response);
        HandshakeInfo handshakeInfo = handshakeInfoFactory.get();
        NettyDataBufferFactory bufferFactory = (NettyDataBufferFactory) response.bufferFactory();
        URI uri = request.getURI();
        final String subProtocol = negotiateSubProtocol(headers);
        final String sessionId = UUID.randomUUID().toString();
        final String[] segments = uri.getPath().split("/");
        final String unitId = segments[2];
        
        log.debug("WebSocketSession initialized %s", equipment);

        // Trigger WebFlux preCommit actions and upgrade
        return response.setComplete().then(Mono.defer(() -> {
            WebsocketServerSpec spec = buildSpec(subProtocol);
            return reactorResponse.sendWebsocket(
                    (in, out) -> {
                        ReactorNettyWebSocketSession session = new ReactorNettyWebSocketSession(
                                in, out, handshakeInfo, bufferFactory, spec.maxFramePayloadLength());
                        Map<String, Object> attributes = session.getAttributes();
                        attributes.put("protocol", subProtocol);                        
                        attributes.put("session", sessionId);
                        attributes.put("unit", unitId);
                        return handler.handle(session)
                                .checkpoint(uri + " [WebSocketUpgradeStrategy]")
                                .doOnCancel(() -> log.debug("WebSocketSession cancelled " + equipment))
                                .doOnTerminate(() -> log.debug("WebSocketSession terminated " + equipment));
                    },
                    spec);
        }));
    }
}
