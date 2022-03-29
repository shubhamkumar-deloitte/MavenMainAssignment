package Tests;

import Pages.MainPage;
import Pages.TransactionPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class LoginPageTest {
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;

    static WebDriver driver;
    static int i = 1;

    String HomeUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    MainPage mainPage;
    TransactionPage transactionPage;

    @BeforeSuite
    public void setup() throws InterruptedException {


//        // start reporters
       htmlReporter = new ExtentHtmlReporter("extent.html");
//
//        // create ExtentReports and attach reporter(s)
       extent = new ExtentReports();
       extent.attachReporter(htmlReporter);
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\shubhamkumar32\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(HomeUrl);
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    public void bankManagerLogin() throws InterruptedException, IOException {

        // creates a toggle for the given test, adds all log events under it
        ExtentTest test = extent.createTest("bankManagerLogin", "It will chec the login function of bank manager");
         //nfo(details)
        test.info("In this test bank manager will log in into the system to add customer");

        mainPage = new MainPage(driver);
        mainPage.bankManagerLoginClick();
        test.pass("bank manager successfully loggedIn");
        Thread.sleep(2000);



    }

    @Test(priority = 2)
    public void addCustomerBtnClick() throws InterruptedException {
        mainPage = new MainPage(driver);

        // creates a toggle for the given test, adds all log events under it
        ExtentTest test2 = extent.createTest("addCustomerBtnClick", "bnk manager will add customer");
        test2.info("after logging In manager will try to add customer, and this test case will check it");
        mainPage.addCustomerBtnClick();
        test2.pass("Successfully clicked on add customer button");
        Thread.sleep(2000);


    }

    @Test(priority = 3)
    public void addCustomerDetails() throws IOException, InterruptedException {


        mainPage = new MainPage(driver);
        // creates a toggle for the given test, adds all log events under it
        ExtentTest test3 = extent.createTest("addCustomerDetails", "adding customer details");
       test3.info("in this test case customer details will be added");
        mainPage.readCustomerDetails();
        test3.info("customer details successfully read from excel");
        mainPage.addCustomerDetails();
        test3.info("customer details enter in the input tag");

        Thread.sleep(1000);

        driver.switchTo().alert().accept();
        test3.info("alert handled");
        test3.pass("new cutomer details have been added successfully");

        Thread.sleep(1000);

    }

    @Test(priority = 4)
    public void openAccount() throws InterruptedException {
        Thread.sleep(2000);
        // creates a toggle for the given test, adds all log events under it
        ExtentTest test4 = extent.createTest("openAccount", "Account will be opened for newly added customer");
        mainPage = new MainPage(driver);
        mainPage.setOpenAccount();
       test4.pass("account opened successfully for new added customer");
    }

    @Test(priority = 5)
    public void customerLogin() throws InterruptedException {
        // creates a toggle for the given test, adds all log events under it
        ExtentTest test5 = extent.createTest("customerLogin", "New Customer will logIn");
        test5.info("New customer will login by selecting their names from drop down");
        mainPage = new MainPage(driver);
        mainPage.customerLogin();
        test5.pass("Customer loggedIn successfully");
    }

    @Test(priority = 6)
    public void makeDepositAndVerifyTransaction() throws Exception {

        // creates a toggle for the given test, adds all log events under it
        ExtentTest test6= extent.createTest("makeDepositAndVerifyTransaction", "Customer will desposit amount and test case will verify the amount updated or  not");
        transactionPage=new TransactionPage(driver);
        //test6.info("Amount will be entered oin the input and accordingly should be updated");
        transactionPage.makeDepositAndVerify();
        test6.pass("Amount deposited and verified");
    }

    @Test(priority = 7)
    public void makeWithDraw() throws Exception {
        ExtentTest test7= extent.createTest("makeWithDrwa", "testcase to check edge cases while withdrawing" );
        test7.info("it will check the withdraw process and fail if withdrawing amount is more than balance");
        transactionPage=new TransactionPage(driver);
        transactionPage.makeWithdraw();
        test7.pass("amount withdrawn successfully");
    }

    @Test(priority = 8)
    public void verifyTransaction() throws InterruptedException {
        transactionPage=new TransactionPage(driver);
        transactionPage.verifyTransaction();
    }

    @AfterSuite
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);

        Thread.sleep(2000);

        extent.flush();
        driver.quit();
    }

    public static void takeScreenshot() {

        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //The below method will save the screen shot in destination directory
            FileHandler.copy(scrFile, new File(System.getProperty("user.dir") + "/src/" + "sample" + i + ".png"));
            i++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
