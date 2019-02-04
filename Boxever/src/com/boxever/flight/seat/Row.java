/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boxever.flight.seat;

import java.util.List;

/**
 *
 * @author Roshan
 */
public class Row {
    private int m_seatRowIndex;
	private int m_remCapacity;
	private int m_totalCapacity;
	private int m_availableWindowSeatsCount;
	private List<SeatNumber> m_seats;

	public int getSeatRowIndex() {
		return m_seatRowIndex;
	}

	public int getRemCapacity() {
		return m_remCapacity;
	}

	public void setRemCapacity(int capacity) {
		m_remCapacity = capacity;
	}

	public int getTotalCapacity() {
		return m_totalCapacity;
	}

	public int getAvailableWindowSeatsCount() {
		return m_availableWindowSeatsCount;
	}

	public void decreaseAvailableWindowSeatsCount() {
		m_availableWindowSeatsCount--;
	}

	public List<SeatNumber> getSeats() {
		return m_seats;
	}

	public void setSeats(List<SeatNumber> seats) {
		m_seats = seats;
	}

	public Row(int seatRowIndex, int totalCapacity,
			int availableWindowSeats, List<SeatNumber> seats) {
		m_seatRowIndex = seatRowIndex;
		m_remCapacity = totalCapacity;
		m_totalCapacity = totalCapacity;
		m_availableWindowSeatsCount = availableWindowSeats;
		m_seats = seats;
	}

	/**
	 * Return the view of the seat row
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SeatNumber seat : m_seats) {
			sb.append(
					seat.getAssignedPax() != null ? seat.getAssignedPax()
							.getPaxId() : 0).append(" ");
		}
		return sb.toString();
	}
}
