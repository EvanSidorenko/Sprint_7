package api.createCourier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Client;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateCourierWithEmptyLoginParameterizedTest {
    private final int expectedStatusCode;
    private final String expectedBodyMessage;
    private final Courier courier;


    public CreateCourierWithEmptyLoginParameterizedTest(int expectedStatusCode, String expectedBodyMessage, Courier courier) {
        this.expectedStatusCode = expectedStatusCode;
        this.expectedBodyMessage = expectedBodyMessage;
        this.courier = courier;
    }


    @Parameterized.Parameters
    public static Object[][] getTestData() {
        Client client = new Client();
        return new Object[][]{
                {SC_BAD_REQUEST, client.getCreateError400Message(), CourierGenerator.getCourierWithEmptyLogin()},
                {SC_BAD_REQUEST, client.getCreateError400Message(), CourierGenerator.getCourierWithLoginEqualsNull()},
        };
    }

    @Test
    @DisplayName("Check a courier with empty Login field or Login field equals null cannot be created")
    public void checkBodyWithEmptyLogin()  {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse response = courierClient.createCourier(courier);

        int actualStatusCode = response.extract().statusCode();
        String actualBodyMessage = response.extract().path("message");

        System.out.println(actualBodyMessage);
        System.out.println(actualStatusCode);

        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBodyMessage, actualBodyMessage);

    }

}
