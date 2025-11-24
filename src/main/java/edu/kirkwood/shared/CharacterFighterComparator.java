package edu.kirkwood.shared;

import edu.kirkwood.model.CharacterFighter;

import java.util.Objects;

public class CharacterFighterComparator {

    /*
    This is just a static method for the Character Fighter calculator due to the function being in
    the same file as the calculator for the command line project version. This is just for to make
    things easier on myself.
    */

    public static String getComparisonResults(CharacterFighter player1, CharacterFighter player2) {
        String results = null;

        // If both of the characters have the same exact parameters
        if(player1.getMoveStartup() == player2.getMoveStartup() && player1.getMoveType().equals(player2.getMoveType())
                && player1.getCharacterStatus().equals(player2.getCharacterStatus())){
            results = "Draw";
        }


        if(player1.CompareMoveTypeToOpponentStatus(player2.getCharacterStatus()).equals("Hit") &&
                player1.CompareCharacterStatusToMoveType(player2.getMoveType()).equals("Hit")){

            if(Objects.equals(player1.CompareMoveTypes(player2.getMoveType()), "Win")){
                results = "Win";
            }

            if(Objects.equals(player1.CompareMoveTypes(player2.getMoveType()), "Trade")){
                if(Objects.equals(player1.CompareMoveStartup(player2.getMoveStartup()), "Win")){
                    results = "Win";
                }
                if(Objects.equals(player1.CompareMoveStartup(player2.getMoveStartup()), "Trade")){
                    results = "Draw";
                }
                if(Objects.equals(player1.CompareMoveStartup(player2.getMoveStartup()), "Lose")){
                    results = "Lose";
                }
            }

            if(Objects.equals(player1.CompareMoveTypes(player2.getMoveType()), "Lose")){
                results = "Lose";
            }
        }

        if(player1.CompareMoveTypeToOpponentStatus(player2.getCharacterStatus()).equals("Hit") &&
                player1.CompareCharacterStatusToMoveType(player2.getMoveType()).equals("Miss")){

            if(Objects.equals(player1.CompareMoveTypes(player2.getMoveType()), "Win")){
                results = "Win";
            }
            if(Objects.equals(player1.CompareMoveTypes(player2.getMoveType()), "Trade")){
                results = "Win";
            }
            if(Objects.equals(player1.CompareMoveTypes(player2.getMoveType()), "Lose")){
                results = "Draw";
            }
        }

        if(player1.CompareMoveTypeToOpponentStatus(player2.getCharacterStatus()).equals("Miss") &&
                player1.CompareCharacterStatusToMoveType(player2.getMoveType()).equals("Hit")){

            if(Objects.equals(player2.CompareMoveTypes(player1.getMoveType()), "Win")){
                results = "Lose";
            }
            if(Objects.equals(player2.CompareMoveTypes(player1.getMoveType()), "Trade")){
                results = "Lose";
            }
            if(Objects.equals(player2.CompareMoveTypes(player1.getMoveType()), "Lose")){
                results = "Draw";
            }
        }

        // If both players miss their move then no interaction happens, and it's a draw.
        if(player1.CompareMoveTypeToOpponentStatus(player2.getCharacterStatus()).equals("Miss") &&
                player1.CompareCharacterStatusToMoveType(player2.getMoveType()).equals("Miss")){
            results = "Draw";
        }

        return results;
    }
}

