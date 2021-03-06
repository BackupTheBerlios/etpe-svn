""" 
Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
Author: Pierre Lamot pierre.lamot@yahoo.fr
This software is licenced under the Eclipse Public License, v 1.0. 
See the LICENSE file for details.
"""
from org.chub.etpe.engines import Engine

class wordcount(Engine):
	def getDescription(self):
		return "count the number of word of the selection"
	
	def getReplaceType(self):
		return Engine.REPLACE_NONE
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		sel = text[startOffset:endOffset]
		print len(sel.split()), "words" 
		return ""