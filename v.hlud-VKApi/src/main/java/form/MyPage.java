package form;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyPage extends Form {
    public MyPage() {
        super(By.xpath("//div[@id='profile_wall']"), "my page");
    }

    public PostForm getPost(int userId, int postId) {
        return new PostForm(userId, postId);
    }
}

