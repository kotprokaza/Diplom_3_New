package com.stellarburgers.tests;

import com.stellarburgers.BaseTest;
import com.stellarburgers.Constants;
import com.stellarburgers.api.User;
import com.stellarburgers.api.UserClient;
import com.stellarburgers.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@DisplayName("Тесты навигации и авторизации")
public class LoginNavigationTest extends BaseTest {

    private User testUser;
    private String accessToken;

    @Before
    @DisplayName("Создание тестового пользователя")
    @Description("Создание пользователя через API перед выполнением тестов")
    public void setUpUser() {
        // Создаем тестового пользователя через API
        testUser = UserClient.createRandomUser();
        accessToken = testUser.getAccessToken();
        System.out.println("Создан пользователь: " + testUser.getEmail());
    }

    @After
    @DisplayName("Удаление тестового пользователя")
    @Description("Удаление пользователя через API после выполнения тестов")
    public void tearDownUser() {
        // Удаляем тестового пользователя после теста
        if (accessToken != null && !accessToken.isEmpty()) {
            UserClient.deleteUser(accessToken);
            System.out.println("Удален пользователь: " + testUser.getEmail());
        }
    }

    @Test
    @DisplayName("Успешная авторизация с вводом логина и пароля")
    @Description("Полная процедура авторизации: ввод логина и пароля, клик по кнопке 'Войти'")
    public void successfulLoginWithCredentials() {
        // Открываем страницу логина
        driver.get(Constants.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);

        // Полная процедура авторизации через методы Page Object
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        // Ожидаем успешной авторизации через метод Page Object
        loginPage.waitForSuccessfulLogin();

        // Проверяем успешный логин
        String currentUrl = loginPage.getCurrentUrl();
        assertTrue("После авторизации не открылась главная страница или личный кабинет. Текущий URL: " + currentUrl,
                currentUrl.contains(Constants.BASE_URL) || currentUrl.contains("account"));
    }

    @Test
    @DisplayName("Переход в личный кабинет после авторизации")
    @Description("Проверка возможности перехода в личный кабинет после успешной авторизации")
    public void navigateToPersonalAccountAfterLogin() {
        // Открываем страницу логина
        driver.get(Constants.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);

        // Авторизуемся через методы Page Object
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        loginPage.waitForSuccessfulLogin();

        // Переходим в личный кабинет через методы Page Object
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();

        // Проверяем что открылся личный кабинет через методы Page Object
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForProfilePageLoad();

        assertTrue("Личный кабинет не отображается",
                profilePage.isProfilePageDisplayed());
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