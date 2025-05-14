import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import ru.praktikum.models.Order;
import ru.praktikum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.instanceOf;
import static ru.praktikum.constants.ScooterColors.BLACK_COLOR;
import static ru.praktikum.constants.ScooterColors.GREY_COLOR;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public OrderCreateTest(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String [] color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                {"Иван", "Иванов", "Адрес 1", "1", "+7 999 188 18 18", 1, "2025-07-07", "Комментарий 1" , new String[]{BLACK_COLOR}},
                {"Петр", "Петров", "Адрес 2", "2", "+7 999 288 28 28", 2, "2025-08-08", "Комментарий 2", new String[]{GREY_COLOR}},
                {"Александр", "Александров", "Адрес 3", "3", "+7 999 388 38 38", 3, "2025-09-09", "Комментарий 3", new String[]{BLACK_COLOR, GREY_COLOR}},
                {"Алексей", "Алексеев", "Адрес 4", "4", "+7 999 488 48 48", 4, "2025-09-09", "Комментарий 4", new String[]{}},
                {"Сергей", "Сергеев", "Адрес 5", "5", "+7 999 588 58 58", 5, "2025-10-10", "Комментарий 5", null}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказов на самокаты с разными цветами")
    public void orderCreate() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        OrderSteps orderSteps = new OrderSteps();
        orderSteps.orderCreate(order)
                .assertThat().body("track", instanceOf(Integer.class))
                .and()
                .statusCode(201);
    }
}
