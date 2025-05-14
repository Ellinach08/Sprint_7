package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierLogin;
import ru.praktikum.models.CourierResponse;

import static io.restassured.RestAssured.given;
import static ru.praktikum.constants.ApiConstants.*;

public class CourierSteps {

    public static RequestSpecification spec(){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ROOT);
    }

    @Step("Создание курьера")
    public ValidatableResponse courierCreate(Courier courier) {
        return spec()
                .body(courier)
                .post(COURIER_POST_CREATE)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse courierLogin(CourierLogin courierLogin) {
        return spec()
                .body(courierLogin)
                .when()
                .post(COURIER_POST_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public void courierDelete(int courierId) {
        spec()
                .when()
                .delete(COURIER_DELETE + courierId)
                .then();
    }

    @Step("Получение ID курьера и его удаление")
    public void courierDeleteAfterLogin(CourierLogin courierDeleteAfterLogin) {
        Response response = courierLogin(courierDeleteAfterLogin)
                .extract().response();
        CourierResponse courierResponse = response.as(CourierResponse.class);
        int courierId = courierResponse.getId();
        courierDelete(courierId);
    }

}
