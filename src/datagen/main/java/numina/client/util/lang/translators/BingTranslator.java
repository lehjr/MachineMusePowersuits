package numina.client.util.lang.translators;

import lehjr.numina.common.base.NuminaLogger;
import numina.client.config.DatagenConfig;
import numina.client.util.lang.LanguageProviderHelper;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.List;

public class BingTranslator implements ITranslator{
    DatagenConfig config;
    Language defaultLang;
    List<String> locales;
    Map<String, Map<String, String>> translations;
    Map<Language, LanguageProviderHelper> languageProviderMap = new HashMap<>();

    String TRANSLATION_PAGE = "https://www.bing.com/translator";

    public BingTranslator(@NotNull DatagenConfig config) {
        this.config = config;
        this.defaultLang = config.getMainLanguageCode();
        translations = new HashMap();
        this.locales = new ArrayList<>();

        try {
            WebDriver driver = getWebDriver();
            driver.get(TRANSLATION_PAGE);
            driver.manage().window().maximize();
            setInputLanguage(config.getMainLanguageCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public WebDriver getWebDriver() {
        WebDriver driver = WebDriverProvider.getWebDriver(config);
        NuminaLogger.logDebug("driver: " + driver);
        if (driver != null) {
            NuminaLogger.logDebug("current url: " + driver.getCurrentUrl());
        } else {
            NuminaLogger.logDebug("driver is NULL!!!!");
        }
        if (driver != null && !Objects.equals(driver.getCurrentUrl(), TRANSLATION_PAGE)) {
            driver.get(TRANSLATION_PAGE);
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver -> Objects.equals(webDriver.getCurrentUrl(), TRANSLATION_PAGE));
        }
        return driver;
    }

    /**
     * Sets the input language
     * @param language
     */
    @Override
    public void setInputLanguage(Language language) {
        WebDriver driver = getWebDriver();
        // captcha triggered
        handleCaptcha(driver);

        if (driver != null) {
            new WebDriverWait(driver, Duration.ofMinutes(30)).until(webDriver -> webDriver.findElement(By.xpath("//optgroup[@id=\"t_srcAllLang\"]/option[@value =\"" + language.bing_key() + "\"]")));
        }
        WebElement langIn = driver.findElement(By.xpath("//optgroup[@id=\"t_srcAllLang\"]/option[@value =\"" + language.bing_key() + "\"]"));
        langIn.click();
    }

    /**
     * set output language
     * @param language
     */
    @Override
    public void setOutputLanguage(Language language) {
        WebDriver driver = getWebDriver();
        // captcha triggered
        handleCaptcha(driver);

        WebElement langOut = driver.findElement(By.xpath("//optgroup[@id=\"t_tgtAllLang\"]/option[@value =\"" + language.bing_key() + "\"]"));
        langOut.click();
    }

    /**
     * Swaps the text to translate with the translated text. Useful to verify translation accuracy
     */
    @Override
    public void swapTranslations() {
        try {
            WebDriver driver = getWebDriver();
            // captcha triggered
            handleCaptcha(driver);

            WebElement copy_link = driver.findElement(By.id("tta_revIcon"));
            copy_link.click();

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the text in the input box
     * @param input
     */
    @Override
    public void setInputString(String input) {
        try {
            WebDriver driver = getWebDriver();
            // captcha triggered
            handleCaptcha(driver);

            WebElement textInput = driver.findElement(By.id("tta_input_ta"));
            if (!textInput.getText().equals(input)) {
                textInput.clear();
                textInput.sendKeys(input);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isCaptcha(WebDriver driver) {
        try {
            if (driver.getPageSource().contains("t_captchaSubmit")) {
                WebElement t_captchaSubmit = driver.findElement(By.id("t_captchaSubmit"));
                ExpectedCondition<Boolean> condition = ExpectedConditions.stalenessOf(t_captchaSubmit);
                new WebDriverWait(driver, Duration.ofMinutes(5)).until(condition);
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    void handleCaptcha(WebDriver driver) {
        while (isCaptcha(driver)) {
            NuminaLogger.logDebug("waiting for captcha");
        }
    }

    WebElement getCopyLink(WebDriver driver) {
        String source = driver.getPageSource();
        WebElement copy_link;

        try {
            if (source.contains("tta_copyIcon")) {
                copy_link = driver.findElement(By.id("tta_copyIcon"));
                copy_link.click();
                return copy_link;
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (source.contains("tta_copyIcongdf")) {
                    copy_link = driver.findElement(By.id("tta_copyIcongdf"));
                    copy_link.click();
                    return copy_link;
                } else {
                    copy_link = null;
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
                return null;
            }
            return copy_link;
        }
        return null;
    }

    /**
     * Gets the translated output text from the dialog box
     * @return text from output dialog box
     */
    @Override
    public String getOutputText() {
        try {
            Thread.sleep(5000);
            WebDriver driver = getWebDriver();
// unreliable
//            try {
//                WebElement outputBox = driver.findElement(By.id("tta_output_ta" ));
//
//                //<textarea
//                // readonly="readonly"
//                // id="tta_output_ta"
//                // class="tta_no_click_outline tta_focusTextLarge  tta_output_hastxt" dir="ltr"
//                // placeholder="Translation"
//                // aria-label="Output text area"
//                // aria-readonly="true"
//                // aria-disabled="true"
//                // style="height: 120px"
//                // spellcheck="false"
//                // lang="es"></textarea>
//                String ret = outputBox.getAttribute("value");
//                if (!ret.isBlank() && !ret.endsWith("...")) {
//                    return ret;
//                }
//            } catch (Exception ignored) {
//                NuminaLogger.logDebug("attempt at getting text failed");
//            }

            if(driver.getPageSource().contains("tta_output_ta")) {
                WebElement outputText = driver.findElement(By.id("tta_output_ta"));
                NuminaLogger.logDebug("outtext: " + outputText.getText());
                NuminaLogger.logDebug("outtext to string: " + outputText.toString() );
            }

            WebElement copy_link;

            // captcha triggered
            handleCaptcha(driver);

            copy_link = getCopyLink(driver);
            if (copy_link == null) {
                // captcha triggered
                handleCaptcha(driver);
                copy_link = getCopyLink(driver);
            }

            if (copy_link != null) {
                copy_link.click();
                return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            }

            throw new RuntimeException();
        } catch (UnsupportedFlavorException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void quit() {
        try {
            this.getWebDriver().quit();
        } catch (Exception e) {

        }
    }
}
