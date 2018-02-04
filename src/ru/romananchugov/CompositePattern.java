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

        System.out.println("Ducks simulator: with observer");
        Quackologist quackologist = new Quackologist();

        flockOfDucks.registerObserver(quackologist);

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
    public interface Quackable extends QuackObservable{
        public void quack();
    }

    public class MallardDuck implements Quackable{

        Observable observable;

        public MallardDuck(){
            observable = new Observable(this);
        }

        @Override
        public void quack() {
            System.out.println("Quack!");
            notifyObservers();
        }

        @Override
        public void registerObserver(Observer observer) {
            observable.registerObserver(observer);
        }

        @Override
        public void notifyObservers() {
            observable.notifyObservers();
        }
    }
    public class RedheadDuck implements Quackable{

        @Override
        public void quack() {
            System.out.println("Quack!");
        }

        @Override
        public void registerObserver(Observer observer) {

        }

        @Override
        public void notifyObservers() {

        }
    }
    public class DuckCall implements Quackable{

        @Override
        public void quack() {
            System.out.println("Kwak");
            notifyObservers();
        }

        @Override
        public void registerObserver(Observer observer) {

        }

        @Override
        public void notifyObservers() {

        }
    }
    public class RubberDuck implements Quackable{

        @Override
        public void quack() {
            System.out.println("Squeak");
            notifyObservers();
        }

        @Override
        public void registerObserver(Observer observer) {

        }

        @Override
        public void notifyObservers() {

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
           notifyObservers();
        }

        @Override
        public void registerObserver(Observer observer) {

        }

        @Override
        public void notifyObservers() {

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
            notifyObservers();
            numberOfQuacks++;
        }

        public int getQuacks(){
            return numberOfQuacks;
        }

        @Override
        public void registerObserver(Observer observer) {
            duck.registerObserver(observer);
        }

        @Override
        public void notifyObservers() {
            //duck.notifyObservers();
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

        @Override
        public void registerObserver(Observer observer) {
            Iterator iterator = quackers.iterator();
            while(iterator.hasNext()){
                Quackable quackable = (Quackable) iterator.next();
                quackable.registerObserver(observer);
            }
        }

        @Override
        public void notifyObservers() {
        }
    }

    //OBSERVER
    public interface QuackObservable{
        public void registerObserver(Observer observer);
        public void notifyObservers();
    }

    public class Observable implements QuackObservable{
        ArrayList observers = new ArrayList();
        QuackObservable duck;

        public Observable(QuackObservable duck){
            this.duck = duck;
        }

        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void notifyObservers() {
            Iterator iterator = observers.iterator();
            while(iterator.hasNext()){
                Observer observer = (Observer) iterator.next();
                observer.update(duck);
            }
        }
    }
    public interface Observer{
        public void update(QuackObservable duck);
    }
    public class Quackologist implements Observer{

        @Override
        public void update(QuackObservable duck) {
            System.out.println("Quackologist: " + duck + "just quacked");
        }
    }
}
