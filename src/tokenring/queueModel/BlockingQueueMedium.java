package tokenring.queueModel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueMedium implements Medium {
    private final BlockingQueue<Package> queue;

    public BlockingQueueMedium(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    @Override
    public void accept(Package data) {
        try {
//            System.out.println("Message in queue");
            queue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Package get() throws InterruptedException {
//        System.out.println("Message was taken from queue");
        return queue.take();
    }
}