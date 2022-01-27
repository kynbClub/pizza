package org.pizza.statemachine;

/**
 * Visitable
 *
 * @author gv
 * @since 2020-02-08 8:41 PM
 */
public interface Visitable {
    String accept(final Visitor visitor);
}
