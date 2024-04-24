package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.programs.SawPrimePlanks;
import edu.iu.c212.programs.StaffScheduler;
import edu.iu.c212.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Store implements IStore{

    List<Item> inventory = new ArrayList<>();
    List<Staff> staffList = new ArrayList<>();

    public Store() throws IOException {
        takeAction();
    }
    @Override
    public List<Item> getItemsFromFile() throws IOException {
        try{
            inventory = FileUtils.readInventoryFromFile();
            return inventory;
        }
        catch (IOException exception)
        {
            System.exit(0);
        }
        return null;
    }

    @Override
    public List<Staff> getStaffFromFile() throws IOException {
        try{
            staffList = FileUtils.readStaffFromFile();
            return staffList;
        }
        catch (IOException exception)
        {
            System.exit(0);
        }
        return null;
    }

    @Override
    public void saveItemsFromFile() throws IOException {
        try{
            FileUtils.writeInventoryToFile(inventory);
        }
        catch (IOException exception)
        {
            System.exit(0);
        }
    }

    @Override
    public void saveStaffFromFile() throws IOException {
        try{
            FileUtils.writeStaffToFile(staffList);
        }
        catch (IOException exception)
        {
            System.exit(0);
        }
    }

    @Override
    public void takeAction() throws IOException {
        try{
            //save items needs to write from file to list
            saveItemsFromFile();
            //save staff needs to write from file to list
            saveStaffFromFile();
            List<String> commands = FileUtils.readCommandsFromFile();

            for(String currCommand : commands){
                String name = "";
                String[] nameGet = currCommand.split("'");
                name = name.concat(nameGet[1]);
                String[] splitCommand = currCommand.split(" ");
                switch (splitCommand[0]) {
                    case "ADD" ->
                        //at some point remove apostrophes from itemname
                            add(splitCommand, name);
                    case "COST" -> cost(splitCommand, name);
                    case "EXIT" -> exit(splitCommand);
                    case "FIND" -> find(splitCommand, name);
                    case "FIRE" -> fire(splitCommand, name);
                    case "HIRE" -> hire(splitCommand, name);
                    case "PROMOTE" -> promote(splitCommand, name);
                    case "SAW" -> saw(splitCommand);
                    case "SCHEDULE" -> schedule(splitCommand);
                    case "SELL" -> sell(splitCommand, name);
                    case "QUANTITY" -> quantity(splitCommand, name);
                }
            }

        }
        catch (IOException exception)
        {
            System.exit(0);
        }
    }

    public void add(String[] splitCommand, String itemName) throws IOException {
        List<Item> newItemList = new ArrayList<>();
        Item newItem = new Item(itemName, Integer.parseInt(splitCommand[2]), Integer.parseInt(splitCommand[3]), Integer.parseInt(splitCommand[4]));
        newItemList.add(newItem);
        FileUtils.writeInventoryToFile(newItemList);
        String outputLine = itemName + " was added to inventory";
        FileUtils.writeLineToOutputFile(outputLine);
    }

    public void cost(String[] splitCommand, String itemName) throws IOException {
        String outputLine = "";
        for(Item currItem : inventory){
            if(currItem.getName().equals(itemName)){
                outputLine = currItem.getName() + ": $" + currItem.getPrice();
            }
        }
        FileUtils.writeLineToOutputFile(outputLine);
    }

    public void exit(String[] splitCommand) throws IOException {
        String outputLine = "Thank you for visiting High's Hardware and Gardening!";
        FileUtils.writeLineToOutputFile(outputLine);
        Scanner usrInput = new Scanner(System.in);
        System.out.println("Please hit Enter to continue...");
        String currInput = "blank";
        while(!currInput.isEmpty()){
            if(!usrInput.hasNext()){
                continue;
            }
            currInput = usrInput.next();
        }
        System.exit(1);
    }

    public void find(String[] splitCommand, String itemName) throws IOException {
        for(Item currItem : inventory){
            if(currItem.getName().equals(itemName)){
                System.out.println(currItem.getQuantity() + " " + itemName + " are available in " + currItem.getAisle());
                return;
            }
        }
        System.out.println("ERROR: " + itemName + " cannot be found");
    }

    public void fire(String[] splitCommand, String staffName) throws IOException {
        String outputLine = "";
        for(Staff currStaff : staffList){
            if(currStaff.getName().equals(staffName)){
                staffList.remove(currStaff);
                FileUtils.removeStaffFromFile(currStaff.getName());
                outputLine = staffName + " was fired";
                FileUtils.writeLineToOutputFile(outputLine);
                return;
            }
        }
        outputLine = "ERROR: " + staffName + "cannot be found";
        FileUtils.writeLineToOutputFile(outputLine);
    }

    public void hire(String[] splitCommand, String staffName) throws IOException {
        List<Staff> newStaffList = new ArrayList<>();
        Staff newStaff = new Staff(staffName, Integer.parseInt(splitCommand[2]), splitCommand[3], splitCommand[4]);
        newStaffList.add(newStaff);
        FileUtils.writeStaffToFile(newStaffList);
        String fullRole = switch (newStaff.getRole()) {
            case "M" -> "Manager";
            case "C" -> "Cashier";
            case "G" -> "Gardening Expert";
            default -> "";
        };
        String outputLine = newStaff.getName() + " has been hired as a " + fullRole;
        FileUtils.writeLineToOutputFile(outputLine);
    }

    public void promote(String[] splitCommand, String staffName) throws IOException {
        List<Staff> newStaffList = new ArrayList<>();
        String fullRole = "";
        for(Staff currStaff : staffList){
            if(currStaff.getName().equals(staffName)){
                Staff newStaff = new Staff(currStaff.getName(), currStaff.getAge(), splitCommand[2], currStaff.getAvailability());
                staffList.remove(currStaff);
                staffList.add(newStaff);
                newStaffList.add(newStaff);
                FileUtils.removeStaffFromFile(staffName);
                FileUtils.writeStaffToFile(newStaffList);
                fullRole = switch (newStaff.getRole()) {
                    case "M" -> "Manager";
                    case "C" -> "Cashier";
                    case "G" -> "Gardening Expert";
                    default -> "";
                };
                break;
            }
        }
        String outputLine = staffName + " was promoted to " + fullRole;
        FileUtils.writeLineToOutputFile(outputLine);
    }

    public void saw(String[] splitCommand) throws IOException {
        /*
        * Old code that updates inventory.txt here instead of in
        * SawPrimePlanks.java. Leaving it here in case we need it
        */
        /*List<Item> newPlanks = new ArrayList<>();
        for(Item item : inventory){
            if(item.getName().contains("Plank")){
                String[] splitName = item.getName().split("-");
                if(Store.isPrime(Integer.parseInt(splitName[1]))){
                    List<Integer> plankLengths = SawPrimePlanks.getPlankLengths(Integer.parseInt(splitName[1]));
                    for(Integer currPlank : plankLengths){
                        int numCurrPlank = 0;
                        for(Integer otherPlank : plankLengths){
                            if(Objects.equals(otherPlank, currPlank)){
                                numCurrPlank++;
                                plankLengths.remove(otherPlank);
                            }
                        }
                        String length = Integer.toString(currPlank);
                        Item newPlank = new Item("Plank-" + length, Math.pow(currPlank, 2), numCurrPlank, 1);
                        inventory.add(newPlank);
                        newPlanks.add(newPlank);
                    }
                }
                FileUtils.removeItemFromInventory(item.getName());
            }
        }
        FileUtils.writeInventoryToFile(newPlanks);*/
        String[] args = new String[0];
        SawPrimePlanks.main(args);
    }

    public void schedule(String[] splitCommand) throws IOException {
        StaffScheduler.scheduleStaff();
        String outputLine = "Schedule Created.";
        FileUtils.writeLineToOutputFile(outputLine);
    }

    public void sell(String[] splitCommand, String itemName) throws IOException {
        List<Item> newItemList = new ArrayList<>();
        for(Item currItem : inventory){
            if(currItem.getName().equals(itemName)){
                Item newItem = new Item(currItem.getName(), currItem.getPrice(), currItem.getQuantity() - Integer.parseInt(splitCommand[splitCommand.length - 1]), currItem.getAisle());
                inventory.remove(currItem);
                inventory.add(newItem);
                newItemList.add(newItem);
                FileUtils.removeItemFromInventory(itemName);
                FileUtils.writeInventoryToFile(newItemList);
                String outputLine = splitCommand[splitCommand.length - 1] + " " + itemName + " was sold";
                FileUtils.writeLineToOutputFile(outputLine);
                return;
            }
        }
        String outputLine = "ERROR: " + itemName + " could not be sold";
        FileUtils.writeLineToOutputFile(outputLine);
    }

    public void quantity(String[] splitCommand, String itemName) throws IOException {
        for(Item currItem : inventory){
            if(currItem.getName().equals(itemName)){
                String outputLine = itemName + ": " + currItem.getQuantity();
                FileUtils.writeLineToOutputFile(outputLine);
                return;
            }
        }
    }

    /*
    * Potentially not needed method that was being used in
    * old implementation of Saw command method
    */
    public static boolean isPrime(int length){
        if(length <= 1){
            return false;
        }
        for(int i = 2; i < Math.sqrt(length); i++){
            if(length % i == 0){
                return false;
            }
        }
        return true;
    }

}

