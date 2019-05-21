package demo;

import java.util.List;

/**
 * @author huishen
 * @date 2019-05-16 16:34
 *
 * parallel
 *
 * https://codereview.stackexchange.com/questions/60401/using-java-8-parallel-streams
 */
public class Maxinator<T> {

    @FunctionalInterface
    public interface QualityFunction<V> {
        double computeQuality(V value);
    }

    private final QualityFunction<T> qualityFunction;
    private T best;
    private double bestQuality;

    public Maxinator(QualityFunction<T> qualityFunction) {
        this.qualityFunction = qualityFunction;
        reset();
    }

    public void reset() {
        best = null;
        bestQuality = Double.NEGATIVE_INFINITY;
    }

    public void updateBest(List<T> population) {
        population.parallelStream()
            .forEach(i -> {
                double quality = qualityFunction.computeQuality(i);
                if (quality > bestQuality) {
                    best = i;
                    bestQuality = quality;
                }
            });
    }

}
