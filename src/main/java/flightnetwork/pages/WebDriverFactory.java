package flightnetwork.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {

    public static WebDriver createDriver() {
        //ChromeDriver version
        WebDriverManager.chromedriver().driverVersion("135.0.7049.96").setup(); // Update if needed
        return new ChromeDriver();
    }
}
