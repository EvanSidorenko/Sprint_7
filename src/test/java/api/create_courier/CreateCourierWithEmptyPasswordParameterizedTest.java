package api.create_courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateCourierWithEmptyPasswordParameterizedTest {
    private final int expectedStatusCode;
    private final String expectedBodyMessage;
    private final Courier courier;


    public CreateCourierWithEmptyPasswordParameterizedTest(int expectedStatusCode, String expectedBodyMessage, Courier courier) {
        this.expectedStatusCode = expectedStatusCode;
        this.expectedBodyMessage = expectedBodyMessage;
        this.courier = courier;
    }


    @Parameterized.Parameters
    public static Object[][] getTestData() {
        Client client = new Client();
        return new Object[][]{
                {SC_BAD_REQUEST, client.getCreateError400Message(), CourierGenerator.getCourierWithEmptyPassword()},
                {SC_BAD_REQUEST, client.getCreateError400Message(), CourierGenerator.getCourierWithPasswordEqualsNull()},
        };
    }

    @Test
    @DisplayName("Check a courier with empty Password field or Password field equals null cannot be created")
    public void checkBodyWithEmptyPassword()  {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse response = courierClient.createCourier(courier);

        int actualStatusCode = response.extract().statusCode();
        String actualBodyMessage = response.extract().path("message");

        if (actualStatusCode == SC_CREATED) {
            System.out.println("This message appears when creating a courier with a bug is successful");
            ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

            int id = loginResponse.extract().path("id");

            // Delete courier after test with a bug is completed

            courierClient.deleteCourier(id);

        }

        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(actualBodyMessage, expectedBodyMessage);

    }

}
