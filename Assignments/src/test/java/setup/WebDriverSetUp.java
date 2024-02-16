package setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class WebDriverSetUp {

    public WebDriver driver;


    public void setUpDriver(String language) {
        // Initialize WebDriver
        driver = new ChromeDriver();
        System.out.println("WebDriver initialized.");

        // Navigate to the website based on the language
        if (language.equalsIgnoreCase("en")) {
            driver.navigate().to("https://syarah.com/en");
        } else if (language.equalsIgnoreCase("ar")) {
            driver.navigate().to("https://syarah.com/");
        } else {
            // Default to English if no valid language is provided
            driver.navigate().to("https://syarah.com/en");
        }
        System.out.println("Navigated to the website.");

        // Maximize the window
        driver.manage().window().maximize();
        System.out.println("Window maximized.");
    }

    @AfterMethod
    public void afterMethodCommands(ITestResult result) {
        // Get the name of the executed test method
        String methodName = result.getMethod().getMethodName();
        System.out.println("Method end: " + methodName);
    }

    @AfterClass
    public void closeDriver() {
        // Quit WebDriver
        driver.quit();
        System.out.println("WebDriver quit.");
    }
}
