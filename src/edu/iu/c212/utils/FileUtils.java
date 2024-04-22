package edu.iu.c212.utils;

import edu.iu.c212.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static File inputFile = new File("../resources/input.txt");
    private static File outputFile = new File("../resources/output.txt");
    private static File inventoryFile = new File("../resources/inventory.txt");
    private static File staffFile = new File("../resources/staff.txt");
    private static File staffAvailabilityFile = new File("../resources/staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File("../resources/shift_schedules_IN.txt");
    private static File storeScheduleFile = new File("../resources/store_schedule_OUT.txt");

    public static List<Item> readInventoryFromFile() throws IOException {
        System.out.println(inventoryFile/*.toURI()*/.getPath() + "\n" + inventoryFile.exists());
        // depending on your OS, toURI() may need to be used when working with paths
        // TODO
        return null;
    }

    public static List<Staff> readStaffFromFile() throws IOException {
        // TODO
        return null;
    }

    public static void writeInventoryToFile(List<Item> items) {

        // TODO
    }

    public static void writeStaffToFile(List<Staff> employees) {
        // TODO
    }

    public static List<String> readCommandsFromFile() throws IOException {
        List<String> commands = new ArrayList<>();
        BufferedReader file = new BufferedReader(new FileReader(inventoryFile));
        String line = "";
        while((line = file.readLine()) != null){
            commands.add(line);
        }
        return commands;
    }

    public static void writeStoreScheduleToFile(List<String> lines) {
        // TODO
    }

    public static void writeLineToOutputFile(String line) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
        pw.println(line);
        pw.close();
    }

    public static void writeNewItemToInventory(String line) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(inventoryFile));
        pw.println(line);
        pw.close();
    }

}
