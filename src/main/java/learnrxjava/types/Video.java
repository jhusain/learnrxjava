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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookmarks == null) ? 0 : bookmarks.hashCode());
        result = prime * result + ((boxarts == null) ? 0 : boxarts.hashCode());
        result = prime * result + id;
        result = prime * result + ((interestingMoments == null) ? 0 : interestingMoments.hashCode());
        long temp;
        temp = Double.doubleToLongBits(rating);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Video other = (Video) obj;
        if (bookmarks == null) {
            if (other.bookmarks != null)
                return false;
        } else if (!bookmarks.equals(other.bookmarks))
            return false;
        if (boxarts == null) {
            if (other.boxarts != null)
                return false;
        } else if (!boxarts.equals(other.boxarts))
            return false;
        if (id != other.id)
            return false;
        if (interestingMoments == null) {
            if (other.interestingMoments != null)
                return false;
        } else if (!interestingMoments.equals(other.interestingMoments))
            return false;
        if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
    
    
}