package numina.client.util.lang.translators;

import io.github.bonigarcia.wdm.WebDriverManager;
import numina.client.config.DatagenConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebDriverProvider {
    static WebDriver browser = null;

    /**
     * Gets the webdriver, sets up if needed
     * @return webdriver
     */
    public static WebDriver getWebDriver(DatagenConfig config) {
        if (browser == null && config.getWebDriverType() != EnumWebDriver.VOID_DRIVER) {
            try {
                setUp(config);
                // Sets max loading time for a website.
                browser.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(200));
                browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return browser;
    }

    static void setUp(DatagenConfig config) {
        switch (config.getWebDriverType()) {
            case CHROME_DRIVER: {
                if (browser != null && !(browser instanceof ChromeDriver)) {
                    quitBrowser();
                }
                if (browser==null) {
                    ChromeOptions options = new ChromeOptions();
                    WebDriverManager.chromedriver().setup();
                    browser = new ChromeDriver(options);
                }
                break;
            }

//                case CHROMIUM_DRIVER: {
//                    if (browser != null && !(browser instanceof ChromiumDriver)) {
//                        quitBrowser();
//                    }
//                    if (browser==null) {
//                        ChromiumOptions options = new ChromiumOptions();
//                        WebDriverManager.chromiumdriver().setup();
//                        browser = new ChromiumDriver(options);
//                    }
//                    return browser;
//                }

            case FIREFOX_DRIVER: {
                if (browser != null && !(browser instanceof FirefoxDriver)) {
                    quitBrowser();
                }
                if (browser==null) {
                    FirefoxOptions options = new FirefoxOptions();
//                    options.asMap().forEach((name, value) -> NuminaLogger.logDebug("capability: " + name +", value: " + value));
/*
capability: acceptInsecureCerts, value: true
capability: browserName, value: firefox
capability: moz:debuggerAddress, value: true
capability: moz:firefoxOptions, value: {}
 */
                    WebDriverManager.firefoxdriver().setup();
                    browser = new FirefoxDriver(options);
                    ((FirefoxDriver)(browser)).getCapabilities();

                }
                break;
            }

//                case OPERA_DRIVER: {
//                    if (browser != null && !(browser instanceof OperaDriver)) {
//                        quitBrowser();
//                    }
//                    if (browser==null) {
//                        OperaOptions options = new OperaOptions();
//                        WebDriverManager.chromedriver().setup();
//                        browser = new OperaDriver(options);
//                    }
//                    return browser;
//                }

            case EDGE_DRIVER: {
                if (browser != null && !(browser instanceof EdgeDriver)) {
                    quitBrowser();
                }
                if (browser==null) {
                    EdgeOptions options = new EdgeOptions();
                    WebDriverManager.edgedriver().setup();
                    browser = new EdgeDriver(options);
                }
                break;
            }

            case IE_DRIVER: {
                if (browser != null && !(browser instanceof InternetExplorerDriver)) {
                    quitBrowser();
                }
                if (browser==null) {
                    InternetExplorerOptions options = new InternetExplorerOptions();
                    WebDriverManager.iedriver().setup();
                    browser = new InternetExplorerDriver(options);
                }
                break;
            }

            case SAFARI_DRIVER: {
                if (browser != null && !(browser instanceof SafariDriver)) {
                    quitBrowser();
                }
                if (browser==null) {
                    SafariOptions options = new SafariOptions();
                    WebDriverManager.safaridriver().setup();
                    browser = new SafariDriver(options);
                }
                break;
            }

            // void driver
            default: {
                break;
            }
        }
    }

    public static boolean quitBrowser() {
        try {
            if (browser != null) {
                browser.quit();
                Thread.sleep(1000);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}