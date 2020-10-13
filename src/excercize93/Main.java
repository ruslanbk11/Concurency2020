package excercize93;

import java.util.ArrayDeque;

public class Main {

    public static void main(String[] args) {
        SimpleReadWriteLock lock = new SimpleReadWriteLock();
        ArrayDeque<String> deque = new ArrayDeque<>();
        for (int i = 0; i < 3; i++){
            deque.addFirst("Initial string " + i);
        }
        for (int i = 1; i < 10; i++){
            if (i % 3 == 0) {
                System.out.println("NEW WRITER");
                new Thread(() -> writeToQueue(lock, deque)).start();
            } else {
                System.out.println("NEW READER");
                new Thread(() -> readFromQueue(lock, deque)).start();
            }
        }
    }

    private static void readFromQueue(SimpleReadWriteLock lock, ArrayDeque<String> deque) {
        lock.readLock.lock();
        System.out.println(deque.pollFirst());
        lock.readLock.unlock();
    }

    private static void writeToQueue(SimpleReadWriteLock lock, ArrayDeque<String> deque) {
        lock.writeLock.lock();
        deque.addFirst("I am Thread and I wrote this down");
        lock.writeLock.unlock();
        System.out.println("written some text, releasing the lock");
    }
}