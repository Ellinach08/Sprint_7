import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.praktikum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получение списка заказов с возвращаемым списком")
    public void getOrderListTest(){
        OrderSteps orderSteps = new OrderSteps();
        orderSteps.orderGetList()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .and()
                .body("orders", notNullValue());
    }
}
