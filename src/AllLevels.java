
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AllLevels {

    private static WebDriver driver = null;
    private By startButton = By.id("start_button");
    private By levelTitle = By.cssSelector("h1");
    private By input = By.id("input");
    private By nextButton = By.id("next");
    private By token = By.cssSelector("label").className("custom_dummy_label");
    private By buttons = By.cssSelector("a");
    private By linkText = By.linkText("Enlace!");
    private By source = By.id("source");
    private By target = By.id("target");

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {

	WebDriverManager.chromedriver().setup();
	ArrayList<String> optionsList = new ArrayList<String>();
	ChromeOptions chromeOptions = new ChromeOptions();
	optionsList.add("--start-maximized");
	optionsList.add("--incognito");
	optionsList.add("disable-notifications");
	chromeOptions.addArguments(optionsList);
	chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

	driver = new ChromeDriver(chromeOptions);

    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
	// Close browser
	driver.close();
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws InterruptedException {

	// Open URL
	driver.get("http://pruebaselenium.serviciosdetesting.com/");
	WebElement levelTitleElement = driver.findElement(levelTitle);

	// Level 1
	assertEquals(levelTitleElement.getText(), "Práctica Selenium");
	WebElement startButtonElement = driver.findElement(startButton);
	startButtonElement.click();
	levelTitleElement = driver.findElement(levelTitle);
	assertEquals(levelTitleElement.getText(), "Level 2");

	// Level 2
	WebElement inputElement = driver.findElement(input);
	inputElement.click();
	inputElement.sendKeys("selenium");
	WebElement nextButtonElement = driver.findElement(nextButton);
	nextButtonElement.click();

	levelTitleElement = driver.findElement(levelTitle);
	assertEquals(levelTitleElement.getText(), "Level 3");

	// Level 3
	WebElement tokenElement = driver.findElement(token);
	inputElement = driver.findElement(input);
	inputElement.click();
	inputElement.sendKeys(tokenElement.getText());
	nextButtonElement = driver.findElement(nextButton);
	nextButtonElement.click();

	// Level 4
	levelTitleElement = driver.findElement(levelTitle);
	assertEquals(levelTitleElement.getText(), "Level 4");
	List<WebElement> buttonsElements = driver.findElements(buttons);
	for (int i = 0; i < buttonsElements.size(); i++) {
	    buttonsElements.get(i).click();
	}

	// Level 5
	levelTitleElement = driver.findElement(levelTitle);
	assertEquals(levelTitleElement.getText(), "Level 5");
	WebElement pressLink = driver.findElement(linkText);
	pressLink.click();

	// Level 6
	levelTitleElement = driver.findElement(levelTitle);
	assertEquals(levelTitleElement.getText(), "Level 6");
	((JavascriptExecutor) driver).executeScript("document.getElementById(\"hidden\\\"\").click();");

	// Level 7
	Thread.sleep(1500);
	driver.switchTo().alert().accept();

	// Level 8
	Thread.sleep(1500);
	driver.switchTo().alert().sendKeys("9");
	driver.switchTo().alert().accept();

	// Level 9
	Thread.sleep(1000);
	Set<String> winHandle = driver.getWindowHandles();
	Iterator<String> winIter = winHandle.iterator();
	String currentWindow = winIter.next();
	String popup = winIter.next();
	driver.switchTo().window(popup);
	String passwordValue = driver.findElement(By.tagName("body")).getText();
	driver.switchTo().window(popup).close();
	driver.switchTo().window(currentWindow);
	levelTitleElement = driver.findElement(levelTitle);
	assertEquals(levelTitleElement.getText(), "Level 9");
	inputElement = driver.findElement(input);
	inputElement.sendKeys(passwordValue);
	nextButtonElement = driver.findElement(nextButton);
	nextButtonElement.click();

	// Level 10
	levelTitleElement = driver.findElement(levelTitle);
	assertEquals(levelTitleElement.getText(), "Level 10");

	WebElement sourceElement = driver.findElement(source);
	WebElement targetElement = driver.findElement(target);
	Actions builder = new Actions(driver);

	builder.clickAndHold(sourceElement).moveToElement(targetElement).release(targetElement).build().perform();

	// Final Stage
	levelTitleElement = driver.findElement(levelTitle);
	assertEquals(levelTitleElement.getText(), "¡Enhorabuena! Has llegado al final de la práctica");

	Thread.sleep(5000);
    }

}
