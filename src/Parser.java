import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Parser {
    public List<Ticket> parse(String path) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        var tickets = new ArrayList<Ticket>();
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader(path));
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy hh:mm"); // Date format with date and time
            JSONArray JSONTickets = (JSONArray) jsonObject.get("tickets");
            for (Object JSONTicket : JSONTickets) {
                JSONObject row = (JSONObject) JSONTicket;
                String origin = (String) row.get("origin");
                String originName = (String) row.get("origin_name");
                String destination = (String) row.get("destination");
                String destinationName = (String) row.get("destination_name");
                Date departureDateTime = formatter.parse(row.get("departure_date").toString() + " " +
                        row.get("departure_time").toString());
                Date arrivalDateTime = formatter.parse(row.get("arrival_date").toString() + " " +
                        row.get("arrival_time").toString());
                String carrier = (String) row.get("carrier");
                int stops = Integer.parseInt(row.get("stops").toString());
                int price = Integer.parseInt(row.get("price").toString());
                var ticket = new Ticket(origin, originName, destination, destinationName,
                        departureDateTime, arrivalDateTime, carrier, stops, price);
                tickets.add(ticket);
            }
        } catch (IOException e) {
            System.out.println("There is some problems with reading data from JSON file. Probably, access to the file is denied or the file does not exist");
        } catch (ParseException e) {
            System.out.println("There is some problems with reading data from JSON file. Probably, data in this file are not in JSON format");
        } catch (java.text.ParseException e) {
            System.out.println("There is some problems with reading data from JSON file. Probably, dates in JSON objects have incorrect format");
        }
        return tickets;
    }
}