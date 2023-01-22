package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String CREATE_ORDER_URL = "/api/v1/orders";
    private static final String GET_ORDER_LIST_URL = "/api/v1/orders";

    public ValidatableResponse createOrder(Order order) {
        return given().log().body()
                .spec(getSpec())
                .body(order)
                .when()
                .post(CREATE_ORDER_URL)
                .then().log().body();
    }
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getSpec())
                .when()
                .get(GET_ORDER_LIST_URL)
                .then();
    }

}
