package edu.iu.c212.programs;

import java.util.*;
import java.io.*;

public class SawPrimePlanks {

    /**
     * Main method to process inventory and update files.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        List<String> primePlankLines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                String plankID = parts[0];
                int plankLength = Integer.parseInt(parts[1]);
                int quantity = Integer.parseInt(parts[2]);

                for(int i = 0; i < quantity; i++){
                    List<Integer> primePlanksLengths = getPlankLengths(plankLength);
                    for(Integer primePlankLength : primePlanksLengths){
                        primePlankLines.add(plankID + "," + primePlankLength + "," + "1");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        updateInventory("inventory.txt", primePlankLines);
        writeOutput("output.txt", "Planks sawed.");
    }

    /**
     * Returns a list of prime plank lengths obtained by sawing a long plank.
     *
     * @param longPlankLength The length of the long plank.
     * @return A list of prime plank lengths.
     */
    public static List<Integer> getPlankLengths(int longPlankLength){
        List<Integer> primePlankLengths = new ArrayList<>();
        int result = sawPlank(longPlankLength);
        primePlankLengths.add(result);
        return primePlankLengths;
    }

    /**
     * Recursively computes the prime factors of a plank length.
     *
     * @param plankLength The length of the plank.
     * @return The product of the prime factors.
     */
    public static int sawPlank(int plankLength){
            if(plankLength == 1){
                return 1;
            }
            int smallestPrimeFactor = findSmallestPrimeFactor(plankLength);
            int result = sawPlank(plankLength / smallestPrimeFactor) * smallestPrimeFactor;

            return result;
        }

    /**
     * Finds the smallest prime factor of a number.
     *
     * @param n The number to find the smallest prime factor of.
     * @return The smallest prime factor of the number.
     */
    private static int findSmallestPrimeFactor(int n){
        for(int i = 2; i <= Math.sqrt(n); i++){
            if(n % i == 0){
                return i;
            }
        }
        return n;
    }

    /**
     * Updates the inventory file with the new prime plank lengths.
     *
     * @param filename         The name of the inventory file.
     * @param primePlankLines The list of new prime plank lengths.
     */
    public static void updateInventory(String filename, List<String> primePlankLines){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for(String line : primePlankLines){
                writer.write(line + "\n");
            }
        } catch(IOException e){
            System.err.println("Error updating inventory: " + e.getMessage());
        }
    }

    /**
     * Writes the output message to a file.
     *
     * @param fileName The name of the output file.
     * @param content  The content to write to the file.
     */
    public static void writeOutput(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing output: " + e.getMessage());
        }
    }
}
