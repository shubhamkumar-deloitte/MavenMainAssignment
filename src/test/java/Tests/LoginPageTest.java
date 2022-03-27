package Tests;

import Pages.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginPageTest {

    WebDriver driver;

    String HomeUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    MainPage mainPage;

    @BeforeSuite
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\shubhamkumar32\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(HomeUrl);
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    public void bankManagerLogin() throws InterruptedException {

        mainPage = new MainPage(driver);
        mainPage.bankManagerLoginClick();
        Thread.sleep(2000);
        //driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button")).click();
    }

    @Test(priority = 2)
    public void addCustomerBtnClick() throws InterruptedException {
        mainPage = new MainPage(driver);
        mainPage.addCustomerBtnClick();
        Thread.sleep(2000);


    }
    @Test(priority = 3)
    public void addCustomerDetails() throws IOException, InterruptedException {



        mainPage=new MainPage(driver);
        mainPage.readCustomerDetails();
        mainPage.addCustomerDetails();

        Thread.sleep(1000);

        driver.switchTo().alert().accept();

        Thread.sleep(1000);

    }
    @Test(priority = 4)
    public void openAccount() throws InterruptedException {
        Thread.sleep(2000);
        mainPage=new MainPage(driver);
        mainPage.setOpenAccount();
    }
    @Test(priority = 5)
    public void customerLogin() throws InterruptedException {
        mainPage=new MainPage(driver);
        mainPage.customerLogin();
    }
    public void makeDepositAndVerifyTransaction(){

    }
//    @AfterSuite
//    public void tearDown(){
//        driver.quit();
//    }
}
