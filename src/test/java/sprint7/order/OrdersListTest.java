package sprint7.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import sprint7.steps.OrderSteps;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {
    public static final String COLOR_BLACK = "BLACK";
    private final OrderSteps orderSteps = new OrderSteps();

    @DisplayName("Список заказов с ограничением 1")
    @Test
    public void listOrdersWithLimit() {
        // На всякий случай создадим заказ, вдруг кто-нибудь почистит тестовую базу
        orderSteps.createOrder(List.of(COLOR_BLACK));

        orderSteps.listOrders(null, null, 1, 0)
                .then()
                .body("orders", notNullValue())
                .body("orders", hasSize(1))
                .statusCode(200);
    }

    @DisplayName("Список заказов без ограничения")
    @Test
    public void listOrders() {
        // На всякий случай создадим заказ, вдруг кто-нибудь почистит тестовую базу
        for (int i = 0; i < 30; i++) {
            orderSteps.createOrder(List.of(COLOR_BLACK));
        }

        orderSteps.listOrders(null, null, null, null)
                .then()
                .body("orders", notNullValue())
                .body("orders", hasSize(30))
                .statusCode(200);
    }
}
