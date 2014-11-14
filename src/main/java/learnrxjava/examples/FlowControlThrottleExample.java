package learnrxjava.examples;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class FlowControlThrottleExample {

    public static void main(String args[]) {
        // first item emitted in each time window
        hotStream().throttleFirst(500, TimeUnit.MILLISECONDS).take(10).toBlocking().forEach(System.out::println);
        
        // last item emitted in each time window
        hotStream().throttleLast(500, TimeUnit.MILLISECONDS).take(10).toBlocking().forEach(System.out::println);
    }

    /**
     * This is an artificial source to demonstrate an infinite stream that emits randomly
     */
    public static Observable<Integer> hotStream() {
        return Observable.create((Subscriber<? super Integer> s) -> {
            int i = 0;
            while (!s.isUnsubscribed()) {
                s.onNext(i++);
                try {
                    // sleep for a random amount of time
                    // NOTE: Only using Thread.sleep here as an artificial demo.
                    Thread.sleep((long) (Math.random() * 100));
                } catch (Exception e) {
                    // do nothing
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }

}
