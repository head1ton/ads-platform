package platform.ads.api.v1.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.ads.api.v1.member.dto.JoinDto;
import platform.ads.api.v1.member.service.MemberAuthService;
import platform.ads.api.v1.member.vo.JoinVo;
import platform.ads.config.base.Api;
import platform.ads.config.base.ResultCode;

@RestController
@RequestMapping("${api-prefix-lv1}/auth")
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberAuthService memberAuthService;

    @PostMapping("/join")
    public ResponseEntity<Api<JoinVo>> join(
        @RequestBody @Validated JoinDto joinDto,
        BindingResult bindingResult
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Api.<JoinVo>builder()
                                      .code(ResultCode.POST.code)
                                      .message(ResultCode.POST.message)
                                      .data(memberAuthService.join(joinDto))
                                      .build());
    }
}