package dataproviders;

import org.testng.annotations.DataProvider;


public class SauceDataProvider {


    @DataProvider(name = "ValidLoginData")
    public static Object[][] getLoginData() {
        return new Object[][]{
                {"3", "standard_user", "secret_sauce"},
                {"4", "problem_user", "secret_sauce"},
                {"5", "performance_glitch_user", "secret_sauce"}
        };

    }
}
