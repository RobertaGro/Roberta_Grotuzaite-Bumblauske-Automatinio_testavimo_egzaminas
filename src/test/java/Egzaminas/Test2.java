package Egzaminas;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class Test2 {
    private WebDriver driver;
    private String baseURL = "https://demo.opencart.com/";

    private void invokeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void setUp() {
        invokeDriver();
    }

    @DataProvider(name = "data")
    public Object[][] data() {
        return new Object[][]{
                {"iPod Nano"},
               // {"iPod Touch"},
               // {"iPod Shuffle"}
        };
    }

    @Test(dataProvider = "data")
    public void test(String product){
        // 1.	Atidarykite testuojamą puslapį;
        driver.get(baseURL);

        // 2.	Iš Menu turi užvesti (hover) ant MP3 Players
        Actions builder = new Actions(driver);
        WebElement element = driver.findElement(By.linkText("MP3 Players"));
        builder.moveToElement(element).build().perform();

        // 3.	Kai atsidaro MP3 Players paspausti ant Show All MP3 Players
        WebElement showAll = driver.findElement(By.linkText("Show All MP3 Players"));
        showAll.click();

        // 4.	Patikrinti ar atsidarė MP3 Players kategorija (assert)
        boolean categoryDisplayed = showAll.isDisplayed();
        assertTrue(categoryDisplayed, "Show ALL MP3 Players kategorija nebuvo matoma");

        // 5.	Paspausti mygtuką, jog produktus rodytų kaip sąrašą
        WebElement list = driver.findElement(By.className("fa fa-th-list"));
        list.click();

        // 6.	Iš data provider paimti produkto pavadinimą, surasti jį tarp produktų ir:
        // Paspausti mygtuką „Add to Cart“
        WebElement addToCart = driver.findElement(By.linkText("Add to Cart"));
        addToCart.click();

        // Patikrinti, jog atsiranda žinutė „Success: You have added <Produkto pavadinimas> to your shopping cart!“
        driver.getPageSource().contains("Success: You have added <Produkto pavadinimas> to your shopping cart!");

         // 9.	Patikrinti, jog produktas buvo įdėtas į krepšelį (viršuje, dešinėje kampe esantis mini krepšelis )


    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

}
