package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.models.Order;

import static io.restassured.RestAssured.given;
import static ru.praktikum.constants.ApiConstants.*;

public class OrderSteps {

    public static RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ROOT);
    }
    @Step("Создание нового заказа")
    public ValidatableResponse orderCreate(Order order) {
        return spec()
                .body(order)
                .post(ORDER_POST_CREATE)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse orderGetList(){
        return spec()
                .baseUri(ROOT)
                .get(ORDER_GET_LIST)
                .then();
    }
}
