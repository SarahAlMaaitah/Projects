package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void setUp() {
        extent = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport.html");
        // Customize Extent Report settings
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Syarah Test Automation Report");
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setReportName("Syarah Test Results");
        extent.attachReporter(htmlReporter);
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void logTestInfo(String message) {
        test.log(Status.INFO, message);
    }

    public static void logTestPass(String message) {
        test.log(Status.PASS, message);
    }

    public static void logTestFail(String message) {
        test.log(Status.FAIL, message);
    }

    public static void addScreenshot(String screenshotPath) {
        try {
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void flush() {
        extent.flush();
    }
}
