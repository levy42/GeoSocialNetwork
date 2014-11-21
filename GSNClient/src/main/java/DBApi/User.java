package DBApi;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements java.io.Serializable{


    public static final int MALE=0;
    public static final int FEMALE=1;
    /**
     * location will be shown to everybody
     */
    public static final int PUBLIC_MODE=0;
    /**
     * location will be shown to nobody
     */
    public static final int PRIVATE_MODE=1;
    /**
     * location will be shown to friends
     */
    public static final int PROTECTED_MODE=2;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
    private int userId;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "city")
    private String city;
    @Column(name = "sex")
    private int sex;
    @Column(name = "mode")
    private int mode;

    @ElementCollection
    @CollectionTable(name="friends", joinColumns=@JoinColumn(name="user1"))
    @Column(name="user2")
    private Set<Integer> friends=new HashSet<Integer>();

    public User() {
    }

    public User(User u){
        this.setName(u.getName());
        this.setSurname(u.getSurname());
        this.setCity(u.getCity());
        this.setSex(u.sex);
        this.setMode(u.mode);
    }
    public User(String username, String surname,
                String city, int sex, int mode) {
        this.setName(username);
        this.setSurname(surname);
        this.setCity(city);
        this.setSex(sex);
        this.setMode(mode);
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId=userId;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname){this.surname=surname;}

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void clone(User user){
        setName(user.getName());
        setSurname(user.getSurname());
        setCity(user.getCity());
        setMode(user.getMode());
        setSex(user.getSex());
    }

    public Set<Integer> getFriends() {
        return friends;
    }

    public void setFriends(Set<Integer> friends) {
        this.friends = friends;
    }

}