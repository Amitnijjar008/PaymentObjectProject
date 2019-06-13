import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class UnitTests {

	
	Date tmp = new Date();
	PaymentObject foo = new PaymentObject(234, 400, tmp);

	
	@Test
	public void testGetPaymentNumber() {
		assertEquals(234,foo.getPaymentNumber());
	}
	
	@Test
	public void testGetAmount() {
		assertEquals(400,foo.getAmount());
	}
	
	@Test
	public void testGetDate() {
		assertEquals(tmp,foo.getDate());
	}
	
	@Test
	public void testMergeSort() {
		foo.setUpDB();
        foo.populateDB(1000);
        List<PaymentObject> toCheck = foo.getEntriesFromDB();
        toCheck = foo.mergeSort(toCheck,0,toCheck.size()-1);
        
		boolean toRet = true;
		for(int x = 0; x < toCheck.size()-1; x++) {
			if(toCheck.get(x).getDate().compareTo(toCheck.get(x+1).getDate()) <= 0)
				toRet = false;
		}
		foo.takeDownDB();
		assertEquals(toRet,true);
	}
	
	@Test
	public void testMergeSortNegativeTest() {
		foo.setUpDB();
        foo.populateDB(1000);
        List<PaymentObject> toCheck = foo.getEntriesFromDB();
       
		boolean toRet = true;
		for(int x = 0; x < toCheck.size()-1; x++) {
			if(toCheck.get(x).getDate().compareTo(toCheck.get(x+1).getDate()) <= 0)
				toRet = false;
		}
		foo.takeDownDB();
		assertEquals(toRet,false);
	}
	
	@Test
	public void testQuickSort() {
		foo.setUpDB();
        foo.populateDB(1000);
        List<PaymentObject> toCheck = foo.getEntriesFromDB();
        foo.quickSort(toCheck,0,toCheck.size()-1);
        
		boolean toRet = true;
		for(int x = 0; x < toCheck.size()-1; x++) {
			if(toCheck.get(x).getDate().compareTo(toCheck.get(x+1).getDate()) <= 0)
				toRet = false;
		}
		foo.takeDownDB();
		assertEquals(toRet,true);
	}
	
	@Test
	public void testQuickSortNegativeTest() {
		foo.setUpDB();
        foo.populateDB(1000); 
        List<PaymentObject> toCheck = foo.getEntriesFromDB();
        
		boolean toRet = true;
		for(int x = 0; x < toCheck.size()-1; x++) {
			if(toCheck.get(x).getDate().compareTo(toCheck.get(x+1).getDate()) <= 0)
				toRet = false;
		}
		foo.takeDownDB();
		assertEquals(toRet,false);
	}
	
	

	
}
