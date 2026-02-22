package sprint7.courier;

import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Test;
import sprint7.dto.courier.IdDto;
import sprint7.steps.CourierSteps;

public class CourierDeleteTest {
    public static final String COURIER_PASSWORD = "strongpassword";
    private final CourierSteps courierSteps = new CourierSteps();

    @DisplayName("Удаление курьера")
    @Test
    public void delete() {
        String login = "vasyapupkin31";
        courierSteps.create(login, COURIER_PASSWORD, null);
        IdDto id = courierSteps.login(login, COURIER_PASSWORD).body().as(IdDto.class);

        courierSteps.delete(id.getId()).then()
                .statusCode(200)
                .body("ok", Matchers.equalTo(true));
    }

    @DisplayName("Удаление несуществующего курьера")
    @Test
    public void deleteNonExisting() {
        courierSteps.delete((long) Integer.MAX_VALUE).then()
                .statusCode(404)
                .body("message", Matchers.equalTo("Курьера с таким id нет"));
    }

    @DisplayName("Удаление курьера без id")
    @Test
    public void deleteNoId() {
        courierSteps.delete(null).then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для удаления курьера"));
    }
}
