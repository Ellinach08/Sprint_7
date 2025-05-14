import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получение списка заказов с возвращаемым списком")
    public void getOrderList(){
        OrderSteps orderSteps = new OrderSteps();
        orderSteps.orderGetList()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
