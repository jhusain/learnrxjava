package learnrxjava.examples;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class GroupByExamples {

    public static void main(String args[]) {
        // odd/even into 2 lists
        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> {
                    return g.toList();
                }).forEach(System.out::println);

        System.out.println("2--------------------------------------------------------------------------------------------------------");

        // odd/even into lists of 10
        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> {
                    return g.take(10).toList();
                }).forEach(System.out::println);

        System.out.println("3--------------------------------------------------------------------------------------------------------");

        //odd/even into lists of 10
        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> {
                    return g.filter(i -> i <= 20).toList();
                }).forEach(System.out::println);

        System.out.println("4--------------------------------------------------------------------------------------------------------");

        //odd/even into lists of 20 but only take the first 2 groups
        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> {
                    return g.take(20).toList();
                }).take(2).forEach(System.out::println);

        System.out.println("5--------------------------------------------------------------------------------------------------------");

        //odd/even into 2 lists with numbers less than 30
        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> {
                    return g.takeWhile(i -> i < 30).toList();
                }).filter(l -> !l.isEmpty()).forEach(System.out::println);

        System.out.println("6--------------------------------------------------------------------------------------------------------");

        Observable.from(Arrays.asList("a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c"))
                .groupBy(n -> n)
                .flatMap(g -> {
                    return g.take(3).reduce((s, s2) -> s + s2);
                }).forEach(System.out::println);

        System.out.println("7--------------------------------------------------------------------------------------------------------");

        Observable.timer(0, 1, TimeUnit.MILLISECONDS)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> {
                    return g.take(10).toList();
                }).take(2).toBlocking().forEach(System.out::println);

        System.out.println("8--------------------------------------------------------------------------------------------------------");

        Observable.timer(0, 1, TimeUnit.MILLISECONDS)
                .take(20)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> {
                    return g.toList();
                }).toBlocking().forEach(System.out::println);

    }
}
