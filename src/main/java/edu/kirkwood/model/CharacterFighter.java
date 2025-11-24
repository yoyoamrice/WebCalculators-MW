package edu.kirkwood.model;

import java.util.*;

/**
 * Represents a character in a fighting game and the move
 * that they are going to do for the calculation against another
 * character with a different or same move.
 */
public class CharacterFighter{
    private int moveStartup;
    private String characterStatus;
    private String moveType;


    /**
     *  A default constructor for a character and their move type
     */
    public CharacterFighter(){
        moveStartup = 10;
        characterStatus = "Stand";
        moveType = "Medium";
    }

    /**
     * Constructs a character and the move they are going to do.
     *
     * @param moveStartup The characters move startup until hit
     * @param characterStatus What the status of the character is doing when it does the move
     * @param moveType The type of move that the character is using.
     */
    public CharacterFighter(int moveStartup, String characterStatus, String moveType){
        this.moveStartup = moveStartup;
        this.characterStatus = characterStatus;
        this.moveType = moveType;
    }

    /**
     * Gets the move startup of the move the character is doing
     *
     * @return int for the move startup
     */
    public int getMoveStartup() {
        return moveStartup;
    }

    /**
     * Set the move startup for the character
     *
     * @param moveStartup the new value for moveStartup
     */
    public void setMoveStartup(int moveStartup) {
        this.moveStartup = moveStartup;
    }

    /**
     * Gets the character status of the character when they do the move
     *
     * @return the character's status
     */
    public String getCharacterStatus() {
        return characterStatus;
    }

    /**
     * Sets the character's status
     *
     * @param characterStatus the new value for characterStatus
     */
    public void setCharacterStatus(String characterStatus) {
        this.characterStatus = characterStatus;
    }

    /**
     * Gets the move type the character will do
     *
     * @return the character's move type
     */
    public String getMoveType() {
        return moveType;
    }

    /**
     * Set the character's move type
     *
     * @param moveType the new move type
     */
    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    /**
     * Compares the move startup of two characters. If one is less than the other, then that character wins.
     *
     * @param OtherMoveStartup The other player's move startup
     * @return the comparison results for move startups
     */
    public String CompareMoveStartup(int OtherMoveStartup){
        //System.out.println("Player 1 " + this.moveStartup + " Player 2 " + OtherMoveStartup);
        String result;
        if(this.moveStartup < OtherMoveStartup) {
            result = "Win";
        } else if (this.moveStartup > OtherMoveStartup) {
            result = "Lose";
        } else {
            result = "Trade";
        }
        //System.out.println(result);
        return result;
    }

    /**
     * Compares the move type of player 1 to the other players Character Status and if it hit or not.
     * Move Types are High, Medium, Low, Command Grab, Throw.
     * Character Statuses are Stand, Crouch, downed.
     *
     * @param OtherCharacterStatus The other player's move type
     * @return the comparision results for move types
     */
    public String CompareMoveTypeToOpponentStatus(String OtherCharacterStatus){
        //System.out.println("Player 1 " + this.moveType + " Player 2 " + OtherCharacterStatus);
        String result = "null";
        if (Objects.equals(this.moveType, "high") && Objects.equals(OtherCharacterStatus, "stand")) {
            result = "Hit";
        }
        if (Objects.equals(this.moveType, "medium") && Objects.equals(OtherCharacterStatus, "stand")) {
            result = "Hit";
        }
        if (Objects.equals(this.moveType, "low") && Objects.equals(OtherCharacterStatus, "stand")) {
            result = "Hit";
        }
        if (Objects.equals(this.moveType, "command grab") && Objects.equals(OtherCharacterStatus, "stand")) {
            result = "Hit";
        }
        if (Objects.equals(this.moveType, "throw") && Objects.equals(OtherCharacterStatus, "stand")) {
            result = "Hit";
        }
        if (Objects.equals(this.moveType, "high") && Objects.equals(OtherCharacterStatus, "crouch")) {
            result = "Miss";
        }
        if (Objects.equals(this.moveType, "medium") && Objects.equals(OtherCharacterStatus, "crouch")) {
            result = "Hit";
        }
        if (Objects.equals(this.moveType, "low") && Objects.equals(OtherCharacterStatus, "crouch")) {
            result = "Hit";
        }
        if (Objects.equals(this.moveType, "command grab") && Objects.equals(OtherCharacterStatus, "crouch")) {
            result = "Miss";
        }
        if (Objects.equals(this.moveType, "throw") && Objects.equals(OtherCharacterStatus, "crouch")) {
            result = "Miss";
        }
        if (Objects.equals(this.moveType, "high") && Objects.equals(OtherCharacterStatus, "downed")) {
            result = "Miss";
        }
        if (Objects.equals(this.moveType, "medium") && Objects.equals(OtherCharacterStatus, "downed")) {
            result = "Miss";
        }
        if (Objects.equals(this.moveType, "low") && Objects.equals(OtherCharacterStatus, "downed")) {
            result = "Miss";
        }
        if (Objects.equals(this.moveType, "command grab") && Objects.equals(OtherCharacterStatus, "downed")) {
            result = "Miss";
        }
        if (Objects.equals(this.moveType, "throw") && Objects.equals(OtherCharacterStatus, "downed")) {
            result = "Miss";
        }
        //System.out.println(result);
        return result;

    }

    /**
     * Compares the move types of both characters and the result of it from player 1's perspective
     *
     * @param OtherCharacterMoveType The move type of the other player
     * @return The comparison result between the two moves
     */
    public String CompareMoveTypes(String OtherCharacterMoveType){
        //System.out.println("Player 1 " + this.moveType + " Player 2 " + OtherCharacterMoveType);
        String result = "null";
        if(Objects.equals(this.moveType, "high") && Objects.equals(OtherCharacterMoveType, "high")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "medium") && Objects.equals(OtherCharacterMoveType, "high")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "low") && Objects.equals(OtherCharacterMoveType, "high")) {
            result = "Win";
        }
        if(Objects.equals(this.moveType, "command grab") && Objects.equals(OtherCharacterMoveType, "high")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "throw") && Objects.equals(OtherCharacterMoveType, "high")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "high") && Objects.equals(OtherCharacterMoveType, "medium")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "medium") && Objects.equals(OtherCharacterMoveType, "medium")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "low") && Objects.equals(OtherCharacterMoveType, "medium")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "command grab") && Objects.equals(OtherCharacterMoveType, "medium")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "throw") && Objects.equals(OtherCharacterMoveType, "medium")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "high") && Objects.equals(OtherCharacterMoveType, "low")) {
            result = "Lose";
        }
        if(Objects.equals(this.moveType, "medium") && Objects.equals(OtherCharacterMoveType, "low")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "low") && Objects.equals(OtherCharacterMoveType, "low")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "command grab") && Objects.equals(OtherCharacterMoveType, "low")) {
            result = "Lose";
        }
        if(Objects.equals(this.moveType, "throw") && Objects.equals(OtherCharacterMoveType, "low")) {
            result = "Lose";
        }
        if(Objects.equals(this.moveType, "high") && Objects.equals(OtherCharacterMoveType, "command grab")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "medium") && Objects.equals(OtherCharacterMoveType, "command grab")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "low") && Objects.equals(OtherCharacterMoveType, "command grab")) {
            result = "Win";
        }
        if(Objects.equals(this.moveType, "command grab") && Objects.equals(OtherCharacterMoveType, "command grab")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "throw") && Objects.equals(OtherCharacterMoveType, "command grab")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "high") && Objects.equals(OtherCharacterMoveType, "throw")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "medium") && Objects.equals(OtherCharacterMoveType, "throw")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "low") && Objects.equals(OtherCharacterMoveType, "throw")) {
            result = "Win";
        }
        if(Objects.equals(this.moveType, "command grab") && Objects.equals(OtherCharacterMoveType, "throw")) {
            result = "Trade";
        }
        if(Objects.equals(this.moveType, "throw") && Objects.equals(OtherCharacterMoveType, "throw")) {
            result = "Trade";
        }
        //System.out.println(result);
        return result;
    }

    /**
     * Compares player 1's status and the move type that the other player is using and see's if player 1 gets hit or not.
     *
     * @param OtherCharacterMoveType The other characters move type
     * @return the result of what happens to the player 1's character
     */
    public String CompareCharacterStatusToMoveType(String OtherCharacterMoveType){
        //System.out.println("Player 1 " + this.characterStatus + " Player 2 " + OtherCharacterMoveType);
        String result = "null";
        if(Objects.equals(this.characterStatus, "stand") && Objects.equals(OtherCharacterMoveType, "high")) {
            result = "Hit";
        }
        if(Objects.equals(this.characterStatus, "stand") && Objects.equals(OtherCharacterMoveType, "medium")) {
            result = "Hit";
        }
        if(Objects.equals(this.characterStatus, "stand") && Objects.equals(OtherCharacterMoveType, "low")) {
            result = "Hit";
        }
        if(Objects.equals(this.characterStatus, "stand") && Objects.equals(OtherCharacterMoveType, "command grab")) {
            result = "Hit";
        }
        if(Objects.equals(this.characterStatus, "stand") && Objects.equals(OtherCharacterMoveType, "throw")) {
            result = "Hit";
        }
        if(Objects.equals(this.characterStatus, "crouch") && Objects.equals(OtherCharacterMoveType, "high")) {
            result = "Miss";
        }
        if(Objects.equals(this.characterStatus, "crouch") && Objects.equals(OtherCharacterMoveType, "medium")) {
            result = "Hit";
        }
        if(Objects.equals(this.characterStatus, "crouch") && Objects.equals(OtherCharacterMoveType, "low")) {
            result = "Hit";
        }
        if(Objects.equals(this.characterStatus, "crouch") && Objects.equals(OtherCharacterMoveType, "command grab")) {
            result = "Miss";
        }
        if(Objects.equals(this.characterStatus, "crouch") && Objects.equals(OtherCharacterMoveType, "throw")) {
            result = "Miss";
        }
        if(Objects.equals(this.characterStatus, "downed") && Objects.equals(OtherCharacterMoveType, "high")) {
            result = "Miss";
        }
        if(Objects.equals(this.characterStatus, "downed") && Objects.equals(OtherCharacterMoveType, "medium")) {
            result = "Miss";
        }
        if(Objects.equals(this.characterStatus, "downed") && Objects.equals(OtherCharacterMoveType, "low")) {
            result = "Miss";
        }
        if(Objects.equals(this.characterStatus, "downed") && Objects.equals(OtherCharacterMoveType, "command grab")) {
            result = "Miss";
        }
        if(Objects.equals(this.characterStatus, "downed") && Objects.equals(OtherCharacterMoveType, "throw")) {
            result = "Miss";
        }
        //System.out.println(result);
        return result;
    }
}


