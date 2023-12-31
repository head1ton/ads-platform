package platform.ads.config.advice;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import platform.ads.config.base.ErrorDto;

@RestControllerAdvice
public class CommonAdvice {

    private final static String ERRORS = "errors";

    @Value("${docs}")
    private String docs;

    // IllegalArgumentException
    @ExceptionHandler
    public ResponseEntity<ProblemDetail> illegalArgumentException(
        IllegalArgumentException e, HttpServletRequest request
    ) {
        List<ErrorDto> errors = new ArrayList<>();
        errors.add(ErrorDto.builder().point("").detail(e.getMessage()).build());

        ProblemDetail pb = ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
            "입력 값을 확인해 주세요."
        );
        pb.setInstance(URI.create(request.getRequestURI()));
        pb.setType(URI.create(docs));
        pb.setTitle(HttpStatus.BAD_REQUEST.name());
        pb.setProperty(ERRORS, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(pb);
    }

    // MethodArgumentNotValidException
    @ExceptionHandler
    public ResponseEntity<ProblemDetail> methodArgumentNotValidException(
        MethodArgumentNotValidException e,
        HttpServletRequest request
    ) {
        List<ErrorDto> errors = new ArrayList<>();

        ProblemDetail pb = ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
            "입력 값을 확인해 주세요."
        );

        Optional<BindingResult> bindingResultOptional = Optional.ofNullable(e.getBindingResult());

        if (bindingResultOptional.isPresent()) {
            BindingResult bindingResult = bindingResultOptional.get();
            Optional<FieldError> fieldErrorOptional = Optional.ofNullable(
                bindingResult.getFieldError());
            if (fieldErrorOptional.isPresent()) {
                FieldError fieldError = fieldErrorOptional.get();
                errors.add(ErrorDto.builder()
                                   .point(Optional.ofNullable(fieldError.getField()).orElse(""))
                                   .detail(Optional.ofNullable(fieldError.getDefaultMessage())
                                                   .orElse("")).build());
            }
        }

        pb.setInstance(URI.create(request.getRequestURI()));
        pb.setType(URI.create(docs));
        pb.setTitle(HttpStatus.BAD_REQUEST.name());
        pb.setProperty(ERRORS, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(pb);
    }

    // HttpMessageNoReadableException
    @ExceptionHandler
    public ResponseEntity<ProblemDetail> httpMessageNoReadableException(
        HttpMessageNotReadableException e,
        HttpServletRequest request
    ) {
        List<ErrorDto> errors = new ArrayList<>();

        errors.add(ErrorDto.builder().point("").detail(e.getMessage()).build());

        ProblemDetail pb = ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
            "입력 값을 확인해 주세요."
        );

        pb.setInstance(URI.create(request.getRequestURI()));
        pb.setType(URI.create(docs));
        pb.setTitle(HttpStatus.BAD_REQUEST.name());
        pb.setProperty(ERRORS, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(pb);
    }

    // MissingServletRequestParameterException
    @ExceptionHandler
    public ResponseEntity<ProblemDetail> missingServletRequestParameterException(
        MissingServletRequestParameterException e,
        HttpServletRequest request
    ) {
        List<ErrorDto> errors = new ArrayList<>();

        errors.add(ErrorDto.builder()
                           .point(e.getParameterName())
                           .detail(
                               String.format("please check parameter : %s (%s)",
                                   e.getParameterType(), e.getParameterType()))
                           .build());

        ProblemDetail pb = ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
            "파라미터 값을 확인해 주세요."
        );

        pb.setInstance(URI.create(request.getRequestURI()));
        pb.setType(URI.create(docs));
        pb.setTitle(HttpStatus.BAD_REQUEST.name());
        pb.setProperty(ERRORS, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(pb);
    }

    // NoHandlerFoundException
    @ExceptionHandler
    public ResponseEntity<ProblemDetail> noHandlerFoundException(
        NoHandlerFoundException e,
        HttpServletRequest request
    ) {
        List<ErrorDto> errors = new ArrayList<>();

        errors.add(ErrorDto.builder().point("").detail("NOT FOUND").build());

        ProblemDetail pb = ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()),
            "URL 을 찾을 수 없습니다."
        );

        pb.setInstance(URI.create(request.getRequestURI()));
        pb.setType(URI.create(docs));
        pb.setTitle(HttpStatus.NOT_FOUND.name());
        pb.setProperty(ERRORS, errors);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(pb);
    }
}
