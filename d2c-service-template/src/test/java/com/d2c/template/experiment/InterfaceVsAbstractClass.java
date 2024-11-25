package com.d2c.template.experiment;

public class InterfaceVsAbstractClass {

    public static void main(String[] args) {
        CircleInterfaceImpl redCircleWithoutState = new CircleInterfaceImpl();
        redCircleWithoutState.setColor("RED");
        System.out.println("is red : "+redCircleWithoutState.isValid());
    }
}
