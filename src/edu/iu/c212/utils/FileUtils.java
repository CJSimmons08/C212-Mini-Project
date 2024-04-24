package edu.iu.c212.utils;

import edu.iu.c212.models.*;

import java.io.*;
import java.text.FieldPosition;
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
        /*System.out.println(inventoryFile*//*.toURI()*//*.getPath() + "\n" + inventoryFile.exists());
        // depending on your OS, toURI() may need to be used when working with paths*/
        List<Item> itemList = new ArrayList<>();
        BufferedReader file = new BufferedReader(new FileReader(inventoryFile));
        String line = "";
        while((line = file.readLine()) != null){
            String[] nameGet = line.split("'");
            String itemName = nameGet[0];
            String notName = nameGet[1];
            String[] everythingElse = notName.split(",");
            Item newItem = new Item(itemName, Double.parseDouble(everythingElse[0]), Integer.parseInt(everythingElse[1]), Integer.parseInt(everythingElse[2]));
            itemList.add(newItem);
        }
        file.close();
        return itemList;
    }

    public static List<Staff> readStaffFromFile() throws IOException {
        List<Staff> staffList = new ArrayList<>();
        BufferedReader file = new BufferedReader(new FileReader(staffAvailabilityFile));
        String line = "";
        while((line = file.readLine()) != null){
            String[] splitLine = line.split(" ");
            Staff newStaff = new Staff(splitLine[0] + " " + splitLine[1], Integer.parseInt(splitLine[2]), splitLine[3], splitLine[4]);
            staffList.add(newStaff);
        }
        return staffList;
    }

    public static void writeInventoryToFile(List<Item> items) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(inventoryFile));
        for(Item item : items){
            pw.println("'" + item.getName() + "'" + "," + item.getPrice() + "," + item.getQuantity() + "," + item.getAisle());
        }
        pw.close();
    }

    public static void writeStaffToFile(List<Staff> employees) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(staffAvailabilityFile));
        for(Staff employee : employees){
            pw.println(employee.getName() + " " + employee.getAge() + " " + employee.getRole() + " " + employee.getAvailability());
        }
        pw.close();
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

    public static void removeStaffFromFile(String staffName) throws IOException {
        BufferedReader staffFile = new BufferedReader(new FileReader(staffAvailabilityFile));
        File newOutputFile = new File("\"../resources/staff_availability_IN_Output.txt\"");
        PrintWriter pw = new PrintWriter(new FileWriter(newOutputFile));

        String line = "";
        while((line = staffFile.readLine()) != null){
            /*
             * Because this is line.contains, staff names need
             * to be written to the list the same way as they are
             * written in the staff file
             */
            if(!line.contains(staffName)){
                pw.println(line);
            }
        }
        pw.close();

        staffFile.close();
        if(staffAvailabilityFile.delete()){
            if(!newOutputFile.renameTo(staffAvailabilityFile)){
                throw new IOException("Could not rename staff_availability_IN_Output.txt to staff_availability_IN.txt");
            }
        }
        else{
            throw new IOException("Could not delete original Staff file");
        }
    }

    public static void removeItemFromInventory(String itemName) throws IOException {
        BufferedReader itemFile = new BufferedReader(new FileReader(inventoryFile));
        File newOutputFile = new File("\"../resources/inventory_Output.txt\"");
        PrintWriter pw = new PrintWriter(new FileWriter(newOutputFile));

        String line = "";
        while((line = itemFile.readLine()) != null){
            /*
             * Because this is line.contains, item names need
             * to be written to the list the same way as they are
             * written in the inventory file
             */
            if(!line.contains(itemName)){
                pw.println(line);
            }
        }
        pw.close();

        itemFile.close();
        if(inventoryFile.delete()){
            if(!newOutputFile.renameTo(inventoryFile)){
                throw new IOException("Could not rename inventory_Output.txt to inventory.txt");
            }
        }
        else{
            throw new IOException("Could not delete original inventory file");
        }
    }

}
