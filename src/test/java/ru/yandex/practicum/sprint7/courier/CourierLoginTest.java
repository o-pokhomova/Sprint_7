package ru.yandex.practicum.sprint7.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.sprint7.steps.CourierSteps;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.*;

@DisplayName("Вход курьера в систему")
public class CourierLoginTest {
    public static final String COURIER_LOGIN = "vasyapupkin";
    public static final String COURIER_PASSWORD = "strongpassword";

    private final CourierSteps courierSteps = new CourierSteps();


    @Before
    public void setUp() {
        // Тут удаляем курьера, потому что предыдущий запуск тестов мог
        // быть прерван и оставить после себя данные.
        // Нельзя просто создать его, потому что если курьер существует, но пароль
        // не совпадает, должен упасть не тест, а этот метод
        courierSteps.cleanupCourier(COURIER_LOGIN, COURIER_PASSWORD);

        courierSteps.create(COURIER_LOGIN, COURIER_PASSWORD, null);
    }

    @After
    public void tearDown() {
        // Тут убираем за собой
        courierSteps.cleanupCourier(COURIER_LOGIN, COURIER_PASSWORD);
    }


    @DisplayName("Вход в систему")
    @Description("Вход курьера в систему с логином и паролем")
    @Test
    public void login() {
        courierSteps.login(COURIER_LOGIN, COURIER_PASSWORD)
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("id", anyOf(
                        isA(Long.class),
                        isA(Integer.class)
                ));
    }

    @DisplayName("Вход в систему без указания логина")
    @Description("Вход курьера в систему без указания логина")
    @Test
    public void loginNoLogin() {
        courierSteps.login(null, COURIER_PASSWORD)
                .then()
                .statusCode(HttpServletResponse.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Вход в систему без указания пароля")
    @Description("Вход курьера в систему без указания пароля")
    @Test
    public void loginNoPassword() {
        courierSteps.login(COURIER_LOGIN, null)
                .then()
                .statusCode(HttpServletResponse.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Вход в систему с некорректным паролем")
    @Description("Вход курьера в систему с паролем, который не совпадает с настоящим паролем пользователя")
    @Test
    public void incorrectPassword() {
        courierSteps.login(COURIER_LOGIN, "blah-blah-blah")
                .then()
                .statusCode(HttpServletResponse.SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Вход в систему с несуществующим логином")
    @Description("Вход несуществующего курьера в систему")
    @Test
    public void loginNotFound() {
        courierSteps.login("another-login", COURIER_PASSWORD)
                .then()
                .statusCode(HttpServletResponse.SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
