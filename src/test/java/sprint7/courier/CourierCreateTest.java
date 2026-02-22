package sprint7.courier;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import sprint7.steps.CourierSteps;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {
    public static final String COURIER_PASSWORD = "strongpassword";
    public static final String COURIER_FIRST_NAME = "Вася";

    private final CourierSteps courierSteps = new CourierSteps();

    @DisplayName("Создать курьера")
    @Test
    public void create() {
        String login = "vasyapupkin1";
        courierSteps.cleanupCourier(login, COURIER_PASSWORD);

        courierSteps.create(login, COURIER_PASSWORD, COURIER_FIRST_NAME)
                .then().statusCode(201)
                .assertThat().body("ok", equalTo(true));

        courierSteps.cleanupCourier(login, COURIER_PASSWORD);
    }

    @DisplayName("Создать курьера второй раз с одинаковым логином")
    @Test
    public void createTwice() {
        String login = "vasyapupkin2";
        courierSteps.cleanupCourier(login, COURIER_PASSWORD);

        // Создаём курьера в первый раз
        courierSteps.create(login, COURIER_PASSWORD, COURIER_FIRST_NAME);
        // Повторно создаём курьера
        courierSteps.create(login, COURIER_PASSWORD, COURIER_FIRST_NAME)
                .then()
                .statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется"));

        courierSteps.cleanupCourier(login, COURIER_PASSWORD);
    }

    @DisplayName("Создать курьера без указания логина")
    @Test
    public void createNoLogin() {
        courierSteps.create(null, COURIER_PASSWORD, COURIER_FIRST_NAME)
                .then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Создать курьера без указания пароля")
    @Test
    public void createNoPassword() {
        String login = "vasyapupkin3";
        courierSteps.cleanupCourier(login, COURIER_PASSWORD);

        courierSteps.create(login, null, COURIER_FIRST_NAME)
                .then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
