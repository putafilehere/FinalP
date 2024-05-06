import java.sql.Time;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Wave
{
  private int rushCount;
  private Enemy[][] wave;
  private int[] spacings;
  private int[] betweens;

  public Wave(Enemy[][] wave, int[] spacings, int[] betweens)
  {
    this.wave = wave;
    this.spacings = spacings;
    this.betweens = betweens;
    rushCount = wave.length;
  }

  public void start(ArrayList<GameObject> objs)
  {
    ScheduledExecutorService[] rushTimers = new ScheduledExecutorService[rushCount];
    ScheduledExecutorService[] betweenDelays = new ScheduledExecutorService[rushCount];

    //initialize timers
    for (int i = 0; i < rushCount; i++)
    {
      rushTimers[i] = Executors.newScheduledThreadPool(1);
      betweenDelays[i] = Executors.newScheduledThreadPool(1);
    }

    int totalTime = 0;

    for (int i = 0; i < rushCount; i++) {
      System.out.println("YAYYYY EYEEEE IIII WOOOO YEAHHH: " + i);
      int finalI = i; // Final variable for lambda
      totalTime += betweens[i];
      int[] enCount = {0};
      betweenDelays[i].schedule(() -> {
        rushTimers[finalI].scheduleAtFixedRate(() -> {
          if (enCount[0] == wave[finalI].length) {
            rushTimers[finalI].shutdown();
            return; // Exit lambda if condition met
          }
          objs.add(wave[finalI][enCount[0]++].clone());
        }, 0, spacings[finalI], TimeUnit.MILLISECONDS);
        betweenDelays[finalI].shutdown();
      }, totalTime, TimeUnit.MILLISECONDS);

    }

  }
}