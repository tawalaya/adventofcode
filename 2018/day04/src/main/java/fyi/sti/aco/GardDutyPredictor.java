package fyi.sti.aco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class GardDutyPredictor {
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
    //Internal repersentation of line entries
    static class Event implements Comparable<Event>{
        Calendar timestamp;
        String value;

        Event(Calendar timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "timestamp=" + sdf.format(timestamp.getTime()) +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Event event) {
            return timestamp.compareTo(event.timestamp);
        }
    }
 

    private static List<Event>  readInEvents(String filename){
        try(BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")))){
           return br.lines().map((s) -> {
                Calendar timestamp = Calendar.getInstance();
                try {
                    Date date = sdf.parse(s.substring(1, 17));
                    timestamp.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String value = s.substring(19);
                return new Event(timestamp, value);
            }).sorted().collect(Collectors.toList());
        } catch (IOException e){
            return null;
        }
    }

    //split for each shift (each time a new guard is working)
    private static List<List<Event>> splitIntoShifts(List<Event> eventes){
        List<List<Event>> shifts = new LinkedList<>();

            List<Event> shift = null;
            for (Event event:events){
                if(event.value.startsWith("Guard")){
                    if (shift != null) shifts.add(shift);
                    shift = new LinkedList<>();
                }
                shift.add(event);
            }
            shifts.add(shift);

        return shifts;
    }

    //for each shift, extract guard and sleep profile
    private static Map<Integer,Gard> assembleGardObservations(List<List<Event>> shifts){
        Map<Integer,Gard> gards = new HashMap<>();
            for (List<Event> gard_shift:shifts){
                Event gardline = gard_shift.get(0);
                Calendar startTime = gardline.timestamp;

                int id = Integer.parseInt(gardline.value.split(" ")[1].substring(1));

                Gard gard = gards.getOrDefault(id, new Gard(id));

                long assleep = startTime.getTimeInMillis();
                long minute = 0;
                for (Event event : gard_shift.subList(1, gard_shift.size())) {
                    minute = event.timestamp.get(Calendar.MINUTE);
                    switch (event.value){
                        case "falls asleep":
                            assleep = event.timestamp.getTimeInMillis();
                            break;
                        case "wakes up":
                            long time = (event.timestamp.getTimeInMillis()-assleep)/60000;
                            for (long i = minute-time; i < minute; i++) {
                                gard.shifts[(int)i]++;
                            }
                            break;
                    }
                }
                gards.put(id,gard);
            }
            return gards;
    }

    public static void main(String[] args) {
        
            List<Event> events = readInEvents(args[0]);

            Map<Integer,Guard> gards = assembleGardObservations(splitIntoShifts(eventes));
            
            gards.values().stream().sorted(Comparator.comparingInt(Gard::asslip)).forEach(System.out::println);
            gards.values().stream().sorted(Comparator.comparingInt(Gard::mostLikelyCount)).forEach(System.out::println);

        
    }

}
