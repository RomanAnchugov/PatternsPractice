package ru.romananchugov;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import java.util.function.Consumer;

public class LinkerPattern {
    public static void program(){
        LinkerPattern k = new LinkerPattern();
        MenuComponent pancakeHouseMenu = k.new Menu("PANCAKE MENU", "Breakfast");
        MenuComponent dinerMenu = k.new Menu("DINER MENU", "Lunch");
        MenuComponent cafeMenu = k.new Menu("CAFE MENU", "Dinner");
        MenuComponent desertMenu = k.new Menu("DESSERT MENU", "Dessert of course!");

        MenuComponent allMenus = k.new Menu("ALL MENUS", "All menus combined");
        allMenus.add(pancakeHouseMenu);
        allMenus.add(dinerMenu);
        allMenus.add(cafeMenu);

        dinerMenu.add(k.new MenuItem("new", "shit", true, 1.3));

        dinerMenu.add(desertMenu);
        desertMenu.add(k.new MenuItem("desert1", "luck", true, 0.5));

        Waitress waitress = k.new Waitress(allMenus);
        waitress.printMenu();
        waitress.printVegetarianMenu();
    }

    //MENU COMPONENT
    public abstract class MenuComponent{
        public void add(MenuComponent menuComponent){
            throw new UnsupportedOperationException();
        }
        public void remove(MenuComponent menuComponent){
            throw new UnsupportedOperationException();
        }
        public MenuComponent getChild(int i){
            throw new UnsupportedOperationException();
        }

        public String getName(){
            throw new UnsupportedOperationException();
        }
        public String getDescription(){
            throw new UnsupportedOperationException();
        }
        public double getPrice(){
            throw new UnsupportedOperationException();
        }
        public boolean isVegetarian(){
            throw new UnsupportedOperationException();
        }

        public void print(){
            throw new UnsupportedOperationException();
        }
        public Iterator createIterator(){
            throw new UnsupportedOperationException();
        }
    }


    public class Menu extends MenuComponent{
        ArrayList menuComponents = new ArrayList();
        String name;
        String description;
        Iterator iterator = null;

        public Menu(String name, String description){
            this.name = name;
            this.description = description;
        }

        @Override
        public void add(MenuComponent menuComponent) {
            this.menuComponents.add(menuComponent);
        }

        @Override
        public void remove(MenuComponent menuComponent) {
            menuComponents.remove(menuComponent);
        }

        @Override
        public MenuComponent getChild(int i) {
            return (MenuComponent) menuComponents.get(i);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public void print() {
            System.out.print("\n" + getName());
            System.out.println(", " + getDescription());
            System.out.println("--------------------");

            Iterator iterator = menuComponents.iterator();
            while (iterator.hasNext()){
                MenuComponent menuComponent = (MenuComponent) iterator.next();
                menuComponent.print();
            }
        }

        @Override
        public Iterator createIterator() {
            if(iterator == null){
                iterator = new CompositeIterator(menuComponents.iterator());
            }
            return iterator;
        }
    }

    public class MenuItem extends MenuComponent{
        String name;
        String description;
        boolean vegetarian;
        double price;

        public MenuItem(String name,
                        String description,
                        boolean vegetarian,
                        double price){
            this.description = description;
            this.name = name;
            this.vegetarian = vegetarian;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public boolean isVegetarian() {
            return vegetarian;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public void print() {
            System.out.print(" " + getName());
            if(isVegetarian()){
                System.out.print("(v)");
            }
            System.out.println(", " + getPrice());
            System.out.println("    -- " + getDescription());
        }

        @Override
        public Iterator createIterator() {
            return new NullIterator();
        }
    }

    //ITERATORS
    public class CompositeIterator implements Iterator{
        Stack stack = new Stack();

        public CompositeIterator(Iterator iterator){
            stack.push(iterator);
        }

        @Override
        public boolean hasNext() {
            if(stack.empty()){
                return false;
            }else{
                Iterator iterator = (Iterator) stack.peek();
                if(!iterator.hasNext()){
                    stack.pop();
                    return hasNext();
                }else{
                    return true;
                }
            }
        }

        public Object next(){
            if(hasNext()){
                Iterator iterator = (Iterator) stack.peek();
                MenuComponent component = (MenuComponent) iterator.next();
                if(component instanceof Menu){
                    stack.push(component.createIterator());
                }
                return component;
            }else{
                return null;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer action) {

        }
    }
    public class NullIterator implements Iterator{

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public class Waitress{
        MenuComponent allMenus;

        public Waitress(MenuComponent allMenus){
            this.allMenus = allMenus;
        }

        public void printMenu(){
            allMenus.print();
        }

        public void printVegetarianMenu(){
            Iterator iterator = allMenus.createIterator();
            System.out.println("\nVEGETARIAN MENU\n---");
            while (iterator.hasNext()){
                MenuComponent menuComponent = (MenuComponent) iterator.next();
                try {
                    if(menuComponent.isVegetarian()){
                        menuComponent.print();
                    }
                }catch (UnsupportedOperationException e){}
            }
        }


    }

}
