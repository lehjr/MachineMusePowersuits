package numina.client.lang;

public enum Localizations {
    DE_DE,
    EN_US,
    FR_FR,
    PT_BR,
    PT_PT,
    RU_RU,
    ZH_CN;

    String code;
    Localizations() {
        this.code = name().toLowerCase();
    }

    String getCode() {
        return code;
    }
}
