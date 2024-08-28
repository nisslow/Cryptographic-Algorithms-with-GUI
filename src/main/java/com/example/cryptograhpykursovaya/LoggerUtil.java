package com.example.cryptograhpykursovaya;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());
    private static FileHandler fileHandler;

    private LoggerUtil() {
        // Приватный конструктор, чтобы предотвратить создание экземпляров класса.
    }

    public static Logger getLogger() {
        return logger;
    }

    static {
        try {
            // Инициализация обработчика файла для журнала
            fileHandler = new FileHandler("eventlog.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void logError(String message) {
        log(Level.SEVERE, message);
    }

    public static void logWarning(String message) {
        log(Level.WARNING, message);
    }

    public static void logInfo(String message) {
        log(Level.INFO, message);
    }

    public static void logConfig(String message) {
        log(Level.CONFIG, message);
    }

    public static void logFine(String message) {
        log(Level.FINE, message);
    }

    public static void logFiner(String message) {
        log(Level.FINER, message);
    }

    public static void logFinest(String message) {
        log(Level.FINEST, message);
    }

    public static void setLogLevel(Level newLevel) {
        logger.setLevel(newLevel);
    }
}

