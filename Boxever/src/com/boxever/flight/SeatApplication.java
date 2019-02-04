/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boxever.flight;
import com.boxever.flight.arrangement.SeatingArrangement;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
/**
 *
 * @author Roshan
 */
public class SeatApplication {
    
    @Option(name = "-f", aliases = { "--file" }, required = true, usage = "File with pax sitting details.")
	private String m_filePath;

	/**
	 * Entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SeatApplication().run(args);
	}

	public void run(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);
		parser.setUsageWidth(80);

		try {
			parser.parseArgument(args);

			SeatingArrangement arrangement = new SeatingArrangement();
			String arrangedSeatMap = arrangement
					.passengerSeatArrangement(m_filePath);
			StringBuilder sb = new StringBuilder();
			sb.append(SeatingArrangement.SEPERATER).append("Resulting Seat Map")
					.append(SeatingArrangement.SEPERATER).append(arrangedSeatMap)
					.append(arrangement.customerSatisfaction() + "%");
			System.out.print(sb.toString());

		} catch (CmdLineException e) {
			System.err
					.println("Arguements not provided");
			parser.printUsage(System.err);
			System.err.println();
			System.err
					.println(" Provide arguement as: java -cf SeatingArrangement --file FilePath");
			return;
		} catch (Exception e) {
			System.out.println("Unable to read the file."
					+ e.getMessage());
		}
	}

}
