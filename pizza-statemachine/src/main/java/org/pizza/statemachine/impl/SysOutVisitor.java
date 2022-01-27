package org.pizza.statemachine.impl;

import org.pizza.statemachine.State;
import org.pizza.statemachine.StateMachine;
import org.pizza.statemachine.Transition;
import org.pizza.statemachine.Visitor;

/**
 * SysOutVisitor
 *
 * @author gv
 * @since 2020-02-08 8:48 PM
 */
public class SysOutVisitor implements Visitor {

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine) {
        String entry = "-----StateMachine:"+stateMachine.getMachineId()+"-------";
        System.out.println(entry);
        return entry;
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> stateMachine) {
        String exit = "------------------------";
        System.out.println(exit);
        return exit;
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        String stateStr = "State:"+state.getId();
        sb.append(stateStr).append(LF);
        System.out.println(stateStr);
        for(Transition transition: state.getTransitions()){
            String transitionStr = "    Transition:"+transition;
            sb.append(transitionStr).append(LF);
            System.out.println(transitionStr);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> visitable) {
        return "";
    }
}
