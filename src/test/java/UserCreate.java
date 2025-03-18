import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Assert;

public class UserCreate {

    private String refreshToken;
    private String accessToken;
    private final String email;
    private final String password;

    public UserCreate() {
        this.email = "test" + System.currentTimeMillis() + "@example.com";
        this.password = UserSteps.generateValidPassword(); // Генерация валидного пароля
    }

    @Description("Создание уникального пользователя и сохранение токенов")
    public void createUser() {
        System.out.println("🔹 Начало создания пользователя");

        String name = "Tata";
        System.out.println("🔹 Используемые данные: email=" + email + ", password=" + password + ", name=" + name);

        // Регистрация пользователя
        Response response = UserSteps.registerUser(email, password, name);
        System.out.println("🔹 Ответ сервера на создание: " + response.asString());

        response.then().statusCode(200);
        Assert.assertTrue(response.jsonPath().getBoolean("success"));

        // Логин пользователя и получение токенов
        Response loginResponse = UserSteps.loginUser(email, password);
        System.out.println("🔹 Ответ сервера на логин: " + loginResponse.asString());

        // Сохраняем токены
        this.accessToken = loginResponse.jsonPath().getString("accessToken");
        this.refreshToken = loginResponse.jsonPath().getString("refreshToken");

        System.out.println("✅ Логин успешен, accessToken: " + accessToken);
    }

    public void cleanup(String token) {
        System.out.println("🔹 Начало очистки: разлогин и удаление пользователя");

        // Разлогин пользователя
        System.out.println("🔹 Перед разлогином refreshToken: " + refreshToken);
        if (refreshToken != null && !refreshToken.isEmpty()) {
            Response logoutResponse = UserSteps.logoutUser(refreshToken);
            System.out.println("🔹 Ответ сервера на разлогин: " + logoutResponse.asString());
            logoutResponse.then().statusCode(200);
            refreshToken = null; // Обнуляем refreshToken
        } else {
            System.out.println("⚠ Ошибка: refreshToken отсутствует или уже невалиден, разлогин невозможен!");
        }

        // Удаление пользователя
        System.out.println("🔹 Перед удалением пользователя accessToken: " + token);
        if (token != null && !token.isEmpty()) {
            Response deleteResponse = UserSteps.deleteUser(token);
            System.out.println("🔹 Ответ сервера на удаление: " + deleteResponse.asString());
            deleteResponse.then().statusCode(202);
        } else {
            System.out.println("⚠ Ошибка: accessToken отсутствует, удаление невозможно!");
        }

        System.out.println("✅ Очистка завершена");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessToken() {
        return accessToken;
    }
}