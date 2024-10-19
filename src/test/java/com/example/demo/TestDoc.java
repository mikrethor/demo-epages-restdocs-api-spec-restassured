package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestDoc {

    @LocalServerPort
    private int port;

    private RequestSpecification specification;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider provider) {
        this.specification = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(provider))
                .build();

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void helloEndpoint() {
        var result = given(this.specification)
                .log().all()
                .filter(document("{method-name}"))
                .when()
                .get("/hello")
                .then()
                .statusCode(200)
                .contentType("text/plain")
                .log().all()
                .extract();

        assertThat(result.body().asString()).isEqualTo("Hello");
    }

}
