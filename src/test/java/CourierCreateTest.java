import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierLogin;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {

    public static String login = RandomStringUtils.randomAlphabetic(10);
    public static String password = RandomStringUtils.randomAlphabetic(10);
    public static String firstName = RandomStringUtils.randomAlphabetic(10);

    private static final String MSG_LOGIN_EXISTS = "Этот логин уже используется. Попробуйте другой.";
    private static final String MSG_COURIER_REQUIRED_FIELDS = "Недостаточно данных для создания учетной записи";


    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера с валидными данными")
    public void createCourier() {
        Courier courier = new Courier(login, password, firstName);
        CourierLogin courierLogin = new CourierLogin(login, password);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courier)
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
        courierSteps.courierDeleteAfterLogin(courierLogin);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка ошибки создания двух курьеров с одинаковым набором данных")
    public void createTwoCouriers() {
        Courier courier = new Courier(login, password, firstName);
        CourierLogin courierLogin = new CourierLogin(login, password);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courier)
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
        courierSteps.courierCreate(courier)
                .assertThat().body("message", equalTo(MSG_LOGIN_EXISTS))
                .and()
                .statusCode(409);
        courierSteps.courierDeleteAfterLogin(courierLogin);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка ошибки создания курьера без передачи поля login")
    public void createCourierWithoutLogin() {
        Courier courier = new Courier(login, null, firstName);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courier)
                .assertThat().body("message", equalTo(MSG_COURIER_REQUIRED_FIELDS))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка ошибки создания курьера без передачи поля password")
    public void createCourierWithoutPassword() {
        Courier courier = new Courier(null, password, firstName);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courier)
                .assertThat().body("message", equalTo(MSG_COURIER_REQUIRED_FIELDS))
                .and()
                .statusCode(400);
    }

}
