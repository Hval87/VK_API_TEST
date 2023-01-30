package test;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import util.MyParser;

public class BaseTest {
    protected Browser browser;

    @BeforeMethod
    public void setUp() {
        browser = AqualityServices.getBrowser();
        browser.maximize();
        browser.goTo(MyParser.getConfigValue("startURL"));
    }

    @AfterMethod
    public void tearDown() {
        browser.quit();
    }
}
