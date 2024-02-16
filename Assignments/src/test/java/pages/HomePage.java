package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectCarModel(String modelName) {
        driver.findElement(By.xpath("//a[contains(@href, '" + modelName + "')]")).click();
    }
}