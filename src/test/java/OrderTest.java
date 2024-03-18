import client.OrderClient;
import dto.OrderCreateRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {

    private static OrderClient orderClient;
    private OrderCreateRequest orderCreateRequest;

    public OrderTest(OrderCreateRequest orderCreateRequest) {
        this.orderCreateRequest = orderCreateRequest;
    }

    @Parameterized.Parameters
    public static Object[][] testOrderData() {
        return new Object[][]{
                {new OrderCreateRequest("Serj", "tza", "Moscow", "test", "89067822866", 10, "2024-01-01", "Жду", new String[]{"BLACK"})},
                {new OrderCreateRequest("Серж", "тза", "Москва", "0", "+7 906 123 11 22", 2, "2024-02-01", "жду", new String[]{"GREY"})},
                {new OrderCreateRequest("Валера", "Борисыч", "Борисыч", "1", "+1233333333", 5, "2024-03-01", "Валерий Борисыч", new String[]{"BLACK", "GREY"})},
                {new OrderCreateRequest("каво", "зачем", "почему", "Выхино", "3333333333", 1, "2024-04-01", "ну и где", new String[]{})},
        };
    }

    @BeforeClass
    public static void setUp() {
        orderClient = new OrderClient();
    }


    @Test
    @DisplayName("Создание заказа самоката с разным цветом")
    @Description("Проверка создания заказа самоката с разным цветом")
    public void createOrder() {
        orderClient.setOrder(orderCreateRequest);
        orderClient.create().then().statusCode(SC_CREATED).and().assertThat().body("track", notNullValue());
    }
}
