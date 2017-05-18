/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.ArrayList;
import model.Utility;
import model.PuzzleGame.action;



/**
 *
 * @author Jan Abelmann
 */
public class BFSPlayer extends Player {
    
    @Override
    public List<PuzzleGame.action> solve(PuzzleGame game) {
        List<action> result = new ArrayList<>();
        List<Integer[][]> root = new ArrayList<>();
        Integer[][] currentBoard = game.getGameBoard();
        root.add(currentBoard);
        visitedBoards.add(currentBoard);
        
        
        System.out.println("Start:");
        System.out.println(game.boardToString(currentBoard));
        
        result = findSolution(game,root, result, false);
        
        return result;
    }
    
    private int i = 0;
    List<Integer[][]> visitedBoards = new ArrayList<>();
    
    private List<PuzzleGame.action> findSolution(PuzzleGame game, List<Integer[][]> frontiers, List<PuzzleGame.action> actions, boolean solved) {
        Integer[][] currentBoard = Utility.deepCopyIntegerArray(frontiers.get(0));
        action[] allowedActions = game.getPossibleActions(currentBoard);
        for(action ac : allowedActions) {
            Integer[][] computedBoard = Utility.deepCopyIntegerArray(game.computeAction(ac, currentBoard));
            if(!isBoardAllreadyVisited(computedBoard)) {
                visitedBoards.add(Utility.deepCopyIntegerArray(computedBoard));
            
                /* Ausgabe von Anzahl der ausgeführten */
                i++;
                System.out.println(i);
                
                /* Ausgabe vom vorgehen */
                //System.out.println(ac.name());
                //System.out.println(game.boardToString(computedBoard));
            
                
                
                frontiers.add(computedBoard);
                if(game.isSolution(computedBoard)) {
                    System.out.println("Lösung gefunden!");
                    System.out.print(game.boardToString(computedBoard));
                    //actions.add(0, ac);
                    solved = true; 
                    return actions;
                }
            } 
        }
        frontiers.remove(0);
        if(!solved)
            findSolution(game,frontiers,actions,solved);

        return actions;
    }
    
    private boolean isBoardAllreadyVisited(Integer[][] board) {
        for(Integer[][] b : visitedBoards) {
            if(b[0][0] == board[0][0] && b[0][1] == board[0][1] && b[0][2] == board[0][2]) {
                if(b[1][0] == board[1][0] && b[1][1] == board[1][1] && b[1][2] == board[1][2]) {
                    if((b[2][0] == board[2][0] && b[2][1] == board[2][1] && b[2][2] == board[2][2])) {
                        return true;
                    }
                }  
            }
        }
        return false;
    }
}
