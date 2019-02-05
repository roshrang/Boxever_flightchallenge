Boxever Flight Seat Allocation Challenge:

Approach Followed:

1. Reading file form command line arguement.
2. Parsing information line by line.
3. Each line denotes the group of passenger either single or in group.
4. Passenger number with W denotes requested for Window seat.
5. First line of the document provides the dimensions of the airplane seats. Assuming window on both ends.
6. Passengers in group are seated together on the same row.
7. Passengers requested for window seat will be allocated with window seat.
8. Single passengers will eb located seat where it's empty if no preference is demanded.
9. Output Seat Map
10. roviding % of customers satisfied at the last line.

How to run:

1. Change the directory to the project: \Boxever\dist\
2. Run the jar file with the command:
java -jar Boxever.jar --file filelocationhere

Example: java -jar Boxever.jar --file F:/challenge/boxever.txt

An example input file
is:
4 4
1W 2 3
4 5 6 7
8
9 10 11W
12W
13 14
15 16
The first line specifies the dimensions of the plane. The first digit is the number of seats in a
row and the second digit is the number of rows on the plane.
Each subsequent line describes a group of travellers. For example, the first line of travelers
describes a group of three where the first traveller has a preference for a window seat. Each
number uniquely identifies the traveller on the flight.
The output for the above file should be:
1 2 3 8
4 5 6 7
11 9 10 12
13 14 15 16
100%

Test Performed: Tests performed with the help of JUnit. 6 cases and combinations were performed.
