package platform.ads.config.base;

public enum ResultCode {
    SUCCESS("0200", "success"),
    POST("0201", "201 success"),
    FAIL("0400", "failure"),
    ;

    public final String CODE;
    public final String MESSAGE;
    ResultCode(String CODE, String MESSAGE) {
        this.CODE = CODE;
        this.MESSAGE = MESSAGE;
    }
}
