package ru.romananchugov;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class IteratorPattern {

    public static void program(){
        IteratorPattern k = new IteratorPattern();
        PancakeHouseMenu pancakeHouseMenu = k.new PancakeHouseMenu();
        DinerMenu dinerMenu = k.new DinerMenu();
        CafeMenu cafeMenu = k.new CafeMenu();

        ArrayList menus = new ArrayList();
        menus.add(pancakeHouseMenu);
        menus.add(dinerMenu);
        menus.add(cafeMenu);

        Waitress waitress = k.new Waitress(menus);

        waitress.printMenu();
    }

    public class MenuItem{
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
    }


    //MENUS
    public interface Menu{
        public Iterator createIterator();
    }
    public class PancakeHouseMenu implements Menu{
        ArrayList menuItems;

        public PancakeHouseMenu(){
            menuItems = new ArrayList();

            addItem("kek1", "desc1", true, 2.99);
            addItem("kek2", "desc1", true, 2.99);
            addItem("kek3", "desc1", true, 2.99);
            addItem("kek4", "desc1", true, 2.99);
        }

        public void addItem(String name, String description,
                            boolean vegetarian, double price){
            MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
            menuItems.add(menuItem);
        }

        public java.util.Iterator createIterator(){
            return menuItems.iterator();
        }

    }

    public class DinerMenu implements Menu{
        static final int MAX_ITEM = 6;
        int numberOfItem = 0;
        MenuItem[] menuItems;

        public DinerMenu(){
            menuItems = new MenuItem[MAX_ITEM];

            addItem("kak1", "desc1", true, 2.99);
            addItem("kak2", "desc1", true, 2.99);
            addItem("kak3", "desc1", true, 2.99);
            addItem("kak4", "desc1", true, 2.99);

        }

        public void addItem(String name, String description,
                            boolean vegetarian, double price){
            MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
            if(numberOfItem >= MAX_ITEM){
                System.err.println("Menu is full!");
            }else{
                menuItems[numberOfItem] = menuItem;
                numberOfItem++;
            }
        }

        public Iterator createIterator(){
            return new DinerMenuIterator(menuItems);
        }
    }

    public class CafeMenu implements Menu{
        Hashtable menuItems = new Hashtable();

        public CafeMenu(){
            addItem("kok1", "desc", true, 2.98);
            addItem("kok1", "desc", true, 2.98);
            addItem("kok1", "desc", true, 2.98);
            addItem("kok1", "desc", true, 2.98);
        }
        public void addItem(String name, String description,
                            boolean vegetarian, double price){
            MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
            menuItems.put(menuItem.getName(), menuItem);
        }
        public Hashtable getMenuItems(){
            return menuItems;
        }

        @Override
        public Iterator createIterator() {
            return menuItems.values().iterator();
        }
    }


    //MENUS



    //ITERATORS
//    public interface Iterator{
//        boolean hasNext();
//        Object next();
//    }
    public class DinerMenuIterator implements Iterator{
        MenuItem[] items;
        int position = 0;

        public DinerMenuIterator(MenuItem[] items){
            this.items = items;
        }

        @Override
        public boolean hasNext() {
            if(position >= items.length || items[position] == null){
                return false;
            }else{
                return true;
            }
        }

        @Override
        public Object next() {
            MenuItem menuItem = items[position];
            position++;
            return menuItem;
        }

        @Override
        public void remove() {
            if(position <= 0){
                throw new IllegalStateException("make one next()");
            }
            if(items[position - 1] != null){
                for(int i = position - 1; i < (items.length - 1); i++){
                    items[i] = items[i + 1];
                }
                items[items.length - 1] = null;
            }
        }
    }
    //ITERATORS



    public class Waitress{
        ArrayList menus;

        public Waitress(ArrayList menus){
            this.menus = menus;
        }

        public void printMenu(){
            Iterator menuIterator = menus.iterator();
            while (menuIterator.hasNext()){
                Menu menu = (Menu)menuIterator.next();
                printMenu(menu.createIterator());
            }
        }

        private void printMenu(Iterator iterator){
            while(iterator.hasNext()){
                MenuItem menuItem = (MenuItem) iterator.next();
                System.out.print(menuItem.getName() + ", ");
                System.out.print(menuItem.getPrice() + " -- ");
                System.out.println(menuItem.getDescription());
            }
        }
    }

}
