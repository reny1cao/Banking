package atm;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AtmTime {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static Date initialTime, currentTime;
    private static long prevMills = -1;

    static void setInitialTime(Date start) {
        initialTime = start;
        currentTime = start;
        prevMills = System.currentTimeMillis();
    }

    public static Date getCurrentTime() {
        if (initialTime == null || prevMills == -1)
            throw new IllegalStateException("ATM time not initialized by Bank Manager yet");

        long currentMills = System.currentTimeMillis();
        currentTime = new Date(currentTime.getTime() + (currentMills - prevMills));
        prevMills = currentMills;
        return new Date(currentTime.getTime());
    }

    public static Date getInitialTime() {
        return new Date(initialTime.getTime());
    }

}
