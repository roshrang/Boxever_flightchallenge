/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boxever.flight.passenger;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Roshan
 */
public class PassengerGroup {
    private List<PassengerDetail> passengerList;

	private PassengerGroup(PassengerGroupBuilder builder) {
		passengerList = builder.passengerList;
	}

	/**
	 * Get total number of passengers in the pax group
	 * 
	 * @return Total no. of passengers
	 */
	public int getTotalPassenger() {
		return passengerList != null ? passengerList.size() : 0;
	}

	/**
	 * Return count of passengers having window seat preference
	 * 
	 * @return Window seat preference pax count
	 */
	public int getWindowSeatPrefPaxesCount() {
		return (int) (getTotalPassenger() > 0 ? passengerList.stream()
				.filter(p -> p.hasWindowSeatPreference()).count() : 0);
	}

	/**
	 * Return if PaxGroup contains WindowSeat Preference Passenger
	 * 
	 * @return True If pax group contains the window seat preference pax
	 *         otherwise false
	 */
	public boolean hasWindowSeatPrefPaxes() {
		return getWindowSeatPrefPaxesCount() > 0 ? true : false;
	}

	public List<PassengerDetail> getPassengerList() {
		return passengerList;
	}

	/**
	 * Sort the passenger list by Window Seat preferred passenger first and then other
	 * passenger
	 * 
	 * @return Sorted list of passengers
	 */
	public List<PassengerDetail> getSortedPaxListByWindowPref() {
		List<PassengerDetail> list = new ArrayList<>();
		List<PassengerDetail> listWithOutWindowPref = new ArrayList<>();
		if (passengerList != null && hasWindowSeatPrefPaxes()) {
			for (PassengerDetail pax : passengerList) {
				if (pax.hasWindowSeatPreference()) {
					list.add(pax);
				} else {
					listWithOutWindowPref.add(pax);
				}
			}
			list.addAll(listWithOutWindowPref);
			return list;
		}
		return passengerList;
	}

	public static class PassengerGroupBuilder {

		private List<PassengerDetail> passengerList;

		public PassengerGroupBuilder(List<PassengerDetail> passengerList) {
			this.passengerList = passengerList;
		}

		public PassengerGroupBuilder passengerList(List<PassengerDetail> passengerList) {
			this.passengerList = passengerList;
			return this;
		}

		public PassengerGroup build() {
			return new PassengerGroup(this);
		}
	}
}
