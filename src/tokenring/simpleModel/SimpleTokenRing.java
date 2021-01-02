package tokenring.simpleModel;

import tokenring.Message;
import tokenring.Node;

import java.util.ArrayList;

class SimpleTokenRing {
    private final ArrayList<Node> nodes;

    SimpleTokenRing(int nodesCount, char type) {
        this.nodes = new ArrayList<>(nodesCount);
        if (type == 't') {
            this.nodes.add(new ThroughputRingNode(0));
            for (int i = 1; i < nodesCount; i++) {
                this.nodes.add(new ThroughputRingNode(i, this.nodes.get(i - 1)));
            }
        } else if (type == 'l') {
            this.nodes.add(new LatencyRingNode(0));
            for (int i = 1; i < nodesCount; i++) {
                this.nodes.add(new LatencyRingNode(i, this.nodes.get(i - 1)));
            }
        }
        this.nodes.get(0).setNext(this.nodes.get(nodesCount - 1));
    }

    void sendMessage( int senderIndex, int destinationIndex, long dispatchTime) {
        Message message = new Message(destinationIndex, dispatchTime);
        this.nodes.get(senderIndex).setMessage(message);
    }

    void start() {
        for (Node node : this.nodes) {
            new Thread((Runnable) node).start();
        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
