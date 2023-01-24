package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String CREATE_USER_URL = "/api/v1/courier";
    private static final String LOGIN_USER_URL = "/api/v1/courier/login";
    private static final String DELETE_USER_URL = "/api/v1/courier/";



    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(CREATE_USER_URL)
                .then();
    }
    public ValidatableResponse loginCourier(CourierCredentials credentials){
        return  given().log().body()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(LOGIN_USER_URL)
                .then();
    }
      public ValidatableResponse deleteCourier(int id) {
            return given()
                    .spec(getSpec())
                    .body(id)
                    .when()
                    .post(DELETE_USER_URL)
                    .then();
    }
}



