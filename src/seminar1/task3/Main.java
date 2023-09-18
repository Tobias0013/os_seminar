package seminar1.task3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread stopwatch = new Thread(() ->{
            System.out.println("Stopwatch thread. Stopwatch starts now");
            float time = 0;
            try {
                while (time < 60000){
                    Thread.sleep(10);
                    time += 10;
                    System.out.printf("Stopwatch thread. Elapsed: %.2f seconds.%n", (time / 1000));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        stopwatch.start();
        System.out.println("seminar1.Main thread. Waiting for stopwatch thread...");
        stopwatch.join();
        System.out.println("seminar1.Main thread. Finished stopwatch thread.");
    }
}
