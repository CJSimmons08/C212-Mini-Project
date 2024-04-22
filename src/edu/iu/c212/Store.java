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
                String[] splitCommand = currCommand.split(" ");
                switch (splitCommand[0]){
                    case "ADD":
                        //at some point remove apostrophes from itemname
                        add(splitCommand);
                        break;
                    case "COST":
                        cost(splitCommand);
                        break;
                    case "EXIT":
                        exit(splitCommand);
                        break;
                    case "FIND":
                        find(splitCommand);
                        break;
                    case "FIRE":
                        fire(splitCommand);
                        break;
                    case "HIRE":
                        hire(splitCommand);
                        break;
                    case "PROMOTE":
                        promote(splitCommand);
                        break;
                    case "SAW":
                        saw(splitCommand);
                        break;
                    case "SCHEDULE":
                        schedule(splitCommand);
                        break;
                    case "SELL":
                        sell(splitCommand);
                        break;
                    case "QUANTITY":
                        quantity(splitCommand);
                        break;
                }
            }

            /*
            * Scanner for the user to end program
            */
            Scanner usrInput = new Scanner(System.in);
            System.out.println("Please hit Enter to end the program when you are finished.");
            String currInput = "blank";
            while(!currInput.isEmpty()){
                if(!usrInput.hasNext()){
                    continue;
                }
                currInput = usrInput.next();
            }
            System.exit(1);
        }
        catch (IOException exception)
        {
            System.exit(0);
        }
    }

    public void add(String[] splitCommand) throws IOException {
        String itemName = splitCommand[1];
        String itemCost = splitCommand[2];
        String itemQuantity = splitCommand[3];
        String itemAisle = splitCommand[4];
        String addedLine = itemName + "," + itemCost + "," + itemQuantity + "," + itemAisle;
        FileUtils.writeNewItemToInventory(addedLine);
        itemName = itemName.substring(1, itemName.length() - 1);
        String outputLine = itemName + " was added to inventory";
        FileUtils.writeLineToOutputFile(outputLine);
    }

    public void cost(String[] splitCommand){

    }

    public void exit(String[] splitCommand){

    }

    public void find(String[] splitCommand){

    }

    public void fire(String[] splitCommand){

    }

    public void hire(String[] splitCommand){

    }

    public void promote(String[] splitCommand){

    }

    public void saw(String[] splitCommand){

    }

    public void schedule(String[] splitCommand){

    }

    public void sell(String[] splitCommand){

    }

    public void quantity(String[] splitCommand){

    }

}

