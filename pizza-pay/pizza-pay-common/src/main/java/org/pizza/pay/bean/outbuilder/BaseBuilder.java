package org.pizza.pay.bean.outbuilder;


import org.pizza.pay.bean.PayOutMessage;

/**
 * source chanjarster/weixin-java-tools
 *
 * @author
 */
public abstract class BaseBuilder<BuilderType, ValueType> {


    public abstract ValueType build();

    public void setCommon(PayOutMessage m) {

    }

}