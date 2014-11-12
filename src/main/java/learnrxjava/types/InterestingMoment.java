package learnrxjava.types;

public class InterestingMoment {
    @Override
    public String toString() {
        return "InterestingMoment{" + "type=" + type + ", time=" + time + '}';
    }

    public String type;
    public int time;

    public InterestingMoment(String type, int time) {
        this.type = type;
        this.time = time;
    }
}