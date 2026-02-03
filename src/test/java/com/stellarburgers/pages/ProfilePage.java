package com.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By profileLink = By.xpath("//a[text()='Профиль']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Проверить что открыт личный кабинет")
    public boolean isProfilePageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(profileLink));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
