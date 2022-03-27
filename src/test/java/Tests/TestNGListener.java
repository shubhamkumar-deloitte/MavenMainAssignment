package Tests;

import Pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener extends LoginPageTest implements ITestListener {


    @Override
    public void onTestStart(ITestResult Result) {
        System.out.println("Test has started "+Result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult Result) {

        System.out.println("Test has succeded"+Result.getName());
        takeScreenshot();



    }

    @Override
    public void onTestFailure(ITestResult Result) {

        System.out.println("Test has failed "+Result.getName());
        takeScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult Result) {

        System.out.println("Test has skipped"+ Result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {

    }

    @Override
    public void onStart(ITestContext Result) {


    }

    @Override
    public void onFinish(ITestContext Result) {

        System.out.println("Test has finished"+Result);
    }
}
