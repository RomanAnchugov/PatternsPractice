package ru.romananchugov;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        WeatherData weatherData = main.new WeatherData();
        CurrentConditionsDisplay currentConditionsDisplay = main.new CurrentConditionsDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }

    public interface Subject{
        public void registerObserver(Observer o);
        public void removeObserver(Observer o);
        public void notifyObservers();
    }

    public interface  DisplayElement{
        public void display();
    }

    public class WeatherData extends Observable{

        private float temperature;
        private float humidity;
        private float pressure;


        public void measurementsChanged(){
            setChanged();
            notifyObservers();
        }

        public void setMeasurements(float temperature, float humidity, float pressure){
            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
            measurementsChanged();
        }

        public float getTemperature() {
            return temperature;
        }

        public float getHumidity() {
            return humidity;
        }

        public float getPressure() {
            return pressure;
        }
    }

    public class CurrentConditionsDisplay implements java.util.Observer, DisplayElement{

        private float temperature;
        private float humidity;
        private Observable observable;


        public CurrentConditionsDisplay(Observable observable){
            this.observable = observable;
            observable.addObserver(this);
        }


        @Override
        public void display() {
            System.out.println("Current conditions: " + temperature + " F degrees and" + humidity + "% humidity");
        }

        @Override
        public void update(Observable o, Object arg) {
            if(o instanceof WeatherData){
                WeatherData weatherData = (WeatherData) o;
                this.temperature = weatherData.getTemperature();
                this.humidity = weatherData.getHumidity();
                display();
            }
        }
    }


}

