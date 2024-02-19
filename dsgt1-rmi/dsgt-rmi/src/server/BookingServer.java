package server;

import hotel.BookingManager;
import remote.IBookingManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BookingServer {
    public static void main(String[] args) throws RemoteException {
        BookingManager crc = new BookingManager();
        IBookingManager stub =
                (IBookingManager) UnicastRemoteObject.exportObject(crc, 0);
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        registry.rebind( "BookingManagerRemote", stub);
    }
}
