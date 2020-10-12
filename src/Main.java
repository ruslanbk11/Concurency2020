import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        baseTest();
        transferTest();
    }

    private static void withdrawFromAccount(SavingsAccount account) {
        new Thread(() -> {
            try {
                account.withdraw(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void depositToAccount(SavingsAccount account) {
        new Thread(() -> account.deposit(2)).start();
    }

    private static void baseTest() throws InterruptedException {
        System.out.println("____________BASE__TEST__BEGINING___________");

        SavingsAccount account = new SavingsAccount();

        new Thread(() -> account.deposit(12)).start();

        new Thread(() -> {
            try {
                account.preferedWithdraw(24);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        withdrawFromAccount(account);
        withdrawFromAccount(account);
        withdrawFromAccount(account);

        new Thread(() -> account.deposit(2)).start();

        new Thread(() -> {
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account.deposit(54);
        }).start();

        depositToAccount(account);
        depositToAccount(account);
        depositToAccount(account);
        depositToAccount(account);
        depositToAccount(account);
        sleep(1500);
        depositToAccount(account);

        withdrawFromAccount(account);
        withdrawFromAccount(account);
        withdrawFromAccount(account);
    }

    static void transferTest() {
        System.out.println("____________TRANSFER__TEST__BEGINING___________");
        ArrayList<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>(10);
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            savingsAccounts.add(new SavingsAccount(random.nextInt(200)));
        }

        Iterator<SavingsAccount> iterator = savingsAccounts.iterator();

        for (int i = 9; i >= 0; i--) {
            int finalI = i;
            new Thread(() -> {
                try {
                    savingsAccounts.get(finalI).transfer(100, iterator.next());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (SavingsAccount account : savingsAccounts) {
            new Thread(() -> {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                account.deposit(1000);
            }).start();
        }
    }
}
