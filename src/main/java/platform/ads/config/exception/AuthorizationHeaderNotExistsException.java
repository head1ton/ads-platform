package platform.ads.config.exception;

import java.io.Serial;

public class AuthorizationHeaderNotExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4858506469476160448L;

    public AuthorizationHeaderNotExistsException() {
        super("Authorization 헤더가 없습니다.");
    }
}
