import static org.junit.Assert.*;
import org.junit.Test;

public class HelloWorldTest {
	@Test
	public void testHello() {
		HelloWorld hw = new HelloWorld();
		assertEquals("Hello World!", hw.getStr());
	}
}