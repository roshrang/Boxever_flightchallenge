/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boxever.flight.passenger;

/**
 *
 * @author Roshan
 */
public class PassengerDetail {
    private int m_paxId;
	private int m_score;
	private int m_seatNum;
	private boolean m_isWithGroup;
	private boolean m_hasWindowSeatPreference;

	private PassengerDetail(PassengerBuilder builder) {
		m_paxId = builder.paxId;
		m_score = builder.score;
		m_seatNum = builder.seatNum;
		m_isWithGroup = builder.isWithGroup;
		m_hasWindowSeatPreference = builder.hasWindowSeatPreference;
	}

	public int getPaxId() {
		return m_paxId;
	}

	public int getScore() {
		return m_score;
	}

	public int getSeatNum() {
		return m_seatNum;
	}

	public boolean isWithGroup() {
		return m_isWithGroup;
	}

	public boolean hasWindowSeatPreference() {
		return m_hasWindowSeatPreference;
	}

	public static class PassengerBuilder {
		private int paxId;
		private int score;
		private int seatNum;
		private boolean isWithGroup;
		private boolean hasWindowSeatPreference;

		public PassengerBuilder(int paxId) {
			this.paxId = paxId;
		}

		public PassengerBuilder score(int score) {
			this.score = score;
			return this;
		}

		public PassengerBuilder seatNum(int seatNum) {
			this.seatNum = seatNum;
			return this;
		}

		public PassengerBuilder isWithGroup(boolean isWithGroup) {
			this.isWithGroup = isWithGroup;
			return this;
		}

		public PassengerBuilder hasWindowSeatPreference(
				boolean hasWindowSeatPreference) {
			this.hasWindowSeatPreference = hasWindowSeatPreference;
			return this;
		}

		public PassengerDetail build() {
			return new PassengerDetail(this);
		}

	}
}
