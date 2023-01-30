package test;

import aquality.selenium.browser.AqualityServices;
import lombok.extern.java.Log;
import org.testng.Assert;
import org.testng.annotations.Test;
import form.*;
import util.*;


@Log
public class VKTest extends BaseTest {
    private MyPage myPage;

    @Test
    public void actionsWithVKApi() {
        String login = MyParser.getCredentialsValue("login");
        String password = MyParser.getCredentialsValue("password");
        String messagePost = TextGenerator.generateRandomString();
        String messageComment = TextGenerator.generateRandomString();
        String messageForEdition = TextGenerator.generateRandomString();

        log.info("STEP 1->UI go to vk.com");
        EntryPage entryPage = new EntryPage();
        Assert.assertTrue(entryPage.state().isDisplayed(), "authorization page is not open");

        log.info("STEP 2->UI  autorization");
        entryPage.submitLogin(login);
        InputPasswordPage inputPasswordPage = new InputPasswordPage();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> inputPasswordPage.state().isDisplayed()), "password page is not visible");
        inputPasswordPage.submitPassword(password);

        log.info("STEP 3->UI go to my page");
        FeedPage feedPage = new FeedPage();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> feedPage.state().isDisplayed()), "feed page is not visible");
        feedPage.getLeftSidePanel().clickBtnMyPage();
        Integer userId = VKApiUtil.usersGet().path("response[0].id");

        log.info("STEP 4->API create post on the wall with random message");
        int postId = VKApiUtil.addPost(messagePost).path("response.post_id");

        log.info("STEP 5->UI check, if our post appeared on the wall");
        myPage = new MyPage();
        Assert.assertTrue(myPage.getPost(userId, postId).state().isDisplayed(), "post is not displayed");

        log.info("STEP 6->API edit out post, add image");
        VKApiUtil.editPost(postId, messageForEdition, "src/test/resources/amatory.jpg");

        log.info("STEP 7->UI verify, if our changes are present");
        Assert.assertEquals(myPage.getPost(userId, postId).getTextFromPost(),messageForEdition, "text is same");
        String path=myPage.getPost(userId, postId).confirmImagePath();
        ImageActions.downloadImage(path);
        Assert.assertEquals(ImageActions
                .compareDownloadedImage(FileProvider.getPath("/amatory.jpg")),true,"images are not equals");

        log.info("STEP 8->API add the comment to the post");
        int commentId = VKApiUtil.addComments(postId, messageComment).path("response.comment_id");

        log.info("STEP 9->UI check, if comment is presented");
        myPage.getPost(userId, postId).clickShowComment();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> myPage.getPost(userId, postId).getComment(userId, commentId).state().isDisplayed(), "comment is not displayed"));
        log.info("STEP 10->UI put a like");
        myPage.getPost(userId, postId).addLike();

        log.info("STEP 11->API check, if like is presented");
        Assert.assertTrue(VKApiUtil.isLikePresentOnThePage(postId, userId), "commit is absent on the page");

        log.info("STEP 12->API delete our post");
        VKApiUtil.deletePost(postId);

        log.info("STEP 13->UI check, if post was deleted");
        myPage.getPost(userId, postId).state().waitForNotDisplayed();
        Assert.assertFalse(myPage.getPost(userId, postId).state().isDisplayed(), "post is displayed on the wall");
    }
}

