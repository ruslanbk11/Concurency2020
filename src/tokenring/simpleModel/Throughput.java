package tokenring.simpleModel;

import tokenring.Node;

public class Throughput {
    public static void main(String[] args) throws InterruptedException {
        int nodesCount = 6;
        int messagesCount = 5;
        SimpleTokenRing tokenRing = new SimpleTokenRing(nodesCount, 't');
        tokenRing.start();

        for (int i = 0; i < 10000; i++) {
            tokenRing.sendMessage(0, 1, -1);
            Thread.sleep(10);
        }

        int sum = 0;
        for (int j = 0; j < messagesCount; j++) {
            tokenRing.sendMessage(j, j + 1, System.nanoTime());
        }
        Thread.sleep(2000);
        for (Node node : tokenRing.getNodes()) {
//            System.out.println( ((ThroughputRingNode) node).getMessagesAmount());
            sum += ((ThroughputRingNode) node).getMessagesAmount();
        }
        System.out.println("Average throughput for " + nodesCount + "nodes" + messagesCount + "messages: "+ (sum / nodesCount));
    }
}
