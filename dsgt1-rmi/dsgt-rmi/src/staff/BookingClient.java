package staff;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.BookingManager;
import remote.IBookingManager;

public class BookingClient extends AbstractScriptedSimpleTest {

	private BookingManager bm = null;

	public static void main(String[] args) throws Exception {
//		BookingClient client = new BookingClient();
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		IBookingManager crc = (IBookingManager) registry.lookup("BookingManagerRemote");
        System.out.println(crc.getAvailableRooms(LocalDate.now()));
		System.out.println("before");
		BookingDetail booking1 = new BookingDetail("Joris", 201, LocalDate.now());
		System.out.println("after");
		crc.addBooking(booking1);
		System.out.println(crc.getAvailableRooms(LocalDate.now()));
//		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient() {
		try {
			//Look up the registered remote instance
			bm = new BookingManager();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		return bm.isRoomAvailable(roomNumber, date);
//		return true;
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws Exception {
		bm.addBooking(bookingDetail);
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) {
		return bm.getAvailableRooms(date);
//		return null;
	}

	@Override
	public Set<Integer> getAllRooms() {
		return bm.getAllRooms();
	}
}
