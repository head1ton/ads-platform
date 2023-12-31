package platform.ads.api.v1.hello.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.ads.config.base.Api;
import platform.ads.config.base.ResultCode;
import platform.ads.config.base.ResultVo;
import platform.ads.api.v1.hello.service.HelloService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix-lv1}")
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/hello")
    public ResponseEntity<Api<ResultVo>> hello() {
        return ResponseEntity.ok(
            Api.<ResultVo>builder()
               .code(ResultCode.SUCCESS.code)
               .message(ResultCode.SUCCESS.message)
               .data(helloService.hello())
               .build()
        );
    }
}
