import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierLogin;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {

    private final static String login = RandomStringUtils.randomAlphabetic(10);
    private final static String password = RandomStringUtils.randomAlphabetic(10);
    private final static String firstName = RandomStringUtils.randomAlphabetic(10);

    private static final String MSG_LOGIN_EXISTS = "Этот логин уже используется. Попробуйте другой.";
    private static final String MSG_COURIER_REQUIRED_FIELDS = "Недостаточно данных для создания учетной записи";

    private final CourierSteps courierSteps = new CourierSteps();


    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера с валидными данными")
    public void createCourierTest() {
        Courier courier = new Courier(login, password, firstName);
        courierSteps.courierCreate(courier)
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка ошибки создания двух курьеров с одинаковым набором данных")
    public void createTwoCouriersTest() {
        Courier courier = new Courier(login, password, firstName);
        courierSteps.courierCreate(courier)
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        courierSteps.courierCreate(courier)
                .assertThat().statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", equalTo(MSG_LOGIN_EXISTS));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка ошибки создания курьера без передачи поля login")
    public void createCourierWithoutLoginTest() {
        Courier courier = new Courier(login, null, firstName);
        courierSteps.courierCreate(courier)
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo(MSG_COURIER_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Проверка ошибки создания курьера без передачи поля firstName")
    public void createCourierWithoutFirstNameTest() {
        Courier courier = new Courier(login, password, null);
        courierSteps.courierCreate(courier)
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка ошибки создания курьера без передачи поля password")
    public void createCourierWithoutPasswordTest() {
        Courier courier = new Courier(null, password, firstName);
        courierSteps.courierCreate(courier)
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo(MSG_COURIER_REQUIRED_FIELDS));
    }

    @After
    public void tearDown(){
            CourierLogin courierLogin = new CourierLogin(login, password);
            courierSteps.courierDeleteAfterLogin(courierLogin);
    }

}
