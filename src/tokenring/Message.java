package tokenring;

class Message {
    private int destinationNodeIndex;
    private long dispatchTime;

    Message(int destinationNodeIndex, long dispatchTime) {
        this.destinationNodeIndex = destinationNodeIndex;
        this.dispatchTime = dispatchTime;
    }

    int getDestinationNodeIndex() {
        return destinationNodeIndex;
    }

    long getDispatchTime() {
        return dispatchTime;
    }
}