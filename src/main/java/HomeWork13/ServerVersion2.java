package HomeWork13;

import lombok.SneakyThrows;

public class ServerVersion2 {
    volatile int number = 1;

    @SneakyThrows
    public synchronized void oddNumber(int amount) {
            while (number < amount) {
                if (number % 2 == 0) {
                    wait();
                }
                System.out.println("ODD: " + number);
                number++;
                notify();
            }
    }

    @SneakyThrows
    public synchronized void evenNumber(int amount) {
            while (number < amount) {
                if (number % 2 == 1) {
                    wait();
                }
                System.out.println("EVEN: " + number);
                number++;
                notify();
            }
    }

    @SneakyThrows
    public static void main(String[] args) {
        ServerVersion2 server = new ServerVersion2();

        Thread tread1 = new Thread(() -> {
                server.evenNumber(30);
        });

        Thread tread2 = new Thread(() -> {
                server.oddNumber(30);
        });

        Thread.sleep(100);
        tread1.start();
        tread1.setName("EVEN");
        tread2.start();
        tread2.setName("ODD");
    }
}