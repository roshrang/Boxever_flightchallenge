/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boxever.flight.arrangement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.boxever.flight.passenger.PassengerDetail;
import com.boxever.flight.seat.Row;
import com.boxever.flight.seat.SeatNumber;
import com.boxever.flight.passenger.PassengerGroup;
/**
 *
 * @author Roshan
 */
public class SeatingArrangement {

	// Global Seat Map
	private Map<Integer, Row> seatsMap = new HashMap<>();
	private int seatAvailable;

	private int satisfiedPassenger = 0;
	private int totalPassenger = 0;

	public static String SEPERATER = "\n=============\n";
	private static final int windowseats = 2;

	/**
	 * Method to read the pax info from file and assign the seats
	 * 
	 * @param filePath
	 *            Path of the file containing pax info
	 * @return Seat map with assigned pax
	 * @throws IllegalArgumentException
	 */
	public String passengerSeatArrangement(String filePath)
			throws IllegalArgumentException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			int lineNum = 1;
			String line;

			while ((line = br.readLine()) != null) {
				String[] passenger = line.split("[\\s+]");

				if (lineNum == 1) {
					getDimensions(line, passenger);
				} else {
					totalPassenger = totalPassenger + passenger.length;
					try {
						if (passenger.length >= 1) {
							PassengerGroup pgrp = createGroup(passenger);
							int seatRowIndex = findSeatForGroup(pgrp);
							if (seatRowIndex >= 0) {
								seatAssignment(seatRowIndex, pgrp);
								seatAvailable = seatAvailable
										- passenger.length;
							} else if (seatAvailable >= pgrp
									.getTotalPassenger()) {
								// Didn't find the available seats in one row
								// for pgrp but there is overall capacity in
								// seatmap
								emptySeatAssignment(pgrp);
								seatAvailable = seatAvailable
										- passenger.length;
							} else {
								throw new RuntimeException(
										String.format(
												"No available seats for passenger group. Available Seats: %s and Total Passenger in the group: %s ",
												seatAvailable,
												pgrp.getTotalPassenger()));
							}
						}
					} catch (IllegalArgumentException e) {
						sb.append(SEPERATER)
								.append(" Step ")
								.append((lineNum - 1))
								.append(SEPERATER)
								.append("Error: Skipping the line as data format for passenger was invalid. "
										+ line);
					} catch (RuntimeException e) {
						sb.append(SEPERATER)
								.append(" Step ")
								.append((lineNum - 1))
								.append(SEPERATER)
								.append("Error: Skipping the passenger group as there is no available seat for passenger group. "
										+ line);
					}
				}
				lineNum++;
				/*sb.append(SEPERATER).append(" Step ").append((lineNum - 1))
						.append(SEPERATER).append(getSeatMap());*/
			}
			//System.out.println(sb.toString());
			return getSeatMap();

		} catch (IOException e) {
			System.out.println("Unable to read passenger file "
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * Parse the dimensions for the seat map and initialise
	 * 
	 * @param line
	 *            The String containing dimension information
	 * @param dimensions
	 *            The dimensions
	 */
	private void getDimensions(String line, String[] dimensions)
			throws IllegalArgumentException {
		if (dimensions.length < 2) {
			throw new IllegalArgumentException(
					"Unable to determine dimensions. Expected two digits with space but found : "
							+ line);
		}
		try {
			Integer numOfSeatsInARow = Integer.parseInt(dimensions[0]);
			Integer numOfRowsInPlane = Integer.parseInt(dimensions[1]);
			seatAvailable = numOfSeatsInARow * numOfRowsInPlane;
			if (seatAvailable == 0) {
				throw new IllegalArgumentException();
			}

			_initialiseSeatMap(numOfRowsInPlane, numOfSeatsInARow);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Values for dimensions must be integer from [1-9] but Found : "
							+ line);
		}
	}

	/**
	 * Initialise the seat map at the first time
	 * 
	 * @param numOfRowsInPlane
	 *            Number of the rows in the plane
	 * @param numOfSeatsInARow
	 *            Number of seats in one row
	 */
	private void _initialiseSeatMap(Integer numOfRowsInPlane,
			Integer numOfSeatsInARow) {
		for (int i = 0; i < numOfRowsInPlane.intValue(); i++) {
			List<SeatNumber> seatList = new ArrayList<>();
			for (int j = 0; j < numOfSeatsInARow.intValue(); j++) {
				boolean isWindowSeat = j == 0
						|| j == numOfSeatsInARow.intValue() ? true : false;
				seatList.add(new SeatNumber(j, i, isWindowSeat));
			}
			seatsMap.put(i, new Row(i, numOfSeatsInARow,
					windowseats, seatList));
		}
	}

	/**
	 * Assign seats for the group of passengers
	 * 
	 * @param seatRowIndex
	 *            SeatRow index which have capacity for the passengers from pax
	 *            group
	 * @param pgrp
	 *            The group of passengers
	 */
	private void seatAssignment(int seatRowIndex, PassengerGroup pgrp) {
		Row seatRow = seatsMap.get(seatRowIndex);
		List<PassengerDetail> paxList = pgrp.getSortedPaxListByWindowPref();

		int remainingPax = paxList.size();
		final int WINDOW_SEAT_1_INDEX = 0;
		final int WINDOW_SEAT_2_INDEX = seatRow.getTotalCapacity() - 1;

		List<SeatNumber> seatList = seatRow.getSeats();
		int emptySeatIndex = findEmptySeat(seatList);
		SeatNumber windowSeatLeft = seatList.get(WINDOW_SEAT_1_INDEX);
		SeatNumber windowSeatRight = seatList.get(WINDOW_SEAT_2_INDEX);

		for (PassengerDetail pax : paxList) {
			if (pax.hasWindowSeatPreference()) {
				if (windowSeatLeft.getAssignedPax() == null) {
					seatList.get(WINDOW_SEAT_1_INDEX).setAssignedPax(pax);
					emptySeatIndex++;
					remainingPax--;
					satisfiedPassenger++;
					seatRow.decreaseAvailableWindowSeatsCount();
					continue;
				} else if (windowSeatRight.getAssignedPax() == null) {
					seatList.get(WINDOW_SEAT_2_INDEX).setAssignedPax(pax);
					remainingPax--;
					satisfiedPassenger++;
					emptySeatIndex = seatRow.getTotalCapacity() - remainingPax
							- 1;
					seatRow.decreaseAvailableWindowSeatsCount();
					continue;
				}
			}
			seatList.get(emptySeatIndex++).setAssignedPax(pax);
			satisfiedPassenger++;
		}
		seatRow.setRemCapacity(seatRow.getRemCapacity() - paxList.size());
		seatsMap.put(seatRowIndex, seatRow);
	}

	/**
	 * Find the index of first empty seat in a row
	 * 
	 * @param seatList
	 *            List of seats in the row
	 * @return Index of first empty seat in the row
	 */
	private int findEmptySeat(List<SeatNumber> seatList) {
		for (SeatNumber seat : seatList) {
			if (seat.getAssignedPax() == null) {
				return seat.getSeatNumInTheRow();
			}
		}
		return -1;
	}

	/**
	 * Assign remaining empty seats for the passengers
	 * 
	 * @param pgrp
	 *            The group of passengers
	 * 
	 */
	private void emptySeatAssignment(PassengerGroup pgrp) {
		List<PassengerDetail> paxList = pgrp.getSortedPaxListByWindowPref();
		int i = 0;
		for (Row seatRow : seatsMap.values()) {
			List<SeatNumber> seatList = seatRow.getSeats();
			for (SeatNumber seat : seatList) {
				if (i == paxList.size()) {
					return;
				} else if (seat.getAssignedPax() == null) {
					PassengerDetail pax = paxList.get(i++);
					seatList.get(seat.getSeatNumInTheRow()).setAssignedPax(pax);
					if (seat.isWindowSeat() && pax.hasWindowSeatPreference()) {
						satisfiedPassenger++;
					}
				}
			}
		}
	}

	/**
	 * Get the current status of the seat map
	 * 
	 * @return Seat Map view
	 */
	private String getSeatMap() {
		StringBuilder sb = new StringBuilder();
		for (Row seatRow : seatsMap.values()) {
			sb.append(seatRow.toString()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Create the PaxGroup for the passenger's info
	 * 
	 * @param passenger
	 *            pax strings
	 * @return PaxGroup instance
	 */
	private PassengerGroup createGroup(String[] passenger) {
		List<PassengerDetail> paxList = Arrays.stream(passenger).map(String::trim)
				.filter(s -> !s.isEmpty()).map(SeatingArrangement::createPassenger)
				.collect(Collectors.toList());
		return new PassengerGroup.PassengerGroupBuilder(paxList).build();
	}

	/**
	 * Create the passenger instance for pax info
	 * 
	 * @param pax
	 *            String
	 * @return Instance of Passenger
	 */
	private static PassengerDetail createPassenger(String pax) {
		return createPassenger(pax, true);
	}

	/**
	 * Create Pax from pax string
	 * 
	 * @param pax
	 *            Pax string
	 * @param isWithGroup
	 *            If the pax is from group or not
	 * @return Passenger instance
	 */
	private static PassengerDetail createPassenger(String pax, boolean isWithGroup) {
		Matcher matcher = Pattern.compile("([0-9]*)(W)?").matcher(pax);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					"Passenger data format is incorrect, expected [0-9]*W?, received "
							+ pax);
		}
		return new PassengerDetail.PassengerBuilder(Integer.valueOf(matcher.group(1)))
				.hasWindowSeatPreference(matcher.group(2) != null)
				.isWithGroup(isWithGroup).build();
	}

	/**
	 * Find possible seat row for a pax group
	 * 
	 * @param pgrp
	 * @return Index of the seat row which have capacity to assign passenger from
	 *         PaxGroup
	 */
	private int findSeatForGroup(PassengerGroup pgrp) {
		int seatRowIndex = -1;
		for (Row seatRow : seatsMap.values()) {
			if (seatRow.getRemCapacity() >= pgrp.getTotalPassenger()) {
				if (pgrp.hasWindowSeatPrefPaxes()
						&& seatRow.getAvailableWindowSeatsCount() > 0) {
					return seatRow.getSeatRowIndex();
				} else if (!pgrp.hasWindowSeatPrefPaxes()) {
					return seatRow.getSeatRowIndex();
				}
			}
		}
		return seatRowIndex;
	}

	/**
	 * Return percentage of satisfied passengers
	 * 
	 * @return Percentage of satisfied passenger
	 */
	public int customerSatisfaction() {
		return satisfiedPassenger * 100 / totalPassenger;
	}
}