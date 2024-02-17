package com.curehealth;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	 //public WebDriver driver;
	 WebDriver driver=new ChromeDriver();
	@Parameters("browserName")
	 @Test
	public void ALaunchWebSite(String browser)
	{
		switch(browser)
		{
		case "chrome":
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		break;
		
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver=new ChromeDriver();
			break;
			
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver=new ChromeDriver();
			break;
			
		case "opera":
			WebDriverManager.operadriver().setup();
			driver=new ChromeDriver();
			break;
			
			default:
				break;
		}
		
		
		driver.get("https://katalon-demo-cura.herokuapp.com/");
		driver.manage().window().maximize();
		driver.findElement(By.id("btn-make-appointment")).click();
		//driver.close();
	}
	@Parameters({"username","password"})
	@Test
	public void BLogin(String username,String password)
	{
		//System.out.println("Paramnater of username"+username);
		//System.out.println("Paramnater of username"+password);
		
		
		  driver.findElement(By.id("txt-username")).sendKeys(username);
		  driver.findElement(By.id("txt-password")).sendKeys(password);
		driver.findElement(By.id("btn-login")).click();
		 
		
	}
	@AfterTest
	@Test
	public void CApplication()
	{
		WebElement dropdown=driver.findElement(By.id("combo_facility"));
	
	Select select=new Select(dropdown);
	select.selectByVisibleText("Hongkong CURA Healthcare Center");
	driver.findElement(By.id("chk_hospotal_readmission")).click();
	driver.findElement(By.id("radio_program_medicaid")).click();
	driver.findElement(By.id("txt_visit_date")).click();
	//new WebDriverWait(driver,Duration.ofSeconds(5))
	//.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("datepicker datepicker-dropdown dropdown-menu datepicker-orient-left datepicker-orient-bottom"))
String currentMonth=driver.findElement(By.cssSelector("div[class='datepicker-days'] th[class='datepicker-switch']")).getText();
System.out.println(currentMonth);
String month=currentMonth;
//String year=currentMonth.split("")[1].trim();
while(month.equals("May 2024"))
{
	driver.findElement(By.xpath("//div[@class='datepicker-days']//th[@class='next'][normalize-space()='»']")).click();
	System.out.println(month);
	 month=currentMonth.split("")[0].trim();	
	//year=currentMonth.split("")[0].trim();
}
driver.findElement(By.cssSelector("tbody tr:nth-child(2) td:nth-child(2)")).click();
driver.findElement(By.id("txt_comment")).sendKeys("Book my appointment");
driver.findElement(By.id("btn-book-appointment")).click();
	}
	
	
	@AfterTest
	@Test
	public void ScreenShot() throws Throwable
	{
		//Convert Web Driver object to TakeScreenShot
		TakesScreenshot scrshot=((TakesScreenshot) driver);
		
		//call getScreenshotAs method to create image file
		File srcFile=scrshot.getScreenshotAs(OutputType.FILE);
		
		//Move image file to new Destination
		
		File destFile=new File("./src/test/resources/Screenshot/Image.jpg");
		//copy file at destination
		FileUtils.copyFile(srcFile, destFile);
	}
	
	@AfterTest
	@Test
	public void quit()
	{
		driver.close();
	}
}
