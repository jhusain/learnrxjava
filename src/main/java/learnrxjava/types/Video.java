package learnrxjava.types;

public class Video {
    @Override
    public String toString() {
        return "Video{" + "id=" + id + ", title=" + title + ", rating=" + rating + ", bookmarks=" + bookmarks + ", boxarts=" + boxarts + ", interestingMoments=" + interestingMoments + '}';
    }

    public int id;
    public String title;
    public double rating;
    public ComposableList<Bookmark> bookmarks;
    public ComposableList<BoxArt> boxarts;
    public ComposableList<InterestingMoment> interestingMoments;

    public Video(int id, String title, double rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
    }

    public Video(int id, String title, double rating, ComposableList<Bookmark> bookmarks, ComposableList<BoxArt> boxarts) {
        this(id, title, rating);
        this.bookmarks = bookmarks;
        this.boxarts = boxarts;
    }

    public Video(int id, String title, double rating, ComposableList<Bookmark> bookmarks, ComposableList<BoxArt> boxarts, ComposableList<InterestingMoment> interestingMoments) {
        this(id, title, rating);
        this.bookmarks = bookmarks;
        this.boxarts = boxarts;
        this.interestingMoments = interestingMoments;
    }
}