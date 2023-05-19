package edit.controller;

public class Example {
    public static void main(String[] args) throws InterruptedException {
        int n = 5; // 创建的线程数
        Thread prevThread = null;
        for (int i = 1; i <= n; i++) {
            Thread currentThread = new Thread(new MyRunnable(prevThread), "Thread-" + i);
            currentThread.start();
            if (prevThread != null) {
                prevThread.join(); // 等待前一个线程执行完毕
            }
            prevThread = currentThread;
        }
    }

    static class MyRunnable implements Runnable {
        private final Thread prevThread;

        public MyRunnable(Thread prevThread) {
            this.prevThread = prevThread;
        }

        @Override
        public void run() {
            try {
                if (prevThread != null) {
                    System.out.println("Wait for Prev thread to finish: " + prevThread.getName());
                    prevThread.join(); // 等待前一个线程执行完毕
                }
                System.out.println("Current thread running: " + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}