package tokenring.queueModel;

public interface Medium {
    public void accept(Package data);
    public Package get() throws InterruptedException;
}