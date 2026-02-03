package com.stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";
    
    @Step("Создание тестового пользователя")
    public static User createRandomUser() {
        RestAssured.baseURI = BASE_URL;
        
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "test" + timestamp + "@example.com";
        String password = "Password123!";
        String name = "TestUser" + timestamp;
        
        User user = new User(email, password, name);
        
        try {
            Response response = given()
                    .header("Content-type", "application/json")
                    .body(user)
                    .when()
                    .post("/auth/register");
            
            if (response.statusCode() == 200) {
                String accessToken = response.path("accessToken");
                user.setAccessToken(accessToken != null ? accessToken.replace("Bearer ", "") : "");
            } else {
                user.setAccessToken("");
            }
            
            return user;
        } catch (Exception e) {
            System.out.println("Ошибка создания пользователя: " + e.getMessage());
            user.setAccessToken("");
            return user;
        }
    }
    
    @Step("Удаление пользователя")
    public static void deleteUser(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            return;
        }
        
        try {
            RestAssured.baseURI = BASE_URL;
            given()
                    .header("Authorization", "Bearer " + accessToken)
                    .when()
                    .delete("/auth/user")
                    .then()
                    .statusCode(202);
        } catch (Exception e) {
            System.out.println("Ошибка удаления пользователя: " + e.getMessage());
        }
    }
}
