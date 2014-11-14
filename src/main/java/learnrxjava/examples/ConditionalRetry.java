package learnrxjava.examples;

import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;

public class ConditionalRetry {

    public static void main(String[] args) {

        final AtomicInteger c = new AtomicInteger();
        Observable<String> oWithRuntimeException = Observable.create((Subscriber<? super String> s) -> {
            System.out.println("Execution: " + c.get());
            if (c.incrementAndGet() < 3) {
                s.onError(new RuntimeException("retryable"));
            } else {
                s.onNext("hello");
                s.onCompleted();
            }
        });

        final AtomicInteger c2 = new AtomicInteger();
        Observable<String> oWithIllegalStateException = Observable.create((Subscriber<? super String> s) -> {
            System.out.println("Execution: " + c2.get());
            if (c2.incrementAndGet() < 3) {
                s.onError(new RuntimeException("retryable"));
            } else {
                s.onError(new IllegalStateException());
            }
        });

        subscribe(oWithRuntimeException);
        subscribe(oWithIllegalStateException);
    }

    public static void subscribe(Observable<String> o) {
        o = o.materialize().flatMap(n -> {
            if (n.isOnError()) {
                if (n.getThrowable() instanceof IllegalStateException) {
                    return Observable.just(n);
                } else {
                    return Observable.error(n.getThrowable());
                }
            } else {
                return Observable.just(n);
            }
        }).retry().dematerialize();

        o.subscribe(System.out::println, t -> t.printStackTrace());
    }
}
