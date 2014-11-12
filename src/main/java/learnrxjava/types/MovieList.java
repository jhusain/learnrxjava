package learnrxjava.types;


public class MovieList {
    @Override
    public String toString() {
        return "MovieList{" + "name=" + name + ", videos=" + videos + '}';
    }

    public String name;
    public ComposableList<Video> videos;

    public MovieList(String name, ComposableList<Video> videos) {
        this.name = name;
        this.videos = videos;
    }
}