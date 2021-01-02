package tokenring.queueModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws InterruptedException {

        LatencyPerCapacityTest();

//        LoadTestForDifferentAmountOfMessages();

//        ThroughputPerCapacityTest();

//        checkSpeed();
    }

    static void warmUp() throws InterruptedException {
        BufferedRing someOtherRing = new BufferedRing(2, 2);
        someOtherRing.sendMessage(new Package( 0, 1, System.nanoTime()));
        someOtherRing.sendMessage(new Package( 1, 0, System.nanoTime()));
//        System.out.println("waiting");
        Thread.sleep(100000);
//        System.out.println("warmed");
    }

    static void checkSpeed() throws InterruptedException {

        warmUp();

        int numNodes = 5;
        int amountOfMsgs = 4;
        int capacity = 3;
        for (int j = 0; j < 10; j++) {
            BufferedRing ring = new BufferedRing(numNodes, capacity);
            for (int i = 0; i < amountOfMsgs; i++) {
                ring.sendMessage(new Package( i % numNodes, (i + 1) % numNodes , System.nanoTime()));
            }
            Thread.sleep(1000);
            long sum = 0;
            for (Long latency : ring.getLatencies()) {
                sum += latency;
            }
            System.out.println(ring.getLatencies() + " " + sum/amountOfMsgs);
        }
    }

    static void LatencyPerCapacityTest() throws InterruptedException {

        warmUp();

        ArrayList<Long> latencies = new ArrayList<>();
        int numOfNodes = 6;
//        List<Integer> amountOfMsgs = new ArrayList<>(Arrays.asList(1, 6, 12));
//        List<Integer> capacityList = new ArrayList<>(Arrays.asList(1, 3, 5));
//        for (Integer capacity : capacityList) {
        int capacity = 2;
        int maxMsgs = numOfNodes * (capacity + 1);
        List<Integer> amountOfMsgs = Arrays.asList(
                maxMsgs / 5,
                maxMsgs / 2,
                maxMsgs * 4 / 5);
        for (Integer j : amountOfMsgs) {
            System.out.println("for " + capacity + " cap " + j + " msgs");
            BufferedRing someRing1 = new BufferedRing(numOfNodes, capacity);
            for (int i = 0; i < j; i++) {
                someRing1.sendMessage(new Package(0, numOfNodes - 1, System.nanoTime()));
                Thread.sleep(1);
            }
            for (int i = 0; i < 100; i++) {
                Thread.sleep(10);
                latencies.add(someRing1.getNodeLatencyMarker(numOfNodes - 1));
            }
//            System.out.println(latencies);
            long sum = 0;
            for (Long latency : latencies) {
                sum += latency;
            }
            System.out.println(sum / latencies.size());
            latencies = new ArrayList<>();
        }

//        }
    }

    static void LoadTestForDifferentAmountOfMessages() throws InterruptedException {

        warmUp();

        int numNodes = 6;
        int capacity = 3;
        int msgs = 22;
        List<Long> latencies = new ArrayList<>();
        BufferedRing someRing = new BufferedRing(numNodes, capacity);

        for (int i = 0; i < msgs; i++) {
//            someRing.sendMessage(new Package( i % numNodes, (i + 1) % numNodes, System.nanoTime()));
            someRing.sendMessage(new Package(0, 2, System.nanoTime()));
            Thread.sleep(1);
        }
        for (int i = 0; i < 30; i++) {
            Thread.sleep(20);
            latencies.add(someRing.getNodeLatencyMarker(2));
        }
        System.out.println(numNodes + " nodes " + capacity + " cap " + msgs + "msgs" );
        System.out.println(latencies);
    }

    static void ThroughputPerCapacityTest() throws InterruptedException {

        warmUp();

        ArrayList<Integer> throughputs = new ArrayList<>();
        int numOfNodes = 6;
        System.out.println(numOfNodes + " nodes");
//        List<Integer> capacities = Arrays.asList(1, 2, 3);
        int capacity = 3;
//        for (Integer capacity : capacities) {
            int maxMsgs = numOfNodes * (capacity + 1);
            List<Integer> amountOfMsgs = Arrays.asList(
                    maxMsgs / 5,
                    maxMsgs / 2,
                    maxMsgs * 4 / 5);
            for (Integer j : amountOfMsgs) {
                System.out.println("for " + capacity + " cap " + j + " msgs");
                BufferedRing someRing1 = new BufferedRing(numOfNodes, capacity);
                for (int i = 0; i < j; i++) {
                    someRing1.sendMessage(new Package(0, numOfNodes - 1, System.nanoTime()));
                    Thread.sleep(15);
                }
                for (int i = 0; i < 50; i++) {
                    someRing1.resetThroughputs();
                    Thread.sleep(1100);
                    throughputs.addAll(someRing1.getNodesThroughputs());
                }
                System.out.println(throughputs);
                throughputs = new ArrayList<>();
            }
//        }
    }

//    static void LoadTestForDifferentAmountOfMessages() throws InterruptedException {
//
//        warmUp();
//
//        int numNodes = 6;
//        int capacity = 3;
//        int numOfMsgs = 22;
////        for (numOfMsgs = 4; numOfMsgs < (numNodes * (capacity + 1)); numOfMsgs += 2) {
//            System.out.println(numNodes + " nodes " + capacity + " cap " + numOfMsgs + "msgs");
//            List<Integer> throughputs = new ArrayList<>();
//            BufferedRing someRing = new BufferedRing(numNodes, capacity);
//            for (int i = 0; i < numOfMsgs; i++) {
//                someRing.sendMessage(new Package(0, 2, System.nanoTime()));
//                Thread.sleep(15);
//            }
//            for (int i = 0; i < 30; i++) {
//                someRing.resetThroughputs();
//                Thread.sleep(1000);
//                throughputs.addAll(someRing.getNodesThroughputs());
//            }
//            System.out.println(throughputs);
//        }
////    }
    }
