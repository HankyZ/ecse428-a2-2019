package com.hankyz;

import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

public class Steps {

    // Variables
    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "/Users/knam/Downloads/chromedriver";
    private final String PRODUCT_URL = "https://www.gmail.com/";
    private final String PRODUCT_NAME = "Monoprice 115365 Monoprice Select Mini 3D Printer";
    private final String DELETE_BTN_NAME = "submit.delete.C3NLW69582M4B4";
    private final String CART_URL = "https://www.amazon.ca/gp/cart/view.html/ref=nav_cart";
    private final String ADD_TO_CART_BTN = "add-to-cart-button";
    private final String ACTIVE_CART = "sc-active-cart";
    private final String CHECKOUT_BTN = "sc-proceed-to-checkout";

    @Given("^I am on a Gmail page$")
    public void iAmOnAGmailPage() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(PRODUCT_URL);
    }

    @Given("^I am logged in$")
    public void iAmLoggedIn() {
    }

    @Given("^I am on my current shopping cart$")
    public void iAmOnMyCurrentShoppingCart() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(CART_URL);
    }

    @And("^I have a product that exists in my shopping cart$")
    public void iHaveAProductThatExistsInMyShoppingCart() throws Throwable {
        // Go to a product page
        goTo(PRODUCT_URL);

        // Add a product to shopping cart
        System.out.println("Attempting to find Add to Cart button.. ");
        WebElement btn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.id(ADD_TO_CART_BTN)));
        System.out.print("Found!\n");
        btn.click();
        System.out.println("Clicking Add to Cart button");

        // Return to cart
        goTo(CART_URL);
    }

    @And("^my shopping cart is empty$")
    public void myShoppingCartIsEmpty() throws Throwable {
        goTo(CART_URL);

        // Wait for presence of current active cart
        WebElement cart = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ACTIVE_CART)));

        // If cart is not empty, delete whatever is in it
        if (!searchForText(cart.getText(), "empty")) {
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.name(DELETE_BTN_NAME)));
            btn.click();
        }
    }

    @And("^I have the same product that already exists in my shopping cart$")
    public void iHaveTheSameProductThatAlreadyExistsInMyShoppingCart() throws Throwable {
        iHaveAProductThatExistsInMyShoppingCart();
    }

    // When
    @When("^I press \"Delete\"$")
    public void iPressDelete() throws Throwable {
        // Attempt to find a delete button and click on it
        try {
            System.out.println("Attempting to find delete button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.name(DELETE_BTN_NAME)));
            System.out.print("Found!\n");
            btn.click();
            System.out.println("Clicking delete button.");
        } catch (Exception e) {
            System.out.println("No delete button present");
        }
    }

    @When("^I press \"Add to cart\"$")
    public void iPressAddToCart() throws Throwable {
        goTo(PRODUCT_URL);
        // Attempt to find add to cart button and click on it
        try {
            System.out.println("Attempting to find Add to cart button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.id(ADD_TO_CART_BTN)));
            System.out.print("Found!\n");
            btn.click();
            System.out.println("Clicking add to cart button");
        } catch (Exception e) {
            System.out.println("No add to cart button present");
        }
    }

    // Then
    @Then("^the product should exist in my shopping cart$")
    public void theProductShouldExistInMyShoppingCart() throws Throwable {
        goTo(CART_URL);

        // Attempt to find active cart
        WebElement cart = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ACTIVE_CART)));

        // Product name should be in active cart
        assertTrue(searchForText(cart.getText(), PRODUCT_NAME));
    }

    @Then("^the product should no longer exist in my shopping cart$")
    public void theProductShouldNoLongerExistInMyShoppingCart() throws Throwable {
        goTo(CART_URL);

        // Attempt to find active cart
        WebElement cart = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ACTIVE_CART)));

        // Product name should not be in active cart
        assertFalse(searchForText(cart.getText(), PRODUCT_NAME));
    }

    @Then("^there is nothing to delete from the shopping cart$")
    public void thereIsNothingToDeleteFromTheShoppingCart() throws Throwable {
        // Attempt to find a delete button
        try {
            System.out.println("Attempting to find a delete button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.name(DELETE_BTN_NAME)));
            btn.click();
            fail("Delete button should not be present");
        } catch (Exception e) {
            System.out.println("No delete button present");
        }
    }

    @And("^the quantity of the product should be equal to two$")
    public void theQuantityOfTheProductShouldBeEqualToTwo() throws Throwable {
        // Attempt to find quantity
        System.out.println("Attempting to find quantity... ");
        WebElement dropDown = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("a-dropdown-prompt")));
        System.out.print("Found!\n");

        // Quantity should be equal to two
        assertTrue(searchForText(dropDown.getText(), "2"));
    }

    @And("^the checkout button exists$")
    public void theCheckoutButtonExists() throws Throwable {
        // Attempt to find checkout button
        try {
            System.out.println("Attempting to find checkout button");
            WebElement checkoutBtn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(CHECKOUT_BTN)));
            System.out.println("Found checkout button.");
            driver.quit();
        } catch (Exception e) {
            fail("No checkout button found. Should have been present.");
        }
    }

    @And("^the checkout button does not exist$")
    public void theCheckoutButtonDoesNotExist() throws Throwable {
        // Attempt to find checkout button
        try {
            System.out.println("Attempting to find checkout button... ");
            WebElement checkoutBtn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(CHECKOUT_BTN)));
            fail("Checkout button was found. Should have not be present.");
        } catch (Exception e) {
            System.out.print("None found!\n");
            driver.quit();
        }
    }

    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
            System.out.print("Done!\n");
        }
    }

    private boolean searchForText(String text, String textToFind) {
        return text.contains(textToFind);
    }

    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }

}