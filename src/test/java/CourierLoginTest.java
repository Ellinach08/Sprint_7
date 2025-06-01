import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierLogin;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class CourierLoginTest {

    private final static String login = RandomStringUtils.randomAlphabetic(10);
    private final static String password = RandomStringUtils.randomAlphabetic(10);
    private final static String firstName = RandomStringUtils.randomAlphabetic(10);
    private final static String badLogin =  RandomStringUtils.randomAlphabetic(10);
    private final static String badPassword = RandomStringUtils.randomAlphabetic(10);

    private final CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp(){
        Courier courier = new Courier(login, password, firstName);
        courierSteps.courierCreate(courier);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка авторизации курьера с передачей всех полей")
    public void loginCourierTest() {
        CourierLogin courierLogin = new CourierLogin(login, password);
        courierSteps.courierLogin(courierLogin)
                .assertThat().statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка ошибки авторизации курьера без передачи поля login")
    public void loginCourierWithoutLoginTest() {
        CourierLogin courierWithoutLogin = new CourierLogin("", password);
        courierSteps.courierLogin(courierWithoutLogin)
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
        CourierLogin courierLogin = new CourierLogin(login, password);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка ошибки авторизации курьера без передачи поля password")
    public void loginCourierWithoutPasswordTest() {
        CourierLogin courierWithoutPass = new CourierLogin(login, "");
        courierSteps.courierLogin(courierWithoutPass)
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
        CourierLogin courierLogin = new CourierLogin(login, password);
    }

    @Test
    @DisplayName("Авторизация курьера с неверным логином")
    @Description("Проверка ошибки авторизации курьера с неверным логином")
    public void loginCourierNonCorrectLoginTest() {
        CourierLogin courierLogin = new CourierLogin(badLogin, password);
        courierSteps.courierLogin(courierLogin)
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с неверным логином")
    @Description("Проверка ошибки авторизации курьера с неверным логином")
    public void loginCourierNonCorrectPasswordTest() {
        CourierLogin courierLogin = new CourierLogin(login, badPassword);
        courierSteps.courierLogin(courierLogin)
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown(){
        CourierLogin courierLogin = new CourierLogin(login, password);
        courierSteps.courierDeleteAfterLogin(courierLogin);
    }
}
