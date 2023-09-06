package platform.ads.config.base;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Builder
public class ErrorDto {

    @NotNull(message = "point 는 필수 정보 입니다.")
    private String point;
    @NotNull(message = "detail 은 필수 정보 입니다.")
    private String detail;
}
