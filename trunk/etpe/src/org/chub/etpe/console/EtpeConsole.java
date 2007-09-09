package org.chub.etpe.console;

import java.io.PrintStream;

import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class EtpeConsole extends MessageConsole
{
	private MessageConsoleStream inStream;
	
	
	public EtpeConsole()
	{
		super("ETPE Console", null);
		inStream = new MessageConsoleStream(this);
		System.setOut(new PrintStream(inStream));
	}

}
