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
    
    for (int[] i = {0}; i[0] < rushCount; i[0]++)
    {
      totalTime += betweens[i[0]];
      int[] enCount = {0};
      betweenDelays[i[0]].schedule(() -> {
        rushTimers[i[0]].scheduleAtFixedRate(() -> {
          objs.add(wave[i[0]][enCount[0]++].clone());
        }, 0, spacings[i[0]], TimeUnit.MILLISECONDS);
      }, totalTime, TimeUnit.MILLISECONDS);
      betweenDelays[i[0]].shutdown();
      rushTimers[i[0]].shutdown();
    }
  }
}