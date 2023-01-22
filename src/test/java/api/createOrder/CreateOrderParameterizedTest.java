package api.createOrder;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {

    private final int expectedStatusCode;
    private final Order orderBody;

    public CreateOrderParameterizedTest(int expectedStatusCode, Order orderBody) {
        this.expectedStatusCode = expectedStatusCode;
        this.orderBody = orderBody;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {SC_CREATED, OrderGenerator.getOrderWithBlackColor()},
                {SC_CREATED, OrderGenerator.getOrderWithGreyColor()},
                {SC_CREATED, OrderGenerator.getOrderWithBlackAndGreyColor()}
        };
    }

    @Test
    @DisplayName("Check create order method with BLACK, GREY, BLACK and GREY, or without colors")
    public void checkCreateOrderMethodWithAndWithoutColors() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.createOrder(orderBody);

        int actualStatusCode = response.extract().statusCode();
        int actualTrackNumber = response.extract().path("track");

        String responseBody = String.valueOf(response.assertThat().body("track", equalTo(actualTrackNumber)));

        assertEquals(expectedStatusCode, actualStatusCode);


    }


}
