package com.AdiAyush.EZPZ.Lexer.FSM;

import java.util.List;

import com.AdiAyush.EZPZ.Lexer.Util.Pair;

public class FiniteStateMachine{
    /*
        We use Finite State Machines to help dictate whether or not
        certain tokens belong within the grammar of our language.
        Example:
            123456_*()124 does not belong in our language. We would throw an error.
            1234567891234 is a number within our language and fits the grammar of the language. We accept this.
    */
    State currentState;
    List<State> acceptingStates;
    List<State> allStates;
    MoveState transitionFunction;

    public FiniteStateMachine(State currentState, List<State> acceptingStates, List<State> allStates, MoveState moveState){
        this.currentState = currentState;
        this.acceptingStates = acceptingStates;
        this.allStates = allStates;
        this.transitionFunction = moveState;
    }

    /**
     * Tests the current input (a token) and whether it fits within the grammar of the language.
     * @param input
     * @return
     */
    public Pair<State, String> testInput(String input){
        //Here, the current state is the "start State" of the code
        char[] chars = input.toCharArray(); //splitting into character array for faster processing of longer lines of code
        StringBuilder output = new StringBuilder();
        State tempState = currentState;

        for(int x = 0; x < chars.length; x++){  
            if(chars[x] == ' ' || chars[x] == ';') break; // We break out of the for loop here if we reach the end of the current token, which will occur at spaces or semicolons.
            tempState = transitionFunction.moveState(tempState, allStates, chars[x]);
            if(tempState.getStateName().equals("Invalid")){
                while( (chars[x] != ' ' && chars[x] != ';')  && x < input.length()-1) {
                    output.append(chars[x]);
                    x++;
                }
                break;
            } // If the state from the transition function is invalid, we break from the for loop.
            else output.append(chars[x]); //Otherwise, if the state was valid, we append the character tested to the output.
        }

        return new Pair<State, String>(tempState, output.toString());
        
    }
}
