package handler;

/**
 * Created by wangyang on 2019-08-21
 */
public class MessageVO {
    private String userName;
    private String message;


    public MessageVO(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public MessageVO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
