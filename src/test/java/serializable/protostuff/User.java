package serializable.protostuff;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = -7890663945232864573L;

    public static String USER_NAME = "user_name";

    @Protobuf(fieldType = FieldType.INT32, required = false, order = 1)
    private Integer userId;

    @Protobuf(fieldType = FieldType.STRING, required = false, order = 2)
    private String userName;

    @Protobuf(fieldType = FieldType.STRING, required = false, order = 3)
    private String passWord;

    @Protobuf(fieldType = FieldType.STRING, required = false, order = 4)
    private String userInfo;

    @Protobuf(fieldType = FieldType.OBJECT, required = false, order = 5)
    private List<User> friends;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", passWord=" + passWord + ", userInfo=" + userInfo
                + ", friends=" + friends + "]";
    }

}