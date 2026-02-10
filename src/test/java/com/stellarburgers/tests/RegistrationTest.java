package com.stellarburgers.tests;

import com.stellarburgers.BaseTest;
import com.stellarburgers.Constants;
import com.stellarburgers.pages.*;
import io.qameta.allure.Description;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseTest {

    @Test
    @Description("Тест проверяет успешную регистрацию пользователя с корректными данными")
    public void successfulRegistrationWithValidPassword() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String name = "Тестовый" + timestamp;
        String email = "test" + timestamp + "@example.com";
        String password = "Password123!";

        // Открываем страницу регистрации
        driver.get(Constants.REGISTER_URL);
        RegisterPage registerPage = new RegisterPage(driver);

        // Проверяем что открыта страница регистрации
        assertTrue("Страница регистрации не отображается",
                registerPage.isRegisterPageDisplayed());

        // Регистрируем пользователя
        registerPage.register(name, email, password);

        // Ожидаем перехода на страницу логина через метод Page Object
        registerPage.waitForLoginPageRedirect();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("После регистрации не открылась страница логина",
                loginPage.isLoginPageDisplayed());
    }

    @Test
    @Description("Тест проверяет отображение ошибки при регистрации с коротким паролем")
    public void registrationErrorWithShortPassword() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String name = "Тестовый" + timestamp;
        String email = "test" + timestamp + "@example.com";
        String shortPassword = "123";

        // Открываем страницу регистрации
        driver.get(Constants.REGISTER_URL);
        RegisterPage registerPage = new RegisterPage(driver);

        // Проверяем что открыта страница регистрации
        assertTrue("Страница регистрации не отображается",
                registerPage.isRegisterPageDisplayed());

        // Регистрируем пользователя с коротким паролем
        registerPage.register(name, email, shortPassword);

        // Проверяем отображение ошибки пароля через метод Page Object
        assertTrue("Ошибка пароля не отображается",
                registerPage.isPasswordErrorDisplayed());
    }
}