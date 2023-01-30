package form;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LeftSidePanelForm extends Form {
    private final ILink lnkMyPage = AqualityServices.getElementFactory().getLink(By.xpath("//*[@id='l_pr']/a"), "my page button");

    public LeftSidePanelForm() {
        super(By.xpath("//div[@id='side_bar']"), "left side panel");
    }

    public void clickBtnMyPage() {
        lnkMyPage.click();
    }
}
