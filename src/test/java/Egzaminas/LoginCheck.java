package Egzaminas;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginCheck {
    private WebDriver driver;
    private String baseURL = "https://demo.opencart.com/";

    private void invokeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    // @BeforeClass
    // public static void setUpClass() {
    //naudojamas, jei reikia prisijungti prieš kiekvieną testą kaipp vz prie tam tikros duombazės, šiam konkrečiam atvejui nenaudosiu;
    //   }

    @BeforeMethod
    public void setUp() {
        invokeDriver();
    }

    @Test
    public void Dropdown() {
        driver.get(baseURL);
        //1.	Pačiame viršuje paspausti MyAccount
        WebElement dropdown = driver.findElement(By.className("caret"));
        dropdown.click();

        //2.	Atsidarius dropdown‘ui paspausti Login
        WebElement login = driver.findElement(By.linkText("Login"));
        login.click();

        //3.	Patikrinti, jog matomas „New customer“ blokas
        boolean isDisplayed = driver.findElement(By.className("well")).isDisplayed();
        System.out.println(isDisplayed);

        //4.	Patikrinti, jog matomas „Returning customer“ blokas
        boolean isDisplayed2 = driver.findElement(By.xpath("(//div[@class='well'])[2]")).isDisplayed();
        System.out.println(isDisplayed2);

        //5.	Suvesti blogus duomenis į email / password
        driver.findElement(By.id("input-email")).sendKeys("test");
        driver.findElement(By.id("input-password")).sendKeys("test");

        //6.	Paspausti Login
        driver.findElement(By.xpath("//input[@class='btn btn-primary']")).click();


        //7.	Patikrinti, jog rodomas error, kuris turi tekstą „Warning: No match for E-Mail Address and/or Password.“
        String warning = "Warning: No match for E-Mail Address and/or Password.";
        String alert = driver.findElement(By.cssSelector(".alert-danger")).getText();
        assertEquals(alert, warning, "The message is different from the initial warning.");
    }


    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}

//  @AfterClass
// public static void tearDownClass() {
//atsijungti jei reikia nuo duomenų bazės arba išvalyti visus prisijungus, kad duomenų bazė būtų švari, šiam konkrečiam atvejui nenaudosiu;