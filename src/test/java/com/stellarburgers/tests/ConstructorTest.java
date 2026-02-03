package com.stellarburgers.tests;

import com.stellarburgers.BaseTest;
import com.stellarburgers.Constants;
import com.stellarburgers.pages.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@DisplayName("Тесты конструктора бургеров")
public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Переключение на раздел 'Булки'")
    @Description("Тест проверяет, что при клике на раздел 'Булки' он становится активным")
    public void switchToBunsSection() {
        driver.get(Constants.BASE_URL);
        MainPage mainPage = new MainPage(driver);
        
        mainPage.clickBunsSection();
        
        // Ожидания внутри метода clickBunsSection()
        assertTrue("Раздел 'Булки' должен быть активным",
                mainPage.isBunsSectionActive());
    }
    
    @Test
    @DisplayName("Переключение на раздел 'Соусы'")
    @Description("Тест проверяет, что при клике на раздел 'Соусы' он становится активным")
    public void switchToSaucesSection() {
        driver.get(Constants.BASE_URL);
        MainPage mainPage = new MainPage(driver);
        
        mainPage.clickSaucesSection();
        
        // Ожидания внутри метода clickSaucesSection()
        assertTrue("Раздел 'Соусы' должен быть активным",
                mainPage.isSaucesSectionActive());
    }
    
    @Test
    @DisplayName("Переключение на раздел 'Начинки'")
    @Description("Тест проверяет, что при клике на раздел 'Начинки' он становится активным")
    public void switchToFillingsSection() {
        driver.get(Constants.BASE_URL);
        MainPage mainPage = new MainPage(driver);
        
        mainPage.clickFillingsSection();
        
        // Ожидания внутри метода clickFillingsSection()
        assertTrue("Раздел 'Начинки' должен быть активным",
                mainPage.isFillingsSectionActive());
    }
}
