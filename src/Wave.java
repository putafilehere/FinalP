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

  public void start(ArrayList<GameObject> objs, Game game) {
      ScheduledExecutorService secretaryJoeBiden = Executors.newScheduledThreadPool(1);
      int totalTime = 0;
      for (int i = 0; i < wave.length; i++) {
          final int waveIndex = i;
          totalTime += betweens[i];
          for (int j = 0; j < wave[0].length; j++) {
              final int rowIndex = j;
              secretaryJoeBiden.schedule(() -> {
                  if (waveIndex == wave.length-1 && rowIndex == wave[0].length-1)
                      game.lastEnSpawned = true;
                  objs.add(wave[waveIndex][rowIndex].clone());
              }, totalTime, TimeUnit.MILLISECONDS);
              totalTime += spacings[waveIndex];
          }
      }
  }

}