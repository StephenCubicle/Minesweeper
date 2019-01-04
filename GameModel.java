import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighboring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {


     // ADD YOUR INSTANCE VARIABLES HERE
	 private java.util.Random generator = new java.util.Random();
	 
	 private int numberOfMines, numberOfSteps, numberUncovered, widthOfGame, heigthOfGame;
	 
	 private DotInfo[][] model;

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        
		// ADD YOU CODE HERE
		
		this.widthOfGame = width;
		this.heigthOfGame = heigth;
		this.numberOfMines = numberOfMines;
		this.numberUncovered = 0;
		this.numberOfSteps = 0;
		
		model = new DotInfo[heigthOfGame][widthOfGame];
		int[][] mineArray = new int[numberOfMines][2];
		int[][] incArray = new int[heigthOfGame][widthOfGame];
		int x = 0, y = 0, c;
        boolean flag = true;
		
        for (int j = 0; j < heigthOfGame; j++){
            for (int i = 0; i < widthOfGame; i++){
                model[j][i] = new DotInfo(j, i);
            }
        }

        for (int i = 0; i < numberOfMines; i++){
            mineArray[i][0] = -1;
            mineArray[i][1] = -1;
        }

        // Generates unique random mine locations
		for (int i = 0; i < numberOfMines; i++){ 

            flag = true;

            // Ensures unique random mine locations
            while (flag){
                c = 0;
                x = generator.nextInt(widthOfGame - 1);
                y = generator.nextInt(heigthOfGame - 1);
                for (int j = 0; j < mineArray.length; j++){
                    if((mineArray[j][0] != x) || (mineArray[j][1] != y)){
                        c++;
                    }
                }
                if (c == mineArray.length){
                    flag = false;
                }
            }
            model[y][x].setMined();
            mineArray[i][0] = x;
            mineArray[i][1] = y;
		}

		for (int k = 0; k < mineArray.length; k++){
			for (int j = mineArray[k][1] - 1; j < mineArray[k][1] + 2; j++){

				for (int i = mineArray[k][0] - 1; i < mineArray[k][0] + 2; i++){
					if (i >= 0 && i <= widthOfGame && j>= 0 && j <= heigthOfGame){
                        if (!isMined(i, j)){
                           incArray[j][i]++; 
                        }
					}

				}

			}

		}

		for (int j = 0; j < heigthOfGame; j++){
			for (int i = 0; i < widthOfGame; i++){
				model[j][i].setNeighbooringMines(incArray[j][i]);
			}
		}
    }


 
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){

        this.numberUncovered = 0;
        this.numberOfSteps = 0;
        
        model = new DotInfo[heigthOfGame][widthOfGame];
        int[][] mineArray = new int[numberOfMines][2];
        int[][] incArray = new int[heigthOfGame][widthOfGame];
        int x = 0, y = 0, c;
        boolean flag = true;
        
        for (int j = 0; j < heigthOfGame; j++){
            for (int i = 0; i < widthOfGame; i++){
                model[j][i] = new DotInfo(j, i);
            }
        }
        for (int i = 0; i < numberOfMines; i++){
            mineArray[i][0] = -1;
            mineArray[i][1] = -1;
        }

        // Generates unique random mine locations
        for (int i = 0; i < numberOfMines; i++){ 

            flag = true;

            // Ensures unique random mine locations
            while (flag){
                c = 0;
                x = generator.nextInt(widthOfGame - 1);
                y = generator.nextInt(heigthOfGame - 1);
                for (int j = 0; j < mineArray.length; j++){
                    if((mineArray[j][0] != x) || (mineArray[j][1] != y)){
                        c++;
                    }
                }
                if (c == mineArray.length){
                    flag = false;
                }
            }
            model[y][x].setMined();
            mineArray[i][0] = x;
            mineArray[i][1] = y;
        }

        for (int k = 0; k < mineArray.length; k++){
                
            for (int j = mineArray[k][1] - 1; j < mineArray[k][1] + 2; j++){

                for (int i = mineArray[k][0] - 1; i < mineArray[k][0] + 2; i++){

                    if (i >= 0 && i <= widthOfGame && j>= 0 && j <= heigthOfGame){
                        incArray[j][i]++;
                    }

                }

            }

        }

        for (int j = 0; j < heigthOfGame; j++){
            for (int i = 0; i < widthOfGame; i++){
                model[j][i].setNeighbooringMines(incArray[j][i]);
            }
        }


    }


    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        
		// ADD YOU CODE HERE
		return heigthOfGame;

    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
		// ADD YOU CODE HERE
		return widthOfGame;

    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        
    // ADD YOU CODE HERE
	
	return model[j][i].isMined();
    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        
    // ADD YOU CODE HERE
	
	return model[j][i].hasBeenClicked();

    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighbor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        
		// ADD YOU CODE HERE
        if (getNeighbooringMines(i, j) == 0){
			return true;
		}
		return false;
    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        
    // ADD YOU CODE HERE
    	return model[j][i].isCovered();
    }

    /**
     * returns the number of neighboring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        
    // ADD YOU CODE HERE
    	return model[j][i].getNeighbooringMines();
    }

    /**
     * returns the number of mines
     *
     * @return the number of mines
     */   
    public int getNumberOfMines(){
        return numberOfMines;
    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        
    // ADD YOU CODE HERE
    	model[j][i].uncover();
    	numberUncovered++;

    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        
    // ADD YOU CODE HERE
    	model[j][i].click();
    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        
    // ADD YOU CODE HERE
	    for (int j = 0; j < getHeigth(); j++){
    		for (int i = 0; i < getWidth(); i++){
                model[j][i].uncover();
    		}
    	}
    	numberUncovered = getHeigth() * getWidth();	
    }

 

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        
    // ADD YOU CODE HERE
    	return numberOfSteps;
    }

  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        
    // ADD YOU CODE HERE
    	return model[j][i];
    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        
    // ADD YOU CODE HERE
     	numberOfSteps++;

    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        
    // ADD YOU CODE HERE
    	for (int j = 0; j < getHeigth(); j++){
    		for (int i = 0; i < getWidth(); i++){
    			if (model[j][i].isCovered() == true && model[j][i].isMined() == false){
    				return false;
    			}
    		}
    	}
    	return true;
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
	    // ADD YOU CODE HERE
	    return "Size of the board: " + getWidth() + " x " + getHeigth() + "\nNumber of Steps: " + getNumberOfSteps() + "\nTotal number of mines: " + numberOfMines + "\nUncovered dots: " + numberUncovered;
    }
}
