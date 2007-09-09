// Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
// Author: Pierre Lamot pierre.lamot@yahoo.fr
// This software is licenced under the Eclipse Public License, v 1.0. 
// See the LICENSE file for details.
package org.chub.etpe.tools;

public class TextBean
{
	private int selectionStartLine;
	private int selectionEndLine;
	
	private int selectionStartOffset;
	private int selectionEndOffset;
	
	private String text;
	
	public int getSelectionEndLine()
	{
		return selectionEndLine;
	}
	public void setSelectionEndLine(int selectionEndLine)
	{
		this.selectionEndLine = selectionEndLine;
	}
	public int getSelectionEndOffset()
	{
		return selectionEndOffset;
	}
	public void setSelectionEndOffset(int selectionEndOffset)
	{
		this.selectionEndOffset = selectionEndOffset;
	}
	public int getSelectionStartLine()
	{
		return selectionStartLine;
	}
	public void setSelectionStartLine(int selectionStartLine)
	{
		this.selectionStartLine = selectionStartLine;
	}
	public int getSelectionStartOffset()
	{
		return selectionStartOffset;
	}
	public void setSelectionStartOffset(int selectionStartOffset)
	{
		this.selectionStartOffset = selectionStartOffset;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}

	
}
