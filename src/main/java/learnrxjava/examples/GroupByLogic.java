package learnrxjava.examples;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

public class GroupByLogic {

    public static void main(String[] args) {
        final TestScheduler testScheduler = Schedulers.test();
        final PublishSubject<PlayEvent> testSubject = PublishSubject.<PlayEvent> create();
        TestSubscriber<StreamState> ts = new TestSubscriber<StreamState>();

        testSubject.groupBy(playEvt -> playEvt.getOriginatorId())
                .flatMap(groupedObservable -> {
                    System.out.println("***** new group: " + groupedObservable.getKey());
                    return groupedObservable
                            /* Timeout after last event, and preventing memory leaks so that inactive streams are closed */
                            .timeout(3, TimeUnit.HOURS, testScheduler)
                            /* So that consecutive identical playevents are skipped, can also use skipWhile */
                            .distinctUntilChanged(PlayEvent::getSession)
                            .onErrorResumeNext(t -> {
                                System.out.println("     ***** complete group: " + groupedObservable.getKey());
                                // complete if we timeout or have an error of any kind (this could emit a special PlayEvent instead
                                    return Observable.empty();
                                })
                            // since we have finite groups we can `reduce` to a single value, otherwise use `scan` if you want to emit along the way
                            .reduce(new StreamState(), (state, playEvent) -> {
                                System.out.println("    state: " + state + "  event: " + playEvent.id + "-" + playEvent.session);
                                // business logic happens here to accumulate state
                                    state.addEvent(playEvent);
                                    return state;
                                })
                            .filter(state -> {
                                // if using `scan` above instead of `reduce` you could conditionally remove what you don't want to pass down
                                return true;
                            });
                })
                .doOnNext(e -> System.out.println(">>> Output State: " + e))
                .subscribe(ts);

        testSubject.onNext(createPlayEvent(1, "a"));
        testSubject.onNext(createPlayEvent(2, "a"));
        testScheduler.advanceTimeBy(2, TimeUnit.HOURS);

        testSubject.onNext(createPlayEvent(1, "b"));
        testScheduler.advanceTimeBy(2, TimeUnit.HOURS);

        testSubject.onNext(createPlayEvent(1, "a"));
        testSubject.onNext(createPlayEvent(2, "b"));

        System.out.println("onNext after 4 hours: " + ts.getOnNextEvents());

        testScheduler.advanceTimeBy(3, TimeUnit.HOURS);

        System.out.println("onNext after 7 hours: " + ts.getOnNextEvents());

        testSubject.onCompleted();
        testSubject.onNext(createPlayEvent(2, "b"));

        System.out.println("onNext after complete: " + ts.getOnNextEvents());
        ts.assertTerminalEvent();
        ts.assertNoErrors();
    }

    public static PlayEvent createPlayEvent(int id, String v) {
        return new PlayEvent(id, v);
    }

    public static class PlayEvent {

        private int id;
        private String session;

        public PlayEvent(int id, String session) {
            this.id = id;
            this.session = session;
        }

        public int getOriginatorId() {
            return id;
        }

        public String getSession() {
            return session;
        }

    }

    public static class StreamState {

        private int id = -1;

        public void addEvent(PlayEvent event) {
            if (id == -1) {
                this.id = event.id;
            }
            // add business logic here
        }

        @Override
        public String toString() {
            return "StreamState => id: " + id;
        }
    }

}
