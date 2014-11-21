package DBApi;

import javax.persistence.*;

@Entity
@Table(name = "sessions")
public class UserSession {

    @Id
    @Column(name = "userId")
    private int userId;
    @Column(name = "token")
    private String token;

    public UserSession(){

    }

    public UserSession(int userId, String token){
        this.userId=userId;
        this.token=token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
