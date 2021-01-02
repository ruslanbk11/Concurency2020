package tokenring.simpleModel;

import tokenring.Message;
import tokenring.Node;

public class LatencyRingNode implements Node, Runnable {
    private final int index;
    private Node next;
    private volatile Message message;
    private long latency;

    LatencyRingNode(int index) {
        this.index = index;
    }

    LatencyRingNode(int index, Node next) {
        this.index = index;
        this.next = next;
    }

    public void run() {
        while (true) {
            if (this.message != null) {
                if (this.message.getDestinationNodeIndex() != index) {
                    sendMessage(this.message);
                } else {
                    this.latency = (System.nanoTime() - message.getDispatchTime());
//                    System.out.println(
//                            "Message received by ThroughputRingNode" + this.index +
//                            " Latency: " + this.latency
//                    );
                }
                this.message = null;
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

    public long getLatency() {
        return latency;
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
