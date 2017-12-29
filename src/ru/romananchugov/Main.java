package ru.romananchugov;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();

        Duck mallard = main.new MallardDuck();
        mallard.performFly();
        mallard.performQuack();

        Duck model = main.new ModelDuck();
        model.performQuack();
        model.performFly();
        model.setFlyBehaviour(main.new FlyRockerPowered());
        model.performFly();
    }

    public class Manock{
        QuackBehaviour quackBehaviour = new Quack();
        public void preformQuack(){
            quackBehaviour.quack();
        }
    }

    public class MallardDuck extends Duck{

        public MallardDuck(){
            quackBehaviour = new Quack();
            flyBehaviour = new FlyWithWings();
        }

        @Override
        public void display() {
            System.out.println("I'm a real mallard duck");
        }

    }

    public class ModelDuck extends Duck{

        public ModelDuck(){
            flyBehaviour = new FlyNoWay();
            quackBehaviour = new Quack();
        }

        @Override
        public void display() {
            System.out.println("I'm a model duck");
        }
    }

    public abstract class Duck{
        FlyBehaviour flyBehaviour;
        QuackBehaviour quackBehaviour;

        public abstract void display();

        public void performFly(){
            flyBehaviour.fly();
        }
        public void performQuack(){
            quackBehaviour.quack();
        }

        public void swim(){
            System.out.println("All ducks float, even decoys!");
        }

        public void setFlyBehaviour(FlyBehaviour fb){
            flyBehaviour = fb;
        }
        public void setQuackBehaviour(QuackBehaviour qb){
            quackBehaviour = qb;
        }
    }
    //flying
    public interface FlyBehaviour{
        public void fly();
    }
    public class FlyWithWings implements FlyBehaviour{
        @Override
        public void fly() {
            System.out.println("I'm flying!");
        }
    }
    public class FlyNoWay implements FlyBehaviour{

        @Override
        public void fly() {
            System.out.println("I can't fly");
        }

    }
    public class FlyRockerPowered implements FlyBehaviour{

        @Override
        public void fly() {
            System.out.println("I'm flying with a rocket");
        }
    }
    //quacking
    public interface QuackBehaviour{
        public void quack();
    }
    public class Quack implements QuackBehaviour{

        @Override
        public void quack() {
            System.out.println("Quack");
        }

    }
    public class MuteQuack implements QuackBehaviour{

        @Override
        public void quack() {
            System.out.println("Silence!");
        }

    }
    public class Squek implements QuackBehaviour{

        @Override
        public void quack() {
            System.out.println("Squek!");
        }

    }
}

