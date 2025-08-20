import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    boolean isBooked;

    Room(int number, String category) {
        this.roomNumber = number;
        this.category = category;
        this.isBooked = false;
    }
}

class Booking {
    String customerName;
    int roomNumber;
    String category;

    Booking(String name, int roomNumber, String category) {
        this.customerName = name;
        this.roomNumber = roomNumber;
        this.category = category;
    }
}

public class HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadRooms();
        loadBookings();

        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: viewAvailableRooms(); break;
                case 2: bookRoom(sc); break;
                case 3: cancelBooking(sc); break;
                case 4: viewBookings(); break;
                case 5: saveBookings(); System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void loadRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
        rooms.add(new Room(302, "Suite"));
    }

    static void loadBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                bookings.add(new Booking(data[0], Integer.parseInt(data[1]), data[2]));
                for (Room r : rooms) {
                    if (r.roomNumber == Integer.parseInt(data[1])) {
                        r.isBooked = true;
                    }
                }
            }
        } catch (IOException e) {
            // Ignore if file not found
        }
    }

    static void saveBookings() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Booking b : bookings) {
                bw.write(b.customerName + "," + b.roomNumber + "," + b.category);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings!");
        }
    }

    static void viewAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room r : rooms) {
            if (!r.isBooked) {
                System.out.println("Room " + r.roomNumber + " (" + r.category + ")");
            }
        }
    }

    static void bookRoom(Scanner sc) {
        viewAvailableRooms();
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter room number to book: ");
        int num = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == num && !r.isBooked) {
                System.out.print("Enter payment amount: ");
                double payment = sc.nextDouble();
                sc.nextLine();
                System.out.println("Payment of $" + payment + " received. Booking confirmed!");
                r.isBooked = true;
                bookings.add(new Booking(name, r.roomNumber, r.category));
                saveBookings();
                return;
            }
        }
        System.out.println("Room not available!");
    }

    static void cancelBooking(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        Booking toRemove = null;
        for (Booking b : bookings) {
            if (b.customerName.equalsIgnoreCase(name)) {
                toRemove = b;
                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) {
                        r.isBooked = false;
                    }
                }
                break;
            }
        }
        if (toRemove != null) {
            bookings.remove(toRemove);
            saveBookings();
            System.out.println("Booking cancelled.");
        } else {
            System.out.println("No booking found for that name.");
        }
    }

    static void viewBookings() {
        System.out.println("\n--- All Bookings ---");
        for (Booking b : bookings) {
            System.out.println(b.customerName + " booked Room " + b.roomNumber + " (" + b.category + ")");
        }
    }
}
