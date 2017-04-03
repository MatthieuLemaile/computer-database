package com.excilys.mlemaile.cdb.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Story line : Open the website, directly to add a computer. Verify that we are on the right page,
 * then check for wrong date without submit, then with submit (disable js?). check also name
 * required Finally add a computer. Then list all computer, go to last page, and check that there is
 * the last - added computer. Edit that one, check for client side and server side verification.
 * Edit the name and validate. Go back to listing computer, and check for the new name. Delete the
 * last computer, assert that he has been deleted. Add 2 computer, and delete them. Assert their
 * creation, then their deletion.
 * @author Matthieu Lemaile.
 */
public class Test {

    private static WebDriver    driver;
    private final static int    TIMEOUT  = 5;
    private final static String BASE_URL = "http://localhost:8080/ComputerDatabase";

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.gecko.driver", "/opt/geckodriver/geckodriver");
        driver = new FirefoxDriver();

    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }

    @org.junit.Test
    public void test() throws IOException {
        testOpenUrl();
        testValidationDateRealTime();
        testAddComputer();
        testEditValidationDateRealTime();
        testEdit();
        testDelete();
        testMultipleDelete();
        // get a screenshot
        // File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        // FileUtils.copyFile(scrFile, new File("/home/excilys/screen.png"));
    }

    private void testOpenUrl() {
        driver.get(BASE_URL + "/addComputer");
        WebElement el = driver.findElement(By.tagName("h1"));
        assertEquals("Add Computer", el.getText());
    }

    private void testValidationDateRealTime() {
        driver.findElement(By.id("computerName")).sendKeys("ZZAutomatised Test with Selenium");
        driver.findElement(By.id("introduced")).sendKeys("2012-02-05");
        driver.findElement(By.id("discontinued")).sendKeys("2012-02-01");
        Pattern pattern = Pattern.compile("alert alert-danger");
        Matcher matcher = pattern.matcher(driver.findElement(By.id("discontinued"))
                .findElement(By.xpath("..")).getAttribute("class"));
        assertTrue(matcher.find());
    }

    private void testAddComputer() {
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("ZZAutomatised Test with Selenium");
        driver.findElement(By.id("introduced")).clear();
        driver.findElement(By.id("introduced")).sendKeys("2012-02-05");
        driver.findElement(By.id("discontinued")).clear();
        driver.findElement(By.id("discontinued")).sendKeys("2014-02-01");
        driver.findElement(By.id("submit")).click();
        WebElement lastPage = (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("lastPage")));
        lastPage.click();
        WebElement el = (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions
                .presenceOfElementLocated(By.linkText("ZZAutomatised Test with Selenium")));
        List<WebElement> els = el.findElement(By.xpath("../..")).findElements(By.xpath(".//td"));
        assertEquals("ZZAutomatised Test with Selenium", els.get(1).getText());
        assertEquals("2012-02-05", els.get(2).getText());
        assertEquals("2014-02-01", els.get(3).getText());
        assertEquals("", els.get(4).getText());
    }

    private void testEditValidationDateRealTime() {
        WebElement el = driver.findElement(By.linkText("ZZAutomatised Test with Selenium"));
        el.sendKeys(Keys.ENTER);
        // wait the loading of the page
        (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("computerName")));
        assertEquals("ZZAutomatised Test with Selenium",
                driver.findElement(By.id("computerName")).getAttribute("value"));
        assertEquals("2012-02-05", driver.findElement(By.id("introduced")).getAttribute("value"));
        assertEquals("2014-02-01", driver.findElement(By.id("discontinued")).getAttribute("value"));
        driver.findElement(By.id("discontinued")).clear();
        driver.findElement(By.id("discontinued")).sendKeys("2012-02-01");
        Pattern pattern = Pattern.compile("alert alert-danger");
        Matcher matcher = pattern.matcher(driver.findElement(By.id("discontinued"))
                .findElement(By.xpath("..")).getAttribute("class"));
        assertTrue(matcher.find());
    }

    private void testEdit() {
        driver.findElement(By.id("introduced")).clear();
        driver.findElement(By.id("introduced")).sendKeys("2014-02-05");
        driver.findElement(By.id("discontinued")).clear();
        driver.findElement(By.id("discontinued")).sendKeys("2015-02-01");
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName"))
                .sendKeys("ZZAutomatised Test with Selenium[Edited]");
        driver.findElement(By.id("submit")).click();
        WebElement lastPage = (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("lastPage")));
        lastPage.click();
        WebElement el = (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions
                .presenceOfElementLocated(By.linkText("ZZAutomatised Test with Selenium[Edited]")));
        List<WebElement> els = el.findElement(By.xpath("../..")).findElements(By.xpath(".//td"));
        assertEquals("ZZAutomatised Test with Selenium[Edited]", els.get(1).getText());
        assertEquals("2014-02-05", els.get(2).getText());
        assertEquals("2015-02-01", els.get(3).getText());
        assertEquals("", els.get(4).getText());
    }

    private void testDelete() throws IOException {
        driver.findElement(By.id("editComputer")).click();
        WebElement el = (new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions
                .presenceOfElementLocated(By.linkText("ZZAutomatised Test with Selenium[Edited]")));
        List<WebElement> els = el.findElement(By.xpath("../..")).findElements(By.xpath(".//td"));
        WebElement checkbox = els.get(0).findElement(By.xpath(".//input"));
        String id = checkbox.getAttribute("value");
        checkbox.sendKeys(Keys.SPACE);
        driver.findElement(By.id("deleteSelected")).click();

        // confirm the javascript pop up
        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement lastPage = (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("lastPage")));
        lastPage.click();
        (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("firstPage")));
        List<WebElement> listComputers = driver
                .findElements(By.linkText("ZZAutomatised Test with Selenium[Edited]"));
        for (WebElement computer : listComputers) {
            List<WebElement> computerElement = computer.findElement(By.xpath("../.."))
                    .findElements(By.xpath(".//td"));
            WebElement check = computerElement.get(0).findElement(By.xpath(".//input"));
            String idcomputer = check.getAttribute("value");
            assertFalse(id.equals(idcomputer));
        }
    }

    private void testMultipleDelete() {
        addComputer();
        addComputer();
        WebElement lastPage = (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("lastPage")));
        lastPage.click();
        (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("firstPage")));
        driver.findElement(By.id("editComputer")).click();
        List<WebElement> listComputers = driver
                .findElements(By.linkText("ZZAutomatised Test with Selenium"));
        for (WebElement computer : listComputers) {
            List<WebElement> computerElement = computer.findElement(By.xpath("../.."))
                    .findElements(By.xpath(".//td"));
            WebElement check = computerElement.get(0).findElement(By.xpath(".//input"));
            check.sendKeys(Keys.SPACE);

        }
        driver.findElement(By.id("deleteSelected")).click();

        // confirm the javascript pop up
        Alert alert = driver.switchTo().alert();
        alert.accept();

        WebElement secondLastPage = (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("lastPage")));
        secondLastPage.click();
        (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("firstPage")));
        List<WebElement> computersStillThere = driver
                .findElements(By.linkText("ZZAutomatised Test with Selenium[Edited]"));
        assertEquals(0, computersStillThere.size());

    }

    private void addComputer() {
        driver.get(BASE_URL + "/addComputer");
        (new WebDriverWait(driver, TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("computerName")));
        driver.findElement(By.id("computerName")).sendKeys("ZZAutomatised Test with Selenium");
        driver.findElement(By.id("introduced")).sendKeys("2014-02-05");
        driver.findElement(By.id("discontinued")).sendKeys("2015-02-01");
        driver.findElement(By.id("submit")).click();
    }

}
