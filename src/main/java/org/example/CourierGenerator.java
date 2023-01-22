package org.example;

import com.github.javafaker.Faker;

public class CourierGenerator {
    public static Courier getRandomCourier() {
        // Import Faker class for random courier generating
        Faker faker = new Faker();
        return new Courier(faker.name().username(), faker.internet().password(), faker.name().firstName());
    }
    public static Courier getRandomCourierWithOnlyCompulsoryFields() {

        Faker faker = new Faker();
        return new Courier(faker.name().username(), faker.internet().password(), null);
    }
    public static Courier getRandomCourierWithCompulsoryFieldsAndEmptyFirstName() {

        Faker faker = new Faker();
        return new Courier(faker.name().username(), faker.internet().password(), "");
    }

    public static Courier getCourierWithPasswordEqualsNull() {
        Faker faker = new Faker();
        return new Courier(faker.name().username(), null,faker.name().firstName());
    }
    public static Courier getCourierWithEmptyPassword() {
        Faker faker = new Faker();
        return new Courier(faker.name().username(), "",faker.name().firstName());
    }

    public static Courier getCourierWithLoginEqualsNull() {
        Faker faker = new Faker();
        return new Courier(null, faker.internet().password(),faker.name().firstName());
    }
    public static Courier getCourierWithEmptyLogin() {
        Faker faker = new Faker();
        return new Courier("", faker.internet().password(),faker.name().firstName());
    }

}
