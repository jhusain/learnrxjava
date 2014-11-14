package learnrxjava.types;

public class BoxArt {
    public int width;
    public int height;
    public String url;

    public BoxArt(int width, int height, String url) {
        this.width = width;
        this.height = height;
        this.url = url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + width;
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
        BoxArt other = (BoxArt) obj;
        if (height != other.height)
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (width != other.width)
            return false;
        return true;
    }
    
    
}