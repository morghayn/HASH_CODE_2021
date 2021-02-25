import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        /*

         */
        final String DIR = "src/data/";
        final String[] FILE_NAMES = {"a.txt", "b.txt", "c.txt", "d.txt", "e.txt", "f.txt"};
        File file = new File(DIR + FILE_NAMES[0]);

        Scanner input = new Scanner(file);
        int simDuration = input.nextInt();
        int numOfIntersection = input.nextInt();
        int numOfStreet = input.nextInt();
        int numOfCar = input.nextInt();
        int scorePerCar = input.nextInt();

        List<String> streetNames = new ArrayList<>();
        List<Street> streets = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        List<Intersection> intersections = new ArrayList<>();

        // Creating the Intersection objects
        for (int i = 0; i<numOfIntersection; i++){
            Intersection intersection = new Intersection();
            intersections.add(intersection);
        }

        // Reading in lines containing description of streets
        //  - Two integers, the intersections at the start and the end of the street.
        //  - A String, the street name
        //  - An integer, the time it takes a car to get from the beginning to the end of that street
        for(int i = 0; i < numOfStreet; i++)
        {
            Intersection intersectionStart = intersections.get(input.nextInt());
            Intersection intersectionEnd = intersections.get(input.nextInt());
            streetNames.add(input.next());
            int nameIndex = streetNames.size()-1;
            int time = input.nextInt();
            Street street = new Street(intersectionStart, intersectionEnd, nameIndex, time);
            intersectionStart.outbounds.add(street);
            intersectionEnd.outbounds.add(street);
            streets.add(street);
        }

        // Reading in the lines that describe the paths of each car
        //  - An integer, the number of streets that the car wants to travel.
        //  - Strings, name of each street
        //
        //  Notes:
        //  **THE CAR STARTS AT THE END OF THE FIRST STREET**
        //  **FOLLOWS PATH UNTIL LAST STREET**
        for(int i = 0; i < numOfCar; i++){
            String carLines = input.nextLine();
            String[] carLinesData = carLines.split(" ");
            int numOfStreetsPerCar = Integer.parseInt(carLinesData[0]);
            int[] path = new int[numOfStreetsPerCar];
            for(int j = 1; j <= numOfStreetsPerCar; j++)
                path[j - 1] = streetNames.indexOf(carLinesData[j]);
            cars.add(new Car(path));
        }

        //~~~~~~~~~~~~
        // Simulation
        //

        // Setting lights that should be always on
        for (Intersection i: intersections){
            if(i.outbounds.size() == 1 && i.inbounds.size() == 1){
                Street street = i.inbounds.get(0);
                street.light = true;
            }
        }

        // OptimalRoad = null Road;
        //
        // For every second of the simulation:
        //      For every node (intersection):
        //          For every road going into the node:
        //              OptimalRoad = Determine which road we should turn on light for to turn on
        //          OptimalRoad.lightCounter++





        /*
         * // Cars maintain calc of all road times (we get this when we read in)
         *
         * // When looking at a node and determining what intersection to turn on,
         * // we just look at the first car for each light going into node
         * // we do road calc time + 1s for each car in the next road (this weight we calculate will factor in the penalty.)
         * // Considering the penalty, we will pick the road connected to the intersection with the lowest weight we calculator
         */


    }

    /*
     * Submission file
     * The first line must contain a single integer, the number of intersections for which you specify the schedule
     *
     * Can describe intersection schedules in any order. Formatted as so...
     * 1st Line: An integer i, ID of the intersection
     * 2nd Line: An integer Ei, number of incoming streets of the intersection covered by this schedule
     * Ei Lines: Describing the order and duration of green lights, each line contains:
     *  (Lines are formatted as so, separated by a space.)
     *      String, street name
     *      Integer, for how long each street will have a green light
     */

}
