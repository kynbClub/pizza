package org.pizza.statemachine.impl;

/**
 * StateMachineException
 *
 * @author gv
 * @since 2020-02-08 5:28 PM
 */
public class StateMachineException extends RuntimeException{
    public StateMachineException(String message){
        super(message);
    }
}
