# demo-epages-restdocs-api-spec-restassured

Demo to show that spring web shouldn't be on pages restdoc classpath.

## Goal

This project is based on spring boot webflux and the purpose is to show that "spring-boot-starter-web" creates a pb when we use restdocs-api-spec with "spring-boot-starter-webflux".

The "TestDoc" contains the "helloEndpoint" test that test a simple "/hello" endpoint that returns "Hello".

The following command fails :

``
./mvnw package
``

And if we uncomment the lines 65 to 70 and it removes "spring-boot-starter-web" from the classpath and if we rerun  

``
./mvnw package
``

It works.
