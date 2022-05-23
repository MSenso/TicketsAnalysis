import java.text.MessageFormat;

public class Run {

    private static boolean validateArgs(String[] args) {
        if (args == null || (args.length != 2 && args.length != 1)) { // If args size are not 1 and not 2
            return false;
        }
        if (args[0] == null || args[0].isBlank() || args[0].isEmpty()) { // if path is empty
            return false;
        }
        if (args.length == 2) { // if there is 2nd argument
            try {
                Double.parseDouble(args[1]); // if 2nd argument can be parsed to Double
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else return true;
    }

    public static void main(String[] args) {
        var correctInput = validateArgs(args);
        if (correctInput) {
            var percentile = args.length == 2 ? Double.parseDouble(args[1]) : 0.9; // if there is no 2nd argument percentile is 0.9 by default
            var tickets = new Parser().parse(args[0]); // parsed tickets from JSON file
            if (tickets.size() > 0) {
                System.out.println(MessageFormat.format("Mean flight time VVO-TLV: {0} minutes", TicketAnalysis.meanTime(tickets)));
                // mean flight time calculated
                long time = TicketAnalysis.percentileTime(tickets, percentile); // percentile flight time calculated
                System.out.println(MessageFormat.format("{0}% percentile for flight time VVO-TLV: {1}",
                        percentile * 100, TicketAnalysis.showTime(time)));
            } else System.out.println("There are no tickets in the selected JSON file");
        } else
            System.out.println("Args should have a path to JSON file and/or percentile float value in range 0-1." + System.lineSeparator() +
                    "Also, please, check if there are any spaces in the path and try to move JSON file to another directory");
    }
}
