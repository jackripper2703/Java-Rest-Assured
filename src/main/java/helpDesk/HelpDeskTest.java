package helpDesk;

import Core.BaseTest;
import helpers.TestValues;
import org.junit.Assert;
import org.junit.Test;

import static helpers.StringModifier.UniqueString;

public class HelpDeskTest extends BaseTest {

    @Test
    public void checkTicket(){
        String title = UniqueString(TestValues.TEST_TITLE);
        TicketPage ticketPage = new MainPage()
                .createTicket(title,TestValues.TEST_BODY,TestValues.TEST_EMAIL)
                .openlogin()
                .auth("admin", "adminat")
                .findTicket(title);
        Assert.assertTrue(ticketPage.getTitle().contains(title));
        Assert.assertTrue(ticketPage.getBody().contains(TestValues.TEST_BODY));
        Assert.assertTrue(ticketPage.getEmail().contains(TestValues.TEST_EMAIL));
    }

}
