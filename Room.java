package hotel;

public class Room {
    private int roomId;
    private String roomType;
    private double pricePerNight;
    private boolean available;

    public Room(int roomId, String roomType, double pricePerNight, boolean available) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.available = available;
    }

    public int getRoomId() { return roomId; }
    public String getRoomType() { return roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return available; }
}
