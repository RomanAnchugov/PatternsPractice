package ru.romananchugov;

public class CommandPattern {

    public static void program(){
        CommandPattern t = new CommandPattern();
        RemoteControl remoteControl = t.new RemoteControl();
        Light light = t.new Light();
        GarageDoor garageDoor = t.new GarageDoor();
        Stereo stereo = t.new Stereo();
        CeilingFan ceilingFan = t.new CeilingFan();

        LightOnCommand lightOn = t.new LightOnCommand(light);
        LightOffCommand lightOff = t.new LightOffCommand(light);
        GarageDoorOpenCommand garageDoorOpen = t.new GarageDoorOpenCommand(garageDoor);
        StereoOnWithCDCommand stereoOnWithCDCommand = t.new StereoOnWithCDCommand(stereo);
        CeilingFanHighCommand ceilingFanHighCommand = t.new CeilingFanHighCommand(ceilingFan);

        Command[] setOfCommand = {lightOff, lightOn, garageDoorOpen, stereoOnWithCDCommand, ceilingFanHighCommand};

        MacroCommand partyOnCommands = t.new MacroCommand(setOfCommand);

        remoteControl.setCommand(0, lightOn, lightOff);
        remoteControl.setCommand(1, ceilingFanHighCommand, ceilingFanHighCommand);
        remoteControl.setCommand(2, partyOnCommands, partyOnCommands);
        remoteControl.onButtonWasPushed(2);
        remoteControl.undoButtonWasPushed();
        System.out.println(remoteControl);
    }

    //RECEIVERS
    public class Light{
        public void on(){
            System.out.println("Light is on");
        };
        public void off(){
            System.out.println("Light is off");
        };
    }
    public class GarageDoor{
        public void up(){
            System.out.println("Garage door is open");
        }
        public void down(){
            System.out.println("Garage door is close");
        }
    }

    public class Stereo{
        public void on(){
            System.out.println("Cd is on");
        }
        public void off(){
            System.out.println("Cd is off");
        }
        public void setCd(){
            System.out.println("Set cd");
        }
        public void setDvd(){
            System.out.println("Set dvd");
        }
        public void setRadio(){
            System.out.println("Set radio");
        }
        public void setVolume(){
            System.out.println("Set volume");
        }
    }

    public class CeilingFan{
        public static final int HIGH = 3;
        public static final int MEDIUM = 2;
        public static final int LOW = 1;
        public static final int OFF = 0;

        int speed;

        public CeilingFan(){
            speed = OFF;
        }

        public void high(){
            speed = HIGH;
        }
        public void medium(){
            speed = MEDIUM;
        }
        public void low(){
            speed = LOW;
        }
        public void off(){
            speed = OFF;
        }

        public int getSpeed(){
            return speed;
        }
    }
    //RECEIVERS


    //COMMAND INTERFACE
    public interface  Command{
        public void execute();
        public void undo();
    }
    //COMMAND INTERFACE

    //COMMANDS
    public class LightOnCommand implements Command{
        Light light;

        public LightOnCommand(Light light){
            this.light = light;
        }

        @Override
        public void execute() {
            light.on();
        }

        @Override
        public void undo() {
            light.off();
        }
    }
    public class LightOffCommand implements Command{
        Light light;

        public LightOffCommand(Light light){
            this.light = light;
        }

        @Override
        public void execute() {
            light.off();
        }

        @Override
        public void undo() {
            light.on();
        }
    }

    public class GarageDoorOpenCommand implements Command{
        GarageDoor garageDoor;

        public GarageDoorOpenCommand(GarageDoor garageDoor){
            this.garageDoor = garageDoor;
        }

        @Override
        public void execute() {
           garageDoor.up();
        }

        @Override
        public void undo() {
            garageDoor.down();
        }
    }

    public class NoCommand implements Command{

        @Override
        public void execute() {

        }

        @Override
        public void undo() {

        }
    }

    public class StereoOnWithCDCommand implements Command{

        Stereo stereo;

        public StereoOnWithCDCommand(Stereo stereo){
            this.stereo = stereo;
        }

        @Override
        public void execute() {
            stereo.on();
            stereo.setCd();
            stereo.setVolume();
        }

        @Override
        public void undo() {
            stereo.off();
        }
    }

    public class CeilingFanHighCommand implements Command{

        CeilingFan ceilingFan;
        int prevSpeed;

        public CeilingFanHighCommand(CeilingFan ceilingFan){
            this.ceilingFan = ceilingFan;
        }

        @Override
        public void execute() {
            prevSpeed = ceilingFan.getSpeed();
            ceilingFan.high();
        }

        @Override
        public void undo() {
            if(prevSpeed == CeilingFan.HIGH){
                ceilingFan.high();
            }else if(prevSpeed == CeilingFan.MEDIUM){
                ceilingFan.medium();
            }else if(prevSpeed == CeilingFan.LOW){
                ceilingFan.low();
            }else if(prevSpeed == CeilingFan.OFF){
                ceilingFan.off();
            }
        }
    }

    public class MacroCommand implements Command{

        Command[] commands;

        public MacroCommand(Command[] commands){
            this.commands = commands;
        }

        @Override
        public void execute() {
            for (Command command : commands) {
                command.execute();
            }
        }

        @Override
        public void undo() {
            for (Command command : commands) {
                command.undo();
            }
        }
    }
    //COMMANDS

    //CONTROL
    public class RemoteControl{
        Command[] onCommands;
        Command[] offCommands;
        Command undoCommand;

        public RemoteControl(){
            onCommands = new Command[7];
            offCommands = new Command[7];

            Command noCommands = new NoCommand();
            for(int i = 0; i < 7; i++){
                onCommands[i] = noCommands;
                offCommands[i] = noCommands;
            }
            undoCommand = noCommands;
        }

        public void setCommand(int slot, Command onCommand, Command offCommand){
            this.onCommands[slot] = onCommand;
            this.offCommands[slot] = offCommand;
        }
        public void onButtonWasPushed(int slot){
            onCommands[slot].execute();
            undoCommand = onCommands[slot];
        }
        public void offButtonWasPushed(int slot){
            offCommands[slot].execute();
            undoCommand = onCommands[slot];
        }

        public void undoButtonWasPushed(){
            undoCommand.undo();
        }

        public String toString(){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\n-------Remote Control--------\n");
            for(int i = 0; i < onCommands.length; i++){
                stringBuffer.append("[slot" + i + "]" + onCommands[i].getClass().getName() + " " + offCommands[i].getClass().getName() + "\n");
            }
            return stringBuffer.toString();
        }
    }
    //CONTROL

    public class SimpleRemoteControl{
        Command slot;

        public SimpleRemoteControl(){}

        public void setCommand(Command command){
            slot = command;
        }

        public void buttonWasPressed(){
            slot.execute();
        }
    }
}
