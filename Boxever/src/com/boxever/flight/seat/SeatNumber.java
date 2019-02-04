/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boxever.flight.seat;
import com.boxever.flight.passenger.PassengerDetail;
/**
 *
 * @author Roshan
 */
public class SeatNumber {
    private int m_seatNumInTheRow;
	private int m_rowNum;
	private boolean m_isWindowSeat;
	private PassengerDetail m_assignedPax;

	public SeatNumber(int seatNumInTheRow, int rowNum, boolean isWindowSeat) {
		m_seatNumInTheRow = seatNumInTheRow;
		m_rowNum = rowNum;
		m_isWindowSeat = isWindowSeat;
	}

	public PassengerDetail getAssignedPax() {
		return m_assignedPax;
	}

	public int getSeatNumInTheRow() {
		return m_seatNumInTheRow;
	}

	public void setAssignedPax(PassengerDetail pax) {
		m_assignedPax = pax;
	}

	public String getSeatId() {
		return Integer.toString(m_rowNum) + Integer.toString(m_seatNumInTheRow);
	}

	public boolean isWindowSeat() {
		return m_isWindowSeat;
	}
}
