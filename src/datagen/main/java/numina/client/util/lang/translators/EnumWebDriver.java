package numina.client.util.lang.translators;

public enum EnumWebDriver {
    CHROME_DRIVER,
    //        CHROMIUM_DRIVER,
    FIREFOX_DRIVER,
    //        OPERA_DRIVER,
    EDGE_DRIVER,
    IE_DRIVER,
    SAFARI_DRIVER,
    VOID_DRIVER;

    public String getName() {
        return this.name();
    }
}
