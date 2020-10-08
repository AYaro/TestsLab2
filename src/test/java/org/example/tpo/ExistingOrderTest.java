package org.example.tpo;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ExistingOrderTest {
    public static WebDriver driver;
    public static LocalStorage local;

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Before
    public void config() {
        WebDriver driver = new ChromeDriver();
        this.driver = driver;
        this.local = ((WebStorage) driver).getLocalStorage();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @org.junit.Test(expected=NoSuchElementException.class)
    public void checkNoError() {
        driver.get("http://localhost:3000/order/test2");
        driver.findElement(By.name("error"));
    }

    @org.junit.Test
    public void getFinishedOrder() throws InterruptedException {
        driver.get("http://localhost:3000/order/test2");

        // нет ошибки
        WebElement err = driver.findElement(By.name("error"));
        assertNull(err);

        List<WebElement> users = driver.findElements(By.name("result"));

        // Количество юзеров которые платят
        int count = users.size();
        assertEquals(2, count);

        // Сумма первого юзера
        WebElement firstSum = users.get(0).findElement(By.name("sum"));
        assertEquals("550.00", firstSum.getText());

        // Сумма второго юзера
        WebElement secondSum = users.get(1).findElement(By.name("sum"));
        assertEquals("250.00", secondSum.getText());
    }

    @org.junit.Test
    public void orderNotFoundError() throws InterruptedException {
        driver.get("http://localhost:3000/order/test3");
        WebElement err = driver.findElement(By.name("error"));
        assertNotNull(err);
    }
}