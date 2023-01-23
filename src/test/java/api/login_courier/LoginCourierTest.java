package api.login_courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class LoginCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandomCourier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Check a courier can login")
    public void courierCanBeCreated() {
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        courierId = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }
    @Test
    @DisplayName("Check there is 404 error when log in with wrong login")
    public void check404ErrorOccuresWhenLogInWithWrongLogin() {
            CourierClient courierClient = new CourierClient();
            Client client = new Client();
            ValidatableResponse response = courierClient.createCourier(courier);
            courier.setLogin("wrongLog!@#");
            ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

            int actualStatusCode = loginResponse.extract().statusCode();
            String expectedMessage = client.getError404Message();
            String actualMessage = loginResponse.extract().path("message");

            System.out.println(actualStatusCode);
            System.out.println(actualMessage);

            assertEquals(SC_NOT_FOUND, actualStatusCode);
            assertEquals(expectedMessage, actualMessage);

        }

    @Test
    @DisplayName("Check there is 404 error when log in with wrong password")
    public void check404ErrorOccuresWhenLogInWithWrongPass() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse response = courierClient.createCourier(courier);
        courier.setPassword("wrongPass!@#");
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        int actualStatusCode = loginResponse.extract().statusCode();
        System.out.println(actualStatusCode);

        assertEquals(SC_NOT_FOUND, actualStatusCode);

    }
    @Test
    @DisplayName("Check there is 404 Error when log in with non existing courier")
    public void check404ErrorOccuresWhenLogInWithNonExistingCourier() {
        CourierClient courierClient = new CourierClient();
        Client client = new Client();

        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        String expectedMessage = client.getError404Message();
        String actualMessage = loginResponse.extract().path("message");
        int actualStatusCode = loginResponse.extract().statusCode();


        System.out.println(actualStatusCode);
        System.out.println(actualMessage);

        assertEquals(SC_NOT_FOUND, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }
    @Test
    @DisplayName("Check a successful login returns courier id")
    public void checkSuccessfulLoginReturnsCourierId() {
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        courierId = loginResponse.extract().path("id");
        String actualResponseBody = loginResponse.toString();

        int actualId = loginResponse.extract().path("id");

        String responseBody = String.valueOf(loginResponse.assertThat().body("id", equalTo(actualId)));
    }


    @After
    public void cleanUp() {
        if ( courierId > 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}
