package learnrxjava.examples;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class FlowControlWindowExample {

    public static void main(String args[]) {
        // buffer every 500ms (using 999999999 to mark start of output)
        hotStream().window(500, TimeUnit.MILLISECONDS).take(10).flatMap(w -> w.startWith(999999999)).toBlocking().forEach(System.out::println);

        // buffer 10 items at a time (using 999999999 to mark start of output)
        hotStream().window(10).take(2).flatMap(w -> w.startWith(999999999)).toBlocking().forEach(System.out::println);

        System.out.println("Done");
    }

    /**
     * This is an artificial source to demonstrate an infinite stream that bursts intermittently
     */
    public static Observable<Integer> hotStream() {
        return Observable.create((Subscriber<? super Integer> s) -> {
            while (!s.isUnsubscribed()) {
                // burst some number of items
                for (int i = 0; i < Math.random() * 20; i++) {
                    s.onNext(i);
                }
                try {
                    // sleep for a random amount of time
                    // NOTE: Only using Thread.sleep here as an artificial demo.
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (Exception e) {
                    // do nothing
                }
            }
        }).subscribeOn(Schedulers.newThread()); // use newThread since we are using sleep to block
    }

}
