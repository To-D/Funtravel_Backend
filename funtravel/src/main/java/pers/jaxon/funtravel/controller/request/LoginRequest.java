package pers.jaxon.funtravel.controller.request;

public class LoginRequest {
    private String userIdentifier;
    private String password;

    public LoginRequest() {
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
