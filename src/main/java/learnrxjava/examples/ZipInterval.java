package learnrxjava.examples;
import java.util.concurrent.TimeUnit;

import rx.Observable;


public class ZipInterval {

    public static void main(String... args) {
        Observable<String> data = Observable.just("one", "two", "three", "four", "five");
        Observable.zip(data, Observable.interval(1, TimeUnit.SECONDS), (d, t) -> {
            return d + " " + t;
        }).toBlocking().forEach(System.out::println);
        
    }
}
