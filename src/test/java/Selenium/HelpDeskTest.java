package Selenium;

import Selenium.Core.BaseTest;
import Selenium.helpDesk.MainPage;
import Selenium.helpDesk.TicketPage;
import Selenium.helpers.TestValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static Selenium.helpers.StringModifier.UniqueString;

@Tag("UI")
public class HelpDeskTest extends BaseTest {

    @Test
    public void checkTicket(){
        String title = UniqueString(TestValues.TEST_TITLE);
        TicketPage ticketPage = new MainPage()
                .createTicket(title,TestValues.TEST_BODY,TestValues.TEST_EMAIL)
                .openlogin()
                .auth("admin", "adminat")
                .findTicket(title);
        Assertions.assertTrue(ticketPage.getTitle().contains(title));
        Assertions.assertTrue(ticketPage.getBody().contains(TestValues.TEST_BODY));
        Assertions.assertTrue(ticketPage.getEmail().contains(TestValues.TEST_EMAIL));
    }

}
