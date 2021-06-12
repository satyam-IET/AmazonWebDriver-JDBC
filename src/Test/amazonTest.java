package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class amazonTest {

	public static void main(String[] args) {
		
		String Category = null;
		String Searchval = null;
		// TODO Auto-generated method stub
		
System.setProperty("webdriver.chrome.driver", "chromedriver");
		
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://amazon.in/");
		
		//To maximize the browser 
		
		driver.manage().window().maximize();
		
		//To wait for 5 sec in every page
		
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		
		// JDBC Setup for Amazon Database
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://Localhost:3306/amazondetails","root","root");
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Amazon");
			
			while(rs.next()){
				System.out.println(rs.getString(2)+" "+ rs.getString(3));
				Category =rs.getString(2);
				Searchval = rs.getString(3);
				
				
			}
		}catch(ClassNotFoundException e) {
			System.out.println("Class Not Found");
			
		}catch(SQLException e){
			System.out.println("Sql Exception");
			
		}
		
		// Search Category in Dropdown Box & Fetching Category Value from Database
		
		WebElement SearchCategory = driver.findElement(By.xpath("//*[@id='searchDropdownBox']"));
		Select selectOption = new Select(SearchCategory);
		selectOption.selectByVisibleText(Category);
		
		// Entering the search appearance in Search Box & Entering Search from From DB
		
		WebElement enterSearchAppearance = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
		enterSearchAppearance.sendKeys(Searchval);
		
		// Clicking On Search Button
		
		WebElement searchResult =driver.findElement(By.xpath("//*[@type='submit']"));
		searchResult.click();
		
		// Getting all Search Appearance Data , Total No of Data and Details of Data
		
		List<WebElement> getData =  driver.findElements(By.xpath("//h2[@class='a-size-mini a-spacing-none a-color-base s-line-clamp-2']"));
		System.out.println("Totaol No of Mobiles : " + getData.size());
		
		for(WebElement e : getData) {
			System.out.println(e.getText());
		}
		
		// Close The Browser 
		
	    driver.close();
		

	}

}
