package org.pizza.pay.bean.outbuilder;


import org.pizza.pay.bean.PayOutMessage;

/**
 * @author
 */
public class PayJsonOutMessage extends PayOutMessage {

    public PayJsonOutMessage() {

    }

    @Override
    public String toMessage() {
        return getContent();
    }


}
