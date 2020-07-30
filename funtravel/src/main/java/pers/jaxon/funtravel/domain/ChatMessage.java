package pers.jaxon.funtravel.domain;


import java.util.Date;

public class ChatMessage {
    private String sendUser;

    private Date time = new Date();

    private String content;

    // 返回消息的类型
    private String type;

    // constructors
    public ChatMessage(){}
    public ChatMessage(String sendUser,String type,Date time) {
        this.sendUser = sendUser;
        this.type = type;
        this.time = time;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
