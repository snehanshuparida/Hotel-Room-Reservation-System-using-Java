package hotel;

import java.util.List;
import java.util.Scanner;

public class HotelReservationSystem {

    private static RoomDAO roomDAO = new RoomDAO();
    private static BookingDAO bookingDAO = new BookingDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Book Room");
            System.out.println("2. Check Room Availability");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Generate Invoice");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    bookRoom();
                    break;
                case 2:
                    checkAvailability();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    generateInvoice();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);
    }

    private static void bookRoom() {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        List<Room> availableRooms = roomDAO.getAvailableRooms();
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        System.out.println("Available Rooms:");
        for (Room room : availableRooms) {
            System.out.println("Room ID: " + room.getRoomId()
                    + ", Type: " + room.getRoomType()
                    + ", Price per night: " + room.getPricePerNight());
        }

        System.out.print("Enter Room ID to book: ");
        int roomId = scanner.nextInt();
        System.out.print("Enter number of days: ");
        int days = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Room room = roomDAO.getRoomById(roomId);
        if (room == null || !room.isAvailable()) {
            System.out.println("Invalid room or room not available.");
            return;
        }

        double totalAmount = room.getPricePerNight() * days;
        int bookingId = bookingDAO.createBooking(customerName, roomId, days, totalAmount);

        if (bookingId != -1) {
            roomDAO.updateRoomAvailability(roomId, false);
            System.out.println("Booking successful! Your Booking ID is: " + bookingId);
        } else {
            System.out.println("Booking failed.");
        }
    }

    private static void checkAvailability() {
        List<Room> availableRooms = roomDAO.getAvailableRooms();
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        System.out.println("Available Rooms:");
        for (Room room : availableRooms) {
            System.out.println("Room ID: " + room.getRoomId()
                    + ", Type: " + room.getRoomType()
                    + ", Price per night: " + room.getPricePerNight());
        }
    }

    private static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        boolean deleted = bookingDAO.deleteBooking(bookingId);
        if (deleted) {
            roomDAO.updateRoomAvailability(booking.getRoomId(), true);
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Failed to cancel booking.");
        }
    }

    private static void generateInvoice() {
        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        Room room = roomDAO.getRoomById(booking.getRoomId());
        if (room == null) {
            System.out.println("Room not found for this booking.");
            return;
        }

        System.out.println("\n===== INVOICE =====");
        System.out.println("Booking ID   : " + booking.getBookingId());
        System.out.println("Customer Name: " + booking.getCustomerName());
        System.out.println("Room Type    : " + room.getRoomType());
        System.out.println("Price/Night  : " + room.getPricePerNight());
        System.out.println("Days Stayed  : " + booking.getNumberOfDays());
        System.out.println("Total Amount : " + booking.getTotalAmount());
        System.out.println("====================\n");
    }
}
