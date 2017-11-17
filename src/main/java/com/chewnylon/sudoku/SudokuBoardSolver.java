package com.chewnylon.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import com.chewnylon.sudoku.exception.SudokuException;
import com.chewnylon.sudoku.exception.UnableToSolveSudokuException;

/*
 * Created November 2017 - Original created on 19-Oct-2005
 * 
 * This is an update to the original. 
 * Bugs fixed, code cleaned up and and brought up to date for a java 8 compiler.
 * 
 * The original was for a J2ME application and hence had very few API's (CLDC 1.1), hence lots of array messing.
 * 
 * Original description:
 * This Class contains the public function getSolution()
 * which takes and returns an int[][]. This should be a 9 by 9
 * Sudoku game grid with initial values in place in the 
 * format int[row][column]. The Solution is the brute force 
 * approach to solving a sudoku game and keeps iterating 
 * through the grid until all spaces are filled.
 * 
 * This solution was originally implemented in Matlab. 
 * It was then converted to Java using the Matlab code as
 * a reference 
 *
 * 
 */


public class SudokuBoardSolver implements SudokuSolver {

	public int[][] getSolution(int[][] gridToSolve) throws SudokuException {
		validateGrid(gridToSolve);
		return solve(gridToSolve.clone());
	}

	private int[][] solve(int solveme[][]) throws SudokuException {

		// Initialise everything...
		int[][] solved = new int[GRID_LENGTH][GRID_LENGTH];
		// i is to count how many itterations of the board it takes
		int iterationCount = 0;
		int candidateAttemptCount = 0;
		int[][] originalBoardState = new int[GRID_LENGTH][GRID_LENGTH];

		int candidateAttemptIndex = 0;
		int candidateAttemptRow = 0;
		int candidateAttemptColumn = 0;
	
		/*
		 * now solve in a while loop... All digits an a correctly completed
		 * Sudoku board add up to 405, So until this has been achieved the while
		 * loop will continue to iterate....
		 * 
		 */
		while (sum(solveme) < TOTAL_SUM_OF_GRID) { // whole grid repeat loop
			if (candidateAttemptCount > 1000) {
				throw new UnableToSolveSudokuException("Sorry, Can't do it...");
			}
			iterationCount++;
			int preCount = TOTAL_SUM_OF_GRID - sum(solveme);
			// check grid...
			checkGrid(solveme);
			int postCount = TOTAL_SUM_OF_GRID - sum(solveme);

			// If nothing was set on this check, we need to set a candidate and
			// try again			
			if (preCount == postCount) {
				//TODO: This block is not good enough - only ever attempts one candidate. Multiple attempts may be required in some cases. 
				//TODO: Needs a better approach! perhaps a method called recursively
				//TODO: Messy passing of variables between here and checkCandidate etc. logic needs to be encapsulated. 
				CandidateSudokuBoard candidateInfo = checkCandidate(solveme, originalBoardState, candidateAttemptIndex, 
						candidateAttemptRow, candidateAttemptColumn);

				originalBoardState = candidateInfo.getOriginal();
				// set the grid we are solving to the new grid with a possible candidate set.
				solveme = candidateInfo.getCandidateBoard();
				candidateAttemptIndex++;				
				if (candidateAttemptIndex == 4) {
					// we only look for candidates if there are 4 or less available, so reset for next row
					candidateAttemptIndex = 0;
					candidateAttemptRow = candidateAttemptColumn == 8 ? candidateAttemptRow+1 : candidateAttemptRow;
					candidateAttemptColumn = candidateAttemptColumn < 8 ? candidateAttemptColumn+1 : 0;
					if (candidateAttemptRow > 8) {
						candidateAttemptRow = 0;
					}					
					//System.out.println(candidateAttemptRow+", "+candidateAttemptColumn);
				}
				candidateAttemptCount++;
			}
		}

		// TODO:: remove system outs
		System.out.println("Total Iterations: " + iterationCount);
		System.out.println("Total Guesses: " + candidateAttemptCount);

		solved = solveme;

		return solved;
	}

	/*
	 * performs a single iteration of the 81 cells in a Sodoku grid and tries to assign values
	 */
	private void checkGrid(int[][] solveme) {
		EliminatedNums enums;
		// Loop through each cell in the board (cell for loop)
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) { // cell loop
				// don't check something we have a value for:
				if (solveme[row][col] != 0) {
					continue;
				}

				// enums will hold the start coordinates of
				// the 3x3 square for this cell and the definite
				// non possible numbers by checking the numbers
				// already in the row, column and 3x3 box for this cell
				enums = eliminate(solveme, row, col);
				// get the top left coords of the 3x3 box and the used nums
				int boxRow = enums.getStartRow();
				int boxCol = enums.getStartColumn();
				int[] eliminations = enums.getNumbers();
				// check... if there is only 1 number left to get... set it
				if (eliminations.length == 8) {
					solveme[row][col] = 45 - sum(eliminations);
					break;
				}

				if (eliminations.length >= 2) {
					// possibilities is inverse of eliminations
					int[] possibleValues = getPossibilities(eliminations);
					boolean isSet = false;
					for (int j = 0; j < possibleValues.length; j++) { // loop
																		// over
																		// possibilities
						if (isSet) {
							break;
						}
						int candidate = possibleValues[j];
						int[][] square = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } }; // an
						// empty
						// 3x3
						// box
						for (int ii = 0; ii < 3; ii++) {
							for (int jj = 0; jj < 3; jj++) { // loop over
																// the 3x3 square
																// for this
																// cell
								// neighbours
								if (solveme[boxRow + ii][boxCol + jj] == 0) {
									// get eliminations for this
									// neighbouring cell to
									// solveme[row][col]
									int[] neighboursEliminations = eliminate(solveme, boxRow + ii, boxCol + jj)
											.getNumbers();

									if (!isIntInArray(candidate, neighboursEliminations)) {
										square[ii][jj] = candidate;
									}
								}
							}
						}
						if (sum(square) == candidate) { // meaning we only find
														// one value, so try to
														// set it:
							solveme[row][col] = candidate;
							isSet = true;
						}
					}
				}
			}
		} // end of full cell iteration
	}

	// For a given cell in gridToSolve... defined by row, col this function
	// will return an EliminatedNums Class which has
	// the numbers that a cell can definitely not be
	// and the start of the 3x3 square the cell is in.
	private EliminatedNums eliminate(int[][] gridToSolve, int row, int col) {

		Set<Integer> eliminations = new HashSet<Integer>();
		EliminatedNums eliminatedNums;

		// Find numbers not possible in the rows and columns
		// go through the row and column of the given cell
		// and add already existing numbers (not 0)
		for (int i = 0; i < 9; i++) {
			// add values from the column
			if (gridToSolve[row][i] != 0) {
				eliminations.add(gridToSolve[row][i]);
			}
			// add values from the row
			if (gridToSolve[i][col] != 0) {
				eliminations.add(gridToSolve[i][col]);
			}
		}


		// find and record the top left coordinates of the 3x3 box of this cell
		int boxStartRow = 0;
		int boxStartCol = 0;
		boxStartRow = row - (row % 3);
		boxStartCol = col - (col % 3);
		// Find numbers not possible in the 3x3 box and add to eliminations
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (gridToSolve[boxStartRow + i][boxStartCol + j] != 0) {
					eliminations.add(gridToSolve[boxStartRow + i][boxStartCol + j]);
				}
			}
		}

		// reduce to a unique array of non-possible values for supplied grid
		int[] elliminatedArray = eliminations.stream().mapToInt(Number::intValue).toArray();

		eliminatedNums = new EliminatedNums(elliminatedArray, boxStartRow, boxStartCol);

		return eliminatedNums;
	}

	// sum all elements in a 1d array
	private int sum(int[] x) {
		int y = 0;
		for (int a : x) {
			y += a;
		}
		return y;
	}

	// sum all elements in a 2d array
	private int sum(int[][] arrayToSum) {
		int sum = 0;
		for (int[] innerArray : arrayToSum) {
			for (int cellValue : innerArray) {
				sum += cellValue;
			}
		}
		return sum;
	}

	/**
	 * get the possible values available; given what's not possible (the
	 * opposite of what's passed in)
	 * 
	 */
	private int[] getPossibilities(int[] usedNumbers) {

		// all possible list
		List<Integer> allPossibilitiesList = new ArrayList<Integer>();
		for (SudokuValues value : SudokuValues.values()) {
			// don't add 0
			if (value.ordinal() > 0) {
				allPossibilitiesList.add(value.ordinal());
			}
		}

		// pre-existing list
		List<Integer> usedNumbersList = new ArrayList<Integer>();
		for (int i : usedNumbers) {
			usedNumbersList.add(i);
		}

		// now remove used values form all values
		allPossibilitiesList.removeAll(usedNumbersList);

		// return possibilities as an int[] array
		return allPossibilitiesList.stream().mapToInt(i -> i).toArray();
	}

	/**
	 * Given a value, x, and an intArray, return true if value is in array, else
	 * false
	 * 
	 * @param x
	 * @param intArray
	 * @return
	 */
	private boolean isIntInArray(int x, int intArray[]) {
		return IntStream.of(intArray).anyMatch(i -> i == x);
	}
	CandidateSudokuBoard candidateBoard = new CandidateSudokuBoard();
	/*
	 * Check Candidate Function
	 *
	 * @return
	 */
	private CandidateSudokuBoard checkCandidate(int[][] toSolve, int[][] original, int candidateIndex, int candidateRow, int candidateCell) {
		
		int[][] newGrid = new int[9][9];
		// this is copying the last known value back in to toSolve
		if (sum(original) > 0) { // not the first time if original is empty.
			for (int a = 0; a < 9; a++) {
				for (int b = 0; b < 9; b++) {
					toSolve[a][b] = original[a][b];
				}
			}
		}

		// copy toSolve in to guess
		for (int a = 0; a < 9; a++) {
			for (int b = 0; b < 9; b++) {
				newGrid[a][b] = toSolve[a][b];
			}
		}
		
		for (int i = candidateRow; i < 9; i++) {
			for (int j = candidateCell; j < 9; j++) {
				int[] eliminations = eliminate(newGrid, i, j).getNumbers();
				if (eliminations.length >= 5 && newGrid[i][j] == 0) {
					// we only check candidates if there are 4 or less
					// candidates
					int[] possibleCandidate = getPossibilities(eliminations);
					candidateBoard.setOriginal(toSolve);
					candidateBoard.setCandidateBoard(newGrid);
					candidateBoard.setCandidateColumn(j);
					candidateBoard.setCandidateRow(i);
					if (candidateIndex < possibleCandidate.length) {
						// try a candidate...
						newGrid[i][j] = possibleCandidate[candidateIndex];
					}
					return candidateBoard;
				}
			}
		}
		candidateBoard.setOriginal(toSolve);
		candidateBoard.setCandidateBoard(newGrid);
		candidateBoard.setCandidateColumn(candidateRow);
		candidateBoard.setCandidateRow(candidateCell);
		return candidateBoard;
	}

	/*
	 * Check validity of provided board
	 */
	private void validateGrid(int[][] gridToSolve) throws SudokuException {
		if (gridToSolve.length != GRID_LENGTH || gridToSolve[0].length != GRID_LENGTH) {
			throw new SudokuException("Incorrect grid dimension.");
		}
		int sumTotal = sum(gridToSolve);
		if (sumTotal == 0) {
			throw new SudokuException("Empty grid.");
		}
		int prePopulatedValuesCount = 0;
		for (int r = 0; r < GRID_LENGTH; r++) {
			for (int c = 0; c < GRID_LENGTH; c++) {
				if (gridToSolve[r][c] > 0) {
					prePopulatedValuesCount++;
				}
			}
		}
		if (prePopulatedValuesCount < MINIMUM_CLUES) {
			throw new SudokuException("Not enough initial values supplied.");
		}
		
		
		// ensure all rows and columns are containing unique clue values
		for (int x = 0; x < GRID_LENGTH; x++) {
			Set<Integer> rowValues = new HashSet<Integer>();
			Set<Integer> colValues = new HashSet<Integer>();
			for (int y = 0; y < GRID_LENGTH; y++) {	
				// check row
				if(gridToSolve[x][y] > 0 && !rowValues.add(gridToSolve[x][y])) {
					throw new SudokuException("Non Unique Value in row "+(x+1));
				}
				// check column
				if(gridToSolve[y][x] > 0 && !colValues.add(gridToSolve[y][x])) {
					throw new SudokuException("Non Unique Value in column "+(y+1));
				}
		
				// check boxes
				// TODO Crude ! - only need to do this 9 times, not 81 times (i.e every cell)!
				Set<Integer> boxValues = new HashSet<Integer>();	
				int starti = 0;
				int startj = 0;		
				// find the top left of the 3x3 box of this cell
				starti = x - (x % 3);
				startj = y - (y % 3);
				// Find numbers  in the 3x3 box and add
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						int value = gridToSolve[starti + i][startj + j];
						if (value > 0 && !boxValues.add(value)) {
							throw new SudokuException("Non Unique Value,"+value+" in square at: "+(starti + i)+", "+(startj + j));
						}
					}
				}
			}
		}		
	}
}
