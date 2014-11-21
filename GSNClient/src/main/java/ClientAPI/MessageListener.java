package ClientAPI;

import DBApi.PrivateMessage;

public interface MessageListener{
    void receive(PrivateMessage message);
}
