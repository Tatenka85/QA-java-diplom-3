import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pages.Constants;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserSteps {

    private static final Gson gson = new Gson(); // Инициализация Gson
    private static final Faker faker = new Faker(); // Инициализация Faker

    @Step("Создание нового пользователя: email={email}, name={name}")
    public static Response registerUser(String email, String password, String name) {
        // Создаем тело запроса в виде Map
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("name", name);

        // Сериализуем Map в JSON с помощью Gson
        String jsonBody = gson.toJson(requestBody);

        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody) // Используем сериализованный JSON
                .when()
                .post(Constants.AUTH_REGISTER_ENDPOINT);
    }

    @Step("Логин пользователя: email={email}")
    public static Response loginUser(String email, String password) {
        // Создаем тело запроса в виде Map
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        // Сериализуем Map в JSON с помощью Gson
        String jsonBody = gson.toJson(requestBody);

        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody) // Используем сериализованный JSON
                .when()
                .post(Constants.AUTH_LOGIN_ENDPOINT);
    }

    @Step("Разлогин пользователя")
    public static Response logoutUser(String refreshToken) {
        // Создаем тело запроса в виде Map
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("token", refreshToken);

        // Сериализуем Map в JSON с помощью Gson
        String jsonBody = gson.toJson(requestBody);

        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody) // Используем сериализованный JSON
                .when()
                .post(Constants.AUTH_LOGOUT_ENDPOINT);
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        // Для удаления пользователя тело запроса не требуется, только заголовок с токеном
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(Constants.AUTH_USER_ENDPOINT);
    }
    // Метод для генерации валидного пароля (6 и более символов)
    public static String generateValidPassword() {
        int passwordLength = faker.random().nextInt(6, 12); // Длина от 6 до 12 символов
        return faker.lorem().characters(passwordLength, true, true); // Генерация случайной строки с буквами и цифрами
    }
    // Метод для генерации невалидного пароля (менее 6 символов)
    public static String generateInvalidPassword() {
        int passwordLength = faker.random().nextInt(1, 5); // Длина от 1 до 5 символов
        return faker.lorem().characters(passwordLength); // Генерация случайной строки
    }
}