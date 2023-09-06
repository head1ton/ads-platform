package platform.ads.api.v1.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import platform.ads.api.BaseTest;
import platform.ads.api.v1.ControllerBaseTest;
import platform.ads.api.v1.member.domain.Member;
import platform.ads.api.v1.member.dto.JoinDto;
import platform.ads.api.v1.member.repository.MemberRepository;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberAuthControllerTest extends ControllerBaseTest {

    private final static String PREFIX = "/api/ads/auth";
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("중복 회원 가입 실패")
    public void joinFail() throws Exception {
        JoinDto joinDto = new JoinDto("test1@test.com", "password", "test1", "testnick1",
            "20010101");

        memberRepository.save(Member.of(joinDto));  // 저장 1

        ResultActions resultActions = mockMvc.perform(
                                                 post(PREFIX + "/join")     // 저장 2
                                                     .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                     .content(objectMapper.writeValueAsString(joinDto)))
                                             .andDo(print());

        String contentAsString = resultActions.andReturn().getResponse()
                                              .getContentAsString(StandardCharsets.UTF_8);
        log.debug("resultActions: " + resultActions + "contentAsString: " + contentAsString);
        assertThat(contentAsString).contains("400");
    }
}