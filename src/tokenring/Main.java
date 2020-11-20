package tokenring;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int nodesCount = 8;
        TokenRing tokenRing = new TokenRing(nodesCount);
        tokenRing.start();

//        int sum = 0;
//        for (int i = 0; i < 10; i++) {
//            tokenRing.sendMessage(0, 1, System.currentTimeMillis());
//            tokenRing.sendMessage(1, 2, System.currentTimeMillis());
//            tokenRing.sendMessage(2, 3, System.currentTimeMillis());
//            tokenRing.sendMessage(3, 4, System.currentTimeMillis());
////            tokenRing.sendMessage(4, 5, System.currentTimeMillis());
////            tokenRing.sendMessage(5, 6, System.currentTimeMillis());
////            tokenRing.sendMessage(6, 7, System.currentTimeMillis());
////            tokenRing.sendMessage(7, 8, System.currentTimeMillis());
////            tokenRing.sendMessage(8, 9, System.currentTimeMillis());
//            Thread.sleep(1000);
//            for (RingNode node : tokenRing.getNodes()) {
//                sum += node.getLatency();
//            }
//        }
//        System.out.println("Average latency: " + (sum / 50));
//
        int sum = 0;
        tokenRing.sendMessage(0, 1, System.currentTimeMillis());
        tokenRing.sendMessage(1, 2, System.currentTimeMillis());
        tokenRing.sendMessage(2, 3, System.currentTimeMillis());
        tokenRing.sendMessage(3, 4, System.currentTimeMillis());
        tokenRing.sendMessage(4, 5, System.currentTimeMillis());
        tokenRing.sendMessage(5, 6, System.currentTimeMillis());
        tokenRing.sendMessage(6, 7, System.currentTimeMillis());
        Thread.sleep(2000);
        for (RingNode node : tokenRing.getNodes()) {
            System.out.println(node.getMessagesAmount());
            sum += node.getMessagesAmount();
        }
        System.out.println("Average throughput: " + (sum / nodesCount));
    }
}
