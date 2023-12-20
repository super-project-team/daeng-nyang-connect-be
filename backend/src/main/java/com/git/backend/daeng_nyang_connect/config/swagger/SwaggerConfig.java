package com.git.backend.daeng_nyang_connect.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("댕냥커넥트 API")
                .description("댕냥커넥트 Swagger UI 테스트")
                .version("1.0.0");
    }

    @Bean
    public GroupedOpenApi getAllApi() {
        return GroupedOpenApi
                .builder()
                .group("ALL")
                .packagesToScan("com.git.backend.daeng_nyang_connect")
                .build();
    }

    @Bean
    public GroupedOpenApi getMyPageApi() {
        return GroupedOpenApi
                .builder()
                .group("myPage_마이페이지")
                .pathsToMatch("/api/myPage/**")
                .build();
    }

    @Bean
    public GroupedOpenApi getAnimalApi() {
        return GroupedOpenApi
                .builder()
                .group("animal_파양동물")
                .pathsToMatch("/api/animal/**")
                .build();
    }

    @Bean
    public GroupedOpenApi getReviewApi() {
        return GroupedOpenApi
                .builder()
                .group("review_입양 후기")
                .pathsToMatch("/api/review/**")
                .build();
    }

    @Bean
    public GroupedOpenApi getLostApi() {
        return GroupedOpenApi
                .builder()
                .group("lost_미아센터")
                .pathsToMatch("/api/lost/**")
                .build();
    }
    @Bean
    public GroupedOpenApi getMateApi() {
        return GroupedOpenApi
                .builder()
                .group("mate_댕냥메이트")
                .pathsToMatch("/api/mate/**")
                .build();
    }

    @Bean
    public GroupedOpenApi getMyPetApi() {
        return GroupedOpenApi
                .builder()
                .group("myPet_나의 댕냥이")
                .pathsToMatch("/api/my_pet/**")
                .build();
    }

    @Bean
    public GroupedOpenApi getTipsApi() {
        return GroupedOpenApi
                .builder()
                .group("tips_댕냥 꿀팁")
                .pathsToMatch("/api/tips/**")
                .build();
    }
}

