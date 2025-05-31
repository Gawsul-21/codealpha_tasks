import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Room class stores details of each room
class Room {
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - $" + pricePerNight + " per night" + (isAvailable ? " [Available]" : " [Booked]");
    }
}

// Reservation class stores booking details
class Reservation {
    private String guestName;
    private Room room;
    private int numberOfNights;
    private boolean paymentDone;

    public Reservation(String guestName, Room room, int numberOfNights) {
        this.guestName = guestName;
        this.room = room;
        this.numberOfNights = numberOfNights;
        this.paymentDone = false;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalPrice() {
        return room.getPricePerNight() * numberOfNights;
    }

    public boolean isPaymentDone() {
        return paymentDone;
    }

    public void makePayment() {
        paymentDone = true;
    }

    @Override
    public String toString() {
        return "Reservation for " + guestName + ": " + room.toString() + ", Nights: " + numberOfNights + ", Total: $" + getTotalPrice() + ", Payment: " + (paymentDone ? "Done" : "Pending");
    }
}

// Main class to manage hotel reservations
public class HotelReservationSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private Scanner scanner;

    public HotelReservationSystem() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeRooms();
    }

    // Initialize some rooms with categories and prices
    private void initializeRooms() {
        rooms.add(new Room(101, "Single", 50));
        rooms.add(new Room(102, "Single", 50));
        rooms.add(new Room(201, "Double", 80));
        rooms.add(new Room(202, "Double", 80));
        rooms.add(new Room(301, "Suite", 150));
    }

    // Main menu for interaction
    public void start() {
        System.out.println("Welcome to the Hotel Reservation System");
        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View My Reservations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    searchAvailableRooms();
                    break;
                case "2":
                    makeReservation();
                    break;
                case "3":
                    viewReservations();
                    break;
                case "4":
                    System.out.println("Thank you for using the system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Search and display available rooms optionally filtered by type
    private void searchAvailableRooms() {
        System.out.print("Enter room type to search (Single/Double/Suite) or press Enter to view all: ");
        String typeFilter = scanner.nextLine().trim();

        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && (typeFilter.isEmpty() || room.getRoomType().equalsIgnoreCase(typeFilter))) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms found for the given criteria.");
        } else {
            System.out.println("Available rooms:");
            for (Room r : availableRooms) {
                System.out.println(r.toString());
            }
        }
    }

    // Process reservation creation
    private void makeReservation() {
        System.out.print("Enter your name: ");
        String guestName = scanner.nextLine().trim();

        System.out.print("Enter room number to book: ");
        String roomNumberStr = scanner.nextLine().trim();
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNumberStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number.");
            return;
        }

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        if (!selectedRoom.isAvailable()) {
            System.out.println("Sorry, this room is already booked.");
            return;
        }

        System.out.print("Enter number of nights to stay: ");
        String nightsStr = scanner.nextLine().trim();
        int nights;
        try {
            nights = Integer.parseInt(nightsStr);
            if (nights <= 0) {
                System.out.println("Number of nights must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of nights.");
            return;
        }

        // Create reservation and mark room unavailable
        Reservation reservation = new Reservation(guestName, selectedRoom, nights);
        reservations.add(reservation);
        selectedRoom.setAvailability(false);

        System.out.println("Reservation created successfully!");
        System.out.println(reservation.toString());

        // Payment simulation
        System.out.print("Would you like to make payment now? (yes/no): ");
        String payNow = scanner.nextLine().trim().toLowerCase();
        if (payNow.equals("yes")) {
            reservation.makePayment();
            System.out.println("Payment successful. Thank you!");
        } else {
            System.out.println("You can pay later.");
        }
    }

    // Display all reservations for a guest
    private void viewReservations() {
        System.out.print("Enter your name to view reservations: ");
        String guestName = scanner.nextLine().trim();

        boolean found = false;
        for (Reservation r : reservations) {
            if (r.getGuestName().equalsIgnoreCase(guestName)) {
                System.out.println(r.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No reservations found under this name.");
        }
    }

    public static void main(String[] args) {
        HotelReservationSystem system = new HotelReservationSystem();
        system.start();
    }
}
