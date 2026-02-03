package com.stellarburgers.tests;

import com.stellarburgers.BaseTest;
import com.stellarburgers.Constants;
import com.stellarburgers.api.User;
import com.stellarburgers.api.UserClient;
import com.stellarburgers.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@DisplayName("Тесты навигации и авторизации")
public class LoginNavigationTest extends BaseTest {

    @Test
    @DisplayName("Успешная авторизация с вводом логина и пароля")
    @Description("Полная процедура авторизации: ввод логина и пароля, клик по кнопке 'Войти'")
    public void successfulLoginWithCredentials() {
        // Создаем тестового пользователя через API
        User testUser = UserClient.createRandomUser();
        String accessToken = testUser.getAccessToken();

        try {
            // Открываем страницу логина
            driver.get(Constants.LOGIN_URL);
            LoginPage loginPage = new LoginPage(driver);

            // Полная процедура авторизации
            loginPage.login(testUser.getEmail(), testUser.getPassword());

            // Проверяем успешный логин
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.urlContains("login")
            ));

            wait.until(driver ->
                    driver.getCurrentUrl().contains(Constants.BASE_URL) ||
                            driver.getCurrentUrl().contains("account")
            );

            String currentUrl = driver.getCurrentUrl();
            assertTrue("После авторизации не открылась главная страница или личный кабинет. Текущий URL: " + currentUrl,
                    currentUrl.contains(Constants.BASE_URL) || currentUrl.contains("account"));

        } finally {
            // Удаляем тестового пользователя после теста
            if (accessToken != null && !accessToken.isEmpty()) {
                UserClient.deleteUser(accessToken);
            }
        }
    }

    @Test
    @DisplayName("Переход в личный кабинет после авторизации")
    @Description("Проверка возможности перехода в личный кабинет после успешной авторизации")
    public void navigateToPersonalAccountAfterLogin() {
        // Создаем тестового пользователя через API
        User testUser = UserClient.createRandomUser();
        String accessToken = testUser.getAccessToken();

        try {
            // Открываем страницу логина
            driver.get(Constants.LOGIN_URL);
            LoginPage loginPage = new LoginPage(driver);

            // Авторизуемся
            loginPage.login(testUser.getEmail(), testUser.getPassword());

            // Ждем успешной авторизации
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.urlContains("login")
            ));

            // Переходим в личный кабинет
            MainPage mainPage = new MainPage(driver);
            mainPage.clickPersonalAccountButton();

            // Проверяем что открылся личный кабинет
            wait.until(ExpectedConditions.urlContains("account"));

            ProfilePage profilePage = new ProfilePage(driver);
            assertTrue("Личный кабинет не отображается",
                    profilePage.isProfilePageDisplayed());

        } finally {
            // Удаляем тестового пользователя после теста
            if (accessToken != null && !accessToken.isEmpty()) {
                UserClient.deleteUser(accessToken);
            }
        }
    }

    @Test
    @DisplayName("Авторизация через кнопку 'Войти в аккаунт'")
    @Description("Переход на страницу логина через кнопку 'Войти в аккаунт'")
    public void loginViaMainPageButton() {
        driver.get(Constants.BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Страница логина не отображается",
                loginPage.isLoginPageDisplayed());
    }

    @Test
    @DisplayName("Авторизация через кнопку 'Личный кабинет'")
    @Description("Переход на страницу логина через кнопку 'Личный кабинет'")
    public void loginViaPersonalAccountButton() {
        driver.get(Constants.BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Страница логина не отображается",
                loginPage.isLoginPageDisplayed());
    }

    @Test
    @DisplayName("Переход на страницу логина со страницы регистрации")
    @Description("Переход на страницу логина со страницы регистрации")
    public void loginFromRegisterPage() {
        driver.get(Constants.REGISTER_URL);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Страница логина не отображается",
                loginPage.isLoginPageDisplayed());
    }

    @Test
    @DisplayName("Переход на страницу логина со страницы восстановления пароля")
    @Description("Переход на страницу логина со страницы восстановления пароля")
    public void loginFromForgotPasswordPage() {
        driver.get(Constants.FORGOT_PASSWORD_URL);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Страница логина не отображается",
                loginPage.isLoginPageDisplayed());
    }
}