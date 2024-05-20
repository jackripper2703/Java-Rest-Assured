package Selenium.helpDesk;

import Selenium.Core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {

    @FindBy(xpath = "//select[@name=\"queue\"]")
    private WebElement selectQueue;

    @FindBy(xpath = "//select[@name=\"queue\"]/option[@value=\"1\"]")
    private WebElement selectIn1Queue;

    @FindBy(xpath = "//*[@id=\"id_title\"]")
    private WebElement inputSummaryOfTheProblem;

    @FindBy(xpath = "//*[@id=\"id_body\"]")
    private WebElement textareaDescriptionOfYourIssue;

    @FindBy(xpath = "//*[@id=\"id_priority\"]")
    private WebElement selectPriority;

    @FindBy(xpath = "//*[@id=\"id_priority\"]/option[@value=\"1\"]")
    private WebElement selectIn1Priority;

    @FindBy(xpath = "//*[@id=\"id_due_date\"]")
    private WebElement inputDueOn;

    @FindBy(xpath = "//*[@id=\"id_submitter_email\"]")
    private WebElement inputYourEMailAddress;

    @FindBy(xpath = "//*[@type='submit']")
    private WebElement buttonSubmit;

    @FindBy(xpath = "//*[@id=\"userDropdown\"]")
    private WebElement buttonUserDropdown;

    public MainPage() {
        driver.get("https://at-sandbox.workbench.lanit.ru/");
        PageFactory.initElements(driver, this);
    }

    public MainPage createTicket(String title,String body, String email){
        selectQueue.click();
        selectIn1Queue.click();
        inputSummaryOfTheProblem.sendKeys(title);
        textareaDescriptionOfYourIssue.sendKeys(body);
        selectPriority.click();
        selectIn1Priority.click();
        inputDueOn.sendKeys("2024-05-22 00:00:00");
        inputYourEMailAddress.sendKeys(email);
        buttonSubmit.click();
        return this;
    }

    public LoginPage openlogin(){
        buttonUserDropdown.click();
        return new LoginPage();
    }
}
