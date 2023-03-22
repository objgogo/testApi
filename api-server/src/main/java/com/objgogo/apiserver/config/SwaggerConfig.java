package com.objgogo.apiserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Slf4j
public class SwaggerConfig {

    @Bean
    public Docket api(){

        log.info("Swagger Success!!!");

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
//                .globalOperationParameters(parameterList)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.objgogo.apiserver.api.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(metaData());

    }

    private ApiInfo metaData(){
        return new ApiInfoBuilder()
                .title("OBJGOGO REST API")
                .description("api documentation")
                .license("Apache License Version 2.0")
                .build();
    }

}
