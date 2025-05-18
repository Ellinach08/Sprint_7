package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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

    public Response orderCreate(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_POST_CREATE);
    }


    @Step("Получение списка заказов")
    public ValidatableResponse orderGetList(){
        return spec()
                .get(ORDER_GET_LIST)
                .then();
    }

    @Step("Отмена заказа")
    public Response orderCancel (int trackNum) {
        return spec()
                .queryParam("track", trackNum)
                .put(ORDER_CANCEL);
    }

    @Step
    public void cancel (int trackNum){
        spec()
                .when()
                .put(ORDER_CANCEL + trackNum)
                .then();
    }
}
