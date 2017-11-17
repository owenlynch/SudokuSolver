package com.chewnylon.sudoku;
/*
 * Created on 21-Oct-2005
 * 
 */

/**
 * A class to hold information about a Sudoku board that you are trying a
 * possible candidate value on, for when you have several choices for a cell.
 * 
 */
public class CandidateSudokuBoard {

	private int[][] candidateBoard = new int[9][9];
	private int[][] original = new int[9][9];
	private int candidateRow;
	private int candidateColumn;

	/**
	 * @return
	 */
	public int[][] getCandidateBoard() {
		return candidateBoard;
	}

	/**
	 * @return
	 */
	public int getCandidateColumn() {
		return candidateColumn;
	}

	/**
	 * @return
	 */
	public int getCandidateRow() {
		return candidateRow;
	}

	/**
	 * @return
	 */
	public int[][] getOriginal() {
		return original;
	}

	/**
	 * @param is
	 */
	public void setCandidateBoard(int[][] candidate) {
		candidateBoard = candidate;
	}

	/**
	 * @param i
	 */
	public void setCandidateColumn(int i) {
		candidateColumn = i;
	}

	/**
	 * @param i
	 */
	public void setCandidateRow(int i) {
		candidateRow = i;
	}

	/**
	 * @param is
	 */
	public void setOriginal(int[][] is) {
		original = is;
	}

	@Override
	public String toString() {
		return "CandidateSudokuBoard [candidateRow=" + candidateRow + ", candidateColumn=" + candidateColumn + "]";
	}

}
