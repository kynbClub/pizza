package org.pizza.pay.bean.outbuilder;


import org.pizza.pay.bean.PayOutMessage;

/**
 * @author
 */
public class PayTextOutMessage extends PayOutMessage {

    public PayTextOutMessage() {
    }

    @Override
    public String toMessage() {
        return getContent();
    }
}
