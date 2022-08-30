import java.util.LinkedList;

public class AutomaticSudokuSolver {
	
	private static final int LENGTH = 9;

	public static void main(String[] args) {
		
//		9 by 9 sudoku board
		int [][]sudoku = {
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0}
			};

//		linked list to store values when added in order to go backwards when necessary
		LinkedList<int[]> prev = new LinkedList<>();
//		boolean variable that is true when going back
		boolean goBack = false;
//		nested for loop that traverses through board
		for(int i = 0; i < LENGTH; i++) {				
			for(int j = 0; j < LENGTH; j++) {
//				when attempting to insert a number into open space
				if(sudoku[i][j] == 0 || goBack ) {		
					if(goBack) {
//						when going back retrieve last value stored in previous list
						i = prev.get(0)[0];					
						j = prev.get(0)[1];						
						prev.remove(0);
//						attempts to insert, if successful store value into prev, 
//						if not go back again and set value to 0
						if(insert(sudoku, sudoku[i][j] + 1, i, j)) {
							prev.addFirst(new int[] {i, j});									
							goBack = false;						
						}
						else 
							sudoku[i][j] = 0;													
					}
//					when normally traversing
					else {		
//						attempts to insert, if successful store value into prev, 
//						if not go back again and set value to 0
						if(insert(sudoku, 1, i, j)) 
							prev.addFirst(new int[] {i, j});							
						
						else {
							sudoku[i][j] = 0;
							goBack = true;							
						}
					}										
				}
//				when spot is occupied and not going back
				else {
					goBack = false;
				}
//				if going back but previous is empty return unable to solve
				if(goBack) {
					if(prev.size() == 0) {
						System.out.println("unable to solve");
						return;
					}
				}
			}
		}
//		after completion if a value in sudoku is 0 return unable to solve
		for(int i = 0; i <LENGTH; i++) {
			for(int j = 0; j < LENGTH; j++) {
				if(sudoku[i][j] == 0) {
					System.out.println("unable to solve");
					return;
				}
			}
		}
		
		printSudoku(sudoku);
	}
	
//	checks if number is available in row or column
	private static boolean numberAvailableRC (int[][]sudoku, int number, int line, char type) {
//		if checking column iterate through the column to check if number is found
		if(type == 'c') {
			for(int i = 0; i < LENGTH; i++) {
				if(sudoku[i][line] == number) {
					return false;
				}
			}
		}
//		if checking row iterate through row to check if number is found
		if(type == 'r') {
			for(int i = 0; i < LENGTH; i++) {
				
				if(sudoku[line][i] == number) 
					return false;				
			}
		}
		return true;
	}
	
//	checks if number is in 3 x 3 grid
	private static boolean numberAvailableGrid(int[][]sudoku, int number, int row, int column) {
//		gets to top left of grid
		row = row - (row % 3);
		column = column - (column % 3);
		
//		iterates through values of grid to check if number is found
		for(int i = row; i < row  + 3; i++) {
			for(int j = column; j < column + 3; j++) {
				if(sudoku[i][j] == number) 
					return false;				
			}
		}
		return true;
	}
	
//	inserts number into board
	private static boolean insert(int[][]sudoku, int start, int row, int column) {
//		finds lowest number to insert based on 
//		starting value, if it cant be inserted return false
		for(int i = start; i <= LENGTH; i++) {										
			if(numberAvailableRC(sudoku, i, row, 'r')
			&& numberAvailableRC(sudoku, i, column, 'c')
			&& numberAvailableGrid(sudoku, i, row, column)) {
				sudoku[row][column] = i;
				return true;
			}	
		}
		return false;
	}
	
//	prints out sudoku
	private static void printSudoku(int[][]sudoku) {
		System.out.println("------------------");
		for(int i = 0; i < LENGTH; i++) {
			for(int j = 0; j < LENGTH; j++) {
				System.out.print(sudoku[i][j]);
				if(j % 3 == 2) {
					System.out.print("|");
				}
				else
					System.out.print(" ");
			}
			System.out.println("");
			if(i % 3 == 2) {
				System.out.println("------------------");
			}
			
		}
	}
	
	
}


