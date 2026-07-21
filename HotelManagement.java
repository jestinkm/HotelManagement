import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

class Room {
    private int roomNo;
    private String roomType;
    private double price;
    private boolean booked;

    public Room(int roomNo, String roomType, double price) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.price = price;
        this.booked = false;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return roomNo + "\t" + roomType + "\t₹" + price + "\t"
                + (booked ? "Booked" : "Available");
    }
}

class Customer {
    private String name;
    private String phone;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}

class Booking {

    private Room room;
    private Customer customer;
    private int days;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public Booking(Room room, Customer customer, int days) {
        this.room = room;
        this.customer = customer;
        this.days = days;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }
}

public class HotelManagement {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {

        System.out.print("Enter Number of Rooms : ");
        int n = sc.nextInt();

        sc.nextLine();

        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter Room " + (i + 1) + " Details");

            System.out.print("Room Number : ");
            int roomNo = sc.nextInt();
            sc.nextLine();

            System.out.print("Room Type : ");
            String type = sc.nextLine();

            System.out.print("Price : ");
            double price = sc.nextDouble();
            sc.nextLine();

            rooms.add(new Room(roomNo, type, price));
        }

        while (true) {

            System.out.println("\n========== HOTEL MANAGEMENT ==========");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Check In");
            System.out.println("4. Check Out");
            System.out.println("5. Generate Bill");
            System.out.println("6. Exit");
            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    viewRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    checkIn();
                    break;

                case 4:
                    checkOut();
                    break;

                case 5:
                    generateBill();
                    break;

                case 6:
                    System.out.println("Thank You...");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void viewRooms() {

        System.out.println("\nRoomNo\tType\tPrice\tStatus");

        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    static void bookRoom() {

        System.out.print("Enter Room Number : ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room room : rooms) {

            if (room.getRoomNo() == roomNo) {

                if (room.isBooked()) {
                    System.out.println("Room Already Booked.");
                    return;
                }

                System.out.print("Customer Name : ");
                String name = sc.nextLine();

                System.out.print("Phone Number : ");
                String phone = sc.nextLine();

                System.out.print("Number of Days : ");
                int days = sc.nextInt();
                sc.nextLine();

                Customer customer = new Customer(name, phone);

                Booking booking = new Booking(room, customer, days);

                bookings.add(booking);

                room.setBooked(true);

                System.out.println("Room Booked Successfully.");

                return;
            }
        }

        System.out.println("Room Not Found.");
    }
        static void checkIn() {

        System.out.print("Enter Room Number : ");
        int roomNo = sc.nextInt();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (Booking booking : bookings) {

            if (booking.getRoom().getRoomNo() == roomNo) {

                if (booking.getCheckIn() != null) {
                    System.out.println("Customer Already Checked In.");
                    return;
                }

                booking.setCheckIn(LocalDateTime.now());

                System.out.println("Check-In Successful.");
                System.out.println("Check-In Time : "
                        + booking.getCheckIn().format(formatter));
                return;
            }
        }

        System.out.println("Booking Not Found.");
    }

    static void checkOut() {

        System.out.print("Enter Room Number : ");
        int roomNo = sc.nextInt();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (Booking booking : bookings) {

            if (booking.getRoom().getRoomNo() == roomNo) {

                if (booking.getCheckIn() == null) {
                    System.out.println("Customer Not Checked In.");
                    return;
                }

                booking.setCheckOut(LocalDateTime.now());

                System.out.println("Check-Out Successful.");
                System.out.println("Check-Out Time : "
                        + booking.getCheckOut().format(formatter));

                return;
            }
        }

        System.out.println("Booking Not Found.");
    }

    static void generateBill() {

        System.out.print("Enter Room Number : ");
        int roomNo = sc.nextInt();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        Iterator<Booking> iterator = bookings.iterator();

        while (iterator.hasNext()) {

            Booking booking = iterator.next();

            if (booking.getRoom().getRoomNo() == roomNo) {

                if (booking.getCheckIn() == null ||
                        booking.getCheckOut() == null) {

                    System.out.println("Customer must Check-In and Check-Out first.");
                    return;
                }

                double roomCharge =
                        booking.getRoom().getPrice() * booking.getDays();

                double gst = roomCharge * 0.18;

                double total = roomCharge + gst;

                System.out.println("\n======================================");
                System.out.println("           HOTEL BILL");
                System.out.println("======================================");
                System.out.println("Customer Name : "
                        + booking.getCustomer().getName());
                System.out.println("Phone Number  : "
                        + booking.getCustomer().getPhone());
                System.out.println("Room Number   : "
                        + booking.getRoom().getRoomNo());
                System.out.println("Room Type     : "
                        + booking.getRoom().getRoomType());
                System.out.println("Days Stayed   : "
                        + booking.getDays());
                System.out.println();

                System.out.println("Check-In  : "
                        + booking.getCheckIn().format(formatter));
                System.out.println("Check-Out : "
                        + booking.getCheckOut().format(formatter));

                System.out.println("--------------------------------------");
                System.out.println("Room Charge : ₹" + roomCharge);
                System.out.println("GST (18%)   : ₹" + gst);
                System.out.println("--------------------------------------");
                System.out.println("Total Bill  : ₹" + total);
                System.out.println("======================================");
                System.out.println("Thank You! Visit Again.");

                booking.getRoom().setBooked(false);

                iterator.remove();

                return;
            }
        }

        System.out.println("Booking Not Found.");
    }
}