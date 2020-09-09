import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.util.*; 

public class ClassPoW {

	private static final String HexDecimalLetters = "0123456789abcdef";
	private static int counter = 0;
	private static BigInteger hexToDecimal;
	private static BigInteger target;
	private static StringBuilder hashInStringBuilder = null;
	
	public static String proof_of_work()
		    throws NoSuchAlgorithmException 
	{		 		
		 	long startTime = System.nanoTime();
		
			String nonce = null;			
			String txPlusNonce = null;			
			byte[] hashInByteArray = null;			
			
			//StringBuilder hashInStringBuilder = null;
			String txString = "user1 sent 1 token to user2";
		    hexToDecimal = new BigInteger("0");
			
			for(;;) 
			{
				// nonce will increase one after another in each round to be tested whether is a correct answer for proof-of-work.
				counter++;	
				nonce = Integer.toString(counter);  
				txPlusNonce = txString + nonce;  
			    
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(txPlusNonce.getBytes(Charset.defaultCharset()));
		    
				hashInByteArray = md.digest();
		    
				// Converting Hash from byteArray to String.
				hashInStringBuilder = new StringBuilder(2 * hashInByteArray.length); 	
				for (byte element : hashInByteArray) 
				{
			        hashInStringBuilder.append(HexDecimalLetters.charAt((element & 0xF0) >> 4)).append(HexDecimalLetters.charAt((element & 0x0F)));     
			    }				
				String hashInString = hashInStringBuilder.toString();	
				
				// Check whether current nonce is a correct answer of PoW.
				// It is a correct answer if it is smaller than Target.
				hexToDecimal = new BigInteger(hashInString, 16);
				if(hexToDecimal.compareTo(target) < 0)	
				{			
					System.out.println("PoW is solved after : "+(System.nanoTime()-startTime)+" nano seconds");
					break; // Current nonce is answer. So break infinite loop.
				}
			} 
			
			// Return nonce as a correct answer of PoW
			return nonce;
			
		}
	
	public static void main(String args[]) throws NoSuchAlgorithmException
	{	
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter target to determine difficulty of proof-of-work:"); 
		target = sc.nextBigInteger(); 
		
		System.out.println("Correct answer of proof-of-work (Correct nonce) = " + proof_of_work());
		System.out.println("Hash of transaction plus nonce (Hex) = " + hashInStringBuilder);
		System.out.println("Hash of transaction plus nonce (Decimal) = " + hexToDecimal);
	}

}
