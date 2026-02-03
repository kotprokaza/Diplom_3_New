package com.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By emailField = By.xpath("//input[@type='text' and @name='name']");
    private final By passwordField = By.xpath("//input[@type='password']");
    private final By loginButton = By.xpath("//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Заполнить поле 'Email' значением: {email}")
    public void fillEmailField(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        element.clear();
        element.sendKeys(email);
    }

    @Step("Заполнить поле 'Пароль' значением: {password}")
    public void fillPasswordField(String password) {
        WebElement element = driver.findElement(passwordField);
        element.clear();
        element.sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        WebElement element = driver.findElement(loginButton);
        element.click();
    }

    @Step("Авторизация с данными: {email}, {password}")
    public void login(String email, String password) {
        fillEmailField(email);
        fillPasswordField(password);
        clickLoginButton();
    }

    @Step("Проверить что открыта страница авторизации")
    public boolean isLoginPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
