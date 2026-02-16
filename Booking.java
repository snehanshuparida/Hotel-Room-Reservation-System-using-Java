package hotel;

public class Booking {
    private int bookingId;
    private String customerName;
    private int roomId;
    private int numberOfDays;
    private double totalAmount;

    public Booking(int bookingId, String customerName, int roomId, int numberOfDays, double totalAmount) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomId = roomId;
        this.numberOfDays = numberOfDays;
        this.totalAmount = totalAmount;
    }

    public int getBookingId() { return bookingId; }
    public String getCustomerName() { return customerName; }
    public int getRoomId() { return roomId; }
    public int getNumberOfDays() { return numberOfDays; }
    public double getTotalAmount() { return totalAmount; }
}
