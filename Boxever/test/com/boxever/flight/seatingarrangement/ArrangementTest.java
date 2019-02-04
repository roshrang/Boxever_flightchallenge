package com.boxever.flight.seatingarrangement;


import com.boxever.flight.arrangement.SeatingArrangement;
import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import org.junit.Assert;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roshan
 */
public class ArrangementTest {
   @Test
	public void testBoxeverInputFile() throws IOException {
		String inputFilePath = "src/resources/test/input/fileinput.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/resources/test/output/fileoutput.txt"), "UTF-8");

		SeatingArrangement seatArrangement = new SeatingArrangement();
		String actualOutput = seatArrangement
				.passengerSeatArrangement(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(100, seatArrangement.customerSatisfaction());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDimensionsAreZero() throws IOException {
		String inputFilePath = "src/resources/test/input/fileinput1.txt";
		SeatingArrangement seatArrangement = new SeatingArrangement();
		String actualOutput = seatArrangement
				.passengerSeatArrangement(inputFilePath);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOneOfTheDimesionIsZero() throws IOException {
		String inputFilePath = "src/resources/test/input/fileinput2.txt";
		SeatingArrangement seatArrangement = new SeatingArrangement();
		String actualOutput = seatArrangement
				.passengerSeatArrangement(inputFilePath);
	}

	@Test
	public void testSeatMapDimensionOneXOne() throws IOException {
		String inputFilePath = "src/resources/test/input/fileinput3.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/resources/test/output/fileoutput3.txt"), "UTF-8");

		SeatingArrangement seatArrangement = new SeatingArrangement();
		String actualOutput = seatArrangement
				.passengerSeatArrangement(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(50, seatArrangement.customerSatisfaction());
	}

	@Test
	public void testSeatMapDimensionTwoXOne() throws IOException {
		String inputFilePath = "src/resources/test/input/fileinput4.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/resources/test/output/fileoutput4.txt"), "UTF-8");

		SeatingArrangement seatArrangement = new SeatingArrangement();
		String actualOutput = seatArrangement
				.passengerSeatArrangement(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(100, seatArrangement.customerSatisfaction());
	}

	@Test
	public void testSeatMapDimensionOneXTwoWithWindowSeatPassenger()
			throws IOException {
		String inputFilePath = "src/resources/test/input/fileinput5.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/resources/test/output/fileoutput5.txt"), "UTF-8");

		SeatingArrangement seatArrangement = new SeatingArrangement();
		String actualOutput = seatArrangement
				.passengerSeatArrangement(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(50, seatArrangement.customerSatisfaction());
	}

	@Test
	public void testInvalidDataForPassengerAtLastRow() throws IOException {
		String inputFilePath = "src/resources/test/input/fileinput6.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/resources/test/output/fileoutput6.txt"), "UTF-8");

		SeatingArrangement seatArrangement = new SeatingArrangement();
		String actualOutput = seatArrangement
				.passengerSeatArrangement(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(84, seatArrangement.customerSatisfaction());
	}

	@Test
	public void testForSeatMapWhenSemiAssignedRows() throws IOException {
		String inputFilePath = "src/resources/test/input/fileinput7.txt";
		String expectedOutput = FileUtils.readFileToString(new File(
				"src/resources/test/output/fileoutput7.txt"), "UTF-8");

		SeatingArrangement seatArrangement = new SeatingArrangement();
		String actualOutput = seatArrangement
				.passengerSeatArrangement(inputFilePath);
		Assert.assertEquals(expectedOutput, actualOutput);
		Assert.assertEquals(94, seatArrangement.customerSatisfaction());
	}

}
