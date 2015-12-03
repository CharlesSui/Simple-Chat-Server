import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class LoanTest   
{
   @Test
   public void test_returnPounds() 
   {
      PennyLoan L = new PennyLoan(200,2);
      assertEquals("checking that pounds are returned",1.0,L.paymentPerMonth(), 0);
   }

   @Test
   public void test_roundUp() 
   {
      PennyLoan L = new PennyLoan(200,3);
      assertEquals("checking that rounds up to nearest penny",0.67,L.paymentPerMonth(), 0);
   }
}

