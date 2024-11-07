package store.enums;

public enum ResourcePath {
    PRODUCTS("products.md"),
    PROMOTIONS("promotions.md");

    private final String path;

    ResourcePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
