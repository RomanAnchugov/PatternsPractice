package ru.romananchugov;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class ProxyPattern {
    public static void program(){
        ProxyPattern k = new ProxyPattern();
        String[] location = {"rmi://santafe.mightygumball.com/gumballmachine",
                            "rmi://boulder.mightyball.com/gumballmachine",
                            "rmi://seattle.mightyball.com/gumballmachine"};

        GumballMonitor[] monitors = new GumballMonitor[location.length];

        for(int i = 0; i < location.length; i++){
            try {
                GumballMachineRemote machine = (GumballMachineRemote) Naming.lookup(location[i]);
                monitors[i] = k.new GumballMonitor(machine);
                System.out.println(monitors[i]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        for(int i = 0; i < monitors.length; i++){
            monitors[i].report();
        }
    }

    public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {
        String location;

        State soldOutState;
        State noCoinState;
        State hasCoinState;
        State soldState;
        State winnerState;

        State state = soldOutState;
        int count = 0;

        public GumballMachine(String location, int numberGumballs) throws RemoteException{
            soldOutState = new SoldOutState(this);
            noCoinState = new NoCoinState(this);
            hasCoinState = new HasCoinState(this);
            soldState = new SoldState(this);
            winnerState = new WinnerState(this);

            this.count = numberGumballs;
            if(numberGumballs > 0){
                state = noCoinState;
            }

            this.location = location;
        }

        public void insertCoin(){
            state.insertCoin();
        }
        public void ejectCoin(){
            state.ejectCoin();
        }
        public void turnCrank(){
            state.turnCrank();
            state.dispense();
        }
        public void setState(State state){
            this.state = state;
        }
        public void releaseBall(){
            System.out.println("Gum are coming");
        }

        public State getSoldOutState() {
            return soldOutState;
        }

        public State getNoCoinState() {
            return noCoinState;
        }

        public State getHasCoinState() {
            return hasCoinState;
        }

        public State getSoldState() {
            return soldState;
        }

        public State getState() {
            return state;
        }

        public int getCount() {
            return count;
        }

        @Override
        public String getLocation(){
            return location;
        }

        public State getWinnerState() {
            return winnerState;
        }
        public void refill(int count){
            this.count = count;
            setState(getNoCoinState());
        }

    }

    public interface State extends Serializable{
        public void insertCoin();
        public void ejectCoin();
        public void turnCrank();
        public void dispense();
    }

    public class SoldState implements State{
        transient GumballMachine gumballMachine;

        public SoldState(GumballMachine gumballMachine){
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertCoin() {
            System.out.println("please wait, we're giving you a gumball");
        }

        @Override
        public void ejectCoin() {
            System.out.println("sorry, you already turned the crank");
        }

        @Override
        public void turnCrank() {
            System.out.println("you cant turn twice with one coin");
        }

        @Override
        public void dispense() {
            gumballMachine.releaseBall();
            if(gumballMachine.getCount() > 0){
                gumballMachine.setState(gumballMachine.getNoCoinState());
            }else{
                System.out.println("oops, out of gums");
                gumballMachine.setState(gumballMachine.getSoldOutState());
            }

        }
    }

    public class WinnerState implements State{
        transient GumballMachine gumballMachine;

        public WinnerState(GumballMachine gumballMachine){
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertCoin() {
            System.out.println("you cnt insert two coins");
        }

        @Override
        public void ejectCoin() {
            System.out.println("you already turn crank");
        }

        @Override
        public void turnCrank() {
            System.out.println("you cant turn crank twice");
        }

        @Override
        public void dispense() {
            System.out.println("YOU ARE A WINNER! You get two gums because of your luck");
            gumballMachine.releaseBall();
            if(gumballMachine.getCount() == 0){
                gumballMachine.setState(gumballMachine.getSoldOutState());
            }else{
                gumballMachine.releaseBall();
                if(gumballMachine.getCount() > 0){
                    gumballMachine.setState(gumballMachine.getNoCoinState());
                }else{
                    System.out.println("oops, out of gums");
                    gumballMachine.setState(gumballMachine.getSoldOutState());
                }
            }
        }
    }

    public class SoldOutState implements State{

        transient GumballMachine gumballMachine;

        public SoldOutState(GumballMachine gumballMachine){
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertCoin() {
            System.out.println("cant insert, sold out");
        }

        @Override
        public void ejectCoin() {
            System.out.println("can't eject, you haven't insert");
        }

        @Override
        public void turnCrank() {
            System.out.println("you turned, but there are no gums");
        }

        @Override
        public void dispense() {
            System.out.println("no gum dispensed ");
        }
    }

    public class NoCoinState implements State{

        transient GumballMachine gumballMachine;

        public NoCoinState(GumballMachine gumballMachine){
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertCoin() {
            System.out.println("you inserted coin");
            gumballMachine.setState(gumballMachine.getHasCoinState());
        }

        @Override
        public void ejectCoin() {
            System.out.println("can't eject, you haven't inserted");
        }

        @Override
        public void turnCrank() {
            System.out.println("you turned. ubt there is no coin");
        }

        @Override
        public void dispense() {
            System.out.println("you need to pay first");
        }
    }

    public class HasCoinState implements State{
        transient GumballMachine gumballMachine;
        Random randomWinner = new Random(System.currentTimeMillis());

        public HasCoinState(GumballMachine gumballMachine){
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertCoin() {
            System.out.println("you can't insert another coin");
        }

        @Override
        public void ejectCoin() {
            System.out.println("eject, coin returned");
            gumballMachine.setState(gumballMachine.getNoCoinState());
        }

        @Override
        public void turnCrank() {
            int winner = randomWinner.nextInt(10);
            System.out.println("you turned and then...");
            if((winner == 0) && (gumballMachine.getCount() > 1)){
                gumballMachine.setState(gumballMachine.getWinnerState());
            }else {
                gumballMachine.setState(gumballMachine.getSoldState());
            }
        }

        @Override
        public void dispense() {
            System.out.println("you need to turn the crank");
        }
    }

    ///

    public interface GumballMachineRemote extends Remote{
        public int getCount() throws RemoteException;
        public String getLocation() throws RemoteException;
        public State getState() throws RemoteException;
    }

    public class GumballMonitor{
        GumballMachineRemote machineRemote;

        public GumballMonitor(GumballMachineRemote machineRemote){
            this.machineRemote = machineRemote;
        }

        public void report(){
            try {
                System.out.println("Gumblaa machine: " + machineRemote.getLocation());
                System.out.println("current inventory: " + machineRemote.getCount() + " gumballs");
                System.out.println("Current state: " + machineRemote.getState());
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }


}

