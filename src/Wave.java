import java.sql.Time;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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

  public void start(GameObject[] objs)
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
    
    for (int i = 0; i < rushCount; i++)
    {
      totalTime += betweens[i];
      int[] enCount = {0};
      betweenDelays[i].schedule(() -> {
        rushTimers[i].scheduleAtFixedRate(() -> {
          objs.add(wave[i][enCounter[0]++].clone());
        }, 0, spacings[i], TimeUnit.MILLISECONDS);
      }, totalTime, TimeUnit.MILLISECONDS);
      betweenDelays[i].shutdown();
      rushTimers[i].shutdown();
    }
  }
}