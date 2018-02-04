package ru.romananchugov;

import java.util.ArrayList;
import java.util.Iterator;

public class CompositePattern {
    static int numberOfQuacks = 0;

    public static void program(){
        CompositePattern k = new CompositePattern();
        AbstractDuckFactory duckFactory = k.new CountingFactory();


        k.simulate(duckFactory);
    }

    private void simulate(AbstractDuckFactory duckFactory){
        Quackable mallardDuck = duckFactory.createMallardDuck();
        Quackable redheadDuck = duckFactory.createRedheadDuck();
        Quackable duckCall = duckFactory.createdDuckCall();
        Quackable rubberDuck = duckFactory.createRubberDuck();
        Quackable gooseDuck =  new GooseAdapter(new Goose());

        Flock flockOfDucks = new Flock();

        flockOfDucks.add(redheadDuck);
        flockOfDucks.add(duckCall);
        flockOfDucks.add(redheadDuck);
        flockOfDucks.add(gooseDuck);

        Flock flockOfMallards = new Flock();

        Quackable mallardOne = duckFactory.createMallardDuck();
        Quackable mallardTwo = duckFactory.createMallardDuck();
        Quackable mallardThree = duckFactory.createMallardDuck();
        Quackable mallardFour = duckFactory.createMallardDuck();

        flockOfMallards.add(mallardOne);
        flockOfMallards.add(mallardTwo);
        flockOfMallards.add(mallardThree);
        flockOfMallards.add(mallardFour);

        flockOfDucks.add(flockOfMallards);

        System.out.println("\nDuck simulator");

        simulate(mallardDuck);
        simulate(redheadDuck);
        simulate(duckCall);
        simulate(rubberDuck);
        simulate(gooseDuck);

        System.out.println("Duck simulator: Whole flock simulator");
        simulate(flockOfDucks);

        System.out.println("Duck simulator: Mallard flock simulator");
        simulate(flockOfMallards);

        System.out.println("The ducks quacked " + numberOfQuacks + " times");
    }
    private void simulate(Quackable duck){
        duck.quack();
    }

    //STRATEGY
    public interface Quackable{
        public void quack();
    }

    public class MallardDuck implements Quackable{

        @Override
        public void quack() {
            System.out.println("Quack!");
        }
    }
    public class RedheadDuck implements Quackable{

        @Override
        public void quack() {
            System.out.println("Quack!");
        }
    }
    public class DuckCall implements Quackable{

        @Override
        public void quack() {
            System.out.println("Kwak");
        }
    }
    public class RubberDuck implements Quackable{

        @Override
        public void quack() {
            System.out.println("Squeak");
        }
    }

    private class Goose{
        public void honk(){
            System.out.println("Honk");
        }
    }

    //ADAPTER
    private class GooseAdapter implements Quackable{
        Goose goose;

        public GooseAdapter(Goose goose){
            this.goose = goose;
        }

        @Override
        public void quack() {
           goose.honk();
        }
    }

    private class QuackCounter implements Quackable{
        Quackable duck;

        public QuackCounter(Quackable duck){
            this.duck = duck;
        }

        @Override
        public void quack() {
            duck.quack();
            numberOfQuacks++;
        }

        public int getQuacks(){
            return numberOfQuacks;
        }
    }

    //FACTORY
    public abstract class AbstractDuckFactory{

        public abstract Quackable createMallardDuck();
        public abstract Quackable createRedheadDuck();
        public abstract Quackable createdDuckCall();
        public abstract Quackable createRubberDuck();
    }

    public class DuckFactory extends AbstractDuckFactory{

        @Override
        public Quackable createMallardDuck() {
            return new MallardDuck();
        }

        @Override
        public Quackable createRedheadDuck() {
            return new RedheadDuck();
        }

        @Override
        public Quackable createdDuckCall() {
            return new DuckCall();
        }

        @Override
        public Quackable createRubberDuck() {
            return new RubberDuck();
        }
    }
    public class CountingFactory extends AbstractDuckFactory{

        @Override
        public Quackable createMallardDuck() {
            return new QuackCounter(new MallardDuck());
        }

        @Override
        public Quackable createRedheadDuck() {
            return new QuackCounter(new RedheadDuck());
        }

        @Override
        public Quackable createdDuckCall() {
            return new QuackCounter(new DuckCall());
        }

        @Override
        public Quackable createRubberDuck() {
            return new QuackCounter(new RubberDuck());
        }
    }

    //LINKER
    public class Flock implements Quackable{
        ArrayList quackers = new ArrayList();

        public void add(Quackable quacker){
            quackers.add(quacker);
        }

        public void quack(){
            Iterator iterator = quackers.iterator();
            while(iterator.hasNext()){
                Quackable quacker = (Quackable) iterator.next();
                quacker.quack();
            }
        }
    }
}
