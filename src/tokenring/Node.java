package tokenring;

import tokenring.Message;

public interface Node {
    public void sendMessage(Message message);

    public void receiveMessage(Message message);

    public void setMessage(Message message);

    public void setNext(Node next);
}
