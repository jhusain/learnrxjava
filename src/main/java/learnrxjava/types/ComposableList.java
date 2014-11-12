package learnrxjava.types;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface ComposableList<T> extends Iterable<T> {

    public <R> ComposableList<R> map(Function<T, R> projectionFunction);

    public ComposableList<T> filter(Predicate<T> predicateFunction);

    public <R> ComposableList<R> concatMap(Function<T, ComposableList<R>> projectionFunctionThatReturnsList);

    public ComposableList<T> reduce(BiFunction<T, T, T> combiner);

    public <R> ComposableList<R> reduce(R initialValue, BiFunction<R, T, R> combiner);

    public int size();

    public void forEach(Consumer<? super T> action);

    public T get(int index);
}
