package api.create_courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;

public class CreateCourierTests {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandomCourier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Check create courier method returns 201")
    public void courierCanBeCreated() {
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        courierId = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);

    }

    @Test
    @DisplayName("Check there is status 409 Conflict when creating same couriers")
    public void sameCouriersCannotBeCreated() {
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        ValidatableResponse responseWhenCreateSameUser = courierClient.createCourier(courier);

        courierId = loginResponse.extract().path("id");
        int errorResponse = responseWhenCreateSameUser.extract().statusCode();
        assertEquals(SC_CONFLICT, errorResponse);

    }


    @Test
    @DisplayName("Check create courier method returns body with \"ok\": true")
    public void createCourierReturnsBodyOkTrue() {
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));

        courierId = loginResponse.extract().path("id");
        boolean isTrue = response.extract().path("ok");


        Assert.assertTrue("Check if ok contains true", isTrue);

    }

    @After
    public void cleanUp() {
        if (courierId > 0) {
            courierClient.deleteCourier(courierId);
        }
    }

}
