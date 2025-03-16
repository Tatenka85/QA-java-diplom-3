import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Assert;


public class UserCreate {

    private String refreshToken;
    private String accessToken;
    private final String email = "test" + System.currentTimeMillis() + "@example.com";
    private final String password = "TestPassword123!";

    public UserCreate() {
    }

    @Description("Создание уникального пользователя и сохранение токенов")
    public void createUser() {
        String name = "Tata";
        Response response = UserSteps.registerUser(email, password, name);
        System.out.println("🔹 Ответ сервера на создание: " + response.asString());

        response.then().statusCode(200);
        Assert.assertTrue(response.jsonPath().getBoolean("success"));

        Response loginResponse = UserSteps.loginUser(email, password);
        System.out.println("🔹 Ответ сервера на логин: " + loginResponse.asString());

        // Поле для хранения accessToken
        this.accessToken = loginResponse.jsonPath().getString("accessToken");
        this.refreshToken = loginResponse.jsonPath().getString("refreshToken");

        System.out.println("✅ Логин успешен, accessToken: " + accessToken);
    }

    public void cleanup(String token) {
        System.out.println("🔹 Перед удалением пользователя accessToken: " + token);

        // Сначала разлогиниваемся (если refreshToken есть)
        if (refreshToken != null && !refreshToken.isEmpty()) {
            Response logoutResponse = UserSteps.logoutUser(refreshToken);
            System.out.println("🔹 Ответ сервера на разлогин: " + logoutResponse.asString());
            logoutResponse.then().statusCode(200);
            refreshToken = null; // Обнуляем refreshToken
        } else {
            System.out.println("⚠ Ошибка: refreshToken отсутствует или уже невалиден, разлогин невозможен!");
        }

        // Затем удаляем пользователя (если accessToken есть)
        if (token != null && !token.isEmpty()) {
            Response deleteResponse = UserSteps.deleteUser(token);
            System.out.println("🔹 Ответ сервера на удаление: " + deleteResponse.asString());
            deleteResponse.then().statusCode(202);
        } else {
            System.out.println("⚠ Ошибка: accessToken отсутствует, удаление невозможно!");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessToken() {
        return accessToken; // Получаем accessToken
    }
}