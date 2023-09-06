package platform.ads.api.v2.hello.service;

import org.springframework.stereotype.Service;
import platform.ads.config.base.ResultVo;

@Service
public class HelloV2Service {

    public ResultVo hello() {
        return ResultVo.builder().message("테스트").build();
    }
}
