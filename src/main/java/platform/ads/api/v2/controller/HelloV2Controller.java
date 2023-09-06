package platform.ads.api.v2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.ads.api.v2.service.HelloV2Service;
import platform.ads.config.base.Api;
import platform.ads.config.base.ResultCode;
import platform.ads.config.base.ResultVo;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class HelloV2Controller {

    private final HelloV2Service helloV2Service;

    @GetMapping("/hello")
    public ResponseEntity<Api<ResultVo>> hello() {
        return ResponseEntity.ok(
            Api.<ResultVo>builder()
               .code(ResultCode.SUCCESS.CODE)
               .message(ResultCode.SUCCESS.MESSAGE)
               .data(helloV2Service.hello())
               .build()
        );
    }
}
