package sprint7.courier;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import sprint7.steps.CourierSteps;

import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    public static final String COURIER_PASSWORD = "strongpassword";
    public static final String COURIER_FIRST_NAME = "Вася";

    private final CourierSteps courierSteps = new CourierSteps();

    @DisplayName("Вход в систему")
    @Test
    public void login() {
        String login = "vasyapupkin11";
        courierSteps.cleanupCourier(login, COURIER_PASSWORD);
        courierSteps.create(login, COURIER_PASSWORD, COURIER_FIRST_NAME);

        courierSteps.login(login, COURIER_PASSWORD)
                .then()
                .statusCode(200)
                .body("id", anyOf(
                        isA(Long.class),
                        isA(Integer.class)
                ));
    }

    @DisplayName("Вход в систему без указания логина")
    @Test
    public void loginNoLogin() {
        courierSteps.login(null, COURIER_PASSWORD)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Вход в систему без указания пароля")
    @Test
    public void loginNoPassword() {
        String login = "vasyapupkin12";
        courierSteps.cleanupCourier(login, COURIER_PASSWORD);
        courierSteps.create(login, COURIER_PASSWORD, COURIER_FIRST_NAME);

        courierSteps.login(login, null)
                .then()
                .statusCode(504)
                .body("message", equalTo("Недостаточно данных для входа"));

        courierSteps.cleanupCourier(login, COURIER_PASSWORD);
    }

    @DisplayName("Вход в систему с некорректным паролем")
    @Test
    public void incorrectPassword() {
        String login = "vasyapupkin13";
        courierSteps.cleanupCourier(login, COURIER_PASSWORD);
        courierSteps.create(login, COURIER_PASSWORD, COURIER_FIRST_NAME);

        courierSteps.login(login, "blah-blah-blah")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        courierSteps.cleanupCourier(login, COURIER_PASSWORD);
    }

    @DisplayName("Вход в систему с несуществующим логином")
    @Test
    public void loginNotFound() {
        String login = "vasyapupkin14";
        courierSteps.cleanupCourier(login, COURIER_PASSWORD);

        courierSteps.login(login, COURIER_PASSWORD)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        courierSteps.cleanupCourier(login, COURIER_PASSWORD);
    }
}
