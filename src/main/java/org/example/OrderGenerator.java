package org.example;

import com.github.javafaker.Faker;

import java.util.concurrent.TimeUnit;

public class OrderGenerator {
    public static Order getOrderWithBlackColor() {
        final String[] BLACK_COLOR = {"BLACK"};
        Faker faker = new Faker();
        return new Order(faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(), faker.number().numberBetween(1,5), faker.phoneNumber().cellPhone(), faker.number().numberBetween(1,7),
                faker.date().future(1, TimeUnit.DAYS), faker.lordOfTheRings().toString(), BLACK_COLOR);
    }
    public static Order getOrderWithGreyColor() {
        final String[] GREY_COLOR = {"GREY"};
        Faker faker = new Faker();
        return new Order(faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(), faker.number().numberBetween(1,5), faker.phoneNumber().cellPhone(), faker.number().numberBetween(1,7),
                faker.date().future(1, TimeUnit.DAYS), faker.lordOfTheRings().toString(), GREY_COLOR);
    }
    public static Order getOrderWithBlackAndGreyColor() {
        final String[] BLACK_AND_GREY_COLOR = {"BLACK, GREY"};
        Faker faker = new Faker();
        return new Order(faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(), faker.number().numberBetween(1,5), faker.phoneNumber().cellPhone(), faker.number().numberBetween(1,7),
                faker.date().future(1, TimeUnit.DAYS), faker.lordOfTheRings().toString(), BLACK_AND_GREY_COLOR);
    }
}
