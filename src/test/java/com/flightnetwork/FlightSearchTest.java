package com.flightnetwork;

import flightnetwork.pages.WebDriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.awt.*;
import java.time.Duration;

import static org.testng.AssertJUnit.assertTrue;

public class FlightSearchTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.createDriver();
    }

    private WebElement waitForElement(By by, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Test
    public void testSearchFlightsAndFilter() throws InterruptedException, AWTException {
        driver.get("https://www.flightnetwork.com/");
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();

        WebElement originField = driver.findElement(By.xpath("//div[@data-testid='searchForm-singleBound-origin-input']//div[@class='css-1hwfws3']"));
        originField.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Origen']")));
        inputField.sendKeys("New York");

        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#react-select-4-option-0")));
        new Actions(driver).moveToElement(optionElement).click().perform();

        WebElement destinyField = driver.findElement(By.xpath("//div[@data-testid='searchForm-singleBound-destination-input']//div[@class='css-1hwfws3']"));
        destinyField.click();

        WebElement destinyInputField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Destino']")));
        destinyInputField.sendKeys("London");

        WebElement destinyElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#react-select-7-option-0")));
        new Actions(driver).moveToElement(destinyElement).click().perform();

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();

        Thread.sleep(20000);

        //Verify that the cheapest flight is visible
        java.util.List<WebElement> cheapestFlights = driver.findElements(By.cssSelector("._1tsyql10"));
        assertTrue("The cheapest flight is not displayed.", !cheapestFlights.isEmpty() && cheapestFlights.get(0).isDisplayed());

        //Verify that standard flight results are visible
        java.util.List<WebElement> standardFlights = driver.findElements(By.cssSelector(".css-1ojsv4 > div > div > div > div > div"));
        assertTrue("Standard flight results are not displayed.", !standardFlights.isEmpty());

        //Verify that there is more than one total result
        int totalResults = cheapestFlights.size() + standardFlights.size();
        assertTrue("Multiple flight results were expected, but found: " + totalResults, totalResults > 1);

        // Apply filter for "Máximo una escala" (Maximum one stop)
        WebElement oneStopOption = waitForElement(By.cssSelector("label[data-testid='MAX_STOPS-max'] div[class='_15h2k5o0 ioeri02 _15h2k5o7 _15h2k5o2']"), Duration.ofSeconds(10));
        oneStopOption.click();

// Wait a moment for the results to load
        Thread.sleep(5000);  // Puede ajustar el tiempo según sea necesario

// Verify that the results are displayed
        java.util.List<WebElement> flightResultsAirlines = driver.findElements(By.cssSelector(".css-1ojsv4 > div > div > div > div > div"));
        assertTrue("Flight results are not displayed after applying the 'Máximo una escala' filter.", !flightResultsAirlines.isEmpty());


        WebElement filtersContainer = driver.findElement(By.cssSelector("[data-testid='resultPage-searchFilters']"));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'nearest'});", filtersContainer);


        Thread.sleep(1000);

        WebElement airlineCheckbox = driver.findElement(By.cssSelector("#airlines-AC"));
        if (airlineCheckbox.isSelected()) {
            airlineCheckbox.click();
        }
        Thread.sleep(5000);


        java.util.List<WebElement> flightResults = driver.findElements(By.cssSelector(".css-1ojsv4 > div > div > div > div > div"));

        for (WebElement flight : flightResults) {
            String flightText = flight.getText();
            assertTrue("Found a flight from Air Canada after filter was unchecked", !flightText.contains("Canada"));
        }

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
