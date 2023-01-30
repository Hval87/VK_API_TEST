package form;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class CommentForm extends Form {
    private int userId;
    private int commentId;

    public CommentForm(int userId, int commentId) {
        super(By.xpath(String.format("//div[@class='replies']//div[@id='wpt%d_%d']", userId, commentId)), "post on the wall");
        this.userId = userId;
        this.commentId = commentId;
    }
}
