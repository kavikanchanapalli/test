package Pages;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Pepperfry {

	ChromeDriver driver;
	int countofProducts;
	String totalproducts;
	HashMap<String,String> hm=new HashMap<String,String>();
	
	ExtentHtmlReporter exthtml;
	ExtentTest exttest;
	ExtentReports report; 
     @Test(priority=1)
	public void openUrl()
	{
		exthtml=new ExtentHtmlReporter("c:\\SEL_PRACT\\reports\\pepperfry.html");
		report=new ExtentReports();
		report.attachReporter(exthtml);
		report.setSystemInfo("Host Name", "Testsystem");
		report.setSystemInfo("Environment", "Test Envy");
		report.setSystemInfo("User Name", "Kavitha");
		exthtml.config().setDocumentTitle("Pepperfry");
		exthtml.config().setReportName("Pepperfry Functional Testing");
		exthtml.config().setTestViewChartLocation(ChartLocation.TOP);
		exthtml.config().setTheme(Theme.STANDARD);
		
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);

		driver.get("https://www.pepperfry.com/");
		try{Thread.sleep(10000);}catch(Exception e) {}
	}
     @Test(priority=2)
	public void closePopupLogin()
	{
		try{driver.findElement(By.xpath("//div[@id='regPopUp']/a")).click();}catch(Exception e) {}  //close the login popup
	}
     @Test(priority=3)
	public void gotoFurniture()
	{
		WebElement w1=driver.findElement(By.xpath("//a[text()='Furniture']"));		
		Actions act=new Actions(driver);
		act.moveToElement(w1).perform();
	}
     @Test(priority=4)
	public void selectBench()
	{
		driver.findElement(By.linkText("Benches")).click();
		try{Thread.sleep(3000);}catch(Exception e) {}
	}
     @Test(priority=5)
	public void validateTitle()
	{
		if(driver.getTitle().contains("Benches"))
		{
			exttest=report.createTest("Validating Title");
			exttest.log(Status.PASS,"Benches page is displayed");
			try {File f = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(f, new File("c:\\SEL_PRACT\\reports\\Benchespage.png"));
			exttest.addScreenCaptureFromPath("c:\\SEL_PRACT\\reports\\Benchespage.png");} catch(Exception e) {}
			
		}
		else
		{
			exttest=report.createTest("Validating Title");
			exttest.log(Status.FAIL,"Benches page NOT displayed");
			try {File f = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(f, new File("c:\\SEL_PRACT\\reports\\Benchespage.png"));
			exttest.addScreenCaptureFromPath("c:\\SEL_PRACT\\reports\\Benchespage.png");} catch(Exception e) {}
			
		}

	}
     @Test(priority=6)
	public void getAllProducts()
	{
		WebElement productslist=driver.findElement(By.xpath("//ul[starts-with(@class,'clip-main-cat-wrpr')]"));
		List<WebElement> lst=productslist.findElements(By.tagName("li"));
		countofProducts=lst.size();


		totalproducts=driver.findElement(By.xpath("//h3[@class='pf-margin-0 font-13 capitalize']/strong")).getText(); //get Total products

		String prodname,qty;
		for(int i=0;i<lst.size();i++)
		{
			prodname=lst.get(i).findElement(By.xpath("a/div/div[2]/h5")).getText();
			qty=lst.get(i).findElement(By.xpath("a/div/div[2]/p")).getText();
			hm.put(prodname, qty);
		}
	}
     @Test(priority=7)
	public void displayProducts()
	{
		
		exttest=report.createTest("Information Details");
		exttest.log(Status.INFO,"Total All products are :"+totalproducts);
		exttest.log(Status.INFO,"Total product categories are  :"+countofProducts);
		exttest.log(Status.INFO,"Below are list of categories :");
		
		exttest=report.createTest("Product Category Details");
		for(String pname : hm.keySet())
		{
			exttest.log(Status.INFO, pname+"    : "+hm.get(pname));
		}
		
		String indusqty=hm.get("Industrial Benches");
		String q=(indusqty.split(" ")[0]);
		if(q.matches("[0-9]*"))
		{
			exttest=report.createTest("Validate Quantity for Industrial Benches");
			exttest.log(Status.PASS, "Industrial Benches has Quantity value digit :  "+q);
			try {File f = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(f, new File("c:\\SEL_PRACT\\reports\\productpage.png"));
			exttest.addScreenCaptureFromPath("c:\\SEL_PRACT\\reports\\productpage.png");} catch(Exception e) {}
			
		}
		else
		{
			exttest=report.createTest("Validate Quantity for Industrial Benches");
		    exttest.log(Status.FAIL, "Industrial Benches has Quantity Not a digit :  "+q);
		    try {File f = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(f, new File("c:\\SEL_PRACT\\reports\\productpage.png"));
			exttest.addScreenCaptureFromPath("c:\\SEL_PRACT\\reports\\productpage.png");} catch(Exception e) {}
			
			
		}

	}
     @Test(priority=8)
	public void closebrowser()
	{
		driver.quit();
		report.flush();
		try
		{
		Runtime.getRuntime().exec("taskkill /f/ im ChromeDriver.exe");
		}catch(Exception e) {}
	}
	

}
