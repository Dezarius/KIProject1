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
        List<Integer[][]> boards = new ArrayList<>();
        Integer[][] currentBoard = game.getGameBoard();
        boards.add(currentBoard);
        
        result = findSolution(game,boards, result, false);
        
        return result;
    }
    
    private int i = 0;
    
    private List<PuzzleGame.action> findSolution(PuzzleGame game, List<Integer[][]> frontiers, List<PuzzleGame.action> actions, boolean solved) {
        Integer[][] currentBoard = Utility.deepCopyIntegerArray(frontiers.get(0));
        action[] allowedActions = game.getPossibleActions(currentBoard);
        for(action ac : allowedActions) {
            Integer[][] computedBoard = game.computeAction(ac, currentBoard);
            frontiers.add(computedBoard);
            
            /* Ausgabe von Anzahl der ausgeführten 
            i++;
            System.out.println(i);
            */
            
            /* Ausgabe vom vorgehen
            System.out.println(ac.name());
            System.out.println(game.boardToString(computedBoard));
            */
            
            
            if(game.isSolution(computedBoard)) {
                System.out.println("Lösung gefunden!");
                System.out.print(game.boardToString(currentBoard));
                /*actions.add(0, ac);
                solved = true; */
                return actions;
            }
        }
        frontiers.remove(0);
        findSolution(game,frontiers,actions,solved);

        return actions;
    }
    
    
    
}
