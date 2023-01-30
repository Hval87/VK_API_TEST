package form;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class EntryPage extends Form {
    private final ITextBox boxLogin=AqualityServices.getElementFactory().getTextBox(By.xpath("//input[@id='index_email']"), "login field");

    public EntryPage() {

        super(By.xpath("//div[@id='index_login']"), "vk authorization page");
    }

    public void submitLogin(String phone) {
        boxLogin.clearAndType(phone);
        boxLogin.submit();
    }
}
