import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Jimmy on 3/23/2015 in PACKAGE_NAME
 * 109259420
 * Homework 4
 * jimmy.ji@stonybrook.edu
 * Recitation 3: Sun Lin
 */

/**
 * Class simulator that simulates the busses
 */
public class Simulator {
    private final static int NUM_BUS_STOPS = 8;
    PassengerQueue[] busStops = new PassengerQueue[NUM_BUS_STOPS];
    int[] inRoute = {0, 1, 2, 3, 0};
    int[] outRoute = {4, 5, 6, 7, 4};

    /**
     * default constructor that initializes the bus queue
     */
    public Simulator() {
        initializeBusQueue();
    }

    /**
     * Translate integer value of stop to String
     * @param stop is integer value of stop
     * @return string value of stop
     */
    public static String getStop(int stop) {
        switch(stop) {
            case 0: return "In Route South P";
            case 1: return "In Route West";
            case 2: return "In Route SAC";
            case 3: return "In Route Chapin";
            case 4: return "Out Route South P";
            case 5: return "Out Route PathMart";
            case 6: return "Out Route Walmart";
            case 7: return "Out Route Target";
            default: return "Invalid Stop";
        }
    }

    /**
     * Sets up the probabilities that passengers will come to each individual stop
     * @param min is the minimum amount of passengers in each passenger group
     * @param max is the maximum amount of passengers in each passenger group
     * @param prob is the probability that a passenger will arrive in a stop
     * @param arrival is the time that the passenger arrives
     */
    public void createStop(int min, int max, double prob, int arrival) {
        for (int i = 0, j = 0; i < NUM_BUS_STOPS; i++) {
            int stop;
            if (i < inRoute.length-1) {
               stop = inRoute[randInt(i, 4)];
            } else {
                stop = outRoute[randInt(j, 4)];
                j++;
            }
            int passengers = randInt(min, max);
            if (prob > Math.random() && i != stop) {
                Passenger passenger = new Passenger(passengers, stop, arrival);
                    System.out.println("A group of " + passenger.getNumberOfPeople() + " passengers arrived at " + getStop(i) + " heading to " + getStop(passenger.getDestination()));
                    busStops[i].enqueue(passenger);
            }
        }

    }

    /**
     * creates the bus stops
     */
    public void initializeBusQueue() {
        for(int i = 0; i < NUM_BUS_STOPS; i++) {
            busStops[i] = new PassengerQueue();
        }
    }

    /**
     * Simplified method of a random integer
     * @param min is the lower limit of the probability
     * @param max is the higher limit of the probability
     * @return the random int between min and max
     */
    private static int randInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    /**
     * Creates the in route busses
     * @param amount is the amount of busses to be created
     * @param capacity is the capacity per busses to be created
     * @return the bus array that holds all the busses
     */
    public Bus[] createInBus(int amount, int capacity) {
        Bus[] array = new Bus[amount];
       for (int i = 0; i < amount; i++) {
           array[i] = new Bus("inRoute", capacity, i);
       }
        return array;
    }

    /**
     * Creates the out route busses
     * @param amount is the amount of busses to be created
     * @param capacity is the capacity per busses to be created
     * @return the bus array that holds all the busses
     */
    public Bus[] createOutBus(int amount, int capacity) {
        Bus[] array = new Bus[amount];
        for (int i = 0; i < amount; i++) {
            array[i] = new Bus("outRoute", capacity, i);
        }
        return array;
    }

    /**
     * Prints the bus array
     * @param array is the bus array to be printed
     */
    public void printBusArray(Bus[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i].toString());
        }
    }

    /**
     * prints all the stops and what they hold
     */
    public void printStops() {
        for (int i = 0; i < NUM_BUS_STOPS; i++) {
            System.out.print(i + " (" +getStop(i)+ "):");
            System.out.println(busStops[i]);
        }
    }

    /**
     * Sets the beginning rest times for the bus array
     * @param array is the bus array to be set
     */
    public void setRestTimes(Bus[] array) {
        for (int i = 0 , j = 0; i < array.length;i++, j+=30) {
            array[i].setTimeToRest(j);
        }
    }

    /**
     * Decreases the time of a individual bus in a bus array regardless if resting or not
     * @param array is the bus array to have its time decreased
     */
    public void decreaseTimes(Bus[] array) {
        for (int i = 0; i < array.length; i++) {
            if(array[i].isResting()) {
                array[i].reduceRestTime(); //decreases rest time
            }
            else {
                array[i].reduceTimeUntilNextStop(); // decreases arrival time
            }
        }
    }

    /**
     * Pick up passengers at the bus's current stop
     * @param array is the bus array to have this done on
     * @param currentTime is the current time of the simulation
     * @return an integer array holding the total time waited and the total passenger groups served
     */
    public int[] pickUp(Bus[] array, int currentTime) {
        int totalWait = 0;
        int totalPassengers = 0;
        for (int i = 0; i < array.length; i++) {
            int counter = 0;
            if (array[i].getToNextStop() == 0 && !array[i].isResting()) {
                int stop = array[i].getCurrentStop();
                if (!busStops[stop].isEmpty() && !array[i].getLastStop()) {
                    for (int j = 0; j < busStops[stop].size(); j++) {
                        if(array[i].getCurrentNumberOfPeople() + busStops[stop].peek().getNumberOfPeople() <= array[i].getCapacity()) {
                            counter += busStops[stop].peek().getNumberOfPeople();
                            array[i].board(busStops[stop].dequeue(), currentTime);
                            totalWait += array[i].peek().getWaitTime();
                            j--;
                            totalPassengers++;
                        } else {
                            break;
                        }
                    }
                    System.out.println(array[i].getBusType() + " Bus " + (array[i].getBusNumber() +1) + " picks up " + counter + " passengers");
                }
                array[i].goToNextStop();
            }
        }
        return new int[] {totalWait, totalPassengers};
    }


    /**
     * Drops off the passengers if they have reached their intended destination in a bus array
     * @param array is the bus array that we have drop off their passengers
     * @return amount of passengers groups dropped.
     */
    public int dropOff(Bus[] array) {
        int counter = 0;
        for (int i = 0; i <array.length; i++) {
            if (array[i].getToNextStop() == 0 && !array[i].isResting()) {
                int stop = array[i].getCurrentStop();
                for (int j = 0; j < array[i].size(); j++) {
                   if (array[i].unload(stop) > 1)
                    counter += 1;
                    }
                }
            }
        return counter;
        }

    /**
     * Calculates the average for time waited per passenger group
     * @param array is the where the time waited and number passenger group is stored
     * @return the average of total time waited and number passengers
     */
    public double averageTime(int [] array ) {
        return (double)array[0]/array[1];
    }

    /**
     * A method that adds the time waited and the total passengers together
     * @param array is the where the data is added from
     * @param add is the where the data is added to
     * @return is the new data stored in the "add" parameter
     */
    public int [] adder(int [] array, int [] add) {
        add[0] += array[0];
        add[1] += array[1];
        return add;
    }

    /**
     * The simulation that requires user input
     * Also holds the simulation times
     * @return a double array that holds the average and total passengers served
     * @throws IllegalArgumentException when there is an illegal input
     */
    public double[] simulate() throws IllegalArgumentException {
        /*
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of In Route busses:");
        int numInBusses = input.nextInt();
        System.out.print("Enter the number of Out Route busses:");
        int numOutBusses = input.nextInt();

        if(numInBusses < 0 || numOutBusses <0)
            throw new IllegalArgumentException("Invalid input");
        System.out.print("Enter the minimum group size:");
        int minGroupSize = input.nextInt();
        System.out.print("Enter the maximum group size:");
        int maxGroupSize = input.nextInt();

        if(minGroupSize > maxGroupSize)
            throw new IllegalArgumentException("Invalid input");

        System.out.print("Enter the maximum capacity of the bus:");
        int capacity = input.nextInt();

        System.out.print("Enter the probability of arrival:");
        double arrivalProb = input.nextDouble();

        if(arrivalProb < 0 || arrivalProb > 1)
            throw new IllegalArgumentException("Invalid input");
        System.out.print("Enter the duration of the simulation:");
        int simulationTime = input.nextInt();

        if (simulationTime < 0)
            throw new IllegalArgumentException("Invalid input");
        */

        int numInBusses = 2;
        int numOutBusses = 2;
        int minGroupSize = 1;
        int maxGroupSize = 5;
        int capacity = 7;
        double arrivalProb = 1;
        int simulationTime = 115;


        Bus[] inBus = createInBus(numInBusses, capacity);
        Bus[] outBus = createOutBus(numOutBusses, capacity);

        int busNumberIn = 0;
        int busNumberOut = 0;
        setRestTimes(inBus);
        setRestTimes(outBus);

        int[] counter = {0,0};
        /**
         * The for loop that starts the simulation by calling all the methods listed above
         */
        for(int currentMinute = 1; currentMinute <= simulationTime; currentMinute++) {

            System.out.println("Minute " +currentMinute);
            System.out.println("----------");
            createStop(minGroupSize, maxGroupSize, arrivalProb, currentMinute);
            printBusArray(inBus);
            printBusArray(outBus);
            printStops();
            System.out.println();
            dropOff(inBus);
            dropOff(outBus);
            counter = adder(pickUp(inBus, currentMinute), counter);
            counter = adder(pickUp(outBus, currentMinute), counter);
            decreaseTimes(inBus);
            decreaseTimes(outBus);


            if (currentMinute%20==0 && busNumberIn < inBus.length)
                busNumberIn++;
            if (currentMinute%20==0 && busNumberOut < outBus.length)
                busNumberOut++;

            System.out.println();
        }

        System.out.printf("The average wait time was %.2f", averageTime(counter));
        System.out.println();
        System.out.printf(counter[1]+ " passenger groups were served");
        System.out.println();

        return new double[] {averageTime(counter), counter[1]};
    }

    public static void main(String[] args) {
        /**
         * This is the loop that initiates the simulation and catches any exceptions that are thrown by the simulation
         * When there is an error, it starts the input process all over again
         */
        String s = "";
        boolean error = false;
        do {
            try {
                Scanner input = new Scanner(System.in);
                Simulator simulation = new Simulator();
                simulation.simulate();
                System.out.print("Do you want to run another simulation?: Y/N");
                s = input.next();
                if(s.equalsIgnoreCase("n")) {
                    System.out.println("Program Terminating");
                    System.exit(1);
                }
            } catch (ArithmeticException ex) {
                System.out.println("The average time was not available");
                error = true;
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid input. Try again");
                error = true;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Try again");
                error = true;
            }
        } while (s.equalsIgnoreCase("y") || error);
    }

}
