package Selenium.helpDesk;

import Selenium.Core.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@id=\"username\"]")
    private WebElement inputUsername;

    @FindBy(xpath = "//input[@id=\"password\"]")
    private WebElement inputPassword;

    public LoginPage(){
        PageFactory.initElements(driver, this);
    }

    public TicketsPage auth(String name,String password){
    inputUsername.sendKeys(name);
    inputPassword.sendKeys(password, Keys.ENTER);
    return new TicketsPage();
    }
}