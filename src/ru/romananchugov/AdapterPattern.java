package ru.romananchugov;

public class AdapterPattern {
    public static void program(){
        AdapterPattern t = new AdapterPattern();
        MallardDuck duck = t.new MallardDuck();

        WildTurkey turkey = t.new WildTurkey();
        Duck turkeyAdapter = t.new TurkeyAdapter(turkey);

        System.out.println("The turkey says...");
        turkey.gobble();
        turkey.fly();

        System.out.println("\nThe duck says");
        testDuck(duck);

        System.out.println("\nThe turkey adapter says...");
        testDuck(turkeyAdapter);

    }

    static void testDuck(Duck duck){
        duck.quack();
        duck.fly();
    }

    public interface Duck{
        public void quack();
        public void fly();
    }
    public interface Turkey{
        public void gobble();
        public void fly();
    }

    public class MallardDuck implements Duck{

        @Override
        public void quack() {
            System.out.println("Quack");
        }

        @Override
        public void fly() {
            System.out.println("I'm flying");
        }
    }

    public class WildTurkey implements Turkey{

        @Override
        public void gobble() {
            System.out.println("Gobble gobble");
        }

        @Override
        public void fly() {
            System.out.println("I'm flying a short distance");
        }
    }

    public class TurkeyAdapter implements Duck{

        Turkey turkey;

        public TurkeyAdapter(Turkey turkey){
            this.turkey = turkey;
        }

        @Override
        public void quack() {
            turkey.gobble();
        }

        @Override
        public void fly() {
            for(int i = 0; i < 5; i++){
                turkey.fly();
            }
        }
    }
    public class DuckAdapter implements Turkey{

        Duck duck;
        public DuckAdapter(Duck duck){
            this.duck = duck;
        }

        @Override
        public void gobble() {
            duck.quack();
        }

        @Override
        public void fly() {
            duck.fly();
        }
    }
}
