package spending.model;

public enum Category {
    ENTERTAINMENT("entertainment"),
    GROCERIES("groceries"),
    TRAVEL("travel");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String alias() {
        return categoryName;
    }
}
