package util;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.UploadPhotoResp;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class VKApiUtil {
    private static Map<String, String> data;
    private static String URL;
    private static String fullRequestPath;

    static {
        data = new HashMap<>();
        URL = MyParser.getConfigValue("url");
        data.put("access_token", MyParser.getCredentialsValue("access_token"));
        data.put("v", MyParser.getCredentialsValue("v"));
    }

    public static Response getResponseGET(String URI, Map<String, String> parametr) {
        fullRequestPath = String.format(URL + "%s", URI);
        return given()
                .when()
                .contentType(ContentType.JSON)
                .params(parametr)
                .get(fullRequestPath)
                .then()
                .extract()
                .response();
    }

    public static Response getResponsePOST(String URI, Map<String, String> param) {
        return given().when()
                .params(param)
                .post(String.format(URL + "%s", VKApiMethod.WALL_EDIT_POST))
                .then()
                .extract().response();
    }

    public static Response usersGet() {
        return getResponseGET(VKApiMethod.USERS_GET, data);
    }

    public static Response addPost(String message) {
        data.put("message", message);
        return getResponseGET(VKApiMethod.WALL_POST, data);
    }

    public static Response addComments(int postId, String comment) {
        data.put("post_id", String.valueOf(postId));
        data.put("message", comment);
        return getResponseGET(VKApiMethod.WALL_CREATE_COMMENT, data);
    }

    public static void deletePost(int postId) {
        data.put("post_id", String.valueOf(postId));
        getResponseGET(VKApiMethod.WALL_POST_DELETE, data);
    }

    public static boolean isLikePresentOnThePage(int postId, int userId) {
        data.put("type", "post");
        data.put("item_id", String.valueOf(postId));
        int count = getResponseGET(VKApiMethod.LIKES_IS_LIKED, data).path("response.liked");
        return count > 0;
    }

    public static Response getDownloadImageQueryResponse() {
        String uploadServer = getResponseGET(VKApiMethod.PHOTOS_GET_UPLOAD_SERVER, data).path("response.upload_url");
        return given().when()
                .multiPart(new File(MyParser.getTestValue("imagePath").toString()))
                .post(uploadServer)
                .then()
                .extract().response();
    }

    public static UploadPhotoResp getModelFromPhotoEditResponse(Response response) {
        String jsonString = response.asString();
        return MyParser.getObjectFromString(jsonString, UploadPhotoResp.class);
    }

    public static Response editPost(int postId, String message, String imagePath) {
        UploadPhotoResp uploadResponse = getModelFromPhotoEditResponse(getDownloadImageQueryResponse());
        data.put("hash", uploadResponse.getHash());
        data.put("photo", uploadResponse.getPhoto());
        data.put("server", uploadResponse.getServer().toString());
        data.put("message", message);
        data.put("post_id", String.valueOf(postId));
        Response response = getResponseGET(VKApiMethod.PHOTOS_SAVE_WALL, data);
        String attachment = "photo" + response.path("response[0].owner_id") + "_" + response.path("response[0].id");
        data.put("attachments", attachment);
        return getResponsePOST(VKApiMethod.WALL_EDIT_POST, data);
    }
}


