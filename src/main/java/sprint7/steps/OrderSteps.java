package sprint7.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import sprint7.dto.order.CreateOrderDto;

import java.util.List;

public class OrderSteps extends BaseSteps {
    public static final CreateOrderDto ORDER = new CreateOrderDto(
            "Иван",
            "Петров",
            "Ленинградский проспект, 20а",
            "Динамо",
            "+7(123) 456-78-90",
            10,
            "2026-02-24",
            "Оставить у двери, не звонить",
            List.of("BLACK")
    );

    @Step("Создание заказа")
    public Response createOrder(List<String> colors) {
        CreateOrderDto request = ORDER.withColors(colors);

        return prepareRestSpec()
                .and().body(request)
                .post("/api/v1/orders");
    }

    @Step("Получение заказов")
    public Response listOrders(
            Long courierId,
            List<String> nearestStations,
            Integer limit,
            Integer page
    ) {
        RequestSpecification spec = prepareRestSpec();
        if (courierId != null) {
            spec = spec.param("courierId", courierId);
        }
        if (nearestStations != null) {
            spec = spec.param("nearestStations", nearestStations);
        }
        if (limit != null) {
            spec = spec.param("limit", limit);
        }
        if (page != null) {
            spec = spec.param("page", page);
        }
        return spec.get("/api/v1/orders");
    }

    @Step("Получить заказ по треку")
    public Response getOrder(int track) {
        return prepareRestSpec()
                .param("t", track)
                .get("/v1/orders/track");
    }


    @Step("Принять заказ")
    public Response accept(Long orderId, Long courierId) {
        RequestSpecification spec = prepareRestSpec();
        if (courierId != null) {
            spec = spec.param("courierId", courierId);
        }
        String orderIdStr = orderId == null
                ? ""
                : orderId.toString();
        return spec.put("/v1/orders/accept/" + orderIdStr);
    }
}
