package api.createCourier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateCourierWithCompulsoryFieldsParameterizedTest {
    private final int expectedStatusCode;
    private final boolean expectedBody;
    private final Courier courier;


    public CreateCourierWithCompulsoryFieldsParameterizedTest(int expectedStatusCode, boolean expectedBody, Courier courier) {
        this.expectedStatusCode = expectedStatusCode;
        this.expectedBody = expectedBody;
        this.courier = courier;
    }


    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {SC_CREATED, true, CourierGenerator.getRandomCourierWithOnlyCompulsoryFields()},
                {SC_CREATED, true, CourierGenerator.getRandomCourierWithCompulsoryFieldsAndEmptyFirstName()},
        };
    }

    @Test
    @DisplayName("Check a courier with compulsory fields can be created")
    public void checkBodyWithCompulsoryFields()  {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        int actualStatusCode = response.extract().statusCode();
        boolean actualBody = response.extract().path("ok");

        System.out.println(actualBody);
        System.out.println(actualStatusCode);

        int id = loginResponse.extract().path("id");

        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(actualBody, expectedBody);

        // Delete courier after test is completed

        courierClient.deleteCourier(id);

    }

}
