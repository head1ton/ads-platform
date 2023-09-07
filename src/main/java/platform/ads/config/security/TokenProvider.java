package platform.ads.config.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.github.dockerjava.api.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;

import lombok.extern.slf4j.Slf4j;
import platform.ads.api.v1.member.vo.LoginVo;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 7;  // 7일
    private static final String TYPE = "Bearer ";
    private final Key key;


    public TokenProvider(@Value(value = "${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public LoginVo generateTokenDto(Authentication authentication) {
//        log.info("token 생성 시작");
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                                           .map(GrantedAuthority::getAuthority)
                                           .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = createAccessToken(authentication.getName(), authorities,
            accessTokenExpiresIn);

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                                  .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                                  .signWith(key, SignatureAlgorithm.HS512)
                                  .compact();

        return new LoginVo("Bearer ", accessToken, refreshToken, accessTokenExpiresIn.getTime(),
            null);
    }

    public String createAccessToken(final String sub, final String authorities,
        final Date accessTokenExpiresIn) {
        return Jwts.builder()
                   .setSubject(sub)       // payload "sub": "name"
                   .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_MEMBER"
                   .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                   .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                   .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new UnauthorizedException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                  .map(SimpleGrantedAuthority::new)
                  .toList();

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getMemberId(String token) {
        Claims claims = parseClaims(token);
//        log.info("claims : {}", claims.get("sub").toString());
        return Long.parseLong(claims.get("sub").toString());
    }

    public Long getMemberId(HttpServletRequest request) {
        String token = resolveToken(request);
        return getMemberId(token);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TYPE)) {
            return bearerToken.substring(TYPE.length());
        }
        return null;
    }
}
