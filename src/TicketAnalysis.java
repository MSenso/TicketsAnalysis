import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TicketAnalysis {

    private static long flightTime(Ticket ticket) {
        long diffInMs = Math.abs(ticket.getArrivalDateTime().getTime() - ticket.getDepartureDateTime().getTime()); // DateTime difference between two dateTimes in milliseconds
        return TimeUnit.MINUTES.convert(diffInMs, TimeUnit.MILLISECONDS); // difference in minutes
    }

    public static double meanTime(List<Ticket> tickets) {
        var filteredTickets = tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
                .toList(); // all tickets with VVO origin and TLV destination
        return filteredTickets.size() == 0 ? 0 : filteredTickets.stream() // if there is no such tickets 0 is returned
                .map(TicketAnalysis::flightTime) // mapping to DateTime difference in minutes
                .reduce(0L, Long::sum) // sum of all minutes
                / (double) filteredTickets.size();
    }

    public static String showTime(long time) {
        long hours = time / 60;
        long minutes = time % 60;
        return MessageFormat.format("{0} hours {1} minutes", hours, minutes);
    }

    private static void show(List<Ticket> tickets) {
        for (var ticket : tickets) {
            System.out.println(ticket.toString());
        }
    }

    public static long percentileTime(List<Ticket> tickets, double percentile) {
        if (percentile <= 0 || percentile > 1) {
            System.out.println("Percentile should be a positive value that is not higher than 1.0");
            return 0;
        }
        var filteredTickets = new ArrayList<>(tickets
                .stream()
                .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
                .toList()); // all tickets with VVO origin and TLV destination
        filteredTickets.sort((t1, t2) -> Long.compare(flightTime(t2), flightTime(t1))); // descending sorting by flight time
        return filteredTickets.size() == 0 ? 0 : // if there is no such tickets 0 is returned
                flightTime(filteredTickets.get((int) Math.floor(Math.round(filteredTickets.size() * (1 - percentile) * 10) / 10.)));
    }
}