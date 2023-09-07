package platform.ads.api.v1.member.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import platform.ads.common.CommonBaseVo;

@Builder
@Getter
@AllArgsConstructor
public class LoginVo extends CommonBaseVo {

    private String type;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpired;
    private Long refreshTokenExpired;
}
