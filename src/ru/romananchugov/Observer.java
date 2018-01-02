package ru.romananchugov;

import java.util.Observable;

public class Observer {
    public interface Subject{
        public void registerObserver(java.util.Observer o);
        public void removeObserver(java.util.Observer o);
        public void notifyObservers();
    }

    public interface  DisplayElement{
        public void display();
    }

    public class WeatherData extends Observable {

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
