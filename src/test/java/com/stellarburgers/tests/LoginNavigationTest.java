package com.stellarburgers.tests;

import com.stellarburgers.BaseTest;
import com.stellarburgers.Constants;
import com.stellarburgers.pages.*;
import io.qameta.allure.Description;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LoginNavigationTest extends BaseTest {
    
    @Test
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
