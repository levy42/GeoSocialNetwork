package DBApi;

import ClientAPI.LocationManager;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {
    /*@OneToMany
    @JoinTable(
            name="chat_messages",
            joinColumns = @JoinColumn( name="chatId")
    )*/
    @Transient
    @JsonIgnore
    private List<ChatMessage>messages=new ArrayList<ChatMessage>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
    private int id;
    @Column(name = "name")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    @Column(name = "locationGroup")
    private int locationGroup;
    @JsonIgnore
    @Column(name = "open")
    private boolean isOpen;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        setLocationGroup(LocationManager.locationGroup(location));
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public synchronized void addMessage(ChatMessage m){
        messages.add(0,m);
    }

    public int getLocationGroup() {
        return locationGroup;
    }

    private void setLocationGroup(int locationGroup) {
        this.locationGroup = locationGroup;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
}
