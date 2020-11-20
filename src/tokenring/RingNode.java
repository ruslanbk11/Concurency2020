package tokenring;

public class RingNode implements Runnable {
    private final int index;
    private RingNode next;
    private volatile Message message;
    private long latency;
    private int messagesAmount = 0;
    private long startTimestamp;

    RingNode(int index) {
        this.index = index;
    }

    RingNode(int index, RingNode next) {
        this.index = index;
        this.next = next;
    }

// __________FOR_LATENCY__________
//    public void run() {
//        while (true) {
//            if (this.message != null) {
//                if (this.message.getDestinationNodeIndex() != index) {
//                    sendMessage(this.message);
//                } else {
//                    this.latency = (System.currentTimeMillis() - message.getDispatchTime());
//                    System.out.println(
//                            "Message received by RingNode" + this.index +
//                            " Latency: " + this.latency
//                    );
//                }
//                this.message = null;
//            }
//        }
//    }

// __________FOR_THROUGHPUT__________
    public void run() {
        while (true) {
            if (this.message != null) {
                if (messagesAmount == 0) {
                    startTimestamp = this.message.getDispatchTime();
                }
                sendMessage(this.message);
                if (System.currentTimeMillis() - startTimestamp <= 1000) {
                    messagesAmount++;
                }
                this.message = null;
            }
        }
    }

    public long getLatency() {
        return latency;
    }

    public int getMessagesAmount() {
        return messagesAmount;
    }

    private void sendMessage(Message message) {
//        System.out.println("Message sent by RingNode" + this.index);
        this.next.receiveMessage(message);
    }

    private void receiveMessage(Message message) {
        while (this.message != null) {}
        this.message = message;
    }

    void setMessage(Message message) {
        this.message = message;
    }

    void setNext(RingNode next) {
        this.next = next;
    }

}
