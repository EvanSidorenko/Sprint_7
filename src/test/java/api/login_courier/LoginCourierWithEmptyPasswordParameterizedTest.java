package api.login_courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginCourierWithEmptyPasswordParameterizedTest {
    private final int expectedStatusCode;
    private final String expectedBodyMessage;
    private final Courier courier;


    public LoginCourierWithEmptyPasswordParameterizedTest(int expectedStatusCode, String expectedBodyMessage, Courier courier) {
        this.expectedStatusCode = expectedStatusCode;
        this.expectedBodyMessage = expectedBodyMessage;
        this.courier = courier;
    }


    @Parameterized.Parameters
    public static Object[][] getTestData() {
        Client client = new Client();
        return new Object[][]{
                {SC_BAD_REQUEST, client.getLoginError400Message(), CourierGenerator.getCourierWithEmptyPassword()},
                {SC_BAD_REQUEST, client.getLoginError400Message(), CourierGenerator.getCourierWithPasswordEqualsNull()},
        };
    }

    @Test
    @DisplayName("Check a courier with empty Password field or Login field equals null cannot be created")
    public void checkBodyWithEmptyPassword()  {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        int actualStatusCode = loginResponse.extract().statusCode();
        String actualBodyMessage = loginResponse.extract().path("message");


        if (actualStatusCode == SC_OK) {
            System.out.println("This message appears when log in a courier with a bug is successful");

            int id = loginResponse.extract().path("id");

            // Delete courier after test is completed

            courierClient.deleteCourier(id);

        }

        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBodyMessage, actualBodyMessage);

    }

}
