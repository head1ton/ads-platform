package platform.ads.service;

import org.springframework.stereotype.Service;
import platform.ads.config.base.ResultVo;

@Service
public class HelloService {

    public ResultVo hello() {
        return ResultVo.builder().message("테스트").build();
    }
}
