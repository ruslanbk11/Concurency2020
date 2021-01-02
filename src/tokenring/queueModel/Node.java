package tokenring.queueModel;

public interface Node extends Runnable {
    public void push(Package pack);
    public void pull();
}