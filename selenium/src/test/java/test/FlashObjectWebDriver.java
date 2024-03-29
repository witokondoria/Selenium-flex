package test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class FlashObjectWebDriver {
	private final WebDriver webDriver;
	private final String flashObjectId;

	public FlashObjectWebDriver(final WebDriver webDriver,
			final String flashObjectId) {
		this.webDriver = webDriver;
		this.flashObjectId = flashObjectId;
	}

	public String callFlashObject(final String functionName,
			final String... args) {
		final Object result = ((JavascriptExecutor) webDriver).executeScript(
				makeJsFunction(functionName, args), new Object[0]);

		return result != null ? result.toString() : null;
	}

	public String callFlashObjectMessage(final String functionName,
			final String... args) {
		final Object result = ((JavascriptExecutor) webDriver).executeScript(
				makeJsFunctionMessage(functionName, args), new Object[0]);

		return result != null ? result.toString() : null;
	}
	
	private String makeJsFunction(final String functionName,
			final String... args) {
		final StringBuffer functionArgs = new StringBuffer();

		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				if (i > 0) {
					functionArgs.append(",");
				}
				if ((args[i].length() > 0) && (args[i].substring(0,1).equals("{"))) {
					functionArgs.append(String.format("%1$s", args[i]));
				}
				else {
					functionArgs.append(String.format("'%1$s'", args[i]));					
				}
			}
		}		
		return String.format("return document.%1$s.%2$s(%3$s);", flashObjectId,
				functionName, functionArgs);
	}

	private String makeJsFunctionMessage(final String functionName,
			final String... args) {
		final StringBuffer functionArgs = new StringBuffer();

		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				if (i > 0) {
					functionArgs.append(",");
				}
				if ((args[i].length() > 0) && (args[i].substring(0,1).equals("{"))) {				
					functionArgs.append(String.format("%1$s", args[i]));
				}
				else {
					functionArgs.append(String.format("'%1$s'", args[i]));					
				}			}
		}
		return String.format("return document.%1$s.%2$s(%3$s).message;", flashObjectId,
				functionName, functionArgs);
	}
	
}