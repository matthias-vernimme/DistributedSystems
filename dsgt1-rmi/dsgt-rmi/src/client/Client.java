package client;

import remote.IBookingManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        IBookingManager crc = (IBookingManager) registry.lookup("BookingManagerRemote");

        crc.getAllRooms();
    }
}
