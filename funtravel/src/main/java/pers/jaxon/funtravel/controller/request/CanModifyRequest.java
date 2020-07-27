package pers.jaxon.funtravel.controller.request;

public class CanModifyRequest {
    private String username;
    private Long pictureId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }
}
