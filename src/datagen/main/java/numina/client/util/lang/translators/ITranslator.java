package numina.client.util.lang.translators;

import org.openqa.selenium.WebDriver;

public interface ITranslator {

    WebDriver getWebDriver();

    /**
     * Sets the input language
     * @param language
     */
    void setInputLanguage(Language language);

    /**
     * set output language
     * @param language
     */
    void setOutputLanguage(Language language);

    /**
     * Swaps the text to translate with the translated text. Useful to verify translation accuracy
     */
    void swapTranslations();

    /**
     * Sets the text in the input box
     * @param input
     */
    void setInputString(String input);

    /**
     * Gets the translated output text from the dialog box
     * @return text from output dialog box
     */
    String getOutputText();

    void quit();
}
