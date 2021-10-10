package HomeWork13;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ServerVersion1 {
        private final List<Integer> integers = new ArrayList<>();

        @SneakyThrows
        public static void main(String[] args) {
            ServerVersion1 serverVersion1 = new ServerVersion1();
            final ReentrantLock threadLock = new ReentrantLock(true);
            final Condition condition = threadLock.newCondition();

            Thread thread1 = new Thread(() -> {

                threadLock.lock();
                try {
                    for (int i = 1; i <= 30; i += 2) {
                        serverVersion1.addNumber(i);
                        condition.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    threadLock.unlock();
                }

            });

            Thread thread2 = new Thread(() -> {

                for (int i = 2; i <= 30; i += 2) {
                    threadLock.lock();
                    try {
                        serverVersion1.addNumber(i);
                        condition.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        threadLock.unlock();
                    }
                }
            });

            Thread.sleep(100);

            thread1.setName("ODD");
            thread1.start();

            thread2.setName("EVEN");
            thread2.start();

            thread1.join();
            thread2.join();

            serverVersion1.show(thread2.getName(), thread1.getName());
        }

        private void show(String name1, String name2) {

            for (Integer i : integers) {
                if (i%2 == 0) {
                    System.out.println(name1 + ": " + i);
                } else {
                    System.out.println(name2 + ": " + i);
                }
            }
        }

        @SneakyThrows
        public void addNumber(int i) {
            integers.add(i);
                Thread.sleep(ThreadLocalRandom.current().nextInt(200, 500));
        }
    }