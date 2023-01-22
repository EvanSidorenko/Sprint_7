package api.getOrderList;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.CourierCredentials;
import org.example.OrderClient;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderList {
    @Test
    @DisplayName("Check get method returns order list")
    public void checkGetMethodReturnsOrderList() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.getOrderList();

        ArrayList<Integer> actualId = response.extract().path("orders.id");

        String responseBody = String.valueOf(response.assertThat().body("orders.id", equalTo(actualId)));
    }

}
