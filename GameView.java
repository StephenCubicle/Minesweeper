import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

    // ADD YOUR INSTANCE VARIABLES HERE
    private DotButton[][] board;
    private GameModel gameModel;
    private javax.swing.JLabel nbreOfStepsLabel, nbreOfRemMinesLabel;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        
    // ADD YOU CODE HERE
        super("MineSweeper It -- The ITI 1121 Version");
        this.gameModel = gameModel;

        int h = gameModel.getHeigth();
        int w = gameModel.getWidth();
        board = new DotButton[h][w];
        setResizable(true);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel bPanel = new JPanel();
        bPanel.setLayout (new FlowLayout(FlowLayout.CENTER, 0, 0));

        for (int j = 0; j < h; j++){
            for (int i = 0; i < w; i++){
                board[j][i] = new DotButton(j, i, board[j][i].COVERED);
                board[j][i].addActionListener(gameController);
                board[j][i].setPreferredSize(new Dimension(28,28));
                bPanel.add(board[j][i]);
            }
        }

        bPanel.setPreferredSize(new Dimension(28 * w, 28 * h));
        bPanel.setBackground (Color.pink);
        add(bPanel);

        JPanel sPanel = new JPanel();
        sPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        add(sPanel, BorderLayout.SOUTH);

        nbreOfStepsLabel = new JLabel();
        sPanel.add(nbreOfStepsLabel);

        nbreOfRemMinesLabel = new JLabel();
        sPanel.add(nbreOfRemMinesLabel);


        JButton reset = new JButton("Reset");
        reset.addActionListener(gameController);
        sPanel.add(reset);

        JButton quit = new JButton("Quit");
        quit.addActionListener(gameController);
        sPanel.add(quit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(28 * w + 40, 28 * h + 69);

        //display the window
        setLocationRelativeTo(null);
        setVisible(true);

        update();
        
    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){
        
    // ADD YOU CODE HERE
        int flags = 0;
        nbreOfStepsLabel.setText("Number of steps: " + gameModel.getNumberOfSteps());
        for (int j = 0; j < gameModel.getHeigth(); j++){
            for (int i = 0; i < gameModel.getWidth(); i++){
                if (!gameModel.get(i, j).isCovered()){
                    if (gameModel.isMined(i,j) && gameModel.get(i, j).hasBeenClicked()){
                        board[j][i].setIconNumber(board[j][i].CLICKED_MINE);
                    }
                    else if (!gameModel.isMined(i, j)){
                        board[j][i].setIconNumber(getIcon(i, j));
                    }
                    else{
                        board[j][i].setIconNumber(board[j][i].MINED);
                    }
                }
                else{
                    board[j][i].setIconNumber(board[j][i].COVERED);
                }
                if (gameModel.get(i, j).isCovered() && gameModel.get(i, j).isFlagged()){
                    board[j][i].setIconNumber(board[j][i].FLAGGED);
                    flags ++;
                }
                else if (gameModel.get(i, j).isCovered() && !gameModel.get(i, j).isFlagged()){
                    board[j][i].setIconNumber(board[j][i].COVERED);
                }
            }
        }
        nbreOfRemMinesLabel.setText("Remaining Flags: " + (gameModel.getNumberOfMines() - flags));
    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        
    // ADD YOU CODE HERE
        return gameModel.getNeighbooringMines(i, j);
    }


}
