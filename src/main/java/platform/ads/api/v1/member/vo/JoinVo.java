package platform.ads.api.v1.member.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import platform.ads.common.CommonBaseVo;


@Getter
@Builder
@AllArgsConstructor
public class JoinVo extends CommonBaseVo {

    private String name;
    private String nickname;
    private String email;
}
