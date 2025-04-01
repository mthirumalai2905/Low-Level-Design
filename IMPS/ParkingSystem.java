import java.time.LocalDateTime;
import java.time.Duration;

class ParkingSystem {
  // enums for vehicletypes
    enum VehicleType {
        CAR, BIKE, TRUCK;
    }
  // concrete class for vehicles 
    class Vehicle {
        private int numberPlate;
        private VehicleType type;

        public Vehicle(int number, VehicleType typeV) {
            this.numberPlate = number;
            this.type = typeV;
        }

        public VehicleType getType() {
            return type;
        }

        public void setType(VehicleType typeP) {
            this.type = typeP;
        }

        public int getNumberPlate() {
            return numberPlate;
        }
    }

  // concrete parkingticket class
    class ParkingTicket {
        private static int ticketCounter = 1;
        private final int ticketId;
        private final int vehicleNumber;
        private final LocalDateTime entryTime;
        private LocalDateTime exitTime;
        private double parkingFee;

        public ParkingTicket(int vehicleNumber) {
            this.ticketId = ticketCounter++;
            this.vehicleNumber = vehicleNumber;
            this.entryTime = LocalDateTime.now();
        }

        public void markExit() {
            this.exitTime = LocalDateTime.now();
            this.parkingFee = calculateFee();
        }

        private double calculateFee() {
            if (exitTime == null) return 0;
            long duration = Duration.between(entryTime, exitTime).toMinutes();
            return duration * 1; // 1Rs per minute
        }

        public double getParkingFee() {
            return parkingFee;
        }

        public int getTicketId() {
            return ticketId;
        }
    }
 // states for payment methods
    enum PaymentMethod {
        CASH, CARD, ONLINE;
    }
// concrete clas for payment
    class Payment {
        private final int ticketId;
        private final double amountPaid;
        private final PaymentMethod method;
        private final LocalDateTime paymentTime;

        public Payment(int ticketId, double amountPaid, PaymentMethod method) {
            this.ticketId = ticketId;
            this.amountPaid = amountPaid;
            this.method = method;
            this.paymentTime = LocalDateTime.now();
        }

        public boolean isSuccessful(double parkingFee) {
            return amountPaid >= parkingFee;
        }
    }
// concrete Gate class
    class Gate {
        private final int gateId;
        private final boolean isEntryGate;

        public Gate(int gateId, boolean isEntryGate) {
            this.gateId = gateId;
            this.isEntryGate = isEntryGate;
        }

        public ParkingTicket generateTicket(int vehicleNumber) {
            if (isEntryGate) {
                return new ParkingTicket(vehicleNumber);
            }
            throw new IllegalStateException("Exit gate cannot generate Tickets!");
        }

        public boolean processExit(ParkingTicket ticket, PaymentMethod method, double amount) {
            if (!isEntryGate) {
                ticket.markExit();
                Payment payment = new Payment(ticket.getTicketId(), amount, method);
                return payment.isSuccessful(ticket.getParkingFee());
            }
            throw new IllegalStateException("Entry gate cannot process exit!");
        }
    }

  // parkingspot
    class ParkingSpot {
        private final int spotId;
        private final VehicleType type;
        private boolean isOccupied;
        private Vehicle parkedVehicle;

        public ParkingSpot(int spotId, VehicleType type) {
            this.spotId = spotId;
            this.type = type;
            this.isOccupied = false;
            this.parkedVehicle = null;
        }

        public boolean parkVehicle(Vehicle vehicle) {
            if (!isOccupied && vehicle.getType() == this.type) {
                this.parkedVehicle = vehicle;
                this.isOccupied = true;
                return true;
            }
            return false;
        }

        public boolean removeVehicle() {
            if (isOccupied) {
                this.parkedVehicle = null;
                this.isOccupied = false;
                return true;
            }
            return false;
        }

        public boolean isOccupied() {
            return isOccupied;
        }

        public VehicleType getType() {
            return type;
        }

        public Vehicle getParkedVehicle() {
            return parkedVehicle;
        }
    }

    public static void main(String[] args) {
        ParkingSystem parkingSystem = new ParkingSystem();

        Vehicle car1 = parkingSystem.new Vehicle(1234, VehicleType.CAR);
        ParkingSpot spot1 = parkingSystem.new ParkingSpot(1, VehicleType.CAR);
        
        if (spot1.parkVehicle(car1)) {
            System.out.println("Car parked successfully.");
        } else {
            System.out.println("Failed to park car.");
        }

        Gate entryGate = parkingSystem.new Gate(1, true);
        ParkingTicket ticket = entryGate.generateTicket(car1.getNumberPlate());
        System.out.println("Ticket ID: " + ticket.getTicketId());

        Gate exitGate = parkingSystem.new Gate(2, false);
        boolean paymentSuccess = exitGate.processExit(ticket, PaymentMethod.CASH, 10);
        System.out.println("Payment Successful: " + paymentSuccess);
    }
}
