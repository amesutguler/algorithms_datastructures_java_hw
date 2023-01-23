package abdullah_mesut_guler_hw3;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.EmptyStackException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
public class HW3 implements HW3_1Interface {
    private static int numberOfRows;
    private static int numberOfColumns;
    private int theMatrix[][] = new int[numberOfRows][numberOfColumns];
    private static int paddedMatrix[][] = new int[numberOfRows+2][numberOfColumns +2];
    private static final int dest = 32;
    private static IntArrayStack rstStack;
    private static String resultedPath;
    /**
     *
     * @param filepath
     */
    @Override
    public void read_file(String filepath) {// read file
        try{
            try (BufferedReader br = new BufferedReader(new FileReader(filepath)) // Open the file at the specified file path
            ) {
                String newLine;
                int [][] initMatrix = null;
                int numRows = 0;
                int numCols = 0;
                while ((newLine = br.readLine()) != null) {
                    String[] numbers = newLine.split(" ");
                    if (initMatrix == null){ // getting the first row of the matrix
                        numRows = 1;
                        numCols = numbers.length;
                        initMatrix = new int[numRows][numCols];
                    }
                    else { // if the first row of the matrix is already read
                        int[][] tmp = new int[numRows + 1][numCols];
                        System.arraycopy(initMatrix, 0, tmp, 0, numRows);
                        initMatrix=tmp;
                        numRows++;
                    }
                    for (int col = 0; col < numbers.length; col++) {
                        initMatrix[numRows - 1][col] = Integer.parseInt(numbers[col]);
                    }
                }   for(int m = 0; m < numRows; m++){
                    for(int n = 0; n < numCols;n++){
                        System.out.print(initMatrix[m][n] + " ");
                    }
                    System.out.println("");
                }   numberOfRows = numRows;
                numberOfColumns = numCols;
                theMatrix = initMatrix; // writing number of rows,colums and the read matrix into the fields
                // Close the file
            }
        }
        catch(IOException e){ }
    }
    /**
     *
     * @return
     */
    @Override
    public String find_path(){// find possible path
        String thePath = "";
        // create a padded matrix
        // a pad is needed because edge points cannot be evaluated otherwise
        int padMatrix[][] = new int[numberOfRows+2][numberOfColumns +2]; 
        for(int i = 0; i < numberOfRows+2; i++){
            for(int j = 0; j< numberOfColumns+2; j++){
                if((i == 0)||(i == numberOfRows+1)) {
                    padMatrix[i][j] = -1;
                }
                else if((j==0)||(j == numberOfColumns+1)){
                    padMatrix[i][j] = -1;
                }
                else{
                    padMatrix[i][j] = theMatrix[i-1][j-1];
                }           
            }
        }
        paddedMatrix = padMatrix; // padded matrix is also written into a field 
                                   // so that it can be used in other methods
                                   
        IntArrayStack myStack = new IntArrayStack(); // this stack will be passed to look_around method
        // the starting points(1s) must be find
        for(int m = 0; m < numberOfRows+2; m++){
            for(int n = 0; n < numberOfColumns+2; n++){
                if(padMatrix[m][n] == 1){
                    myStack.push((m*10 + n)); 
                    // here the if the index is 3:3 then 33 will be pushed into the stack
                    // while popping out from the stack, the row and the column
                    // will be deciphered by the theRow() and theColumn() methods
                    // check IntArrayStack below
                }
                else{
                }
            }
        }
        if((myStack.isEmpty())){ // if there is no 1 in the matrix, there is no path!
            thePath = "There is no path";
            resultedPath = "There is no path"; 
            return thePath;
        }
        rstStack =look_around(myStack); // the stack that is containing
                                                  // resulting path will be returned here
                                                  
                                                  
        if(rstStack.isEmpty()){             // if the returned stack is empty then
                                             // it means there is no path! 
            thePath = "There is no path";
            resultedPath = "There is no path";
            return thePath;
        }
        else{
        while(true){                                // the obtained path will be taken
                                                    // from the stack and put into a string
            int popped = rstStack.pop() - 11; // here -11 because of the padding
            int m = rstStack.theRow(popped);
            int n = rstStack.theColumn(popped);
            if(rstStack.isEmpty()) {
                thePath += m + ":" + n; 
            }
            else{
                thePath += m + ":" + n + "->";    
            }
            if(rstStack.isEmpty()) break;
        }
        resultedPath = thePath;
        return thePath;
        }
    }
    private static IntArrayStack look_around(IntArrayStack theStack){
        int currRow;
        int currCol;
        boolean condOfFail = false;
        boolean cond;
        IntArrayStack finalStack = new IntArrayStack(); // resulting path will be stored in this stack
        int currRow0,currCol0,currRow1,currCol1;
        while(true){
            if(theStack.isEmpty()){ // since the 1s are already on the stack and if the stack goes empty 
                condOfFail = true;  // it means there is no path!
                break;
            }
            cond = true; 
            currRow = theStack.theRow(theStack.peek());
            currCol = theStack.theColumn(theStack.peek());
            if(paddedMatrix[currRow][currCol+1] - 1 == paddedMatrix[currRow][currCol]){ // right
                theStack.push(currRow*10+(currCol+1));
                cond = false;
            }
            // each of the neighbors of the current point will be check by the following if blocks
            if(paddedMatrix[currRow+1][currCol+1] - 1 == paddedMatrix[currRow][currCol]){ // right down
                theStack.push((currRow+1)*10+(currCol+1));
                cond = false;
            }
            if(paddedMatrix[currRow+1][currCol] - 1 == paddedMatrix[currRow][currCol]){ // down
                theStack.push((currRow+1)*10 + (currCol));
                cond = false;
            }
            if(paddedMatrix[currRow+1][currCol-1] - 1 == paddedMatrix[currRow][currCol]){ // left down
                theStack.push((currRow+1)*10 + (currCol-1));
                cond = false;
            }
            if(paddedMatrix[currRow][currCol-1] - 1 == paddedMatrix[currRow][currCol]){ // left
                theStack.push((currRow)*10+(currCol-1));
                cond = false;
            }
            if(paddedMatrix[currRow-1][currCol-1] - 1 == paddedMatrix[currRow][currCol]){ // left up 
                theStack.push((currRow-1)*10 + (currCol-1));
                cond = false;
            }
            if(paddedMatrix[currRow-1][currCol] - 1 == paddedMatrix[currRow][currCol]){ // up
                theStack.push((currRow-1)*10 + (currCol));
                cond = false;
            }
            if(paddedMatrix[currRow-1][currCol+1] - 1 == paddedMatrix[currRow][currCol]){ // up right
                theStack.push((currRow-1)*10 + (currCol+1));
                cond = false;
            }
            if(paddedMatrix[currRow][currCol] == dest) break; // success condition is satisfied, break out
            if(cond){
                paddedMatrix[currRow][currCol] = -1;
                theStack.pop();    // if cond is true it means it's a dead end
                                    // this point is marked with -1 and the index of 
                                    // this point is popped out from the stack
            }
        }
        if(condOfFail) {
            return finalStack; // failure cond is satisfied 
                                // a null reference returned
        }
        else{
        finalStack.push(theStack.pop()); // the top item of theStack is pushed into finalStack
                                                  // so that it will be the a reference to next operation
        while(true){
            // since the order of the path is upside down in theStack
            // and there is a possibility of different indexes which have
            // same value and they are consequtive so they should be eliminated
            currRow0 = theStack.theRow(theStack.peek());
            currCol0 = theStack.theColumn(theStack.peek());
            currRow1 = theStack.theRow(finalStack.peek());
            currCol1 = theStack.theColumn(finalStack.peek());
            if(paddedMatrix[currRow0][currCol0] != paddedMatrix[currRow1][currCol1]){
                finalStack.push(theStack.pop());
            }
            else{
                theStack.pop();
            }
            if(theStack.isEmpty())break;
        }
        }
        return finalStack; 
    }
    /**
     *
     * @param mypath
     */
    @Override
    public void print_path(String mypath){//print the path to the screen
        System.out.println(mypath);
    }
    /**
     *
     * @param filepath
     */
    @Override
    public void print_path_to_file(String filepath){//print path to the file
      
        try{
            BufferedWriter f_writer
                = new BufferedWriter(new FileWriter(
                    filepath));
 
            f_writer.write(resultedPath);
            f_writer.close();
        
        } catch (IOException e) {
	}

        
    }
    private static class IntArrayStack {
        public int top = -1;
        private int[] data;
        private static final int DEFAULT_CAPACITY=100;
        public IntArrayStack(){
            data = new int[DEFAULT_CAPACITY];
        }
        public void push(int newItem){
            if(top == data.length - 1) resize(2 * data.length);
            data[++top] = newItem;
        }
        public int pop(){
            if(isEmpty()) throw new EmptyStackException();
            return data[top--];
        }
        public int peek(){
            if(isEmpty()) throw new EmptyStackException();
            return data[top];
        }
        public int size(){
            return top+1;
        }
        public boolean isEmpty(){
            return top <= -1;
        }
        public int theRow(int param1){
            return param1/10;
        }
        public int theColumn(int param1){
            return param1%10;
        }
        private void resize(int newSize){
            int[] newData = new int[newSize];
            System.arraycopy(data, 0, newData, 0, top);
            data = newData;
        }
    }
}
