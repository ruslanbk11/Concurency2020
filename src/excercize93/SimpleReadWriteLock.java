package excercize93;

class SimpleReadWriteLock {
    private int readers;
    private boolean writer;
    Lock readLock;
    Lock writeLock;

    SimpleReadWriteLock(){
        writer = false;
        readers = 0;
        readLock = new ReadLock();
        writeLock = new WriteLock();
    }

    class ReadLock implements Lock {
        @Override
        public void lock() {
            synchronized (this) {
                while (writer) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                readers++;
            }
        }
        @Override
        public void unlock() {
            synchronized (this) {
                readers--;
                if (readers == 0)
                    this.notifyAll();
            }
        }
    }

    class WriteLock implements Lock {
        @Override
        public void lock() {
            synchronized (this) {
                while (readers > 0 || writer){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                writer = true;
            }
        }
        @Override
        public void unlock() {
            synchronized (this) {
                writer = false;
                this.notifyAll();
            }
        }
    }
}