package edu.iu.c212.programs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StaffScheduler {

    public void scheduleStaff(){
        HashMap<String, ArrayList<String>> staffAvailability = new HashMap<>();
        HashMap<String, Double> staffHours = new HashMap<>();
        String filename = "/Users/colinvanhoveln/Documents/school/CSCI-C212/C212-Mini-Project/src/edu/iu/c212/resources/staff_availability_IN.txt";


        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] sections = line.split(" ");
                String fullName = sections[0] + " " + sections[1];
                ArrayList<String> days = new ArrayList<>();
                if (sections.length > 4) {
                    String[] daysTemp = sections[4].split("\\.");
                    for (String day : daysTemp) {
                        if (day.equals("M") || day.equals("T") || day.equals("W") || day.equals("TR") || day.equals("F")) {
                            if (staffHours.containsKey(fullName)) {
                                staffHours.put(fullName, staffHours.get(fullName) + 9.0);
                            } else {
                                staffHours.put(fullName, 9.0);
                            }
                        } else if (day.equals("SAT") || day.equals("SUN")) {
                            if (staffHours.containsKey(fullName)) {
                                staffHours.put(fullName, staffHours.get(fullName) + 12.5);
                            } else {
                                staffHours.put(fullName, 12.5);
                            }
                        }
                        days.add(day);
                    }
                }
                staffAvailability.put(fullName, days);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            e.printStackTrace();
        }
    }
}
