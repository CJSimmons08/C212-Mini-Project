package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;

import java.util.List;

public class Store implements IStore{

    public Store(){
        takeAction();
    }
    @Override
    public List<Item> getItemsFromFile() {
        return null;
    }

    @Override
    public List<Staff> getStaffFromFile() {
        return null;
    }

    @Override
    public void saveItemsFromFile() {

    }

    @Override
    public void saveStaffFromFile() {

    }

    @Override
    public void takeAction() {

    }
}
