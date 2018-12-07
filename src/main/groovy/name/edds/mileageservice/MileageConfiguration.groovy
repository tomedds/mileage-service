package name.edds.mileageservice

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "name.edds.mileageservice")
class MileageConfiguration implements WebMvcConfigurer {

    @Override
    void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("http://localhost:8080");
    }

    @Bean
    Docket mileageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("name.edds.mileageservice"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .ignoredParameterTypes(MetaClass.class)
                .ignoredParameterTypes(org.bson.types.ObjectId.class)
                .apiInfo(apiInfo())
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Mileage Service REST API",
                "Intended to be used with MyMileage application.",
                "0.1",
                "Terms of service",
                new Contact("Tom Edds", "", "tom.edds@gmail.com"),
                "License TBD", "", Collections.emptyList());
    }

}

