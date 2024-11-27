package RevenueCalculatorFitpeo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FitpeoRevenue {

	@Test
	void calculator() {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		// 1.navigated to FitPeo page
		driver.get("https://www.fitpeo.com/");
		driver.manage().window().maximize();
		System.out.println("navigated to fitpeo home page successfully");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// 2.Navigate to the Revenue Calculator Page
		driver.findElement(
				By.xpath("//div[@class=\"satoshi MuiBox-root css-5ty6tm\"][normalize-space()=\"Revenue Calculator\"]"))
				.click();
		System.out.println("Navigate to the Revenue Calculator Page successfully");

		// 3.Scroll Down to the Slider section
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement calculator = driver
				.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 inter css-k0m0w']"));
		System.out.println(js.executeScript("return window.pageYOffset;"));
		js.executeScript("arguments[0].scrollIntoView();", calculator);
		System.out.println("Scroll Down to the Slider section successfully");

		// 4.Adjust the Slider

		Actions act = new Actions(driver);
		WebElement slider = driver.findElement(By.xpath("//span[@class='MuiSlider-rail css-3ndvyc']"));
		act.dragAndDropBy(slider, -26, 0).perform();
		System.out.println("Adjusted the Slider");

		// 5.update text field
		WebElement textField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=':r0:']")));
		textField.clear();
		js.executeScript("arguments[0].value='560';", textField);
		js.executeScript("arguments[0].dispatchEvent(new Event('change'));", textField);

		// 6.Validate Slider Value to 560
		boolean isTextFieldUpdated = wait.until(ExpectedConditions.attributeToBe(textField, "value", "560"));
		if (isTextFieldUpdated) {
			System.out.println("Text field successfully updated to 560.");
		} else {
			System.out.println("Text field not updated to 560.");
		}

		// 7.Select CPT Codes

		WebElement cpt1 = driver.findElement(By.xpath("(//input[@type='checkbox'])[1]"));

		cpt1.click();
		WebElement cpt2 = driver.findElement(By.xpath("(//input[@type='checkbox'])[2]"));

		cpt2.click();
		WebElement cpt3 = driver.findElement(By.xpath("(//input[@type='checkbox'])[3]"));
		cpt3.click();
		WebElement cpt4 = driver.findElement(By.xpath("(//input[@type='checkbox'])[8]"));
		cpt4.click();

//7.Validate Total Recurring Reimbursement
		WebElement reimbursementHeader = driver.findElement(By.xpath("//p[4]"));
		String reimbursementValue = reimbursementHeader.getText();
		if (reimbursementValue.contains("$110700")) {
			System.out.println("Total Recurring Reimbursement is correct: " + reimbursementValue);
		} else {
			System.err.println("Reimbursement value mismatch. Found: " + reimbursementValue);
		}

		driver.close();
	}

}
