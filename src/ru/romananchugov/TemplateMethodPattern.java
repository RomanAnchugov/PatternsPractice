package ru.romananchugov;

public class TemplateMethodPattern {
    public static void program(){
        TemplateMethodPattern t = new TemplateMethodPattern();

        Tea tea = t.new Tea();
        tea.prepareRecipe();

        Coffee coffee = t.new Coffee();
        coffee.prepareRecipe();

    }
    public abstract class CaffeineBeverage{
        //this method is a template method
        final void prepareRecipe(){
            boilWater();
            brew();
            pourInCup();
            if(customerWantscondiments()) {
                addCondiments();
            }
        }
        private void boilWater(){
            System.out.println("Boil water");
        }
        private void pourInCup(){
            System.out.println("Pouring into cup");
        }
        abstract void brew();
        abstract void addCondiments();

        boolean customerWantscondiments(){
            return true;
        }
    }

    public class Tea extends CaffeineBeverage{

        @Override
        void brew() {
            System.out.println("Steeping the tea");
        }

        @Override
        void addCondiments() {
            System.out.println("Adding lemon");
        }

        @Override
        boolean customerWantscondiments() {
            return false;
        }
    }
    public class Coffee extends CaffeineBeverage{

        @Override
        void brew() {
            System.out.println("Dripping coffee through filter");
        }

        @Override
        void addCondiments() {
            System.out.println("Adding sugar and milk");
        }
    }
}
