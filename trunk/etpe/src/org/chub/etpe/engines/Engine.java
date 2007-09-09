// Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
// Author: Pierre Lamot pierre.lamot@yahoo.fr
// This software is licenced under the Eclipse Public License, v 1.0. 
// See the LICENSE file for details.
package org.chub.etpe.engines;

public interface Engine
{
	public static int REPLACE_NONE = 0;
	public static int REPLACE_ALL = 1;
	public static int REPLACE_SELECTION = 2;
	
	public int getReplaceType();
	public String filter(String text, int SelectStartOffset, int SelectEndOffset, int SelectStartLine, int SelectEndLine);
}
