package com.stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    @Before
    @Step("Инициализация драйвера и открытие браузера")
    public void setUp() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "safari":
                // Safari не требует WebDriverManager на macOS
                driver = new SafariDriver();
                break;
                
            case "yandex":
                WebDriverManager.chromedriver().setup();
                ChromeOptions yandexOptions = new ChromeOptions();
                
                // Определяем ОС для пути к Яндекс.Браузеру
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    // Windows
                    yandexOptions.setBinary("C:\\Users\\" + System.getProperty("user.name") + 
                                           "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
                } else if (os.contains("mac")) {
                    // macOS
                    yandexOptions.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
                } else if (os.contains("linux") || os.contains("unix")) {
                    // Linux
                    yandexOptions.setBinary("/usr/bin/yandex-browser");
                }
                
                yandexOptions.addArguments("--no-sandbox");
                yandexOptions.addArguments("--disable-dev-shm-usage");
                yandexOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(yandexOptions);
                break;
                
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }
    
    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            takeScreenshot();
            driver.quit();
        }
    }
    
    @Attachment(value = "Скриншот", type = "image/png")
    public byte[] takeScreenshot() {
        if (driver != null) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }
}
