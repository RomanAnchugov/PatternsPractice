package ru.romananchugov;

public class Main {

    public static void main(String[] args) {
        System.out.println("-----------DECORATOR-----------");
        Decorator.programm();
        System.out.println("-----------FACTORY(errors, just example)-----------");
        //Factory.program(); - errors, just example
        System.out.println("-----------SINGLETON(NO PROGRAM)-----------");
        System.out.println("-----------COMMAND-----------");
        CommandPattern.program();

    }




}

