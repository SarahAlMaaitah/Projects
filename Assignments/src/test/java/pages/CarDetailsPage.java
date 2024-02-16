package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class CarDetailsPage {
    private final WebDriver driver;

    // Constructor
    public CarDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Page Methods

    public void openCarPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> carsListResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[@class=\"UnbxdCard-module__wrapper \"]")));

        if (!carsListResults.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(carsListResults.size());
            WebElement randomElement = carsListResults.get(randomIndex);
            randomElement.click();
        } else {
            System.out.println("The list is empty.");
        }

    }

    public void clickCarReservation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> priceElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div > strong.PaymentTabBox-module__NewPriceHolder")));

        WebElement secondPriceElement = priceElements.get(1);
        String getCheckOutPrice = secondPriceElement.getText().replaceAll("[^\\d,]", "");

        List<WebElement> buyNowElements = driver.findElements(By.cssSelector(".greenBtn.PaymentTabBox-module__buyLinkPPt"));
        WebElement secondBuyNow = buyNowElements.get(1);
        secondBuyNow.click();

        String carDetailsPagePrice = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.PaymentInfo-module__genralInfo > div:nth-child(1) > strong"))).getText().replaceAll("[^\\d,]", "");

        Assert.assertEquals(getCheckOutPrice, carDetailsPagePrice);
    }

}
