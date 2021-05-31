package hu.vasvari.util;

public enum ImgResource {

    EXPORT_PIC("/images/export.png"),
    LIST_PIC("/images/list.png"),
    WARNING_PIC("/images/warning.png"),
    ;

    private final String src;

    ImgResource(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }
}
