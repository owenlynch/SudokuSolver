package com.chewnylon.sudoku;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chewnylon.sudoku.exception.SudokuException;
import com.chewnylon.sudoku.exception.UnableToSolveSudokuException;
import com.chewnylon.sudoku.original.SudokuBoardSolverOriginal;

@SuppressWarnings("deprecation")
public class TestSudokuSolver {

	@Test
	public void testSolver() throws SudokuException {

		int[][] gameToSolve = SudokuGames.hard1;

		printGrid(gameToSolve, "Problem: hard1");

		SudokuBoardSolverOriginal oldSolver = new SudokuBoardSolverOriginal();

		long currentTimeMillis = System.currentTimeMillis();
		int[][] solved = oldSolver.getSolution(gameToSolve);

		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");

		assertEquals(9, solved.length);

		// compare
		gameToSolve = SudokuGames.hard1;
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		printGrid(sbsSolution, "Solution:");

		// assertEquals(sbsSolution, solved);

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				assertEquals(sbsSolution[x][y], solved[x][y]);
			}
		}
	}
	
	@Test
	public void testHardGrid2() throws SudokuException {
		
		int[][] solution =
			{{7,5,6,4,1,9,2,3,8},
			{8,9,1,3,2,5,4,7,6},
			{4,3,2,7,6,8,9,1,5},
			{2,6,9,5,3,4,1,8,7},
			{5,1,8,2,9,7,3,6,4},
			{3,7,4,1,8,6,5,2,9},
			{1,4,5,8,7,2,6,9,3},
			{6,2,7,9,5,3,8,4,1},
			{9,8,3,6,4,1,7,5,2}}; 
	

		System.out.println("Problem hard2:");
		printGrid(SudokuGames.hard2);
		SudokuBoardSolver sbs = new SudokuBoardSolver();

		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(SudokuGames.hard2);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				assertEquals(solution[x][y], sbsSolution[x][y]);
			}
		}
		

	}
	
	@Test
	public void testHardGrid3() throws SudokuException {
		// TODO Test something
		int[][] gameToSolve = SudokuGames.hard3;
		System.out.println("Problem hard3:");
		printGrid(gameToSolve);
		SudokuBoardSolver sbs = new SudokuBoardSolver();

		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);

	}
	@Test
	public void testHardGrid4() throws SudokuException {
		// TODO Test something
		int[][] gameToSolve = SudokuGames.hard4;
		System.out.println("Problem hard4:");
		printGrid(gameToSolve);
		SudokuBoardSolver sbs = new SudokuBoardSolver();


		System.out.println();
		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);

	}
	@Test
	public void testHardGrid5() throws SudokuException {
		// TODO Test something
		int[][] gameToSolve = SudokuGames.hard5;
		System.out.println("Problem hard5:");
		printGrid(gameToSolve);
		SudokuBoardSolver sbs = new SudokuBoardSolver();


		System.out.println();
		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);

	}
	@Test
	public void testHardGrid6() throws SudokuException {
		// TODO Test something
		int[][] gameToSolve = SudokuGames.hard6;
		System.out.println("Problem hard6:");
		printGrid(gameToSolve);
		SudokuBoardSolver sbs = new SudokuBoardSolver();


		System.out.println();
		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);
		printGrid(gameToSolve);
	}
	
	@Test
	public void testHardGrid7() throws SudokuException {
		// TODO Test something
		int[][] gameToSolve = SudokuGames.hard7;
		System.out.println("Problem hard7:");
		printGrid(gameToSolve);
		SudokuBoardSolver sbs = new SudokuBoardSolver();


		System.out.println();
		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);

	}
	
	@Test
	public void testHardGrid8() throws SudokuException {
		// TODO Test something
		int[][] gameToSolve = SudokuGames.hard8;
		System.out.println("Problem hard8:");
		printGrid(gameToSolve);
		SudokuBoardSolver sbs = new SudokuBoardSolver();


		System.out.println();
		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);

	}
	
	@Test
	public void testHardGrid9() throws SudokuException {
		// TODO Test something
		int[][] gameToSolve;
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		gameToSolve = SudokuGames.hard9;
		System.out.println("Problem hard9:");
		printGrid(gameToSolve);
		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);
	}

	@Test
	public void testCanSolveSeventeenClues() throws SudokuException{

		int [][] solution = 
			{{2,6,4,7,1,5,8,3,9},
					{1,3,7,8,9,2,6,4,5},
					{5,9,8,4,3,6,2,7,1},
					{4,2,3,1,7,8,5,9,6},
					{8,1,6,5,4,9,7,2,3},
					{7,5,9,6,2,3,4,1,8},
					{3,7,5,2,8,1,9,6,4},
					{9,8,2,3,6,4,1,5,7},
					{6,4,1,9,5,7,3,8,2}};
		
		int[][] gameToSolve = SudokuGames.seventeen_clues;
		System.out.println("Problem:");
		printGrid(gameToSolve);
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				assertEquals(solution[x][y], sbsSolution[x][y]);
			}
		}
	}
	
	@Test(expected = UnableToSolveSudokuException.class)
	//@Ignore //can't solve yet...
	public void testCanSolveSeventeenClues2() throws SudokuException{

		int[][] gameToSolve = SudokuGames.seventeen_clues2;
		System.out.println("Problem:");
		printGrid(gameToSolve);		
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		long currentTimeMillis = System.currentTimeMillis();
		int[][] sbsSolution = sbs.getSolution(gameToSolve);
		System.out.println("Run Time: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
		
		System.out.println("Solution:");
		printGrid(sbsSolution);
	}
	
	
	@Test(expected = SudokuException.class)
	public void testEmptyGrid() throws SudokuException {
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		sbs.getSolution(SudokuGames.empty);
	}
	
	@Test(expected = SudokuException.class)
	public void testIncompleteGrid() throws SudokuException {
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		sbs.getSolution(SudokuGames.incomplete);
	}
	
	@Test(expected = SudokuException.class)
	public void testInvalidGridDuplicateBoxValue() throws SudokuException {
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		sbs.getSolution(SudokuGames.duplicate_box_val);
	}
	
	@Test(expected = SudokuException.class)
	public void testInvalidGridDuplicateCol() throws SudokuException {
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		sbs.getSolution(SudokuGames.duplicate_column_val);
	}
	
	@Test(expected = SudokuException.class)
	public void testInvalidGridDuplicateRow() throws SudokuException {
		SudokuBoardSolver sbs = new SudokuBoardSolver();
		sbs.getSolution(SudokuGames.duplicate_row_val);
	}
	
	

	private void printGrid(int[][] gameToSolve, String... message) {
		for (String m : message) {
			System.out.println(m);
		}
		StringBuilder problem = new StringBuilder();
		problem.append("{");
		for (int i = 0; i < 9; i++) {
			problem.append("{");
			for (int j = 0; j < 9; j++) {
				problem.append(gameToSolve[i][j]);
				if (j < 8)
					problem.append(",");
			}
			if (i < 8)
				problem.append("},\n");
		}
		problem.append("}}");
		System.out.println(problem.toString());
	}
	
}
