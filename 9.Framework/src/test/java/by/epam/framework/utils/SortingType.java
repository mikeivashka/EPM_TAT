package by.epam.framework.utils;

import lombok.Getter;

public enum SortingType {

    POPULARITY("Popularity", ""),
    ALPHABETICAL("Alphabetical: A-Z", "name_sort asc"),
    ALPHABETICAL_REVERSED("Alphabetical: Z-A", "name_sort desc"),
    PRICE_ASC("Price: Low to High", "price_sort asc"),
    PRICE_DESC("Price: High to Low", "price_sort desc");

    @Getter
    private final String name;

    @Getter
    private final String value;

    SortingType(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
