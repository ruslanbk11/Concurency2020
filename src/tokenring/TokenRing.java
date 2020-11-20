package tokenring;

import java.util.ArrayList;

class TokenRing {
    private final ArrayList<RingNode> nodes;

    TokenRing(int nodesCount) {
        this.nodes = new ArrayList<>(nodesCount);
        this.nodes.add(new RingNode(0));
        for (int i = 1; i < nodesCount; i++) {
            this.nodes.add(new RingNode(i, this.nodes.get(i - 1)));
        }
        this.nodes.get(0).setNext(this.nodes.get(nodesCount - 1));
    }

    void sendMessage( int senderIndex, int destinationIndex, long dispatchTime) {
        Message message = new Message(destinationIndex, dispatchTime);
        this.nodes.get(senderIndex).setMessage(message);
    }

    void start() {
        for (RingNode node : nodes) {
            new Thread(node).start();
        }
    }

    public ArrayList<RingNode> getNodes() {
        return nodes;
    }
}
