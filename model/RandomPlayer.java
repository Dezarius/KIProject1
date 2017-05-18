/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.ArrayList;

import model.PuzzleGame.action;
/**
 *
 * @author Jan Abelmann 3127890
 * @author Kristof Dinkgräve 3131770    
 */
public class RandomPlayer extends Player{

    @Override
    public List<PuzzleGame.action> solve(PuzzleGame game) {
        List<action> result = new ArrayList<>();
        
        Integer[][] currentBoard = game.getGameBoard();
        
        for(int i = 0; i<20; i++) {
            action[] allowedActions = game.getPossibleActions(currentBoard);
            int nextAction = (int) Math.ceil(Math.random()* allowedActions.length) - 1;
            action next = allowedActions[nextAction];
            currentBoard = game.computeAction(next, currentBoard);
            result.add(next);
        }
        
        return result;
    }
    
}
