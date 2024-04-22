package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;

import java.io.IOException;
import java.util.List;

public interface IStore {

    public List<Item> getItemsFromFile() throws IOException;

    public List<Staff> getStaffFromFile() throws IOException;

    public void saveItemsFromFile() throws IOException;

    public void saveStaffFromFile() throws IOException;

    public void takeAction() throws IOException;
}
