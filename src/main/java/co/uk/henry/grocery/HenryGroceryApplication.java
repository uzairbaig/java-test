package co.uk.henry.grocery;

import static java.lang.System.exit;
import static java.lang.System.out;
import static java.time.LocalDate.now;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HenryGroceryApplication {

	public static void main(String[] args) {

		final ApplicationContext ctx = SpringApplication.run(HenryGroceryApplication.class, args);
		if (args == null || args.length == 0) {
			printUsageAndExit();
		}

		final int date = getPurchaseDate(args[args.length - 1]);

		final BasketScanner runner = ctx.getBean(BasketScanner.class);
		runner.setItems(args);
		runner.setPurchaseDate(now().plusDays(date));
		runner.scan();
	}

	private static int getPurchaseDate(final String arg) {
		try {
			final int i = Integer.parseInt(arg);
			return i >= 0 ? i : 0;
		} catch (NumberFormatException nfe) {
			out.println("last parameter is not digit hence purchase date will be today's date");
		}
		return 0;
	}

	private static void printUsageAndExit() {
		out.println("Usage: java -jar target/java-test-1.0-SNAPSHOT.jar [items]\n"
				+ "eg. java -jar target/java-test-1.0-SNAPSHOT.jar soup apples milk bread 0\n" +
				" Purchase Of Days (e.g 0 mean today 5 mean five days in advance and must be passed as last parameter, if no number of days pass by default it will be today)");

		exit(0);
	}

	
}
