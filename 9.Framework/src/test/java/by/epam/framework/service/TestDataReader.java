package by.epam.framework.service;

import java.util.ResourceBundle;

public class TestDataReader {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(System.getProperty("environment"));

    public static String getTestData(String key) {
        return resourceBundle.getString(key);
    }

    public static String[] getProductPagesLinksArray() {
        int testDataSize = Integer.parseInt(getTestData("testdata.product.pages.count"));
        String[] allPages = new String[testDataSize];
        for (int i = 0; i < testDataSize; i++) {
            allPages[i]=getTestData("testdata.product.pages." + (i+1));
        }
        return allPages;
    }
}
