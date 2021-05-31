package hu.vasvari.util;


public enum ColumnName {

    BIRTH_DATE("Szül.Dátum","birthDate"),
    TYPE("Típus","type"),
    COLOR("Szín","color"),
    PET_NAME("Név","name"),
    PET_KIND("Fajta","kind"),
    PET_CHIP_NUMBER("Chipszám", "chipNumber"),
    VACC_DATE("Beoltás ideje", "vaccinationDate"),
    VACC_VALID("Érvényesség", "validDate"),
    VACC_REASON("Mire kapta", "reason"),
    KOTELEZO_KITOLTES("A *-gal jelölt mezők kitöltése kötelező"),
    KOTELEZO_VALASZTAS("Előszőr válassz gazdit!"),
    ;

    private final String msg;
    private String property;

    ColumnName(String msg) {
        this.msg = msg;
    }

    ColumnName(String msg, String property) {
        this.msg = msg;
        this.property = property;
    }

    public String getMsg() {
        return msg;
    }

    public String getProperty() {
        return property;
    }
}

