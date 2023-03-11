package util;

public enum Color {
    RESET("\u001B[0m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    SKY_BLUE("\u001B[38;2;135;206;250m"),
    PEACH("\u001B[38;2;255;203;164m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

