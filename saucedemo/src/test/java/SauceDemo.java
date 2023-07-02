import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SauceDemo {
    private final String url = "https://www.saucedemo.com/";
    public WebDriver driver;
    public WebDriverWait wait;

    @Test
    public void test1() {
        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
        driver.manage().window().maximize();

        //set username
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        //set password
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        //click login button
        driver.findElement(By.id("login-button")).click();

        By productNamesLocator = By.className("inventory_item_name");
        String firstProductName = "Sauce Labs Backpack";
        //select first product
        selectOptionByValue(driver, productNamesLocator, firstProductName);
        Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@class, 'inventory_details_name large_size')]")).getText().trim(), firstProductName);
        //click add to cart button
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        //click back button
        driver.findElement(By.id("back-to-products")).click();
        //select last product
        String lastProductName = "Test.allTheThings() T-Shirt (Red)";
        selectOptionByValue(driver, productNamesLocator, lastProductName);
        Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@class, 'inventory_details_name large_size')]")).getText().trim(), lastProductName);
        //click add to cart button
        driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        //click back button
        driver.findElement(By.id("back-to-products")).click();
        //click shopping cart button
        driver.findElement(By.className("shopping_cart_link")).click();

        //selected products names list
        List<WebElement> selectedProducts = driver.findElements(By.className("inventory_item_name"));
        Assert.assertEquals(selectedProducts.get(0).getText().trim(), firstProductName);
        Assert.assertEquals(selectedProducts.get(1).getText().trim(), lastProductName);

        //click checkout
        driver.findElement(By.id("checkout")).click();

        //set firstName
        driver.findElement(By.id("first-name")).sendKeys("John");
        //set lastName
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        //set postal code
        driver.findElement(By.id("postal-code")).sendKeys("1245");
        //click continue
        driver.findElement(By.id("continue")).click();
        //click finish button
        driver.findElement(By.id("finish")).click();
        //verify success page
        Assert.assertEquals(driver.findElement(By.tagName("h2")).getText().trim(), "Thank you for your order!");
        //click back home button
        driver.findElement(By.id("back-to-products")).click();

//        Select selectOption = new Select(driver.findElement(By.className("product_sort_container")));
//        selectOption.selectByVisibleText("Price (high to low)");

        for (int i = 0; i < driver.findElements(By.tagName("option")).size(); i++) {
            //click dropDown
            driver.findElement(By.className("product_sort_container")).click();
            driver.findElements(By.tagName("option")).get(i).click();
        }

        //click burger menu
        driver.findElement(By.id("react-burger-menu-btn")).click();
        //click logout link
        driver.findElement(By.id("logout_sidebar_link")).click();

        driver.close();
        driver.quit();
    }

    @Test
    public void test2() {
        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
        driver.manage().window().maximize();

        //set username
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        //set password
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        //click login button
        driver.findElement(By.id("login-button")).click();

        //verify red error message
        Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@class,'error-message-container error')]"))
                .getCssValue("background-color"), "rgba(226, 35, 26, 1)");

        driver.close();
        driver.quit();
    }

    static void staticWaitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void selectOptionByIndex(WebDriver driver, By optionsLocator, int index) {
        List<WebElement> options = driver.findElements(optionsLocator);

        options.get(index - 1).click();
    }

    static void selectOptionByValue(WebDriver driver, By optionsLocator, String value) {
        List<WebElement> options = driver.findElements(optionsLocator);

        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(value)) {
                option.click();
                break;
            }
        }
    }
}

