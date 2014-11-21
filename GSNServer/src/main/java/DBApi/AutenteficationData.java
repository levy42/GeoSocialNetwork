package DBApi;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by oleh on 29.10.2014.
 */
@Entity
@Table(name = "passwords")
public class AutenteficationData implements Serializable {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name="user_id")
    private int  userId;

    public AutenteficationData(){}

    public AutenteficationData(String username, String password, int userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
