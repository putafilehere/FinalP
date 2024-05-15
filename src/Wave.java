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
    ScheduledExecutorService secretaryJoeBiden = Executors.newScheduledThreadPool(1);
    int totalTime = 0;
    for (int[] i = {0}; i[0] < wave.length; i[0]++)
    {
      System.out.println("blah blahis the loop working" + i[0]);
      totalTime += betweens[i[0]];
      for (int[] j = {0}; j[0] < wave[0].length; j[0]++)
      {
        secretaryJoeBiden.schedule(() -> {
          System.out.println("enemy moment XD ");
          objs.add(wave[i[0]][j[0]].clone());
        }, totalTime, TimeUnit.MILLISECONDS);
        totalTime += spacings[i[0]];
        System.out.println("blah blah inner loop working" + j[0] + "\nTimestamp: " + totalTime);
      }

    }

  }
}