package platform.ads.config.base;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    @NotNull
    private String code;
    @NotNull
    private String message;
    @NotNull
    private T data;
}
