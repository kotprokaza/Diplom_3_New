package com.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    
    // Локаторы
    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//p[text()='Личный Кабинет']/parent::a");
    
    // Упрощенные локаторы для разделов - только span
    private final By bunsSection = By.xpath(".//span[text()='Булки']");
    private final By saucesSection = By.xpath(".//span[text()='Соусы']");
    private final By fillingsSection = By.xpath(".//span[text()='Начинки']");
    
    private final By activeSection = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]");
    
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }
    
    @Step("Нажать кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        element.click();
    }
    
    @Step("Нажать кнопку 'Личный кабинет'")
    public void clickPersonalAccountButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton));
        element.click();
    }
    
    @Step("Нажать раздел 'Булки'")
    public void clickBunsSection() {
        // Ищем элемент по тексту
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(bunsSection));
        // Прокручиваем к элементу
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        // Используем JavaScript клик
        js.executeScript("arguments[0].click();", element);
        waitForSectionActive("Булки");
    }
    
    @Step("Нажать раздел 'Соусы'")
    public void clickSaucesSection() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(saucesSection));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
        waitForSectionActive("Соусы");
    }
    
    @Step("Нажать раздел 'Начинки'")
    public void clickFillingsSection() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(fillingsSection));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
        waitForSectionActive("Начинки");
    }
    
    @Step("Проверить что активен раздел 'Булки'")
    public boolean isBunsSectionActive() {
        return isSectionActive("Булки");
    }
    
    @Step("Проверить что активен раздел 'Соусы'")
    public boolean isSaucesSectionActive() {
        return isSectionActive("Соусы");
    }
    
    @Step("Проверить что активен раздел 'Начинки'")
    public boolean isFillingsSectionActive() {
        return isSectionActive("Начинки");
    }
    
    private boolean isSectionActive(String sectionName) {
        try {
            WebElement active = wait.until(ExpectedConditions.presenceOfElementLocated(activeSection));
            return active.getText().contains(sectionName);
        } catch (Exception e) {
            return false;
        }
    }
    
    private void waitForSectionActive(String sectionName) {
        wait.until(driver -> isSectionActive(sectionName));
    }
}
