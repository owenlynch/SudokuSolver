package com.chewnylon.sudoku;
/*
 * Created on 18-Oct-2005
 * 
 */

import com.chewnylon.sudoku.exception.SudokuException;

public interface SudokuSolver {
	
	public static final Integer[] VALUES = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public static final int GRID_LENGTH = 9;
	public static final int MINIMUM_CLUES = 17;
	public static final int TOTAL_SUM_OF_GRID = 405;
	
	public int[][] getSolution(int[][] gridToSolve) throws SudokuException;
}



