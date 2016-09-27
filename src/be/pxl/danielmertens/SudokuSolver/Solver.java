package be.pxl.danielmertens.SudokuSolver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Solver {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		Solver oplosser;
		oplosser = new Solver();
		oplosser.parseInput(sc);

	}

	/**
	 * This function will read the input en write the output.
	 * @param sc Scanner to read user input
	 */
	private void parseInput(Scanner sc) {
		/*
		 * input voorbeeld:
		 * 
		 *  	8xxxxxxxx
				xx36xxxxx
				x7xx9x2xx
				x5xxx7xxx
				xxxx457xx
				xxx1xxx3x
				xx1xxxx68
				xx85xxx1x
				x9xxxx4xx
		 */
		int[][] bord = new int[9][9];

		for (int i = 0; i < bord.length; i++) {
			String regel = sc.nextLine();
			for (int j = 0; j < bord[i].length; j++) {
				char c = regel.charAt(j);
				if(Character.isDigit(c)){
					bord[i][j] = (int) c - (int) '0';
				}
				else{
					bord[i][j] = 0;
				}
			}
		}
		long startTime = System.currentTimeMillis();
		solve(bord, 0);
		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		System.out.println("Elapsed time: " + diff + " ms");
	}

	/**
	 * Method that will solve the sudoku using backtracking and recursion.
	 * @param bord The sudoku representation.
	 * @param pos Position in the sudoku.
	 * @return True if sudoku is solved.
	 */
	private boolean solve(int[][] bord, int pos){
		if(pos == 81){
			for (int i = 0; i < bord.length; i++) {
				for (int j = 0; j < bord[i].length; j++) {
					System.out.print(bord[i][j]);
				}
				System.out.println();
			}
			return true;
		}
		else{
			int rij = pos / 9;
			int kolom = pos % 9;

			if(bord[rij][kolom] == 0){
				for (int i = 1; i <= 9; i++) {
					boolean check = checkRowAndColumn(bord, rij, kolom, i);

					if(!check){
						bord[rij][kolom] = i;

						if(solve(bord, pos + 1)){
							return true;
						}
						else{
							bord[rij][kolom] = 0;
						}
					}
				}
				return false;
			}
			else{
				return solve(bord, pos + 1);
			}
		}

	}

	/**
	 * This method will check if the proposed number can be placed in the field.
	 * @param bord The sudoku field.
	 * @param rij The active row.
	 * @param kolom The active column.
	 * @param getal The proposed number.
	 * @return True if the number can not be placed in this spot. False if it can be placed in this spot.
	 */
	private boolean checkRowAndColumn(int[][] bord, int rij, int kolom, int getal) {
		for (int i = 0; i < bord[rij].length ; i++) {
			if(bord[rij][i] == getal)
				return true;
		}

		for (int i = 0; i < bord.length ; i++) {
			if(bord[i][kolom] == getal)
				return true;
		}

		int startX = (rij / 3) * 3;
		int startY = (kolom / 3) * 3;

		for (int i = 0; i < 3 ; i++) {
			for (int j = 0; j < 3 ; j++) {
				if(bord[startX + i][startY + j] == getal){
					return true;
				}
			}
		}
		return false;
	}
}