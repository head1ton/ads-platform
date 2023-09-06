package platform.ads.api.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import platform.ads.api.BaseTest;
import platform.ads.config.RestdocsConfig;

@Import(RestdocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public class ControllerBaseTest extends BaseTest {

    @Autowired
    protected MockMvc mock;
    @Autowired
    protected RestDocumentationResultHandler docs;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected String MEMBER_EMAIL = "test@ads.com";
    protected String PASSWORD = "password";

    @BeforeEach
    void setUp(
        final WebApplicationContext context,
        final RestDocumentationContextProvider provider
    ) throws Exception {
        this.mock = MockMvcBuilders.webAppContextSetup(context)
                                   .apply(
                                       MockMvcRestDocumentation.documentationConfiguration(provider)
                                                               .uris()
                                                               .withScheme("http")
                                                               .withHost("127.0.0.1")
                                                               .withPort(80)
                                   )
                                   .alwaysDo(MockMvcResultHandlers.print())
                                   .alwaysDo(docs)
                                   .addFilters(
                                       new CharacterEncodingFilter("UTF-8", true)
                                   )
                                   .build();
    }
}
