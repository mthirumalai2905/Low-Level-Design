import java.util.*;
class ParkingLot{
    private final int totalSpots;
    private final PriorityQueue<Integer> availableSpots;
    private final Map<Integer, Car> parkedCars;

    public ParkingLot(int totalSpots){
        this.totalSpots = totalSpots;
        this.availableSpots = new PriorityQueue<>();
        this.parkedCars = new HashMap<>();

        for(int i = 1; i <= totalSpots; i++){
            availableSpots.add(i);
        }
    }

    public boolean parkCar(Car car){
        if(availableSpots.isEmpty()){
            System.out.println("Parking lot is full");
            return false;
        }

        int spot = availableSpots.poll();
        parkedCars.put(spot, car);
        System.out.println("Car" + car.getLicensePlate() + " parked at spot" + spot);
        return true;
    }

    public boolean leaveCar(int spot){
        if(!parkedCars.containsKey(spot)){
            System.out.println("Car not found at spot" + spot);
            return false;
        }

        Car car = parkedCars.remove(spot);
        availableSpots.add(spot);
        System.out.println("Car" + car.getLicensePlate() + "left spot" + spot);
        return true;
    }
}

class Car{
    private final String licensePlate;

    public Car(String licensePlate){
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate(){
        return licensePlate;
    }
}

public class Main{
    public static void main(String[] args){
        ParkingLot parkingLot = new ParkingLot(5);

        Car car1 = new Car("ABC123");
        Car car2 = new Car("DEF456");
        Car car3 = new Car("GHI789");
        Car car4 = new Car("JKL012");
        Car car5 = new Car("MNO345");

        parkingLot.parkCar(car1);
        parkingLot.parkCar(car2);
        
    }
}
