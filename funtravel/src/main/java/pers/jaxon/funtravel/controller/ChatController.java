package pers.jaxon.funtravel.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import pers.jaxon.funtravel.domain.ChatMessage;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/chat/{sendUser}/{toUser}")
public class ChatController{
    // chat 池
    private static ConcurrentHashMap<String, ChatController> chatMap = new ConcurrentHashMap<>();

    private Session session;// 当前会话,属于websocket的session
    private String sendUser;// 当前用户
    private String toUser;// 接收人

    @OnOpen
    public void onOpen(@PathParam("sendUser") String sendUser,@PathParam("toUser") String toUser,Session session) throws IOException {
        this.sendUser = sendUser;
        this.toUser = toUser;
        this.session = session;

        // 将当前chat存入chat池
        chatMap.put(sendUser, this);

        // 查看接收人状态
        ChatController toUserChat = chatMap.get(toUser);

        // 新建一条返回通知
        ChatMessage notify = new ChatMessage(sendUser,"notify",new Date());

        // 对方不在线
        if( toUserChat == null){
            notify.setContent(toUser +" is offline now");
            this.session.getBasicRemote().sendText(JSONObject.toJSONString(notify));
        }else{
            // 对方在线且正在跟我聊天
            if(toUserChat.toUser.equalsIgnoreCase(sendUser) ) {
                notify.setContent(toUser +" is chatting with you");
                this.session.getBasicRemote().sendText(JSONObject.toJSONString(notify));

                notify.setContent(sendUser + " is chatting with you");
                toUserChat.session.getBasicRemote().sendText(JSONObject.toJSONString(notify));
            }else {
                // 对方在线但在和别人聊天
                notify.setContent(toUser + " is chatting with others now");
                this.session.getBasicRemote().sendText(JSONObject.toJSONString(notify));

                notify.setContent(sendUser +" is waiting for you");
                toUserChat.session.getBasicRemote().sendText(JSONObject.toJSONString(notify));
            }
        }
    }

    @OnClose
    public void onClose() throws IOException {
        chatMap.remove(sendUser);
        ChatController toUserChat = chatMap.get(toUser);
        if(toUserChat != null){
            ChatMessage notify = new ChatMessage(sendUser,"notify",new Date());
            notify.setContent(sendUser +" is offline");
            toUserChat.session.getBasicRemote().sendText(JSONObject.toJSONString(notify));
        }
    }

    @OnMessage
    public void onMessage(String msg) throws IOException {
        ChatController toUserChat = chatMap.get(toUser);

        ChatMessage notify = new ChatMessage(sendUser,"notify",new Date());
        ChatMessage message = new ChatMessage(sendUser,"message",new Date());
        message.setContent(msg);

        if( toUserChat == null){// 对方下线了
            this.session.getBasicRemote().sendText(JSONObject.toJSONString(message));

            notify.setContent(toUser +" is offline");
            this.session.getBasicRemote().sendText(JSONObject.toJSONString(notify));
        }else{ // 对方还在线上
            String res = JSONObject.toJSONString(message);
            this.session.getBasicRemote().sendText(res);
            toUserChat.session.getBasicRemote().sendText(res);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
