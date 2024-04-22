package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
            return FileUtils.readInventoryFromFile();
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
            return FileUtils.readStaffFromFile();
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
            FileUtils.writeInventoryToFile(getItemsFromFile());
        }
        catch (IOException exception)
        {
            System.exit(0);
        }
    }

    @Override
    public void saveStaffFromFile() throws IOException {
        try{
            FileUtils.writeStaffToFile(getStaffFromFile());
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
                    case "SAW" -> saw(splitCommand, name);
                    case "SCHEDULE" -> schedule(splitCommand, name);
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
        String itemCost = splitCommand[2];
        String itemQuantity = splitCommand[3];
        String itemAisle = splitCommand[4];
        String addedLine = itemName + "," + itemCost + "," + itemQuantity + "," + itemAisle;
        FileUtils.writeNewItemToInventory(addedLine);
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
        /*
        * Still need to make writeToStaffList work
        */
        for(Staff currStaff : staffList){
            if(currStaff.getName().equals(staffName)){
                staffList.remove(currStaff);
                FileUtils.removeStaffFromFile(currStaff.getName());
                outputLine = staffName + " was fired";
                FileUtils.writeLineToOutputFile(outputLine);
                return;
            }
        }

    }

    public void hire(String[] splitCommand, String staffName) throws IOException {
        String outputLine = "";

    }

    public void promote(String[] splitCommand, String name) throws IOException {
        String outputLine = "";

    }

    public void saw(String[] splitCommand, String name) throws IOException {
        String outputLine = "";

    }

    public void schedule(String[] splitCommand, String name) throws IOException {
        String outputLine = "";

    }

    public void sell(String[] splitCommand, String name) throws IOException {
        String outputLine = "";

    }

    public void quantity(String[] splitCommand, String name) throws IOException {
        String outputLine = "";

    }

}

