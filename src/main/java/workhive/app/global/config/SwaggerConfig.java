package workhive.app.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("WorkHive API")
                .version("0.0.1")
                .description("WorkHive API 명세입니다.");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement =
                new SecurityRequirement().addList("BearerAuth");

        return new OpenAPI()
                .components(new Components())
                .addSecurityItem(securityRequirement)
                .schemaRequirement("BearerAuth", securityScheme)
                .info(info);
    }
}
