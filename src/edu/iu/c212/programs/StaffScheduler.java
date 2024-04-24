package edu.iu.c212.programs;


import java.io.*;
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
        String filename = "../resources/staff_availability_IN.txt";

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
                            staffHours.put(fullName, staffHours.getOrDefault(fullName, 0.0) + 9.0);
                        } else if (day.equals("SAT") || day.equals("SUN")) {
                            staffHours.put(fullName, staffHours.getOrDefault(fullName, 0.0) + 12.5);
                        }
                        days.add(day);
                    }
                }
                staffAvailability.put(fullName, days);
            }

            // calculates the total hours between all staff
            double totalHours = staffHours.values().stream().mapToDouble(Double::doubleValue).sum();
            // the average hours of work per day each staff member should ideally be working
            double avgHrsPerDay = totalHours / 7;
            // used to keep track of all days and hours allocated
            HashMap<String, HashMap<String, Double>> dailyAllocations = new HashMap<>();

            // go through each staff member and figure out their assigned days and hours
            for (String staff : staffAvailability.keySet()) {
                // all available days for that staff member
                ArrayList<String> availableDays = staffAvailability.get(staff);
                // pretty self-explanatory, total available hours for that staff member
                double totalAvailableHours = staffHours.get(staff);
                // around how much that staff member should be scheduled throughout the week
                double targetHoursPerDay = totalAvailableHours / availableDays.size();

                HashMap<String, Double> allocations = new HashMap<>(); // keeps track of hours for each day
                dailyAllocations.put(staff, allocations);

                // this loop keeps track of total available hours and the number of hours each day needs to be allocated
                for (String day : availableDays) {
                    double hoursToAllocate = Math.min(targetHoursPerDay, (day.equals("SAT") || day.equals("SUN")) ? 12.5 : 9.0);
                    allocations.put(day, allocations.getOrDefault(day, 0.0) + hoursToAllocate);
                    totalAvailableHours -= hoursToAllocate;
                }

                // allocated remaining hours
                while (totalAvailableHours > 0) {
                    for (String day : availableDays) {
                        if (totalAvailableHours <= 0) break;
                        double currentAllocation = allocations.get(day);
                        double additionalHours = Math.min(targetHoursPerDay - currentAllocation, totalAvailableHours);
                        allocations.put(day, currentAllocation + additionalHours);
                        totalAvailableHours -= additionalHours;
                    }
                }
            }

            String filenameOut = "../resources/store_schedule_OUT.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(filenameOut))) {
                // will be used for the beginning of the output file, when it asks to write the current date
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy 'at' HHmm");
                writer.println("Created on " + now.format(formatter));
                // just a way to iterate through the days
                String[] weekDays = {"M", "T", "W", "TR", "F", "SAT", "SUN"};

                // Iterate over each day of the week and format the schedule
                for (String day : weekDays) {
                    writer.print(day + " "); // makes sure there is a space between each name
                    boolean firstOne = true; // prints each name
                    for (String staff : dailyAllocations.keySet()) {
                        HashMap<String, Double> allocations = dailyAllocations.get(staff);
                        if (allocations.containsKey(day)) {
                            String[] nameParts = staff.split(" ");
                            String firstName = nameParts[0];
                            String lnInitial = nameParts[1].substring(0, 1);
                            if (!firstOne) { writer.print(" "); }
                            writer.print("(" + firstName + " " + lnInitial + ")");
                            firstOne = false;
                        }
                    }
                    if (!day.equals("SUN")) {
                        writer.println(); // makes sure there is no empty line at the end
                    }
                }

            } catch (IOException e) {
                System.err.println("File not found");
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            e.printStackTrace();
        }

    }

}
