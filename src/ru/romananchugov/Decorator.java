package ru.romananchugov;

public class Decorator {

    public static void programm(){
        Decorator decorator = new Decorator();
        Beverage beverage = decorator.new Espresso();
        System.out.println(beverage.getDescription() + " $" + beverage.cost());

        Beverage beverage1 = decorator.new DarkRoast();
        beverage1 = decorator.new Mocha(beverage1);
        beverage1 = decorator.new Mocha(beverage1);
        beverage1 = decorator.new Whip(beverage1);
        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());

        Beverage beverage2 = decorator.new HouseBlend();
        beverage2 = decorator.new Soy(beverage2);
        beverage2 = decorator.new Mocha(beverage2);
        beverage2 = decorator.new Whip(beverage2);
        System.out.println(beverage2.getDescription() + " $" + beverage2.cost());

    }

    public abstract class Beverage{
        String description = "Unknow bevarage";

        public String getDescription(){
            return description;
        }
        public abstract double cost();
    }

    public abstract class CondimentDecorator extends Beverage{
        public abstract String getDescription();
    }


    //classes of drinks
    public class Espresso extends Beverage{

        public Espresso(){
            description = "Espresso";
        }

        @Override
        public double cost() {
            return 1.99;
        }
    }

    public class HouseBlend extends Beverage{
        public HouseBlend(){
            description = "House Blend Coffee";
        }

        @Override
        public double cost() {
            return .89;
        }
    }

    public class DarkRoast extends Beverage{
        public DarkRoast(){
            description = "Dark Roast Coffee";
        }

        @Override
        public double cost() {
            return .99;
        }
    }

    public class Decaf extends Beverage {

        public Decaf(){
            description = "Decaf coffee";
        }

        @Override
        public double cost() {
            return 1.05;
        }
    }

    //classes of additions for drinks
    public class Mocha extends CondimentDecorator{
        Beverage beverage;

        public Mocha(Beverage beverage){
            this.beverage = beverage;
        }

        @Override
        public double cost() {
            return .20 + beverage.cost();
        }

        @Override
        public String getDescription() {
            return beverage.getDescription() + ", Mocha";
        }
    }

    public class Soy extends CondimentDecorator{

        Beverage beverage;

        public Soy(Beverage beverage){
            this.beverage = beverage;
        }

        @Override
        public double cost() {
            return .15 + beverage.cost();
        }

        @Override
        public String getDescription() {
            return beverage.getDescription() + ", Soy";
        }
    }

    public class Whip extends CondimentDecorator{

        Beverage beverage;
        public Whip(Beverage beverage){
            this.beverage = beverage;
        }

        @Override
        public double cost() {
            return .10 + beverage.cost();
        }

        @Override
        public String getDescription() {
            return beverage.getDescription() + ", Whip";
        }
    }
}
