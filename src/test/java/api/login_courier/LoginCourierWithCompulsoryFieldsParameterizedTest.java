package api.login_courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginCourierWithCompulsoryFieldsParameterizedTest {
    private final int expectedStatusCode;
    private final Courier courier;

    public LoginCourierWithCompulsoryFieldsParameterizedTest(int expectedStatusCode, Courier courier) {
        this.expectedStatusCode = expectedStatusCode;
        this.courier = courier;
    }


    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {SC_OK, CourierGenerator.getRandomCourierWithOnlyCompulsoryFields()},
                {SC_OK, CourierGenerator.getRandomCourierWithCompulsoryFieldsAndEmptyFirstName()},
        };
    }

    @Test
    @DisplayName("Check a courier with compulsory fields can be created")
    public void checkBodyWithCompulsoryFields()  {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse response = courierClient.createCourier(courier);

        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        int actualStatusCode = loginResponse.extract().statusCode();

        System.out.println(actualStatusCode);

        int courierId = loginResponse.extract().path("id");

        assertEquals(expectedStatusCode, actualStatusCode);

        // Delete courier after test is completed

        courierClient.deleteCourier(courierId);

    }

}
