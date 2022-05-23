# TicketsAnalysis
Application to analyze tickets flight time

To run this application, you should run cmd/terminal and run "java -jar path/to/TicketsAnalysis.jar path/to/tickets.json percentile_value", 

percentile_value - float positive number that is not more than 1.0.

If you pass only one argument, it is considered as a path parameter, while percentile value is equal to 0.9.

Also, please, notice that having spaces in a path may lead to problems with running the application so you should to move JSON file to another directory.
