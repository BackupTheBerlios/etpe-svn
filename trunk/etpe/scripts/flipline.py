""" 
Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
Author: Pierre Lamot pierre.lamot@yahoo.fr
This software is licenced under the Eclipse Public License, v 1.0. 
See the LICENSE file for details.
"""
from org.chub.etpe.engines import Engine
import string
import StringIO

class flipline(Engine):
	def getDescription(self):
		return "flip slected line with the next one"
	
	def getReplaceType(self):
		return Engine.REPLACE_ALL
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		inBuf = StringIO.StringIO(text)
		strarrray = inBuf.readlines()

		tmpline = strarrray[startLine]
		strarrray[startLine] =  strarrray[startLine + 1]
		strarrray[startLine + 1] =  tmpline

		res =  string.join(strarrray, "")
		return res