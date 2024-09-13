//package ir.tci.ashna.dev.config.security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.SignatureException;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//class AuthServiceJwt extends AuthService {
//
////    @Value("${auth.client.id}")
////    private String clientId;
//
//    private final JwtParser jwtParser;
//
//    @Override
//    public Optional<Authentication> authenticate(HttpServletRequest request) {
//        return extractBearerTokenHeader(request).flatMap(this::verify);
//    }
//
//    private Optional<Authentication> verify(String token) {
//        try {
//            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
//            String username = (String) claimsJws.getBody().get("preferred_username");
//            Map<String, Object> resourceAccess = claimsJws.getBody().get("resource_access", Map.class);
//            Map<String, Object> map = (Map<String, Object>) resourceAccess.get("account");
//            List<String> roles = (List<String>) map.get("roles");
//            Authentication authentication = createAuthentication(username, roles.toArray(new String[]{}));
//            return Optional.of(authentication);
//        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
//            e.printStackTrace();
//            return Optional.empty();
//        } catch (Exception e) {
//            log.warn("Unknown error while trying to verify JWT token", e);
//            return Optional.empty();
//        }
//    }
//
//}
