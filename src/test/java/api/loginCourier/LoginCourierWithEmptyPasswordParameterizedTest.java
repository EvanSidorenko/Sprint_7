package api.loginCourier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
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
    @DisplayName("Check a courier with empty Login field or Login field equals null cannot be created")
    public void checkBodyWithEmptyLogin()  {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        int actualStatusCode = loginResponse.extract().statusCode();
        String actualBodyMessage = loginResponse.extract().path("message");

        System.out.println(actualBodyMessage);
        System.out.println(actualStatusCode);

        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBodyMessage, actualBodyMessage);

    }

}
