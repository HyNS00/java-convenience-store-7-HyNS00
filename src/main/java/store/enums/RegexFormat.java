package store.enums;

import java.util.regex.Pattern;

public enum RegexFormat {
    VALID_ORDER_FORMAT(Pattern.compile("^\\[([가-힣]+)\\-([1-9][0-9]*)\\](,\\[([가-힣]+)\\-([1-9]+)\\])*$")),
    VALID_RESPONSE_FORMAT(Pattern.compile("^[YN]$"));

    private final Pattern pattern;

    RegexFormat(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
