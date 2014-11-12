package learnrxjava.types;

public class BoxArtRow {

    public int videoId;
    public int width;
    public int height;
    public String url;

    public BoxArtRow(int videoId, int width, int height, String url) {
        this.videoId = videoId;
        this.width = width;
        this.height = height;
        this.url = url;
    }
}