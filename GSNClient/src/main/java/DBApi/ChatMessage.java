package DBApi;

import javax.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="message")
    private String message;
    @Column(name="userFromId")
    private int userFromId;
    @Column(name="userToId")
    private int userToId;
    @Column(name="date")
    private long date;
    @Column(name="isReceived")
    private boolean received;
    @Column(name = "chatId")
    private int chatId;

    public ChatMessage(){  }

    public ChatMessage(String message,int userToId,int userFromId,long date,int chatId){
        this.message=message;
        this.userFromId=userFromId;
        this.userToId=userToId;
        this.date=date;
        this.chatId=chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(int userFromId) {
        this.userFromId = userFromId;
    }

    public int getUserToId() {
        return userToId;
    }

    public void setUserToId(int userToId) {
        this.userToId = userToId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date=date;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
