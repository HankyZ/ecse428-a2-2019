import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Steps {

    // Variables
    private WebDriver driver;
    private final String projectPath = "C:\\Users\\Kristy\\workspace\\ecse428-a2-2019";
    private final String chromeDriverPath = "drivers/chromedriver.exe";
    private final String gmailPath = "https://www.gmail.com/";
    private final String username = "ecse428a2";
    private final String password = "seleniumtest1!";
    private final String defaultRecipient = "yuhuan01@gmail.com";
    private final String defaultSubject = "Selenium Test";
    private final String defaultFilePath = "res\\star.jpg";
    private final String confirmationDialogText = "Send this message without a subject or text in the body?";
    private final String successMessage = "Message sent.";
    private final String errorMessageText = "Please specify at least one recipient.";

    private final String usernameInputId = "identifierId";
    private final String usernameNextButtonId = "identifierNext";
    private final String passwordInputName = "password";
    private final String passwordNextButtonId = "passwordNext";
    private final String composeButtonCssSelector = ".T-I.J-J5-Ji.T-I-KE.L3";
    private final String recipientInputName = "to";
    private final String subjectInputName = "subjectbox";
    private final String attachmentButtonCssSelector = ".a1.aaA.aMZ";
    private final String fileAttachmentProgressBarClassName = "dQ";
    private final String emailSentMessageClassName = "bAq";
    private final String errorMessageClassName = "Kj-JD-Jz";

    // Given
    @Given("I am on a Gmail page")
    public void iAmOnAGmailPage() {
        // open browser
        setUp();
        // navigate to Gmail
        driver.get(gmailPath);
    }

    @And("I am logged in")
    public void iAmLoggedIn() {
        // enter the username
        driver.findElement(By.id(usernameInputId)).sendKeys(username);
        // click on next
        driver.findElement(By.id(usernameNextButtonId)).click();
        // wait for the password field to load then enter password
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.name(passwordInputName)))
                .sendKeys(password);
        // click on next
        driver.findElement(By.id(passwordNextButtonId)).click();
        // wait for login to be completed
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(composeButtonCssSelector)));
    }

    @And("I have opened an empty New Message window")
    public void iHaveOpenedAnEmptyNewMessageWindow() {
        // click on Compose
        driver.findElement(By.cssSelector(composeButtonCssSelector)).click();
        // wait for New Message window to be opened
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.name(recipientInputName)));
    }

    // When
    @When("I write the recipient (.*)")
    public void iWriteTheRecipientRecipient(String recipient) {
        // enter the recipient
        driver.findElement(By.name(recipientInputName)).sendKeys(recipient);
    }

    @When("I write the recipient")
    public void iWriteTheRecipient() {
        // enter the recipient
        driver.findElement(By.name(recipientInputName)).sendKeys(defaultRecipient);
    }

    @And("I write the subject")
    public void iWriteTheSubject() {
        // enter the subject
        driver.findElement(By.name(subjectInputName)).sendKeys(defaultSubject);
    }

    @And("I attach an image file (.*)")
    public void iAttachAnImageFileFile(String filePath) {
        // click on attachment button
        driver.findElement(By.cssSelector(attachmentButtonCssSelector)).click();
        // select image to attach
        selectImage(filePath);
        // wait for the image to be uploaded
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className(fileAttachmentProgressBarClassName)));
    }

    @And("I attach an image file")
    public void iAttachAnImageFile() {
        // click on attachment button
        driver.findElement(By.cssSelector(attachmentButtonCssSelector)).click();
        // select image to attach
        selectImage(defaultFilePath);
        // wait for the image to be uploaded
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className(fileAttachmentProgressBarClassName)));
    }

    @When("I press OK")
    public void iPressOK() {
        driver.switchTo().alert().accept();
    }

    @And("I press Send")
    public void iPressSend() {
        // press the shortcut for send
        String send = Keys.chord(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.name(subjectInputName)).sendKeys(send);
    }

    // Then
    @Then("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        confirmEmailSent();
        tearDown();
    }

    @Then("I should see a confirmation dialog")
    public void iShouldSeeAConfirmationDialog() {
        // wait for the dialog to pop up
        new WebDriverWait(driver, 10).until(ExpectedConditions.alertIsPresent());
        // assert the message of the dialog
        Assert.assertEquals(driver.switchTo().alert().getText(), confirmationDialogText);
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        // wait for the message to pop up
        String message = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.className(errorMessageClassName))).getText();
        // assert the error message content
        Assert.assertEquals(message, errorMessageText);
        tearDown();
    }

    // helper methods

    /**
     * method to confirm system is in appropriate initial state
     */
    private void setUp() {
        // start chrome browser
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        // maximize browser
        driver.manage().window().maximize();
    }

    private void selectImage(String filePath) {
        // wait for the file upload window to be opened
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // copy the file path to the clip board
        StringSelection ss = new StringSelection(projectPath + "\\" + filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        // use a robot to paste the file path and press enter
        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        if (r != null) {
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
        }
        // wait for the file upload window to close
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to confirm that the email was actually sent
     */
    private void confirmEmailSent() {
        // check if the success message is displayed
        new WebDriverWait(driver, 20).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return driver.findElement(By.className(emailSentMessageClassName)).getText().equals(successMessage);
            }
        });
        // there is no need to make assertions since we waited until the success message is displayed
        // if the success message was not displayed then the program will timeout and result in an error
    }

    /**
     * method to ensure system is returned to initial state
     */
    private void tearDown() {
        driver.quit();
    }

}