package learnrxjava.examples;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class FlowControlDebounceBuffer {

    public static void main(String args[]) {
        // debounce to the last value in each burst
        //intermittentBursts().debounce(10, TimeUnit.MILLISECONDS).toBlocking().forEach(System.out::println);

        /* The following will emit a buffered list as it is debounced */
        // first we multicast the stream ... using refCount so it handles the subscribe/unsubscribe
        Observable<Integer> burstStream = intermittentBursts().take(20).publish().refCount();
        // then we get the debounced version
        Observable<Integer> debounced = burstStream.debounce(10, TimeUnit.MILLISECONDS);
        // then the buffered one that uses the debounced stream to demark window start/stop
        Observable<List<Integer>> buffered = burstStream.buffer(debounced);
        // then we subscribe to the buffered stream so it does what we want
        buffered.toBlocking().forEach(System.out::println);
    }

    /**
     * This is an artificial source to demonstrate an infinite stream that bursts intermittently
     */
    public static Observable<Integer> intermittentBursts() {
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
