package util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA. User: muda1120 Date: 2013. 10. 28. Time: 오전 10:43 To change this
 * template use File | Settings | File Templates.
 */
public class ScheduledExecutorServiceTest {

    private static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledExecutorService scheduledExecutorService2 = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture scheduledFuture = getScheduledFuture();
//  private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
  private static long stRunTime;
  private static long endRunTime;

  private static boolean isCompleted;

  public static void main(String[] args) throws InterruptedException {

    scheduleAtFixedRate();
//    System.out.println(scheduledExecutorService.awaitTermination(2, TimeUnit.SECONDS));

    synchronized (ScheduledExecutorServiceTest.class) {
      ScheduledExecutorServiceTest.class.wait();
    }
  }

  private static void scheduleAtFixedRate() {
    scheduledFuture = getScheduledFuture();

    scheduledExecutorService2.scheduleAtFixedRate(new Runnable() {
      public void run() {
        if (!isCompleted) {
//          System.out.println("TimeOut");
          long delayMillis = System.currentTimeMillis() - stRunTime;
          System.out.println("Delay : " + delayMillis);
          // if over then 10 sec
          if (delayMillis > 10 * 2000) {
            scheduledFuture.cancel(true);
            scheduledFuture = getScheduledFuture();
          }
        } else {
          System.out.println("Working Good");
        }
      }
    }, 5, 3, TimeUnit.SECONDS);

  }

  private static ScheduledFuture getScheduledFuture() {
    return scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        stRunTime = System.currentTimeMillis();
        isCompleted = false;
        System.out.println("Executed!!");
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        isCompleted = true;
        System.out.println("Working Done");
      }
    }, 1, 2, TimeUnit.SECONDS);
  }

  private static Object schedule() throws ExecutionException, InterruptedException {
    ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable() {
      @Override
      public Object call() throws Exception {
        System.out.println("Executed!!");
        return "Hello";
      }
    }, 5, TimeUnit.SECONDS);

    return scheduledFuture.get();
  }


}
