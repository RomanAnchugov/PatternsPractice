package ru.romananchugov;

public class MVCPattern {
    public static void program(){
        MVCPattern k = new MVCPattern();
    }

    public interface BeatModelInterface{
        void initialize();

        void on();

        void off();

        void setBPM(int bpm);

        int getBPM();

        void registerObserver(BeatObserver o);

        void removeObserver(BeatObserver o);

        void registerObserver(BPMObserver o);

        void removeObserver(BPMObserver o);


    }

    public class BeatModel implements BeatModelInterface{

        @Override
        public void initialize() {

        }

        @Override
        public void on() {

        }

        @Override
        public void off() {

        }

        @Override
        public void setBPM(int bpm) {

        }

        @Override
        public int getBPM() {
            return 0;
        }

        @Override
        public void registerObserver(BeatObserver o) {

        }

        @Override
        public void removeObserver(BeatObserver o) {

        }

        @Override
        public void registerObserver(BPMObserver o) {

        }

        @Override
        public void removeObserver(BPMObserver o) {

        }
    }
}
