package Pages;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.codec.binary.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import org.apache.bcel.generic.Visitor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPage<htmlReporter> {

   static WebDriver driver;
    //homepage
    By bankManagerLoginBtn = By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button");
    //add customer
    By addCustomerBtn = By.xpath("//div[@class='center']/child::button[1]");
    //customer input fields
    ///html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input
    By customerFirstName = By.xpath("//form[@name='myForm']/child::div[1]/input");
    By customerLastName = By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input");
    By customerPostCode = By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input");
    By getAddCustomerBtnAfterDetails = By.xpath("//button[@class='btn btn-default']");
    String customerFirstNameValue;
    String customerLastnameValue;
    double customerPostCodevalue;
    String customerPostCodeStringValue;
    //open account
    By openAccount = By.xpath("/html/body/div/div/div[2]/div/div[1]/button[2]");
    By customerNameDropDown = By.xpath("//select[@id='userSelect']");
    By currencyDropDown=By.xpath("//select[@id='currency']");
    By process=By.xpath("//button[@type='submit']");
    String customerFullName="Shubham Kumar";
    By home=By.xpath("//button[@class='btn home']");
    By customerLoginBtn=By.xpath("//div[@class='borderM box padT20']/child::div[1]/button");
    By login=By.xpath("//div[@class='form-group']/following::button");
    //transaction page
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
//******************************************************************************************************************


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void bankManagerLoginClick() {
        WebElement bankManagerLoginElement = driver.findElement(bankManagerLoginBtn);
        bankManagerLoginElement.click();
    }

    public void addCustomerBtnClick() {
        WebElement addCustomerBtnElement = driver.findElement(addCustomerBtn);
        addCustomerBtnElement.click();
    }

    public void readCustomerDetails() throws IOException {

        String excelFilePath = "C:\\Users\\shubhamkumar32\\Desktop\\MAIN_ASSIGNMENT_EXCEL_DATA\\Customer_Details.xlsx";
        FileInputStream fis = new FileInputStream(excelFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;

        //iterate over all the rows and columns present in the sheet
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (j == 0) {
                    customerFirstNameValue = cell.getStringCellValue();
                }
                if (j == 1) {
                    customerLastnameValue = cell.getStringCellValue();
                }
                if (j == 2) {
                    customerPostCodevalue = cell.getNumericCellValue();
                }
            }

        }
        int value = (int) customerPostCodevalue;
        customerPostCodeStringValue = String.valueOf(value);



    }

    public void addCustomerDetails() throws InterruptedException {
        WebElement customerFisrtnameElement = driver.findElement(customerFirstName);
        WebElement customerLastnameElement = driver.findElement(customerLastName);
        WebElement customerPostCodeElement = driver.findElement(customerPostCode);
        customerFisrtnameElement.sendKeys(customerFirstNameValue);
        customerLastnameElement.sendKeys(customerLastnameValue);
        customerPostCodeElement.sendKeys(customerPostCodeStringValue);
        Thread.sleep(2000);
        driver.findElement(getAddCustomerBtnAfterDetails).click();
    }

    public void setOpenAccount() throws InterruptedException {

        WebElement openAccountElement = driver.findElement(openAccount);
        openAccountElement.click();
        Thread.sleep(2000);
        //handling dropdown
        //checking whether the name is present in the dropDown or not
        WebElement customerNameDropDownELement = driver.findElement(customerNameDropDown);
        Select customerName = new Select(driver.findElement(customerNameDropDown));

        System.out.println("customer full name is "+customerFullName);
        boolean found = false;
        List<WebElement> allOptions = customerName.getOptions();
        for (int i = 0; i < allOptions.size(); i++) {
            System.out.println("priniting all options of customer name dropdown");
            System.out.println(allOptions.get(i).getText());
            if (allOptions.get(i).getText().equals(customerFullName)) {
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Value exists");
            customerName.selectByVisibleText(customerFullName);
        }else{
            System.out.println("customer name not found");
        }
        //handling currency drop down
        WebElement currencyDropDownElement= driver.findElement(currencyDropDown);
        Select currency=new Select(driver.findElement(currencyDropDown));
        currency.selectByVisibleText("Rupee");
        WebElement processELement= driver.findElement(process);
        processELement.click();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);


    }

    public void customerLogin() throws InterruptedException {
        WebElement homeElement=driver.findElement(home);
        homeElement.click();
        Thread.sleep(1000);
        WebElement customerLoginBtnELement= driver.findElement(customerLoginBtn);
        customerLoginBtnELement.click();
        Thread.sleep(1000);

        Select name=new Select(driver.findElement(customerNameDropDown));
        name.selectByVisibleText(customerFullName);

        WebElement loginElement= driver.findElement(login);
        loginElement.click();
        Thread.sleep(2000);

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
        List<WebElement>transaction=driver.findElements(By.xpath("//*[@ng-repeat='tx in transactions | orderBy:sortType:sortReverse | sDate:startDate:end']"));

        System.out.println("no of transaction in list is "+transaction.size());
        System.out.println("transaction count made by me is "+numberOfTransactions);

        Assert.assertEquals(numberOfTransactions,transaction.size());


    }

    }


//Shubham
//Kumar
//560064