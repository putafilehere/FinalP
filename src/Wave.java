  import java.util.*;
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
    ScheduledExecutorService[] rushTimers = new SchedulableExecutorService[rushCount];
    SheduledExecuterService[] betweenDelays = new ScheduledExecutorService[rushCount];

    //initialize timers
    for (int i = 0; i < rushCount; i++)
      timas[i] = new ScheduledExecuter();

    
    for (int i = 0; i < rushCount; i++)
      {
        
      }
  }
}