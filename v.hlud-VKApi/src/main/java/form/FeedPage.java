package form;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FeedPage extends Form {

    public FeedPage() {
        super(By.xpath("//div[contains(@class,'wall_wrap')]"), "feed page");
    }

    public LeftSidePanelForm getLeftSidePanel() {
        return new LeftSidePanelForm();
    }
}
