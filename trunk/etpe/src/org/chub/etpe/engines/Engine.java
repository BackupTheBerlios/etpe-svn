// Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
// Author: Pierre Lamot pierre.lamot@yahoo.fr
// This software is licenced under the Eclipse Public License, v 1.0. 
// See the LICENSE file for details.
package org.chub.etpe.engines;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.chub.etpe.tools.RessourceAcces;

public abstract class Engine
{
	public static final int REPLACE_NONE = 0;

	public static final int REPLACE_ALL = 1;

	public static final int REPLACE_SELECTION = 2;

	public String getDescription()
	{
		return "no description avaible";
	}

	public int getReplaceType()
	{
		return REPLACE_NONE;
	}

	public abstract String filter(String text, int SelectStartOffset,
			int SelectEndOffset, int SelectStartLine, int SelectEndLine);

	public String getDocumentTitle()
	{
		return RessourceAcces.getDocumentTitle();
	}
	
	public void setClipboard(String txt)
	{
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
				new StringSelection(txt), null);
	}

	public String getClipboard()
	{
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
				.getContents(null);
		try
		{
			/** Vérification que le contenu est de type texte. */
			if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor))
			{
				return (String) t.getTransferData(DataFlavor.stringFlavor);
			}
		} catch (UnsupportedFlavorException e1)
		{
			System.out.println("Unexpected content while retriving clipBoard");
		} catch (IOException e2)
		{
			System.out.println("Unexpected error while retriving clipBoard");
		}
		return "";
	}
}
