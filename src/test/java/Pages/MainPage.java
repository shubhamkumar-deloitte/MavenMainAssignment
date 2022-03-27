package Pages;

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

public class MainPage {

    WebDriver driver;
    By bankManagerLoginBtn=By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button");
    By addCustomerBtn=By.xpath("//div[@class='center']/child::button[1]");
    By customerFirstName=By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input");
    By customerLastName=By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input");
    By customerPostCode=By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input");
    String customerFirstNameValue;
    String customerLastnameValue;
    double customerPostCodevalue;


    public MainPage(WebDriver driver){
        this.driver=driver;
    }

    public void bankManagerLoginClick(){
        WebElement bankManagerLoginElement=driver.findElement(bankManagerLoginBtn);
        bankManagerLoginElement.click();
    }
    public void addCustomerBtnClick(){
        WebElement addCustomerBtnElement= driver.findElement(addCustomerBtn);
        addCustomerBtnElement.click();
    }
    public void readCustomerDetails() throws IOException {

        String excelFilePath="C:\\Users\\shubhamkumar32\\Desktop\\MAIN_ASSIGNMENT_EXCEL_DATA\\Customer_Details.xlsx";
        FileInputStream fis=new FileInputStream(excelFilePath);
        XSSFWorkbook workbook=new XSSFWorkbook(fis);
        XSSFSheet sheet=workbook.getSheetAt(0);
        XSSFRow row=null;
        XSSFCell cell=null;

        //iterate over all the rows and columns present in the sheet
        for(int i=1;i<sheet.getLastRowNum();i++)
        {
            row=sheet.getRow(i);
            for(int j=0;j<row.getLastCellNum();j++)
            {
                cell=row.getCell(j);
                if(j==0)
                {
                    customerFirstNameValue=cell.getStringCellValue();
                }
                if(j==1){
                    customerLastnameValue=cell.getStringCellValue();
                }
                if(j==2){
                    customerPostCodevalue=cell.getNumericCellValue();
                }
            }
        }

        System.out.println("printing customer details");
        System.out.println("fisrt name is "+customerFirstNameValue);
        System.out.println("last name is "+customerLastnameValue);
        System.out.println("postal code is"+customerPostCodevalue);

    }
    public void addCustomerDetails(){
        WebElement customerFisrtnameElement= driver.findElement(customerFirstName);
        WebElement customerLastnameElement= driver.findElement(customerLastName);
        WebElement customerPostCodeElement= driver.findElement(customerPostCode);
        customerFisrtnameElement.sendKeys("Shubham");
        customerLastnameElement.sendKeys("Kumar");
        customerPostCodeElement.sendKeys("852201");
    }


}

//Shubham
//Kumar
//560064