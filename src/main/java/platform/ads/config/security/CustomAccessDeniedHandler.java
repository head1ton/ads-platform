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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import platform.ads.config.base.ErrorDto;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
        final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        List<ErrorDto> errors = new ArrayList<>();
        errors.add(ErrorDto.builder().point("UNAUTHORIZED").detail("unauthorized token").build());

        ProblemDetail pb = ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.SC_UNAUTHORIZED), "UNAUTHORIZED");
        pb.setType(URI.create("/docs.html"));
        pb.setProperty("errors", errors);
        pb.setInstance(URI.create(request.getRequestURI()));
        ObjectMapper objectMapper = new ObjectMapper();

        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writer.write(objectMapper.writeValueAsString(pb));
    }
}
