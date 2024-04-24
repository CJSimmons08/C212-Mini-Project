package edu.iu.c212.programs;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StaffScheduler {

    public static void scheduleStaff(){
        // hash map to track days available for each staff member
        HashMap<String, ArrayList<String>> staffAvailability = new HashMap<>();
        // hash map to track the hours of each staff member
        HashMap<String, Double> staffHours = new HashMap<>();
        // file path was being weird and not working, had to use absolute for the time being
        String filename = "/Users/colinvanhoveln/Documents/school/CSCI-C212/C212-Mini-Project/src/edu/iu/c212/resources/staff_availability_IN.txt";

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                // splits each line up so i can isolate certain parts of the line
                String[] sections = line.split(" ");
                String fullName = sections[0] + " " + sections[1];
                // used to keep the days that will be added to the availability map later
                ArrayList<String> days = new ArrayList<>();
                if (sections.length > 4) {
                    // temporary array because you cant use split for ArrayLists
                    String[] daysTemp = sections[4].split("\\."); // must use backslashes otherwise it will just split using every character
                    // this loop adds each day from the tempDays array to the ArrayList that will be added to the hash map
                    for (String day : daysTemp) {
                        // also keeps track of each employees hours while adding the days
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

        // need to implement a way of averaging out each employees hours while respecting their availability
        // need to output these results in a specific format into the store_schedule_OUT.txt file


        // will be used for the beginning of the output file, when it asks to write the current date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy 'at' HHmm");
        String formattedDateTime = "Created on " + now.format(formatter);
    }
}
