package sprint7.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sprint7.steps.OrderSteps;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.isA;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    public static final String COLOR_BLACK = "BLACK";
    public static final String COLOR_GREY = "GREY";

    private final OrderSteps orderSteps;
    private final List<String> color;

    public OrderCreateTest(List<String> color) {
        this.orderSteps = new OrderSteps();
        this.color = color;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getSuitableColors() {
        return new Object[][]{
                {List.of(COLOR_BLACK, COLOR_GREY)},
                {List.of(COLOR_BLACK)},
                {List.of(COLOR_GREY)},
                {Collections.emptyList()},
                {Collections.singletonList(null)},
        };
    }

    @DisplayName("Создать заказ")
    @Test
    public void createOrder() {
        orderSteps.createOrder(color)
                .then()
                .statusCode(201)
                .body("track", anyOf(
                        isA(Long.class),
                        isA(Integer.class)
                ));
    }
}
