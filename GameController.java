import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    // ADD YOUR INSTANCE VARIABLES HERE
    private GameModel gameModel;
    private GameView gameView;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param heigth
     *            the heigth of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int heigth, int numberOfMines) {

    // ADD YOU CODE HERE
        gameModel = new GameModel(width, heigth, numberOfMines);
        gameView = new GameView(gameModel, this);
    }

    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        
    // ADD YOU CODE HERE
        if (e.getSource() instanceof DotButton) {
            DotButton db = (DotButton) e.getSource();
            int x = db.getRow();
            int y = db.getColumn();
            if((e.getModifiers() & ActionEvent.CTRL_MASK) > 0) {
                gameModel.get(x, y).setFlagged();
                gameView.update();
            }
            else if (!gameModel.get(x, y).isFlagged()){
                play(x, y);
                if (gameModel.isBlank(x, y) && gameModel.get(x, y).hasBeenClicked() && !gameModel.get(x, y).isMined()){
                    clearZone(gameModel.get(x, y));
                }
            }
        }
        else if(e.getActionCommand().equals("Reset")){
            gameModel.reset();
            gameView.update();
        }
        else if(e.getActionCommand().equals("Quit")){
            System.exit(0);
        }
    }

    /**
     * resets the game
     */
    private void reset(){

    // ADD YOU CODE HERE
        gameModel.reset();
        gameView.update();
    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){

    // ADD YOU CODE HERE
        if (!gameModel.hasBeenClicked(width,heigth)){
            gameModel.click(width, heigth);
            gameModel.uncover(width, heigth);
            gameModel.step();
        }

        if (gameModel.isMined(width, heigth)){
            gameModel.uncoverAll();
            gameView.update();
            String[] options = { "Quit", "Play again" };
            int x = JOptionPane.showOptionDialog(null, "Aouch, you lost in " + gameModel.getNumberOfSteps() + " steps!\nWould you like to play again?", "Boom!", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (x == 0){
                System.exit(0);
            }
            else{
                reset();
            }
        }

        if (gameModel.isFinished()){
            gameModel.uncoverAll();
            gameView.update();
            String[] options = { "Quit", "Play again" };
            int x = JOptionPane.showOptionDialog(null, "Congratulations, you won in " + gameModel.getNumberOfSteps() + " steps!\nWould you like to play again?", "Won!", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (x == 0){
                System.exit(0);
            }
            else{
                reset();
            }
        }
        gameView.update();
    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {


    // ADD YOU CODE HERE
        GenericArrayStack<DotInfo> stack = new GenericArrayStack<>(1);
        DotInfo dot;

        stack.push(initialDot);

        while (!stack.isEmpty()){
            dot = stack.pop();
            for (int j = dot.getX() - 1; j < dot.getX() + 2; j++){
                for (int i = dot.getY() - 1; i < dot.getY() + 2; i++){
                    if (j >= 0 && j < gameModel.getHeigth() && i >= 0 && i < gameModel.getWidth()){
                        if (gameModel.get(i, j).isCovered() && !gameModel.get(i, j).isFlagged()){
                            gameModel.get(i, j).uncover();
                            if (gameModel.isBlank(i, j)){
                                stack.push(gameModel.get(i, j));
                            }
                        }
                    }
                }
            }
        }

        gameView.update();
    }
}
