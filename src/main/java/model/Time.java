package model;

public class Time {

    private int minute;
    private int second;

    public Time(int minute, int second) {
        this.minute = minute;
        this.second = second;
    }

    public Time(String currentTime) {
        String[] time = currentTime.split(":");
        minute = Integer.parseInt(time[0]);
        second = Integer.parseInt(time[1]);
    }

    public String getCurrentTime() {
        return minute + ":" + second;
    }

    public void oneSecondPassed() {
        second++;
        if (second == 60) {
            minute++;
            second = 0;

        }
    }

    public int getSecondsLeft() {
        return second + minute * 60;
    }
}
