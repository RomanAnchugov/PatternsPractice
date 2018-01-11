package ru.romananchugov;

import java.util.ArrayList;

public class Factory {

    public static void program(){
        Factory factory = new Factory();
        PizzaStore nyStore = factory.new NYPizzaStore();
        PizzaStore chicagoStore = factory.new ChicagoPizzaStore();

         Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("Ethan order: " + pizza.getName());

        pizza = chicagoStore.orderPizza("cheese");
        System.out.println("Joel order: " + pizza.getName());
    }

    //super class for all pizzas
    public abstract class Pizza{
        String name;
        Dough dough;
        Sauce sauce;
        Veggies veggies[];
        Cheese cheese;
        Pepperoni pepperoni;
        Clams clam;



        public abstract void prepare();

        public void bake(){
            System.out.println("Bake for 25 minutes at 350");
        }

        public void cut(){
            System.out.println("Cutting pizza");
        }

        public void box(){
            System.out.println("Place pizza in official box");
        }

        public String getName(){
            return  name;
        }

        void setName(String name){
            this.name = name;
        }
    }

    //several sub pizzas
    public class NYStyleCheesePizza extends Pizza{
        public NYStyleCheesePizza (){
            name = "NY Style Sauce and Cheese Pizza";
            dough = "Thin Crust Dough";
            sauce = "Marinara sauce";

            toppings.add("Grated Reggiamo Cheese");
        }
    }

    public class ChicagoStyleCheesePizza extends Pizza{
        public ChicagoStyleCheesePizza(){
            name = "Chicago Style Deep Dish Cheese Pizza";
            dough = "Extra Thick Crust Dough";
            sauce = "Plum Tomato Sauce";

            toppings.add("Topping");
        }

        @Override
        public void cut(){
            System.out.println("Cutting into square slice");
        }
    }

    //example of simple factory(not a pattern)
//    public class SimplePizzaFactory{
//        public Pizza createPizza(String type){
//            Pizza pizza = null;
//
//            if(type.equals("cheese")){
//                pizza = new CheesePizza();
//            }else if(type.equals("pepperoni")){
//                pizza = new PepperoniPizza();
//            }else if(type.equals("clam")){
//                pizza = new ClamPizza();
//            }else if(type.equals("veggie")){
//                pizza = new VeggiePizza();
//            }
//
//            return pizza;
//        }
//    }

    //abastract of pizza
    public abstract class PizzaStore{

        public Pizza orderPizza(String type){
            Pizza pizza;
            pizza = createPizza(type);

            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
            return pizza;
        }

        abstract Pizza createPizza(String type);
    }

    public class NYPizzaStore extends PizzaStore{

        @Override
        Pizza createPizza(String type) {
            Pizza pizza = null;
            PizzaIngredientFractory pizzaIngredientFractory = new NYPizzaIngredientFactory();

            if(type.equals("cheese")){
                pizza = new CheesePizza(pizzaIngredientFractory);
                pizza.setName("new york style cheese pizza");
            }

            return pizza;
        }
    }

    public class ChicagoPizzaStore extends PizzaStore{

        @Override
        Pizza createPizza(String type) {
            Pizza pizza = null;

            if(type.equals("cheese")){
                pizza = new ChicagoStyleCheesePizza();
            }

            return pizza;
        }
    }


    //ingredients
    public interface PizzaIngredientFractory{
        public Dough createDough();
        public Sauce createSauce();
        public Cheese createCheese();
        public Veggies[] createVeggies();
        public Pepperoni createPepperoni();
        public Clams createClam();
    }

    public class NYPizzaIngredientFactory implements PizzaIngredientFractory{

        @Override
        public Dough createDough() {
            return new ThinCrustDough();
        }

        @Override
        public Sauce createSauce() {
            return new MarinaraSauce();
        }

        @Override
        public Cheese createCheese() {
            return new ReggianoCheese();
        }

        @Override
        public Veggies[] createVeggies() {
            Veggies veggies[] = {new Gralic(), new Onion(), new Mushroom(), new RedPepper()};
            return veggies;
        }

        @Override
        public Pepperoni createPepperoni() {
            return new SlicedPepperoni();
        }

        @Override
        public Clams createClam() {
            return new FreshClams();
        }
    }

    public class CheesePizza extends Pizza{

        PizzaIngredientFractory ingredientFractory;

        public CheesePizza(PizzaIngredientFractory pizzaIngredientFractory){
            this.ingredientFractory = pizzaIngredientFractory;
        }

        @Override
        public void prepare() {
            System.out.println("Preparing" + name);
            dough = ingredientFractory.createDough();
            sauce = ingredientFractory.createSauce();
            cheese = ingredientFractory.createCheese();
        }
    }

    public class ClamPizza extends Pizza{

        PizzaIngredientFractory pizzaIngredientFractory;

        public ClamPizza(PizzaIngredientFractory pizzaIngredientFractory){
            this.pizzaIngredientFractory = pizzaIngredientFractory;
        }

        @Override
        public void prepare() {
            System.out.println("Preparing " + name);
            dough = pizzaIngredientFractory.createDough();
            sauce = pizzaIngredientFractory.createSauce();
            cheese = pizzaIngredientFractory.createCheese();
            clam = pizzaIngredientFractory.createClam();
        }
    }

}
