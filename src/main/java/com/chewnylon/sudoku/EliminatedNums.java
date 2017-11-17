package com.chewnylon.sudoku;
/*
 * Created on 19-Oct-2005
 * 
 * for a cell, this will hold the top left coordinates of the box it is within,
 * as well as an array of numbers that are not possible for the cell (eliminations)  
 * 
 */


public class EliminatedNums {
	
	int[] numbers;
	int startRow;
	int startColumn;
	
	
	public EliminatedNums(int[] n, int sr, int sc){
		numbers = n;
		startRow = sr;
		startColumn = sc;
	}

	/**
	 * @return
	 */
	public int[] getNumbers() {
		return numbers;
	}

	/**
	 * @return
	 */
	public int getStartColumn() {
		return startColumn;
	}

	/**
	 * @return
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * @param is
	 */
	public void setNumbers(int[] is) {
		numbers = is;
	}

	/**
	 * @param i
	 */
	public void setStartColumn(int i) {
		startColumn = i;
	}

	/**
	 * @param i
	 */
	public void setStartRow(int i) {
		startRow = i;
	}

}
