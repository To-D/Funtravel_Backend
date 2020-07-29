package pers.jaxon.funtravel.controller.request;

public class AddNewFriendRequest {
    private String username;
    private String friendName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
