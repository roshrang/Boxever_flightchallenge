package com.boxever.flight.passenger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Assert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roshan
 */
public class GroupTest {
    @Test
	public void testPaxGroupMethods() throws IOException {
		PassengerGroup passengerGroup = new PassengerGroup.PassengerGroupBuilder(_createDummyPassengerList()).build();
		
		Assert.assertNotNull(passengerGroup.getPassengerList());
		Assert.assertEquals(5, passengerGroup.getTotalPassenger());
		Assert.assertEquals(2, passengerGroup.getWindowSeatPrefPaxesCount());
		Assert.assertTrue(passengerGroup.hasWindowSeatPrefPaxes());
		
		List<PassengerDetail> sortedPassengerList =  passengerGroup.getSortedPaxListByWindowPref();
		Assert.assertNotNull(sortedPassengerList);
		Assert.assertEquals(5, sortedPassengerList.size());
		Assert.assertEquals(3, sortedPassengerList.get(0).getPaxId());
		Assert.assertEquals(5, sortedPassengerList.get(1).getPaxId());
		Assert.assertEquals(1, sortedPassengerList.get(2).getPaxId());
		Assert.assertEquals(2, sortedPassengerList.get(3).getPaxId());
		Assert.assertEquals(4, sortedPassengerList.get(4).getPaxId());
	}
	
	private List<PassengerDetail> _createDummyPassengerList(){
		List<PassengerDetail> paxList = new ArrayList<>();
		paxList.add(new PassengerDetail.PassengerBuilder(1).hasWindowSeatPreference(false).build());
		paxList.add(new PassengerDetail.PassengerBuilder(2).hasWindowSeatPreference(false).build());
		paxList.add(new PassengerDetail.PassengerBuilder(3).hasWindowSeatPreference(true).build());
		paxList.add(new PassengerDetail.PassengerBuilder(4).hasWindowSeatPreference(false).build());
		paxList.add(new PassengerDetail.PassengerBuilder(5).hasWindowSeatPreference(true).build());
		return paxList;
	}
}
