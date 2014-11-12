package learnrxjava.types;

public class VideoRow {

    private int listId;
    private int videoId;
    private String name;

    public VideoRow(int listId, int videoId, String name) {
        this.listId = listId;
        this.videoId = videoId;
        this.name = name;
    }
}