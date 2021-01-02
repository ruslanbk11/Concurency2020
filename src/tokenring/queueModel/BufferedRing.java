package tokenring.queueModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BufferedRing {
    private final NodeImpl[] nodes;
    private final Medium[] mediums;

    public BufferedRing(int numOfNodes, int capacity) {
        this.nodes = new NodeImpl[numOfNodes];
        this.mediums = new BlockingQueueMedium[numOfNodes];
        createRing(capacity);
    }

    public void sendMessage(Package pack) {
        this.mediums[pack.getFrom()].accept(pack);
    }

    private void createRing(int capacity) {
        for (int i = 0; i < mediums.length; i++) {
            this.mediums[i] = new BlockingQueueMedium(capacity);

        }
        for (int i = 0; i < nodes.length - 1; i++) {
            this.nodes[i] = new NodeImpl(i, this.mediums[i + 1], this.mediums[i]);
        }
        this.nodes[nodes.length - 1] = new NodeImpl(nodes.length - 1, this.mediums[0], this.mediums[nodes.length - 1]);
        for (Node node : nodes) {
            new Thread(node).start();
        }
    }

    public ArrayList<Long> getLatencies() {
        ArrayList<Long> latencies = new ArrayList<>(this.nodes.length);
        latencies.addAll(Stream.of(this.nodes).map(NodeImpl::getLatencyMarker).collect(Collectors.toList()));
        return latencies;
    }

    public List<Integer> getNodesThroughputs() {
        return Stream.of(this.nodes).map(n -> n.getAmountOfMsgs()).collect(Collectors.toList());
    }

    public void resetThroughputs() {
        for (NodeImpl node : this.nodes) {
            node.setFirstMsgRecieved(false);
            node.setAmountOfMsgs(0);
        }
    }

    public Long getNodeLatencyMarker(int num) {
        return this.nodes[num].getLatencyMarker();
    }
}