package tokenring.simpleModel;

import tokenring.Message;
import tokenring.Node;

public class ThroughputRingNode implements Node, Runnable {
    private final int index;
    private Node next;
    private volatile Message message;
    private long messagesAmount = 0;
    private long startTimestamp;

    ThroughputRingNode(int index) {
        this.index = index;
    }

    ThroughputRingNode(int index, Node next) {
        this.index = index;
        this.next = next;
    }


    @Override
    public void run() {
        while (true) {
            if (this.message != null) {
                if (this.message.getDispatchTime() != -1) {
//                    System.out.println("START PROCESSSSSSSSSSSSSSSSS");
                    if (messagesAmount == 0) {
                        startTimestamp = this.message.getDispatchTime();
                    }
                    sendMessage(this.message);
                    if (System.nanoTime() - startTimestamp <= 1000000000) {
                        messagesAmount++;
                    }
                    this.message = null;
                } else {
//                    System.out.println("warmUp" + this.message.getDispatchTime());
                    if (this.message.getDestinationNodeIndex() != index) {
                        sendMessage(this.message);
                    }
                    this.message = null;
                }
            }
        }
    }

    @Override
    public void sendMessage(Message message) {
        //        System.out.println("Message sent by ThroughputRingNode" + this.index);
        this.next.receiveMessage(message);
    }

    @Override
    public void receiveMessage(Message message) {
        while (this.message != null) {}
        this.message = message;
    }

    public long getMessagesAmount() {
        return messagesAmount;
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public void setNext(Node next) {
        this.next = next;
    }
}
