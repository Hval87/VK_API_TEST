package form;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class InputPasswordPage extends Form {
    private final ITextBox boxPassword = AqualityServices.getElementFactory().getTextBox(By.xpath("//input[@name='password']"), "fielg for password");

    public InputPasswordPage() {
        super(By.xpath("//*[contains(@class,'vkc__EnterPasswordNoUserInfo__container')]"), "password page");
    }

    public void submitPassword(String password) {
        boxPassword.clearAndType(password);
        boxPassword.submit();
    }
}
