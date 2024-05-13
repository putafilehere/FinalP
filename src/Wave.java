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
    for (int i = 0; i < betweens.length; i++)
    {
      for (int j = 0; j < i; j++) {
        betweens[i] += betweens[j];
      }
    }
    this.betweens = betweens;
    rushCount = wave.length;
  }

  public void start(ArrayList<GameObject> objs)
  {
    ScheduledExecutorService[] rushTimers = new ScheduledExecutorService[rushCount];
    ScheduledExecutorService betweenDelay = Executors.newScheduledThreadPool(1);

    //initialize timers
    for (int i = 0; i < rushCount; i++)
    {
      rushTimers[i] = Executors.newScheduledThreadPool(1);
    }

    int totalTime = 0;

    for (int i = 0; i < rushCount; i++) {
      System.out.println("YAYYYY EYEEEE IIII WOOOO YEAHHH: " + i);
      int finalI = i; // Final variable for lambda
      totalTime += betweens[i];
      if (i > 0)
        totalTime += spacings[i-1] * rushCount;
      int[] enCount = {0};
      betweenDelay.schedule(() -> {
        rushTimers[finalI].scheduleAtFixedRate(() -> {
          if (enCount[0] == wave[finalI].length) {
            rushTimers[finalI].shutdown();
            return; // Exit lambda if condition met
          }
          objs.add(wave[finalI][enCount[0]++].clone());
        }, 0, spacings[finalI], TimeUnit.MILLISECONDS);
      }, totalTime, TimeUnit.MILLISECONDS);

    }

  }
}