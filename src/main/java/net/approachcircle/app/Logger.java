package net.approachcircle.app;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static void log(Object message, LogLevel logLevel) {
        String time = timeFormat.format(new Date());
        String buffer = String.format("[%s %s] %s%n", time, logLevel.name(), message);
        if (logLevel == LogLevel.ERR) {
            System.err.print(buffer);
        } else if (logLevel == LogLevel.MSG) {
            System.out.print(buffer);
        }
    }

    public static void info(Object message) {
        log(message, LogLevel.MSG);
    }

    public static void error(Object message) {
        log(message, LogLevel.ERR);
    }
}
