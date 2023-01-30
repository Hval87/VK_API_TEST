package form;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

@Log
public class PostForm extends Form {
    private static final String LIKE_LOCATOR = "//div[contains(@class,'like_wall%d_%d')]//div[contains(@class,'PostButtonReactions')]";
    private static final String TEXT_LOCATOR = "//div[@id='wpt%d_%d']/div";
    private static final String IMAGE_LOCATOR = "//*[@id='wpt%d_%d']//a";
    private static final String STYLE_ATTRIBUTE = "style";
    private final ILabel commentsArea = AqualityServices.getElementFactory().getLabel(By.xpath("//span[contains(@class,'js-replies')]"), "show  comment");
    private int userId;
    private int postId;

    public PostForm(int userId, int postId) {
        super(By.xpath(String.format("//div[@id='wpt%d_%d']", userId, postId)), "post on the wall");
        this.userId = userId;
        this.postId = postId;
    }

    public static String createSubstringFromString(String srcString) {
        return StringUtils.substringBetween(srcString, "\"");
    }

    public String getTextFromPost() {
        String loc = String.format(TEXT_LOCATOR, userId, postId);
        return AqualityServices.getElementFactory().getLabel(By.xpath(loc), "my post").getText();
    }

    public void addLike() {
        AqualityServices.getElementFactory()
                .getLabel(By.xpath(String.format(LIKE_LOCATOR, userId, postId)),
                        "like label").click();
    }

    public String confirmImagePath() {
        String tmp = AqualityServices.getElementFactory().getLabel(By.xpath(String.format(IMAGE_LOCATOR, userId, postId)), "image").getAttribute(STYLE_ATTRIBUTE);
        return createSubstringFromString(tmp);
    }

    public void clickShowComment() {
        commentsArea.clickAndWait();
    }

    public static CommentForm getComment(int userId, int commentId) {
        return new CommentForm(userId, commentId);
    }
}
