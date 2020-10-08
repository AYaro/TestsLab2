package org.example.tpo;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderTest {
    public String orderID;
    public static WebDriver driver;
    public static LocalStorage local;


    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Before
    public void loadPage() {
        WebDriver driver = new ChromeDriver();
        this.driver = driver;
        this.local = ((WebStorage) driver).getLocalStorage();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/create");
    }

    @org.junit.Test
    public void createOrder() throws InterruptedException {
        driver.get("http://localhost:3000/create");

        // Вводим валюту
        WebElement currency = driver.findElement(By.name("currency"));
        currency.sendKeys("RUB");

        // Выбераем первый режим
        WebElement mode = driver.findElement(By.name("mode"));
        Select select = new Select(mode);
        select.selectByIndex(1);

        // Подготовка к добавлению имен
        WebElement name = driver.findElement(By.name("name"));
        WebElement add = driver.findElement(By.name("add"));

        // Первое имя
        name.sendKeys("Berners-Lee");
        add.click();
        name.clear();

        // Второе имя
        name.sendKeys("Elon Musk");
        add.click();

        // Проверяем что именна созданы
        int count = driver.findElements(By.name("addedUser")).size();
        assertEquals(2, count);

        // cоздаем заказ
        WebElement create = driver.findElement(By.name("createOrder"));
        create.click();

        // переходим на страницу заказа
        WebElement orderLink = driver.findElement(By.name("orderLink"));
        orderLink.click();

        String url = driver.getCurrentUrl();
        String orderID = url.substring(url.lastIndexOf("/") + 1);
        assertEquals(6, orderID.length());

        assertEquals("http://localhost:3000/order/" + orderID, driver.getCurrentUrl());
    }

    @org.junit.Test
    public void addDishesToOrder() throws InterruptedException {
        driver.get("http://localhost:3000/order/test1");
        Thread.sleep(500);

        // Вводим название блюда
        WebElement dishName = driver.findElement(By.name("dishName"));
        dishName.sendKeys("Soup");

        // Вводим стоимость блюда
        WebElement dishPrice = driver.findElement(By.name("price"));
        dishPrice.sendKeys("Soup");

        // Выбераем первого пользователя
        WebElement mode = driver.findElement(By.name("userSelection"));
        Select select = new Select(mode);
        select.selectByIndex(0);

        // добавляем блюдо
        WebElement add = driver.findElement(By.name("addDish"));
        add.click();

        WebElement user = driver.findElement(By.name("user"));
        //WebElement user = driver.findElement(By.xpath("//div[contains(text(),'Bob')]"));

        assertEquals(user.getText(), "Steve");
    }
}