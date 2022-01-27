package org.pizza.pay.exception;


import org.pizza.pay.bean.result.PayError;

/**
 * @author
 */
public class PayErrorException extends RuntimeException  {

    private PayError error;

    public PayErrorException(PayError error) {
        super(error.getString());
        this.error = error;
    }


    public PayError getPayError() {
        return error;
    }
}
