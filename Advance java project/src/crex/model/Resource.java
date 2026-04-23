package crex.model;

public class Resource {
    private int resourceId;
    private String title;
    private int ownerId;
    private boolean isAvailable;

    public Resource(int resourceId, String title, int ownerId, boolean isAvailable) {
        this.resourceId = resourceId;
        this.title = title;
        this.ownerId = ownerId;
        this.isAvailable = isAvailable;
    }

    public int getResourceId() { return resourceId; }
    public String getTitle() { return title; }
    public int getOwnerId() { return ownerId; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return resourceId + " - " + title;
    }
}
