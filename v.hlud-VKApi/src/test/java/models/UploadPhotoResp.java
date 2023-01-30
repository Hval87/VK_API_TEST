package models;

import lombok.Data;

@Data
public class UploadPhotoResp {
    private Integer server;
    private String photo;
    private String hash;

}
