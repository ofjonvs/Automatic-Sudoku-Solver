import java.util.LinkedList;

public class AutomaticSudokuSolver {
	
	private static final int LENGTH = 9;

	public static void main(String[] args) {
		
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

		LinkedList<int[]> prev = new LinkedList<>();
		boolean goBack = false;
		for(int i = 0; i < LENGTH; i++) {				
			for(int j = 0; j < LENGTH; j++) {
				if(sudoku[i][j] == 0 || goBack ) {		
					if(goBack) {
						i = prev.get(prev.size() - 1)[0];					
						j = prev.get(prev.size() - 1)[1];						
						prev.remove(prev.size() - 1);												
						if(insert(sudoku, sudoku[i][j] + 1, i, j)) {
							prev.add(new int[] {i, j});									
							goBack = false;						
						}
						else 
							sudoku[i][j] = 0;													
					}
					else {										
						if(insert(sudoku, 1, i, j)) 
							prev.add(new int[] {i, j});							
						
						else {
							sudoku[i][j] = 0;
							goBack = true;							
						}
					}										
				}
				else {
					goBack = false;
				}
				if(goBack) {
					if(prev.size() == 0) {
						System.out.println("unable to solve");
						return;
					}
				}
			}
		}
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
	
	
	private static boolean numberAvailableRC (int[][]sudoku, int number, int line, char type) {
		if(type == 'c') {
			for(int i = 0; i < LENGTH; i++) {
				if(sudoku[i][line] == number) {
					return false;
				}
			}
		}
		if(type == 'r') {
			for(int i = 0; i < LENGTH; i++) {
				
				if(sudoku[line][i] == number) 
					return false;				
			}
		}
		return true;
	}
	
	private static boolean numberAvailableGrid(int[][]sudoku, int number, int row, int column) {
		
		row = row - (row % 3);
		column = column - (column % 3);
		
		for(int i = row; i < row  + 3; i++) {
			for(int j = column; j < column + 3; j++) {
				if(sudoku[i][j] == number) 
					return false;				
			}
		}
		return true;
	}
	
	private static boolean insert(int[][]sudoku, int start, int row, int column) {
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


