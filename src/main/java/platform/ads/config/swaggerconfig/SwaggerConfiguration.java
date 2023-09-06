package platform.ads.config.swaggerconfig;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private Info info;

    @Bean
    public GroupedOpenApi getApiV1() {
        return GroupedOpenApi.builder()
                             .group("ADS API V1")
                             .pathsToMatch("/api/ads/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi getApiV2() {
        return GroupedOpenApi.builder()
                             .group("E-COMMERCE API V2")
                             .pathsToMatch("/api/ecommerce/**")
                             .build();
    }

    @Bean
    public OpenAPI getOpenAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
            .type(Type.HTTP).scheme("bearer").bearerFormat("JWT")
            .in(In.HEADER).name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
            .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
            .security(Collections.singletonList(securityRequirement))
            .info(getInfo());
    }

    public Info getInfo() {
        return new Info()
            .version("1.0.0")
            .description("ADS REST API DOC")
            .title("ADS API DEV");
    }
}
