package org.pizza.statemachine.builder;

/**
 * ExternalTransitionBuilder
 *
 * @author gv
 * @since 2020-02-07 6:11 PM
 */
public interface ExternalTransitionBuilder<S, E, C> {
    /**
     * Build transition source state.
     * @param stateId id of state
     * @return from clause builder
     */
    From<S, E, C> from(S stateId);

}
