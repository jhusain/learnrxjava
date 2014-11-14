package learnrxjava.examples;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

public class UnitTesting {

    public static void main(String... args) {
        TestScheduler test = Schedulers.test();
        TestSubscriber<String> ts = new TestSubscriber<>();

        Observable.interval(200, TimeUnit.MILLISECONDS, test)
                .map(i -> {
                    return i + " value";
                }).subscribe(ts);

        test.advanceTimeBy(200, TimeUnit.MILLISECONDS);
        ts.assertReceivedOnNext(Arrays.asList("0 value"));

        test.advanceTimeTo(1000, TimeUnit.MILLISECONDS);
        ts.assertReceivedOnNext(Arrays.asList("0 value", "1 value", "2 value", "3 value", "4 value"));
    }

}
