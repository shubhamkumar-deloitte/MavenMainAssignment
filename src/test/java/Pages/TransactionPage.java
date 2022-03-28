package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class TransactionPage  {

    static WebDriver driver;

    By depositBtn=By.xpath("//button[@ng-click='deposit()']");
    By depositInput=By.xpath("//input[@ng-model='amount']");
    By makeDepositBtn=By.xpath("//button[@class='btn btn-default']");
    By currentBalanceInfo=By.xpath("//div[@class='borderM box padT20 ng-scope']/child::div[2]/child::strong[2]");
    By withDraw=By.xpath("//button[@ng-click='withdrawl()']");
    By withDrawInput=By.xpath("//form[@name='myForm']/child::div/child::input");
    By withDrawBtn=By.xpath("//button[@type='submit']");

    By errorText=By.xpath("//*[@class='container-fluid mainBox ng-scope']/child::div[1]/child::span");
    By getTransactionBtn=By.xpath("//button[@ng-click='transactions()']");
    By numberOftransaction=By.xpath("//*[@ng-repeat='tx in transactions | orderBy:sortType:sortReverse | sDate:startDate:end']");
    public static int numberOfTransactions=0;
    static int i=1;

    public TransactionPage(WebDriver driver){
        this.driver=driver;
    }

    public void makeDepositAndVerify() throws Exception {
        WebElement depositBtnElement= driver.findElement(depositBtn);
        depositBtnElement.click();
        Thread.sleep(2000);
        String balanceBeforeDeposit=driver.findElement(By.xpath("//div[@class='borderM box padT20 ng-scope']/child::div[2]/child::strong[2]")).getText();
        String depositAmount="12";
        WebElement depositInputElement= driver.findElement(depositInput);

        depositInputElement.sendKeys(depositAmount);
        WebElement makeDepositBtnElement= driver.findElement(makeDepositBtn);
        makeDepositBtnElement.click();
        numberOfTransactions++;
        System.out.println("number of trans" + numberOfTransactions);

        int balBeforeDeposit=Integer.parseInt(balanceBeforeDeposit);
        int depAmount=Integer.parseInt(depositAmount);

        String balanceAfterDeposit=driver.findElement(By.xpath("//div[@class='borderM box padT20 ng-scope']/child::div[2]/child::strong[2]")).getText();
        int balAfterDeposit=Integer.parseInt(balanceAfterDeposit);
        if(balBeforeDeposit+depAmount==balAfterDeposit){
            System.out.println("transaction verified");
        }else{
            throw new Exception("amount not updated");
        }


    }

    public int getCurrentBalance(){

        WebElement curBalanceElement= driver.findElement(currentBalanceInfo);
        String currentBalance=curBalanceElement.getText();
        int curBal=Integer.parseInt(currentBalance);

        return curBal;
    }

    public void makeWithdraw() throws Exception {
        int balance1=getCurrentBalance();
        WebElement withDrawElement= driver.findElement(withDraw);
        withDrawElement.click();
        Thread.sleep(1000);
        String withDrawAmount="96";
        int withDrawAmt=Integer.parseInt(withDrawAmount);
        WebElement withDrawInputElement=driver.findElement(withDrawInput);
        withDrawInputElement.sendKeys(withDrawAmount);
        WebElement withDrawBtnElement=driver.findElement(withDrawBtn);
        WebElement errorTextElement=driver.findElement(errorText);
        String actulaErrorMessage="Transaction Failed. You can not withdraw amount more than the balance.";
        if(withDrawAmt>balance1){
            withDrawBtnElement.click();
            String errorMessage=errorTextElement.getText();
            Assert.assertEquals(actulaErrorMessage,errorMessage);

        }else{
            withDrawBtnElement.click();
            numberOfTransactions++;

            int curbal=getCurrentBalance();
            if(balance1-withDrawAmt==curbal)
            {
                System.out.println("balance updated after withdrawing");
            }else
            {
                throw new Exception("balanced do not updated");
            }
            System.out.println("total transactions are"+numberOfTransactions);
        }


    }
    public void verifyTransaction() throws InterruptedException {

        WebElement getTransactionBtnElement=driver.findElement(getTransactionBtn);
        getTransactionBtnElement.click();
        Thread.sleep(1000);
        driver.navigate().refresh();
        Thread.sleep(2000);
        List<WebElement> transaction=driver.findElements(By.xpath("//*[@ng-repeat='tx in transactions | orderBy:sortType:sortReverse | sDate:startDate:end']"));

        System.out.println("no of transaction in list is "+transaction.size());
        System.out.println("transaction count made by me is "+numberOfTransactions);

        Assert.assertEquals(numberOfTransactions,transaction.size());


    }
}
