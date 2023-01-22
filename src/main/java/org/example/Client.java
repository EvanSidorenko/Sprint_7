package org.example;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Client {
    private static final String BASE_URL =  "http://qa-scooter.praktikum-services.ru";
    private static final String ERROR_404_MESSAGE =  "Учетная запись не найдена";
    private static final String CREATE_ERROR_400_MESSAGE =  "Недостаточно данных для создания учетной записи";
    private static final String LOGIN_ERROR_400_MESSAGE =  "Недостаточно данных для входа";

    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

    public String getError404Message() {
        return ERROR_404_MESSAGE;
    }
    public String getCreateError400Message() {
        return CREATE_ERROR_400_MESSAGE;
    }
    public String getLoginError400Message() {
        return LOGIN_ERROR_400_MESSAGE;
    }
}
