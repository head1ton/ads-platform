package platform.ads.config.base;

public enum ResultCode {
    SUCCESS("0200", "success"),
    POST("0201", "201 success"),
    FAIL("0400", "failure"),
    ;

    public final String code;
    public final String message;
    ResultCode(String CODE, String MESSAGE) {
        this.code = CODE;
        this.message = MESSAGE;
    }
}
