package checkOutFlow;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CarDetailsPage;
import pages.HomePage;
import setup.WebDriverSetUp;
import utility.ExtentReportManager;
import utility.RandomGenerator;

import java.time.Duration;

import static utility.RandomGenerator.generateFakeCreditCardNumber;
import static utility.RandomGenerator.generateRandomCVC;

public class CheckOutFlowCarTest extends WebDriverSetUp {

    @BeforeClass
    public void setUpExtentReport() {
        ExtentReportManager.setUp();
    }

    @Test(priority = 1, description = "Open a browser and navigate to Syarah site")
    public void navigateToTheSite() {
        ExtentReportManager.createTest("Navigate to Syarah Site");
        try {
            setUpDriver("en");
            ExtentReportManager.logTestPass("Successfully navigated to the Syarah website.");
        } catch (Exception e) {
            ExtentReportManager.logTestFail("Failed to navigate to the Syarah website.");
            ExtentReportManager.addScreenshot("screenshots/navigation_failed.png");
            Assert.fail("Failed to navigate to the Syarah website.", e);
        }
    }

    @Test(priority = 2, description = "Select Car Model", dependsOnMethods = "navigateToTheSite")
    public void selectACarModel() {
        ExtentReportManager.createTest("Select Car Model");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.selectCarModel("toyota");
            ExtentReportManager.logTestPass("Car model selected.");
        } catch (Exception e) {
            ExtentReportManager.logTestFail("Failed to select car model.");
            ExtentReportManager.addScreenshot("screenshots/select_car_model_failed.png");
            Assert.fail("Failed to select car model.", e);
        }
    }

    @Test(priority = 3, description = "Open Car details page", dependsOnMethods = "selectACarModel")
    public void openCarPage() {
        ExtentReportManager.createTest("Open Car details page");
        try {
            CarDetailsPage carDetailsPage = new CarDetailsPage(driver);
            carDetailsPage.openCarPage();
            carDetailsPage.clickCarReservation();
            ExtentReportManager.logTestPass("Car details page opened.");
        } catch (Exception e) {
            ExtentReportManager.logTestFail("Failed to open car details page.");
            ExtentReportManager.addScreenshot("screenshots/open_car_details_page_failed.png");
            Assert.fail("Failed to open car details page.", e);
        }
    }

    @Test(priority = 4, description = "Fill Users Info", dependsOnMethods = "openCarPage")
    public void fillUsersInfo() {
        ExtentReportManager.createTest("Fill Users Info");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement cashButton = driver.findElement(By.cssSelector("label > input[type='radio'][value='1']"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", cashButton);

            WebElement nextButton = driver.findElement(By.cssSelector("div.PreCheckout-module__subBox > div > button"));
            nextButton.click();

            WebElement fullNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("full_name")));
            String randomName = RandomGenerator.generateRandomName();
            fullNameInput.sendKeys(randomName);

            String randomPhoneNumber = RandomGenerator.generateRandomPhoneNumber();
            WebElement phoneNumberInput = driver.findElement(By.name("phone_number"));
            phoneNumberInput.sendKeys(randomPhoneNumber);

            WebElement button = driver.findElement(By.cssSelector("button.greenBtn"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            button.click();

            ExtentReportManager.logTestPass("User information filled.");
        } catch (Exception e) {
            ExtentReportManager.logTestFail("Failed to fill user information.");
            ExtentReportManager.addScreenshot("screenshots/fill_user_information_failed.png");
            Assert.fail("Failed to fill user information.", e);
        }
    }

    @Test(priority = 5, description = "Fill Payment Info", dependsOnMethods = "fillUsersInfo")
    public void fillPaymentInfo() {
        ExtentReportManager.createTest("Fill Payment Info");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#root > div.hasAuthPad.MainContainerLO > main")));

            WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mysr-cc-name")));
            nameInput.sendKeys(RandomGenerator.generateRandomName());

            WebElement creditCardInput = driver.findElement(By.id("mysr-cc-number"));
            creditCardInput.sendKeys(generateFakeCreditCardNumber());

            WebElement expiryDateInput = driver.findElement(By.cssSelector("input.mysr-form-input.mysr-form-cardInfoElement"));
            expiryDateInput.sendKeys(RandomGenerator.generateRandomExpiryYear());

            WebElement cvcInput = driver.findElement(By.cssSelector("input[placeholder='CVC']"));
            cvcInput.sendKeys(generateRandomCVC());

            WebElement button = driver.findElement(By.className("mysr-form-button"));
            button.click();

            ExtentReportManager.logTestPass("Payment information filled.");
        } catch (Exception e) {
            ExtentReportManager.logTestFail("Failed to fill payment information.");
            ExtentReportManager.addScreenshot("screenshots/fill_payment_information_failed.png");
            Assert.fail("Failed to fill payment information.", e);
        }
    }

    @AfterMethod
    public void afterTestMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.logTestFail("Test failed: " + result.getName());
            ExtentReportManager.addScreenshot("screenshot-path.png");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReportManager.logTestPass("Test passed: " + result.getName());
        } else {
            ExtentReportManager.logTestInfo("Test skipped: " + result.getName());
        }

        // Flush the Extent report after each test method
        ExtentReportManager.flush();
    }

    @AfterClass
    public void tearDownExtentReport() {
        ExtentReportManager.flush();
    }
}
