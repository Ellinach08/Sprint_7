import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierLogin;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class CourierLoginTest {

    public static String login = RandomStringUtils.randomAlphabetic(10);
    public static String password = RandomStringUtils.randomAlphabetic(10);
    public static String firstName = RandomStringUtils.randomAlphabetic(10);

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка авторизации курьера с передачей всех полей")
    public void loginCourier() {
        Courier courier = new Courier(login, password, firstName);
        CourierLogin courierLogin = new CourierLogin(login, password);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courier);
        courierSteps.courierLogin(courierLogin)
                .assertThat().body("id", instanceOf(Integer.class))
                .and()
                .statusCode(200);
        courierSteps.courierDeleteAfterLogin(courierLogin);
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка ошибки авторизации курьера без передачи поля login")
    public void loginCourierWithoutLogin() {
        Courier courier = new Courier(login, password, firstName);
        CourierLogin courierWithoutLogin = new CourierLogin("", password);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courier);
        courierSteps.courierLogin(courierWithoutLogin)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        CourierLogin courierLogin = new CourierLogin(login, password);
        courierSteps.courierDeleteAfterLogin(courierLogin);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка ошибки авторизации курьера без передачи поля password")
    public void loginCourierWithoutPassword() {
        Courier courier = new Courier(login, password, firstName);
        CourierLogin courierWithoutPass = new CourierLogin(login, "");
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courier);
        courierSteps.courierLogin(courierWithoutPass)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        CourierLogin courierLogin = new CourierLogin(login, password);
        courierSteps.courierDeleteAfterLogin(courierLogin);
    }

    @Test
    @DisplayName("Авторизация курьера с несуществующими данные")
    @Description("Проверка ошибки авторизации курьера с несуществующими данными")
    public void CourierNotExistLogin () {
        CourierLogin courierLogin = new CourierLogin(login, password);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierLogin(courierLogin)
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

}
