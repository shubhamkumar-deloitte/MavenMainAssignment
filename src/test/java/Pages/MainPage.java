package Pages;

//import org.apache.bcel.generic.Select;
import org.openqa.selenium.support.ui.Select;
import org.apache.bcel.generic.Visitor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainPage {

    WebDriver driver;
    //homepage
    By bankManagerLoginBtn = By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button");
    //add customer
    By addCustomerBtn = By.xpath("//div[@class='center']/child::button[1]");
    //customer input fields
    By customerFirstName = By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input");
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
    String customerFullName;


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
            customerFullName=customerFirstNameValue+customerLastnameValue;
        }
        int value = (int) customerPostCodevalue;
        customerPostCodeStringValue = String.valueOf(value);

//        System.out.println("printing customer details");
//        System.out.println("fisrt name is "+customerFirstNameValue);
//        System.out.println("last name is "+customerLastnameValue);
//        System.out.println("postal code is"+customerPostCodevalue);

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
//        customerName.selectByVisibleText("Shubham Kumar");


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
        }else{
            System.out.println("customer name not found");
        }
        System.out.println("customer full name is "+customerFullName);

    }
}

//Shubham
//Kumar
//560064