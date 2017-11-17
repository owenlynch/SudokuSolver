package com.chewnylon.sudoku.original;
/*
 * Created on 19-Oct-2005
 * 
 * This Class contains the public function getSolution()
 * which takes and returns an int[][]. This should be a 9 by 9
 * Sudoku game grid with initial values in place in the 
 * format int[row][column]. The Solution is the brute force 
 * approach to solving a sudoku game and keeps iterating 
 * through the grid until all spaces are filled.
 * 
 * This solution was originally implemented in Matlab. 
 * It was copied to Java using the Matlab code
 * 
 */

import com.chewnylon.sudoku.CandidateSudokuBoard;
import com.chewnylon.sudoku.EliminatedNums;

@Deprecated
public class SudokuBoardSolverOriginal {
	
	// a 2d array to contain the Sudoku Solution
	int[][] solution;

	// taken from solve()
	int[] nonmembers;
	
	@Deprecated
	public int[][] getSolution(int[][] todo){
		solution = solve(todo);
		return solution;
	}

	private int [][] solve(int solveme[][]){
		
		// Initialise everything...
		int[][] solved = new int[9][9];
		EliminatedNums enums;
		EliminatedNums enums1;
		int[][] solvemeOriginal = new int[9][9];
		int ff = 1;
		int guessi = 1;
		int guessj = 1;
		int j = 0;
		int ii = 0;
		int jj = 0;
		CandidateSudokuBoard guess;
		int startTotal, finishTotal;
		int w,y;
		/*
		 * now solve in a while loop...
		 * All didgits an a correctly completed 
		 * Sudoku board add up to 405, So until
		 * this has been achieved the while loop
		 * will continue to itterate....
		 * 
		 */ 
		
		while(sum(solveme)<405){
			startTotal = sum(solveme);
			w = 405 - startTotal;
			// Loop through each cell in the board (cell for loop)
			for (int row = 0; row < 9 ; row++){			
				for (int col = 0 ; col < 9; col++){
					// enums will hold the start coordinates of
					// the 3x3 box for this cell and the definite 
					// non possible numbers by checking the numbers 
					// already in the row, column and 3x3 box for this cell
					enums = getNotPossible(solveme,row,col);
					// get the top left coords of the 3x3 box and the used nums
					int boxRow = enums.getStartRow();
					int boxCol = enums.getStartColumn();
					int[] usedNums = enums.getNumbers();
					// check... if there is only 1 number left to get... 
					if (usedNums.length == 8 && solveme[row][col] == 0){
						solveme[row][col] = 45 - sum(usedNums); 
						break;
					}
					
					if (usedNums.length >= 2 && solveme[row][col] == 0){
						int [] x = {1,2,3,4,5,6,7,8,9};
						nonmembers = getNonMember(x,usedNums);
						for (j = 0 ; j < nonmembers.length ; j++){
							int [][] zz = {{0,0,0},{0,0,0},{0,0,0}};
							for(ii = 0 ; ii < 3 ; ii++){
								for(jj = 0 ; jj < 3 ; jj++){
									if (solveme[boxRow+ii][boxCol+jj] == 0){
										enums1 = getNotPossible(solveme,boxRow+ii,boxCol+jj);
										int [] c_1 = enums1.getNumbers();
										zz[ii][jj] = getNonMember(nonmembers[j],c_1);
									}
								}
							}
							if (sum(zz) == nonmembers[j]){
								for (ii = 0 ; ii < 3 ; ii ++){
									for (jj = 0 ; jj <3 ; jj++){
										if(zz[ii][jj] == nonmembers[j] && solveme[row][col] == 0){
											solveme[row][col] = nonmembers[j];
										}
									}								
								}
							}							
						}
					}
				}
			}// end of cell for loop
						
			finishTotal = sum(solveme);
			y = 405-finishTotal;
			if (w == y){
				guess = guess(solveme,solvemeOriginal,ff,guessi,guessj);
				
				//solvemeOriginal = guess.getOriginal();
				solvemeOriginal = solveme;
				solveme = guess.getCandidateBoard();
				//guess.setOriginal(guess.getGuess());
				ff++;
				if (ff==5){
					ff = 1;
					guessi = guess.getCandidateRow();
					guessj = guess.getCandidateColumn()+1;
				}
			}
		}//end while
		

		solved = solveme;

		return  solved;
	}

	// For a given cell in sove me... defined by row, col this function
	// will return an EliminatedNums Class which has 
	// the numbers that a cell can definitly not be
	// and the start of the 3x3 box the cell is in.
	private EliminatedNums getNotPossible(int[][] solveme, int row, int col) {

		int ii = 0;
		int jj = 0;
		int [] temp = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int k = 0;
		EliminatedNums enums;
		
		// Find numbers not possible in the rows and colums
		// go through the row and column of the gien cell 
		// and add already existing numbers (not 0)
		for (ii = 0 ; ii < 9; ii++){
			if (solveme[row][ii] != 0){
				temp[k] = solveme[row][ii];
				k++;
			}
			if (solveme[ii][col] != 0){
				temp[k] = solveme[ii][col];
				k++;				
			}
		}
		
		int starti = 0;
		int startj = 0;
		
		//find the top left of the 3x3 box of this cell
		starti = (((row/3)+1)*3)-3;
		startj = (((col/3)+1)*3)-3;
		// Find numbers not possible in the 3x3 box
		for (ii = 0 ; ii < 3 ; ii++){
			for (jj = 0; jj < 3 ; jj++){
				if (solveme[starti+ii][startj+jj] != 0){
					temp[k] = solveme[starti+ii][startj+jj];
					k++;
				}
			}
		}
		// reduce to a non repeating array of not possible nums 
		if (k!=0){
			temp = reduce(temp, k);
		}
		enums = new EliminatedNums(temp,starti,startj); 
		
		return enums;		
	}

	// reduce an array to an orderly non repeating one
	private int[] reduce(int[] r, int size){

		int x =0;
		// first of all remove the excess 0's
		int t[] = new int[size];
		for (x = 0; x < t.length; x++){
			t[x] = r[x];
		}
	
		// Now t may still have repeating values so...
		int norepeats[] = new int[size]; // too big at first

		int counter = 0;
		// fill norepeats with unrepeating values of t
		
		int[] tminus1 = t;
		
		for(int i = 0 ; i < 9 ; i++){
			for (int j = 0 ; j < tminus1.length ; j++){
				tminus1[j] = tminus1[j]-1;
			}
			for (int j = 0 ; j < tminus1.length ; j++){
				if (tminus1[j] == 0){
					norepeats[counter] = i+1;
					counter++;
					break;
				}
			}
		}
		int[] reducedArray = new int[counter];
		for (x = 0; x < reducedArray.length; x++){
			reducedArray[x] = norepeats[x];
		}
	
		return reducedArray;
	}

	// sum all elements in a 1d array
	private int sum(int[] x){
		int y = 0;
		for (int a = 0 ; a < x.length ; a++){
				y+=x[a];
		}
		return y;
	}

	// sum all elements in a 2d array
	private int sum(int[][] x){
		int y = 0;
		for (int a = 0 ; a < x.length ; a++){
			for (int b = 0 ; b < x[a].length ; b++){
				y+=x[a][b];
			}
		}
		return y;
	}
	
	private int[] getNonMember(int[] x, int[] c){
		int i = 0;
		int t = 0;
		int temp = 55;
		for (i = 0 ; i < 9 ; i++){
			for (t = 0 ; t < c.length ; t++){
				temp = c[t] - x[i];
				if (temp == 0){
					x[i] = 0;
					break;
				}
			}
		}
		
		int nonzero = 9 - c.length;
		int count = 0;
		int nonmembers[] = new int[nonzero];
		for (i = 0; i < x.length; i++){
			if (x[i] != 0) {
				nonmembers[count] = x[i];
				count++;				
			} 
		}
		return nonmembers;
	}

	private int getNonMember(int x, int c[]){
		int t = 0;
		int temp = 55;
		for (t = 0 ; t < c.length ; t++){
			temp = c[t] - x;
			if (temp == 0){
				x = 0;
				break;
			}
			
		}
		return x;
	}
	
	private CandidateSudokuBoard guess(int[][] A, int[][] B,int f, int guessi, int guessj){
		CandidateSudokuBoard gb = new CandidateSudokuBoard();
		EliminatedNums nums;
		int[][] Q = new int[9][9];
		int [] c;
		int [] missing;
		int a, b;
		if (f+guessi+guessj > 3){
			for (a = 0 ; a < 9 ; a++){
				for (b = 0 ; b < 9 ; b++){
					A[a][b] = B[a][b];
				}
			}	
		}
		for (a = 0 ; a < 9 ; a++){
			for (b = 0 ; b < 9 ; b++){
				Q[a][b] = A[a][b];
			}
		}
		
		for (int iii = guessi ; iii < 10 ; iii++){
			for (int jjj = guessj; jjj < 10 ; jjj++){
				nums = getNotPossible(A, iii-1, jjj-1);
				c = nums.getNumbers();
				if (c.length >= 5 && Q[iii-1][jjj-1] == 0){
					int [] x = {1,2,3,4,5,6,7,8,9};
					missing = getNonMember(x,c);
					if (f > missing.length){
						gb.setOriginal(A);
						gb.setCandidateBoard(Q);
						gb.setCandidateColumn(jjj);
						gb.setCandidateRow(iii);
						return gb;						
					}
					else{
						Q[iii-1][jjj-1] = missing[f-1];
						gb.setCandidateBoard(Q);
						gb.setOriginal(A);
						gb.setCandidateColumn(jjj);
						gb.setCandidateRow(iii);
						return gb;
					}
				}
			}
		}
		return gb;
	}
}

