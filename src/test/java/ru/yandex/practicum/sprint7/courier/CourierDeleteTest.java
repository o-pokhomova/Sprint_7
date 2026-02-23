package ru.yandex.practicum.sprint7.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.sprint7.dto.courier.IdDto;
import ru.yandex.practicum.sprint7.steps.CourierSteps;

import javax.servlet.http.HttpServletResponse;

@DisplayName("Удаление курьера")
public class CourierDeleteTest {
    public static final String COURIER_LOGIN = "vasyapupkin";
    public static final String COURIER_PASSWORD = "strongpassword";
    private final CourierSteps courierSteps = new CourierSteps();
    private IdDto courierId;

    @Before
    public void setUp() {
        // Тут удаляем курьера, потому что предыдущий запуск тестов мог
        // быть прерван и оставить после себя данные.
        // Нельзя просто создать его, потому что если курьер существует, но пароль
        // не совпадает, должен упасть не тест, а этот метод
        courierSteps.cleanupCourier(COURIER_LOGIN, COURIER_PASSWORD);

        courierSteps.create(COURIER_LOGIN, COURIER_PASSWORD, null);
        courierId = courierSteps.login(COURIER_LOGIN, COURIER_PASSWORD).body().as(IdDto.class);
    }

    @After
    public void tearDown() {
        // Тут убираем за собой
        courierSteps.cleanupCourier(COURIER_LOGIN, COURIER_PASSWORD);
    }

    @DisplayName("Удаление курьера")
    @Description("Удаление курьера из системы")
    @Test
    public void delete() {
        courierSteps.delete(courierId.getId()).then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("ok", Matchers.equalTo(true));
    }

    @DisplayName("Удаление несуществующего курьера")
    @Description("Удаление несуществующего курьера из системы")
    @Test
    public void deleteNonExisting() {
        courierSteps.delete((long) Integer.MAX_VALUE).then()
                .statusCode(HttpServletResponse.SC_NOT_FOUND)
                .body("message", Matchers.equalTo("Курьера с таким id нет"));
    }

    @DisplayName("Удаление курьера без id")
    @Description("Удаление курьера из системы: не указан ID")
    @Test
    public void deleteNoId() {
        courierSteps.delete(null).then()
                .statusCode(HttpServletResponse.SC_BAD_REQUEST)
                .body("message", Matchers.equalTo("Недостаточно данных для удаления курьера"));
    }
}
