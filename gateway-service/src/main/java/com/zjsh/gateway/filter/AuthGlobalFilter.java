//package com.zjsh.gateway.filter;
//
//import org.springframework.http.HttpStatus;
//import com.zjsh.gateway.util.JwtUtil;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class AuthGlobalFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();
//
//        // 1. 白名单放行
//        if (path.contains("/login") || path.contains("/public")) {
//            return chain.filter(exchange);
//        }
//
//        // 2. 获取token
//        String token = request.getHeaders().getFirst("Authorization");
//
//        if (token == null || !token.startsWith("Bearer ")) {
//            return unauthorized(exchange);
//        }
//
//        token = token.replace("Bearer ", "");
//
//        try {
//            // 3. JWT验签
//            String userId = String.valueOf(JwtUtil.getUserId(token));
//
//            // 4. 透传用户信息
//            ServerHttpRequest newRequest = request.mutate()
//                    .header("userId", userId)
//                    .build();
//
//            return chain.filter(exchange.mutate().request(newRequest).build());
//
//        } catch (Exception e) {
//            return unauthorized(exchange);
//        }
//    }
//
//    private Mono<Void> unauthorized(ServerWebExchange exchange) {
//
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        return exchange.getResponse().setComplete();
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}