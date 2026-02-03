package com.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    // Исправленные локаторы без [1]
    private final By nameField = By.xpath(".//label[contains(text(),'Имя')]/following-sibling::input");
    private final By emailField = By.xpath(".//label[contains(text(),'Email')]/following-sibling::input");
    private final By passwordField = By.xpath(".//input[@type='password']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");
    private final By passwordError = By.xpath(".//p[contains(text(),'Некорректный пароль')]");
    
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @Step("Заполнить поле 'Имя' значением: {name}")
    public void fillNameField(String name) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        element.clear();
        element.sendKeys(name);
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
    
    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        WebElement element = driver.findElement(registerButton);
        element.click();
    }
    
    @Step("Нажать ссылку 'Войти'")
    public void clickLoginLink() {
        WebElement element = driver.findElement(loginLink);
        element.click();
    }
    
    @Step("Регистрация пользователя с данными: {name}, {email}, {password}")
    public void register(String name, String email, String password) {
        fillNameField(name);
        fillEmailField(email);
        fillPasswordField(password);
        clickRegisterButton();
    }
    
    @Step("Проверить отображение ошибки пароля")
    public boolean isPasswordErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Step("Проверить что открыта страница регистрации")
    public boolean isRegisterPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
