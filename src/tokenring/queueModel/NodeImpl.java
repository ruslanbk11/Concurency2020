package tokenring.queueModel;

public class NodeImpl implements Node, Runnable {
    private final Integer num;
    private Package data;
    private final Medium pushingMedium;
    private final Medium pullingMedium;
    private Long latencyMarker = 0L;
    private Integer amountOfMsgs = 0;
    private boolean isFirstMsgRecieved = false;
    private Long time;

    public NodeImpl(Integer num, Medium pushingMedium, Medium pullingMedium) {
        this.num = num;
        this.pushingMedium = pushingMedium;
        this.pullingMedium = pullingMedium;
    }

    @Override
    public void push(Package pack) {
//        System.out.println("Pushed to queue");
        pushingMedium.accept(data);
        this.data = null;
    }

    @Override
    public void pull() {
        try {
            this.data = pullingMedium.get();
//            System.out.println("Got from queue");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if (!this.isFirstMsgRecieved) {
//            this.time = System.nanoTime();
//            isFirstMsgRecieved = true;
//        } else {
//            if (System.nanoTime() - time <= 1000000000) {
//                amountOfMsgs += 1;
//            }
//        }
        if (this.data.getWhere().equals(this.num)) {
            this.latencyMarker = System.nanoTime() - this.data.getTimeSent();
        }
        if (this.data.getFrom().equals(this.num)) {
            this.data.setTimeSent(System.nanoTime());
        }
    }

    @Override
    public void run() {
        while (true) {
            if (this.data != null) {
                push(this.data);
            } else {
                try {
                    pull();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Integer getNum() {
        return num;
    }

    public Package getData() {
        return data;
    }

    public void setData(Package data) {
        this.data = data;
    }

    public Medium getPushingMedium() {
        return pushingMedium;
    }

    public Medium getPullingMedium() {
        return pullingMedium;
    }

    public Long getLatencyMarker() {
        return latencyMarker;
    }

    public void setLatencyMarker(Long latencyMarker) {
        this.latencyMarker = latencyMarker;
    }

    public Integer getAmountOfMsgs() {
        return amountOfMsgs;
    }

    public void setAmountOfMsgs(Integer amountOfMsgs) {
        this.amountOfMsgs = amountOfMsgs;
    }

    public boolean isFirstMsgRecieved() {
        return isFirstMsgRecieved;
    }

    public void setFirstMsgRecieved(boolean firstMsgRecieved) {
        isFirstMsgRecieved = firstMsgRecieved;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
