package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class TransactionPage {

    static WebDriver driver;

    By depositBtn = By.xpath("//button[@ng-click='deposit()']");
    By depositInput = By.xpath("//input[@ng-model='amount']");
    By makeDepositBtn = By.xpath("//button[@class='btn btn-default']");
    By currentBalanceInfo = By.xpath("//div[@class='borderM box padT20 ng-scope']/child::div[2]/child::strong[2]");
    By withDraw = By.xpath("//button[@ng-click='withdrawl()']");
    By withDrawInput = By.xpath("//form[@name='myForm']/child::div/child::input");
    By withDrawBtn = By.xpath("//button[@type='submit']");

    By errorText = By.xpath("//*[@class='container-fluid mainBox ng-scope']/child::div[1]/child::span");
    By getTransactionBtn = By.xpath("//button[@ng-click='transactions()']");
    By numberOftransaction = By.xpath("//*[@ng-repeat='tx in transactions | orderBy:sortType:sortReverse | sDate:startDate:end']");
    public static int numberOfTransactions = 0;
    static int i = 1;

    public TransactionPage(WebDriver driver) {
        this.driver = driver;
    }

    //logger
    private static Logger logger = LogManager.getLogger(TransactionPage.class);

    public boolean makeDepositAndVerify() throws Exception {
        WebElement depositBtnElement = driver.findElement(depositBtn);
        depositBtnElement.click();
        Thread.sleep(2000);
        //getting the amount before depositing
        String balanceBeforeDeposit = driver.findElement(By.xpath("//div[@class='borderM box padT20 ng-scope']/child::div[2]/child::strong[2]")).getText();
        String depositAmount = "12";
        WebElement depositInputElement = driver.findElement(depositInput);

        depositInputElement.sendKeys(depositAmount);
        WebElement makeDepositBtnElement = driver.findElement(makeDepositBtn);
        makeDepositBtnElement.click();

        //keeping track of transactions made
        numberOfTransactions++;


        //converting the balance before depostion into integer
        int balBeforeDeposit = Integer.parseInt(balanceBeforeDeposit);
        int depAmount = Integer.parseInt(depositAmount);

        String balanceAfterDeposit = driver.findElement(By.xpath("//div[@class='borderM box padT20 ng-scope']/child::div[2]/child::strong[2]")).getText();
        int balAfterDeposit = Integer.parseInt(balanceAfterDeposit);

        //verifying whether the amount is updated or not after depositing
        if (balBeforeDeposit + depAmount == balAfterDeposit) {
            System.out.println("transaction verified");
            logger.info("balance updated after making deposits");
            return true;
        } else {
            logger.warn("balance not updated after making deposits");
            return false;
        }


    }

    //function to get current balance any time
    public int getCurrentBalance() {


        WebElement curBalanceElement = driver.findElement(currentBalanceInfo);
        String currentBalance = curBalanceElement.getText();
        int curBal = Integer.parseInt(currentBalance);

        return curBal;
    }

    public boolean makeWithdraw() throws Exception {

        //retrieving the current balance through a function to verify whether the withdrawing amount is less or more than current balane
        int balance1 = getCurrentBalance();
        WebElement withDrawElement = driver.findElement(withDraw);
        withDrawElement.click();
        Thread.sleep(1000);
        String withDrawAmount = "6";
        //converting the withdrawing amount to integer to perform arithmetic operations
        int withDrawAmt = Integer.parseInt(withDrawAmount);

        WebElement withDrawInputElement = driver.findElement(withDrawInput);

        //sending the withdraw amount
        withDrawInputElement.sendKeys(withDrawAmount);
        WebElement withDrawBtnElement = driver.findElement(withDrawBtn);

        //getting the error message displayed when withdraw amount> balance present
        WebElement errorTextElement = driver.findElement(errorText);

        //actual message that should be displayed
        String actulaErrorMessage = "Transaction Failed. You can not withdraw amount more than the balance.";

        //checking the withDrawl amount
        if (withDrawAmt > balance1) {
            logger.warn("withdrawing amount greater than balance, so verifying error messages");
            withDrawBtnElement.click();
            String errorMessage = errorTextElement.getText();
            Assert.assertEquals(actulaErrorMessage, errorMessage);
            if(!errorMessage.equals(actulaErrorMessage)){
                return false;
            }else{
                return true;
            }

        } else {
            logger.info("withdrawing amount lesser than balance");
            withDrawBtnElement.click();
            numberOfTransactions++;

            //balance after withdrawing successfully
            int curbal = getCurrentBalance();
            //so the amount before the withdrawing minus amount withdrawn should be equals to balance after withdrawing
            if (balance1 - withDrawAmt == curbal) {
                logger.info("balance updated after withdrawing");
                System.out.println("balance updated after withdrawing");
                return true;
            } else {
                logger.warn("balance not updated after withdarwing");
                return false;
            }

        }


    }

    public void verifyTransaction() throws InterruptedException {

        WebElement getTransactionBtnElement = driver.findElement(getTransactionBtn);
        getTransactionBtnElement.click();
        Thread.sleep(1000);
        driver.navigate().refresh();
        Thread.sleep(2000);
        //storing the number of transactions in a list
        List<WebElement> transaction = driver.findElements(By.xpath("//*[@ng-repeat='tx in transactions | orderBy:sortType:sortReverse | sDate:startDate:end']"));


        //checking if size of list containing all the transactions and total number of actual transactions made are equal or not?
        Assert.assertEquals(numberOfTransactions, transaction.size());


    }
}
