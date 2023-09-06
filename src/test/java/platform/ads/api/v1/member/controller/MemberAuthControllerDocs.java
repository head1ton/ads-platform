package platform.ads.api.v1.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import platform.ads.api.v1.ControllerBaseTest;
import platform.ads.api.v1.member.dto.JoinDto;
import platform.ads.config.base.ResultCode;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class MemberAuthControllerDocs extends ControllerBaseTest {

    private static final String PREFIX = "/api/ads/auth";

    @Test
    @DisplayName("회원 가입 성공")
    public void join() throws Exception {
        JoinDto joinDto = new JoinDto("test@test.com", "password", "test", "testnick", "20010101");

        ResultActions resultActions = mockMvc.perform(
                                                 post(PREFIX + "/join")
                                                     .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                     .content(objectMapper.writeValueAsString(joinDto)))
                                             .andDo(print());

        String contentAsString = resultActions.andReturn().getResponse()
                                              .getContentAsString(StandardCharsets.UTF_8);
        log.debug("resultActions: " + resultActions + "contentAsString: " + contentAsString);
        assertThat(contentAsString).contains(ResultCode.POST.code);

        // docs field
        resultActions.andDo(docs.document(
            // Request
            requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("birth").type(JsonFieldType.STRING).description("생년월일 ex) 20010101")
                                      .optional()
            ),
            // Response
            responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description("결과 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일")
            )
        ));
    }
}
