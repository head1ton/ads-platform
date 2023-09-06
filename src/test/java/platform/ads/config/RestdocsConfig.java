package platform.ads.config;

import javax.management.Attribute;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

@TestConfiguration  // Test 시에는 Bean 등록이 되지 않으므로 이걸 써준다.
public class RestdocsConfig {

    public static Attribute field(String key, String value) {
        return new Attribute(key, value);
    }

    @Bean
    public RestDocumentationResultHandler handler() {
        return MockMvcRestDocumentation.document(
            "{class-name}/{method-name}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        );
    }
}
