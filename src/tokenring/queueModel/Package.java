package tokenring.queueModel;

public class Package {
    private final Integer from;
    private final Integer where;
    private Long timeSent; //in order to measure latency, I decide to track sending time of the package

    public Package(
            Integer from,
            Integer where,
            Long timeSent
    ) {
        this.from = from;
        this.where = where;
        this.timeSent = timeSent;
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getWhere() {
        return where;
    }


    public Long getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Long timeSent) {
        this.timeSent = timeSent;
    }
}
