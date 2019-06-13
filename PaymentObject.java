/*
 
Coding language:   Java 
Repo : Use github 
•   Create an object/class, call it PaymentObject, it has three fields, paymentNumber, amount and date
•   Use DB of your choice and persist list 1000 of Payment objects (you can use JUnit or any other method to populate your DB)
•   Write two methods, each using a different sorting algorithm and not using built in sorts or external libraries, to sort a List of an n-Payment objects from most recent to oldest.
•   Write at least two JUNIT tests (one per method) to generate a randomly ordered list with random payment dates  and sort them. Record the time it takes to sort and verify that the list is ordered correctly.
 
*/
 
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Statement; 
 
public class PaymentObject {
     
    private int paymentNumber;
    private int amount;
    private Date date;
	
	//database config info
    static final String JDBC_DRIVER = "org.h2.Driver";   
    static final String DB_URL = "jdbc:h2:~/test";  
     
    //  Database credentials 
    static final String USER = "sa"; 
    static final String PASS = ""; 
     
    public void setPaymentNumber(int paymentNumber){
        this.paymentNumber = paymentNumber;
    }
     
    public int getPaymentNumber(){
        return this.paymentNumber;
    }
     
    public void setAmount(int amount){
        this.amount = amount;
    }
     
    public int getAmount(){
        return this.amount;
    }
     
    public void setDate(Date date){
        this.date = date;  
    }
    public Date getDate(){
        return this.date;
    }
     
    public PaymentObject(int paymentNumber, int amount, Date date){
        this.paymentNumber = paymentNumber;
        this.amount = amount;
        this.date = date;
    }
     
    //TODO: write sort algorithm 1 to sort n-payment objects from most recent to oldest 
	public List<PaymentObject> mergeSort(List<PaymentObject> toSort, int left, int right) {
		if(left < right) {
			int mid = (left+right)/2;
			
	        // Sort first and second halves 
            mergeSort(toSort, left, mid); 
            mergeSort(toSort , mid+1, right); 
  
            // Merge the sorted halves 
            mergeHelper(toSort, left, mid, right);
		}
		return toSort;
	}
	
	public static void mergeHelper(List<PaymentObject> toSort, int left, int mid, int right) {        
        List<PaymentObject> leftList = new ArrayList<PaymentObject>();
        List<PaymentObject> rightList = new ArrayList<PaymentObject>();
  
        /*Copy data to temp arrays*/
        for (int x = 0; x < (mid-left+1); x++) 
            leftList.add(toSort.get(left + x)); 
        
        for (int y = 0; y < (right-mid); y++) 
            rightList.add(toSort.get(mid+1+y)); 
  
  
        /* Merge the temp arrays */
  
        // Initial indexes of first and second subarrays 
        int x = 0;
        int y = 0; 
  
        // Initial index of merged subarry array 
        int z = left; 
        while (x < (mid-left+1) && y < (right-mid)){ 
        	//left date is before right date
            if (leftList.get(x).getDate().compareTo(rightList.get(y).getDate()) >= 0){ 
                toSort.set(z, leftList.get(x));
            	x++;
            } 
            else{ 
            	toSort.set(z, rightList.get(y));
            	y++;
            } 
            z++; 
        } 
  
        /* Copy remaining elements of L[] if any */
        while (x < (mid-left+1)){ 
        	toSort.set(z, leftList.get(x));
        	x++;
            z++; 
        } 
  
        /* Copy remaining elements of R[] if any */
        while (y < (right-mid)){ 
        	toSort.set(z, rightList.get(y));
        	y++; 
            z++; 
        } 
	}
         
    //TODO: write sort algorithm 2 to sort n-payment objects from most recent to oldest 
    public void quickSort(List<PaymentObject> toSort, int low, int high) {
    	if (low < high) 
        { 
            int pivitIndex = quickSortHelper(toSort, low, high);  
            quickSort(toSort, low, pivitIndex - 1); 
            quickSort(toSort, pivitIndex + 1, high); 
        } 
    }
    
    public static int quickSortHelper(List<PaymentObject> toSort, int low, int high) {
    	Date pivot = toSort.get(high).getDate();  
        int x = (low-1); // index of smaller element 
        for (int y = low; y < high; y++){ 
            if (toSort.get(y).getDate().compareTo(pivot) >= 0) 
            { 
                x++; 
 
                // swap arr[i] and arr[j] 
                PaymentObject temp = toSort.get(x); 
                toSort.set(x, toSort.get(y));
                toSort.set(y, temp);
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        PaymentObject temp = toSort.get(x+1);
        toSort.set(x+1, toSort.get(high));
        toSort.set(high, temp);
  
        return x+1;     
    }
     
     
    public void setUpDB(){
		Connection conn = null; 
        Statement stmt = null; 
        try { 
            Class.forName(JDBC_DRIVER); 
                  
            System.out.println("Connecting to DB"); 
            conn = DriverManager.getConnection(DB_URL,USER,PASS);  
              
            System.out.println("Creating table in DB"); 
            stmt = conn.createStatement(); 
            String sql =  "CREATE TABLE  PAYMENTS " +
			   "(paymentNumber INTEGER not NULL, " +  
               " amount INTEGER, " +  
               " date BIGINT, " +  
               " PRIMARY KEY ( paymentNumber ))";  
            stmt.execute(sql); 	//stmt.executeUpdate(sql); 
            System.out.println("Created table PAYMENTS in DB"); 
            
            stmt.close(); 
            conn.close(); 
        } catch(SQLException se) { 
            se.printStackTrace(); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } finally { 
            try{ 
               if(stmt!=null) stmt.close(); 
            } catch(SQLException se2) { 
            } 
            try { 
                if(conn!=null) conn.close(); 
            } catch(SQLException se){ 
                se.printStackTrace(); 
            } 
        } 
	}
	
	public void takeDownDB(){
		Connection conn = null; 
        Statement stmt = null; 
        try { 
            Class.forName(JDBC_DRIVER); 
                  
            System.out.println("Connecting to DB"); 
            conn = DriverManager.getConnection(DB_URL,USER,PASS);  
              
            System.out.println("Deleting PAYMENTS table"); 
            stmt = conn.createStatement(); 
            String sql = "DROP TABLE PAYMENTS";
            stmt.execute(sql);
			
			stmt.close(); 
            conn.close(); 
        } catch(SQLException se) { 
            se.printStackTrace(); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } finally { 
            try{ 
               if(stmt!=null) stmt.close(); 
            } catch(SQLException se2) { 
            } 
            try { 
                if(conn!=null) conn.close(); 
            } catch(SQLException se){ 
                se.printStackTrace(); 
            } 
        } 
	}
	
	public void populateDB(int entries){
		Connection conn = null; 
        Statement stmt = null;
		try { 
			Class.forName(JDBC_DRIVER); 
				  
			System.out.println("Connecting to database..."); 
			conn = DriverManager.getConnection(DB_URL,USER,PASS);  
			  
			System.out.println("Adding Random Data"); 
			stmt = conn.createStatement(); 
			
			Random rand = new Random();
			int randAmount;
			Date randDate;
			long dateHolder;
			System.out.println("this is in populate db");
			
			Date d1 = new Date(0);
			Date d2 = new Date();
			
			for(int x = 0; x < entries; x++){
				randAmount = rand.nextInt(100000);

				randDate = new Date(ThreadLocalRandom.current().nextLong(d1.getTime(), d2.getTime()));
				dateHolder = randDate.getTime();

				
				String sql = "INSERT INTO PAYMENTS " +
				"VALUES (" + x + ", " + randAmount + ", " +  dateHolder + ")";
				stmt.execute(sql);
			}
			
			stmt.close(); 
			conn.close(); 
		} catch(SQLException se) { 
			se.printStackTrace(); 
		} catch(Exception e) { 
			e.printStackTrace(); 
		} finally { 
			try{ 
			   if(stmt!=null) stmt.close(); 
			} catch(SQLException se2) { 
			} 
			try { 
				if(conn!=null) conn.close(); 
			} catch(SQLException se){ 
				se.printStackTrace(); 
			}  
		} 
	}
	
	public List<PaymentObject> getEntriesFromDB(){
		Connection conn = null; 
        Statement stmt = null;
        List<PaymentObject> toRet = new ArrayList<PaymentObject>();
        
		try { 
			Class.forName(JDBC_DRIVER); 
				  
			System.out.println("Connecting to database..."); 
			conn = DriverManager.getConnection(DB_URL,USER,PASS);  
			  
			System.out.println("Adding Random Data"); 
			stmt = conn.createStatement(); 
			
			PaymentObject foo;
			
			String sql = "SELECT * FROM PAYMENTS";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("This is in get entreis from DB");
			long dateHolder;
			int paymentNumberHolder;
			int amountHolder;
			Date convertedDate;
			while(rs.next()){
				dateHolder = rs.getLong("date");
				paymentNumberHolder = rs.getInt("paymentNumber");
				amountHolder = rs.getInt("amount");
				convertedDate = new Date(dateHolder);
				foo = new PaymentObject(paymentNumberHolder, amountHolder, convertedDate);

				toRet.add(foo);
			}
						
			stmt.close(); 
			conn.close(); 
		} catch(SQLException se) { 
			se.printStackTrace(); 
		} catch(Exception e) { 
			e.printStackTrace(); 
		} finally { 
			try{ 
			   if(stmt!=null) stmt.close(); 
			} catch(SQLException se2) { 
			} 
			try { 
				if(conn!=null) conn.close(); 
			} catch(SQLException se){ 
				se.printStackTrace(); 
			} 
		}  
		
		return toRet;
	}
     
     
    
    public void printOutList(List<PaymentObject> toPrint) {
    	for(PaymentObject foo : toPrint) {
	        System.out.println();
    		System.out.println(foo.getPaymentNumber());
	        System.out.println(foo.getAmount());
	        System.out.println(foo.getDate());
    	}
    }
     
     
    public static void main(String[] args){
        Date tmp = new Date();
        PaymentObject foo = new PaymentObject(234, 400, tmp);
        
        foo.setUpDB();
        foo.populateDB(50);
                
        List<PaymentObject> tmpForTest = foo.getEntriesFromDB();
        foo.printOutList(tmpForTest);
        
        tmpForTest = foo.mergeSort(tmpForTest,0,tmpForTest.size()-1);
//        quickSort(tmpForTest,0,tmpForTest.size()-1);
        System.out.println("________________");
        foo.printOutList(tmpForTest);
        
        
        foo.takeDownDB();

       } 
         
     
     
}