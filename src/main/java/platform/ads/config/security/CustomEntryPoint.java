package platform.ads.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import platform.ads.config.base.ErrorDto;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {

    @Value(value = "${docs}")
    private String docs;

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
        final AuthenticationException authException) throws IOException, ServletException {
        List<ErrorDto> errors = new ArrayList<>();
//        errors.add(ErrorDto.builder().point("ACCESS TOKEN / REFRESH TOKEN").detail("please check request token").build());
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/login")) {
            errors.add(ErrorDto.builder().point("email / password")
                               .detail("please check email or password").build());
        } else {
            errors.add(ErrorDto.builder().point("access token").detail("please check access token")
                               .build());
        }

        ProblemDetail pb = ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.SC_FORBIDDEN), "FORBIDDEN");
        pb.setType(URI.create(docs));
        pb.setProperty("errors", errors);
        pb.setInstance(URI.create(requestURI));
        ObjectMapper objectMapper = new ObjectMapper();

        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writer.write(objectMapper.writeValueAsString(pb));
    }
}