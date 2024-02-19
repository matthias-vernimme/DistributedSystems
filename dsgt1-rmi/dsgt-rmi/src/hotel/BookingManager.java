package hotel;

import remote.IBookingManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.*;

public class BookingManager implements IBookingManager {

	private Room[] rooms;

	public BookingManager() throws RemoteException {
        super();
        this.rooms = initializeRooms();
	}

	public Set<Integer> getAllRooms() {
		Set<Integer> allRooms = new HashSet<Integer>();
		Iterable<Room> roomIterator = Arrays.asList(rooms);
		for (Room room : roomIterator) {
			allRooms.add(room.getRoomNumber());
		}
		return allRooms;
	}

	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		for(Room room: rooms){
			if(Objects.equals(room.getRoomNumber(), roomNumber)){
				for(BookingDetail details: room.getBookings()){
					if(details.getDate() == date){
						return false;
					}
				}
			}
		}
		return true;
	}

	public void addBooking(BookingDetail bookingDetail) {
		if(isRoomAvailable(bookingDetail.getRoomNumber(), bookingDetail.getDate())){
			for(Room room: rooms){
				if(Objects.equals(room.getRoomNumber(), bookingDetail.getRoomNumber())){
					List<BookingDetail> tempList = room.getBookings();
					tempList.add(bookingDetail);
					room.setBookings(tempList);
				}
			}
		}
		else{
			System.out.println("Room is not available for booking");
//			throw new RuntimeException("Room is not available for booking");
		}
	}

	public Set<Integer> getAvailableRooms(LocalDate date) {
		Set<Integer> availableRooms = new HashSet<>(Set.of());
		for(Room room: rooms){
			boolean booked = false;
			List<BookingDetail> tempBookings = room.getBookings();
			for(BookingDetail details: tempBookings){
                if (details.getDate() == date) {
                    booked = true;
                    break;
                }
			}
			if(!booked){
				availableRooms.add(room.getRoomNumber());
			}
		}
		return availableRooms;
	}

	private static Room[] initializeRooms() {
		Room[] rooms = new Room[4];
		rooms[0] = new Room(101);
		rooms[1] = new Room(102);
		rooms[2] = new Room(201);
		rooms[3] = new Room(203);
		return rooms;
	}
}
