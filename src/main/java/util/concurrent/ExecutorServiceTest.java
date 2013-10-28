package util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA. User: muda1120 Date: 2013. 10. 28. Time: 오전 10:31 To change this
 * template use File | Settings | File Templates.
 */
public class ExecutorServiceTest {

  private static ExecutorService executorService = Executors.newSingleThreadExecutor();
//  private static ExecutorService executorService = Executors.newFixedThreadPool(10);
//  private static ExecutorService executorService = Executors.newScheduledThreadPool(10)


  public static void main(String[] args) throws InterruptedException {

    executorService.execute(new Runnable() {
      @Override
      public void run() {
        System.out.println("Start Executor Service Test");
        try {
          Thread.sleep(20000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("End Executor Service Test");
      }
    });

    executorService.awaitTermination(10, TimeUnit.SECONDS);
    executorService.shutdown();

    //Waiting this class
    synchronized (ExecutorServiceTest.class){
      ExecutorServiceTest.class.wait();
    }
  }

}
