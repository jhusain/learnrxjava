package learnrxjava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static rx.Observable.error;
import static rx.Observable.just;
import static rx.Observable.range;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import learnrxjava.types.BoxArt;
import learnrxjava.types.JSON;
import learnrxjava.types.Movie;
import learnrxjava.types.Movies;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

public class ObservableSolutionsTest {

    public ObservableExercises getImpl() {
        return new ObservableSolutions();
    }

    @Test
    public void exerciseHello() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exerciseHello().subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Hello World!"));
    }

    @Test
    public void exerciseMap() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exerciseMap(just("Hello")).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        assertEquals(1, ts.getOnNextEvents().size());
        assertTrue(ts.getOnNextEvents().get(0).startsWith("Hello "));
    }

    @Test
    public void exerciseFilterMap() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exerciseFilterMap(range(1, 10)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("2-Even", "4-Even", "6-Even", "8-Even", "10-Even"));
    }

    @Test
    public void exerciseConcatMap() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();

        Observable<Movies> movies = just(
                new Movies(
                        "New Releases", // name
                        Arrays.asList( // videos
                                new Movie(70111470, "Die Hard", 4.0),
                                new Movie(654356453, "Bad Boys", 5.0))),
                new Movies(
                        "Dramas",
                        Arrays.asList(
                                new Movie(65432445, "The Chamber", 4.0),
                                new Movie(675465, "Fracture", 5.0)))
                );

        getImpl().exerciseConcatMap(movies).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(70111470, 654356453, 65432445, 675465));
    }

    @Test
    public void exerciseFlatMap() {
        TestSubscriber<Map<Integer, Integer>> ts = new TestSubscriber<>();

        Observable<Movies> movies = just(
                new Movies(
                        "New Releases", // name
                        Arrays.asList( // videos
                                new Movie(70111470, "Die Hard", 4.0),
                                new Movie(654356453, "Bad Boys", 5.0))),
                new Movies(
                        "Dramas",
                        Arrays.asList(
                                new Movie(65432445, "The Chamber", 4.0),
                                new Movie(675465, "Fracture", 5.0)))
                );

        Map<Integer, Integer> map = getImpl().exerciseFlatMap(movies).toMap(i -> i).toBlocking().single();
        assertTrue(map.containsKey(70111470));
        assertTrue(map.containsKey(654356453));
        assertTrue(map.containsKey(65432445));
        assertTrue(map.containsKey(675465));
    }

    @Test
    public void exerciseReduce() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        getImpl().exerciseReduce(just(3, 6, 8, 9, 4, 12, 4, 2)).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList(12));
    }

    @Test
    public void exerciseMovie() {
        TestSubscriber<Map<Integer, JSON>> ts = new TestSubscriber<>();

        Observable<Movies> movies = just(
                new Movies(
                        "New Releases",
                        Arrays.asList(
                                new Movie(
                                        70111470,
                                        "Die Hard",
                                        4.0,
                                        Collections.emptyList(),
                                        Arrays.asList(
                                                new BoxArt(150, 200, "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"),
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/DieHard200.jpg")
                                                )),
                                new Movie(
                                        654356453,
                                        "Bad Boys",
                                        5.0,
                                        Collections.emptyList(),
                                        Arrays.asList(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys200.jpg"),
                                                new BoxArt(140, 200, "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg")
                                                ))
                                )
                ),
                new Movies(
                        "Thrillers",
                        Arrays.asList(
                                new Movie(
                                        65432445,
                                        "The Chamber",
                                        3.0,
                                        Collections.emptyList(),
                                        Arrays.asList(
                                                new BoxArt(130, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"),
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/TheChamber200.jpg")
                                                )),
                                new Movie(
                                        675465,
                                        "Fracture",
                                        4.0,
                                        Collections.emptyList(),
                                        Arrays.asList(
                                                new BoxArt(200, 200, "http://cdn-0.nflximg.com/images/2891/Fracture200.jpg"),
                                                new BoxArt(120, 200, "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"),
                                                new BoxArt(300, 200, "http://cdn-0.nflximg.com/images/2891/Fracture300.jpg")
                                                ))
                                )
                )
                );

        Map<Integer, JSON> map = getImpl().exerciseMovie(movies).toMap(i -> (int) i.get("id")).toBlocking().single();
        System.out.println(map);
        assertTrue(map.containsKey(70111470));
        assertEquals(map.get(70111470).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/DieHard150.jpg, id=70111470, title=Die Hard}");
        assertTrue(map.containsKey(654356453));
        assertEquals(map.get(654356453).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg, id=654356453, title=Bad Boys}");
        assertTrue(map.containsKey(65432445));
        assertEquals(map.get(65432445).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg, id=65432445, title=The Chamber}");
        assertTrue(map.containsKey(675465));
        assertEquals(map.get(675465).toString(), "{boxart=http://cdn-0.nflximg.com/images/2891/Fracture120.jpg, id=675465, title=Fracture}");
    }

    @Test
    public void exerciseZip() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().exerciseZip(just("one", "two", "blue", "red"), just("fish", "fish", "fish", "fish")).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("one fish", "two fish", "blue fish", "red fish"));
    }

    @Test
    public void handleError() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        getImpl().handleError(error(new RuntimeException("failure"))).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("default-value"));
    }

    @Test
    public void retry() {
        TestSubscriber<String> ts = new TestSubscriber<>();
        AtomicInteger c = new AtomicInteger();
        Observable<String> o = Observable.create(s -> {
            if (c.incrementAndGet() <= 1) {
                s.onError(new RuntimeException("fail"));
            } else {
                s.onNext("success!");
                s.onCompleted();
            }
        });
        getImpl().retry(o).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("success!"));
    }

    /**
     * The data stream fails intermittently so return the stream
     * with retry capability.
     */
    public Observable<String> retry(Observable<String> data) {
        return data.retry();
    }

}
