package tokenring.simpleModel;

import tokenring.Node;

public class Latency {
    public static void main(String[] args) throws InterruptedException {
//        int messagesCount = 5;
        int nodesCount = 6;
        SimpleTokenRing tokenRing = new SimpleTokenRing(nodesCount, 'l');
        tokenRing.start();

        for (int i = 0; i < 10000; i++) {
            tokenRing.sendMessage(0, 1, System.nanoTime());
            Thread.sleep(10);
        }

        int iterationsNumber = 100;
        for (int messagesCount = 1; messagesCount < nodesCount; messagesCount++) {
            int sum = 0;
            float max = -1, min = Integer.MAX_VALUE;
            for (int i = 0; i < iterationsNumber; i++) {
                for (int j = 0; j < messagesCount; j++) {
                    tokenRing.sendMessage(j, j + 1, System.nanoTime());
                }

                Thread.sleep(1000);
                int sum_1 = 0;
                for (Node node : tokenRing.getNodes()) {
//                    System.out.println(((LatencyRingNode) node).getLatency());
                    sum_1 += ((LatencyRingNode) node).getLatency();
                    float avg = sum_1 / messagesCount;
                    if (avg > max) {
                        max = avg;
                    }
                    if (avg < min && avg > 0) {
                        min = avg;
                    }
                }
                sum += sum_1;
            }
            System.out.println("Average latency for " + nodesCount + "nodes" + messagesCount + "messages: " + (sum / (iterationsNumber * messagesCount)) + "ns, max: " + max + " min: " + min);
        }
    }
}
