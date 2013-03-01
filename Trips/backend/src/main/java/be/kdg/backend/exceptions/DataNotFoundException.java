package be.kdg.backend.exceptions;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 28/02/13
 * Time: 15:12
 * Copyright @ Soulware.be
 */
public class DataNotFoundException extends Exception {
    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}