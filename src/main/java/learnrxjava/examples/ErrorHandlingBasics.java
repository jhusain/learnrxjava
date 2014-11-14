package learnrxjava.examples;

import rx.Observable;

public class ErrorHandlingBasics {

    public static void main(String... args) {

        /*
         * Errors should be emitted via onError
         */
        Observable.create(s -> {
            s.onError(new RuntimeException("failed"));
        }).subscribe(System.out::println, t -> System.out.println("1) Error: " + t));

        /*
         * But RxJava trys to do the right thing if an error is thrown
         */
        Observable.create(s -> {
            throw new RuntimeException("failed");
        }).subscribe(System.out::println, t -> System.out.println("2) Error: " + t));

        Observable.just("hello").map(s -> {
            throw new RuntimeException("failed");
        }).subscribe(System.out::println, t -> System.out.println("3) Error: " + t));

        /*
         * Conditionals that may return an error can be done in a flatMap
         */
        Observable.just(true).flatMap(v -> {
            if (v) {
                return Observable.error(new RuntimeException("failed"));
            } else {
                return Observable.just("data", "here");
            }
        }).subscribe(System.out::println, t -> System.out.println("4) Error: " + t));

        /*
         * Errors can be handled on any Observable
         */
        Observable.error(new RuntimeException("failed"))
                .onErrorResumeNext(Observable.just("5) data"))
                .subscribe(System.out::println, t -> System.out.println("5) Error: " + t));

        /*
         * Or the Throwable can be obtained to do conditional logic
         */
        Observable.error(new IllegalStateException("failed"))
                .onErrorResumeNext(t -> {
                    if (t instanceof IllegalStateException) {
                        return Observable.error(t);
                    } else {
                        return Observable.just("6) data");
                    }
                })
                .subscribe(System.out::println, t -> System.out.println("6) Error: " + t));

        Observable.error(new RuntimeException("failed"))
                .onErrorResumeNext(t -> {
                    if (t instanceof IllegalStateException) {
                        return Observable.error(t);
                    } else {
                        return Observable.just("7) data");
                    }
                })
                .subscribe(System.out::println, t -> System.out.println("7) Error: " + t));

    }
}
