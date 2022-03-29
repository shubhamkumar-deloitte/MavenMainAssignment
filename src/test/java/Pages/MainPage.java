package Pages;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class MainPage<htmlReporter> {

    static WebDriver driver;
    //homepage
    By bankManagerLoginBtn = By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button");
    //add customer
    By addCustomerBtn = By.xpath("//div[@class='center']/child::button[1]");
    //customer input fields

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
    By currencyDropDown = By.xpath("//select[@id='currency']");
    By process = By.xpath("//button[@type='submit']");
    String customerFullName = "Shubham Kumar";
    By home = By.xpath("//button[@class='btn home']");
    By customerLoginBtn = By.xpath("//div[@class='borderM box padT20']/child::div[1]/button");
    By login = By.xpath("//div[@class='form-group']/following::button");

    public static int numberOfTransactions = 0;
    static int i = 1;
//******************************************************************************************************************


    public MainPage(WebDriver driver) {
        this.driver = driver;

    }

    //logger
    private static Logger logger= LogManager.getLogger(MainPage.class);

    public void bankManagerLoginClick() {


        try{
            WebElement bankManagerLoginElement = driver.findElement(bankManagerLoginBtn);
            bankManagerLoginElement.click();
            logger.info("bankManagerLoginClick test done, bank manager loggedIN");

        }catch (Exception e){
            e.printStackTrace();
            logger.warn("bankManagerLoginClick test failed");

        }

    }

    public void addCustomerBtnClick() {

        try{
            WebElement addCustomerBtnElement = driver.findElement(addCustomerBtn);
            addCustomerBtnElement.click();
            logger.info("addCustomerBtnClick test done");

        }catch (Exception e){
            e.printStackTrace();
            logger.warn("addCustomerBtnClick test case failed");

        }

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

        try{
            WebElement customerFisrtnameElement = driver.findElement(customerFirstName);
            WebElement customerLastnameElement = driver.findElement(customerLastName);
            WebElement customerPostCodeElement = driver.findElement(customerPostCode);
            customerFisrtnameElement.sendKeys(customerFirstNameValue);
            customerLastnameElement.sendKeys(customerLastnameValue);
            customerPostCodeElement.sendKeys(customerPostCodeStringValue);
            Thread.sleep(2000);
            driver.findElement(getAddCustomerBtnAfterDetails).click();
            logger.info("addCustomerDetails test done, customers details added");

        }catch (Exception e){
            e.printStackTrace();
            logger.warn("addCustomerDetails failed, customer details not added");

        }
    }

    public void setOpenAccount() throws InterruptedException {


        WebElement openAccountElement = driver.findElement(openAccount);
        openAccountElement.click();
        Thread.sleep(2000);
        //handling dropdown
        //checking whether the name is present in the dropDown or not
        WebElement customerNameDropDownELement = driver.findElement(customerNameDropDown);
        Select customerName = new Select(driver.findElement(customerNameDropDown));


        boolean found = false;
        List<WebElement> allOptions = customerName.getOptions();
        for (int i = 0; i < allOptions.size(); i++) {

            if (allOptions.get(i).getText().equals(customerFullName)) {
                found = true;
                break;
            }
        }
        if (found) {
            //System.out.println("Value exists");
            customerName.selectByVisibleText(customerFullName);
            logger.info("setOpenAccount test case and customer name found in newly added customer list");

        } else {
            System.out.println("customer name not found");
            logger.info("customer name not found");

        }
        //handling currency drop down
        WebElement currencyDropDownElement = driver.findElement(currencyDropDown);
        Select currency = new Select(driver.findElement(currencyDropDown));
        currency.selectByVisibleText("Rupee");
        WebElement processELement = driver.findElement(process);
        processELement.click();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
    }

    public void customerLogin() throws InterruptedException {


        try{
            WebElement homeElement = driver.findElement(home);
            homeElement.click();
            Thread.sleep(1000);
            WebElement customerLoginBtnELement = driver.findElement(customerLoginBtn);
            customerLoginBtnELement.click();
            Thread.sleep(1000);

            Select name = new Select(driver.findElement(customerNameDropDown));
            name.selectByVisibleText(customerFullName);

            WebElement loginElement = driver.findElement(login);
            loginElement.click();
            logger.info("customerLogin test case, customer able to log In");
            Thread.sleep(2000);

        }catch (Exception e){
            e.printStackTrace();
            logger.warn("Customer not able to logIn");

        }
    }

}


