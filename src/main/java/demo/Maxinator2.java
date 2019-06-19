package demo;

import java.util.List;
import java.util.stream.Collector;

/**
 * @author huishen
 * @date 2019-05-16 19:55
 *
 * parallel
 *
 * https://codereview.stackexchange.com/questions/60401/using-java-8-parallel-streams
 */
public class Maxinator2 {

    @FunctionalInterface
    public interface QualityFunction<V> {
        double computeQuality(V value);
    }

    private Maxinator2() {
        //        no public instances
    }

    private static final class AccumulateResult<T> {
        T bestItem = null;
        double bestScore = Double.MIN_VALUE;

        public T getBestItem() {
            return bestItem;
        }

        public void accept(QualityFunction<T> function, T item) {
            double score = function.computeQuality(item);
            if (score > bestScore) {
                bestScore = score;
                bestItem = item;
            }
        }

        public AccumulateResult<T> combine(AccumulateResult<T> r) {
            if (r.bestScore > bestScore) {
                bestScore = r.bestScore;
                bestItem = r.bestItem;
            }
            return this;
        }

    }

    public static <T> T getBest(final QualityFunction<T> qualityFunction, final List<T> population) {
        return population.parallelStream().collect( Collector.of(
            AccumulateResult<T>::new,
            (a,t) -> a.accept(qualityFunction, t),
            (a, b) -> a.combine(b))
        ).getBestItem();
    }

}
