package platform.ads.api.v1.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import platform.ads.api.v1.BaseTest;
import platform.ads.api.v1.member.dto.JoinDto;
import platform.ads.config.base.ResultCode;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberAuthControllerTest extends BaseTest {

    private final static String PREFIX = "/api/ads/auth";

    @Test
    @DisplayName("회원 가입 성공")
    public void join() throws Exception {
        JoinDto joinDto = new JoinDto("test@test.com", "password", "test", "testnick", "20010101");

        ResultActions resultActions = mockMvc.perform(
                                                 post(PREFIX + "/join")
                                                     .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                     .content(objectMapper.writeValueAsString(joinDto)))
                                             .andDo(print()
                                             );

        String contentAsString = resultActions.andReturn().getResponse()
                                              .getContentAsString(StandardCharsets.UTF_8);
        log.debug("resultActions: " + resultActions + "contentAsString: " + contentAsString);
        assertThat(contentAsString).contains(ResultCode.POST.code);
    }
}