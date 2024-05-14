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

    for (int i = 0; i < wave.length; i++)
    {
      try {
        Thread.sleep(betweens[i]);
      } catch(Exception e){}
      for (int j = 0; j < wave[0].length; j++)
      {
        try {
          Thread.sleep(spacings[i]);
        } catch(Exception e){}
        objs.add(wave[i][j].clone());
      }

    }

  }
}