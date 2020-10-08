package org.example.tpo;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest {
    public static WebDriver driver;
    public static LocalStorage local;
    @After
    public void closeBrowser() {
        driver.quit();
    }
    @Before
    public void loadConfig() {
        WebDriver driver = new ChromeDriver();
        this.driver = driver;

        this.local = ((WebStorage) driver).getLocalStorage();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        driver.get("http://localhost:3000/");

    }

    @org.junit.Test
    public void findOrderRedirect() throws InterruptedException {
        WebElement inputOrder = driver.findElement(By.name("inputOrder"));
        inputOrder.sendKeys("test");
        WebElement findButton = driver.findElement(By.name("find"));
        findButton.click();
        assertEquals("http://localhost:3000/order/test", driver.getCurrentUrl());
    }

    @org.junit.Test
    public void createOrderRedirect() throws InterruptedException {
        WebElement createButton = driver.findElement(By.name("createOrder"));
        createButton.click();
        assertEquals("http://localhost:3000/create", driver.getCurrentUrl());
    }

}