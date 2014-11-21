package DBApi;

import javax.persistence.*;

@Entity
@Table(name = "avatars")
public class AvatarURL {
    @Id
    @Column(name = "id")
    private int userId;
    @Column(name = "normal")
    private String normal;
    @Column(name = "mini")
    private String mini;

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getMini() {
        return mini;
    }

    public void setMini(String mini) {
        this.mini = mini;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
