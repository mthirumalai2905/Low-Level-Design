import java.util.*;

enum Direction {UP, DOWN, IDLE}

class Elevator{
    private int currentFloor;
    private Direction direction;
    private TreeSet<Integer> upStops;
    private TreeSet<Integer> downStops;
    
    public Elevator(){
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.upStops = new TreeSet<>();
        this.downStops = new TreeSet<>(Collections.reverseOrder());
    }
    
    public void requestFloor(int floor){
        if(floor > currentFloor) upStops.add(floor);
        else downStops.add(floor);
        updateDirection();
    }
    
    private void updateDirection(){
        if(!upStops.isEmpty()) direction = Direction.UP;
        else if(!downStops.isEmpty()) direction = Direction.DOWN;
        else direction = Direction.IDLE;
    }
    
    public void move(){
        while(!upStops.isEmpty() || !downStops.isEmpty()){
            if(direction == Direction.UP){
                currentFloor = upStops.pollFirst();
            } else if(direction == Direction.DOWN){
                currentFloor = downStops.pollFirst();
            }
            System.out.println("Stopped at floor: " + currentFloor);
            updateDirection();
        }
    }
}

public class ElevatorSystem{
    public static void main(String[] args){
        Elevator elevator = new Elevator();
        elevator.requestFloor(5);
        elevator.requestFloor(2);
        elevator.requestFloor(8);
        elevator.move();
    }
}
