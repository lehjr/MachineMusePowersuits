package numina.client.util.lang.translators;

import com.google.gson.JsonObject;
import numina.client.config.DatagenConfig;
import org.asynchttpclient.util.Utf8UrlEncoder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoogleTranslation implements ITranslator {
    DatagenConfig config;
    Language source_lang = null;
    Language target_lang = null;
    String translationText = "";


    public GoogleTranslation(DatagenConfig config ) {
        this.config = config;
        this.source_lang = config.getMainLanguageCode();

        ///html/body/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[1]/c-wiz/div[2]/c-wiz/div[2]/div/div[3]/div/div[2]/div[2]/div[2]
        JsonObject obj = new JsonObject();

        try {
////            config.set("bing_languages", Localizations2.getBingLanguages());
//            Map<String, String> googleLangs = config.getSomething("googleLanguages");
//            Map<String, String> bingLangs = config.getSomething("bing_languages");
//
//            JsonObject translationMap = new JsonObject();
//            googleLangs.forEach((key, value)->{
//                JsonObject tmp;
//                if(translationMap.has(key)) {
//                    tmp = translationMap.getAsJsonObject(key);
//                } else {
//                    tmp = new JsonObject();
//                }
//                tmp.addProperty("google_key", value);
//                translationMap.add(key, tmp);
//            });
//
//            bingLangs.forEach((key, value)->{
//                JsonObject tmp;
//                if(translationMap.has(key)) {
//                    tmp = translationMap.getAsJsonObject(key);
//                } else {
//                    tmp = new JsonObject();
//                }
//                tmp.addProperty("bing_key", value);
//                translationMap.add(key, tmp);
//            });
//
//            config.set("translationsAvailable", translationMap);
//
//
//            NuminaLogger.logDebug("googleLangs: " + googleLangs.size());
//            NuminaLogger.logDebug("bingLangs: " + bingLangs.size());
//
//            Map<String, String> matches = new HashMap<>();
//            Map<String, String> bingNotFound = new HashMap<>();
//            Map<String, String> googleNotFound = new HashMap<>();
//
//            googleLangs.forEach((key, value)->{
//                if (bingLangs.containsKey(key)) {
//                    if (bingLangs.get(key).equalsIgnoreCase(value)) {
//                        matches.put(key, value);
//                    } else {
//                        NuminaLogger.logDebug("langmatches " + key + ", value: " + value + "!= " + bingLangs.get(key));
//                    }
//                } else {
//                    if (bingLangs.containsValue(value)) {
//                        matches.put(key, value);
//                    } else {
//                        googleNotFound.put(key, value);
//                    }
//                }
//            });
//
//            bingLangs.forEach((key, value)->{
//                if(!matches.containsKey(key)) {
//                    if (!matches.containsValue(value)) {
//                        bingNotFound.put(key, value);
//                    }
//                }
//            });
//
//            NuminaLogger.logDebug("\nbing not found: " + bingNotFound.size());
//            bingNotFound.forEach((key, value) ->{
//                NuminaLogger.logDebug("lang: " + key + ", value: " + value);
//            });
//
//            NuminaLogger.logDebug("\ngoogle not found: " + googleNotFound.size());
//            googleNotFound.forEach((key, value) ->{
//                NuminaLogger.logDebug("lang: " + key + ", value: " + value);
//            });
//
//
//            Map<Language, Map<String, String>> mc_langs = config.getMinecraftLanguages();
//
//            NuminaLogger.logDebug("mc_langs: " + mc_langs.size());
//
//            List<String> mc_matches = new ArrayList<>();
//
//            mc_langs.forEach((key, map) ->{
//                if (bingLangs.containsKey(key)) {
//                    NuminaLogger.logDebug("key found: " + key);
//                    mc_matches.add(key);
//                }
//            });
//            NuminaLogger.logDebug("matches found: " + mc_matches.size() +", not found: " + (mc_langs.size() - mc_matches.size()));
//            NuminaLogger.logDebug("matches: " + mc_matches);
//
//
//            bingLangs.forEach((key, value) -> {
//                config.addLanguageToMinecraft(key, "bing_key", value, "bing_languages");
//            });
//
//            googleLangs.forEach((key, value) -> {
//                config.addLanguageToMinecraft(key, "google_key", value, "googleLanguages");
//            });








//            config.getSomething("minecraft_lanugages");
//            config.getSomething("bing_languages");


//            config.set("minecraft_lanugages", Localizations2.getMinecraftLanguages());


//            WebDriver webDriver = getWebDriver(config);
////            webDriver.get("https://translate.google.com/");
////            List<WebElement> elements = webDriver.findElements(By.className("textarea"));//*[@id="yDmH0d"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[3]/c-wiz[1]
//
//            List<WebElement> elements = webDriver.findElements(By.className("qSb8Pe"));
//            elements.forEach(webElement -> {
//                String dataLanguageCode = webElement.getAttribute("data-language-code");
//                String languageName = webElement.findElement(By.className("Llmcnf")).getAttribute("innerText"); // gets name of language
//
//                obj.addProperty(languageName, dataLanguageCode);
//            });

//            if (!obj.isEmpty()) {
//                config.set("googleLanguages", obj);
//            }

            // TODO: put the languages into a file

//            List<WebElement> elements2 = webDriver.findElements(By.className("Llmcnf"));
//
//            NuminaLogger.logDebug("elements2 size: " + elements2.size());
//
//
//            elements2.forEach(webElement -> NuminaLogger.logDebug("name: " + webElement.getText()));




            /*
                <div class="qSb8Pe" jsname="sgblj" role="option" aria-selected="false" data-language-code="af" tabindex="0">
                    <div class="l7O9Dc">
                        <i class="material-icons-extended VfPpkd-kBDsod hsfpcd" aria-hidden="true" lang="">check</i>
                        <i class="material-icons-extended VfPpkd-kBDsod S3Dwie" aria-hidden="true" lang="">history</i>
                    </div>
                    <div class="Llmcnf" jsname="Lm8Uhb">Afrikaans</div>
                </div>


             */


// Caused by: java.lang.NoSuchMethodError: 'void io.netty.handler.codec.DefaultHeadersImpl.<init>(io.netty.util.HashingStrategy, io.netty.handler.codec.ValueConverter, io.netty.handler.codec.DefaultHeaders$NameValidator, int, io.netty.handler.codec.DefaultHeaders$ValueValidator)'
            // //*[@id="yDmH0d"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[1]/c-wiz/div[2]/c-wiz/div[1]/div/div[3]/div/div[3]/div[29]/div[2]/text()
//            NuminaLogger.logDebug("num elements: " + elements.size());
////            webDriver.wait(5000);
//            webDriver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public WebDriver getWebDriver() {
        return WebDriverProvider.getWebDriver(config);
    }

//    from selenium import webdriver
//    from selenium.webdriver.support import expected_conditions as EC
//    from selenium.webdriver.support.ui import WebDriverWait
//    from selenium.webdriver.common.by import By
//    from fake_useragent import UserAgent
//    from random import randrange
//    from datetime import datetime
//import time
//
//            ua = UserAgent()
//    userAgent = ua.random
//            options = webdriver.ChromeOptions()
//options.add_argument(f"user-agent={userAgent}")
//            options.add_experimental_option('excludeSwitches', ['enable-logging'])
//    browser = webdriver.Chrome(executable_path=r"chromedriver.exe", options=options)
//
//    def translate_term(text, target_lang, source_lang="auto"):
//    translation = 'N/A'
//
//            try:
//            browser.get(f"https://translate.google.com/?sl={source_lang}&tl={target_lang}&text={text}&op=translate")
//    condition = EC.presence_of_element_located((By.XPATH, f"//span[@data-language-for-alternatives='{target_lang}']/span[1]"))
//    element = WebDriverWait(browser, 10).until(condition)
//    translation = element.text
//        time.sleep(randrange(2))
//    except Exception:
//    now = datetime.now()
//    current_time = now.strftime("%H:%M:%S")
//    print("Something went wrong at: ", current_time)
//
//    return translation
//
//if __name__ == "__main__":
//    print(translate_term(text="maison", target_lang="en"))
//    print(translate_term(text="book", target_lang="pt") )
//            browser.close()

    @Override
    public void setInputLanguage(Language language) {
        this.source_lang = language;
    }

    @Override
    public void setOutputLanguage(Language language) {
        this.target_lang = language;
    }

    @Override
    public void swapTranslations() {
//        getWebDriver().findElement()
    }

    @Override
    public void setInputString(String input) {
        this.translationText = Utf8UrlEncoder.encodeQueryElement(input);
    }

    @Override
    public String getOutputText() {
        WebDriver browser = getWebDriver();
        browser.get("https://translate.google.com/?sl={source_lang}&tl={target_lang}&text=" + translationText + "&op=translate");
        ExpectedCondition<WebElement> condition = ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@data-language-for-alternatives='{target_lang}']/span[1]"));
        WebElement element = new WebDriverWait(browser, Duration.ofMillis(10)).until(condition);
        return element.getText();
    }

    @Override
    public void quit() {
        try {
            this.getWebDriver().quit();
        } catch (Exception e) {

        }
    }
}
