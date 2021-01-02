package tokenring;

public class Message {
    private int sender;
    private int destinationNodeIndex;
    private long dispatchTime;

    public Message(int destinationNodeIndex, long dispatchTime) {
        this.destinationNodeIndex = destinationNodeIndex;
        this.dispatchTime = dispatchTime;
    }

    public Message(int sender, int destinationNodeIndex, long dispatchTime) {
        this.sender = sender;
        this.destinationNodeIndex = destinationNodeIndex;
        this.dispatchTime = dispatchTime;
    }

    public int getDestinationNodeIndex() {
        return destinationNodeIndex;
    }

    public long getDispatchTime() {
        return dispatchTime;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
}