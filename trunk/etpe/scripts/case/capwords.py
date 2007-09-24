""" 
Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
Author: Pierre Lamot pierre.lamot@yahoo.fr
This software is licenced under the Eclipse Public License, v 1.0. 
See the LICENSE file for details.
"""
from org.chub.etpe.engines import Engine
import string

class capwords(Engine):
	def getDescription(self):
		return "capitalize each word of the selection"
	
	def getReplaceType(self):
		return Engine.REPLACE_SELECTION
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		selection = text[startOffset : endOffset]
		return string.capwords(selection)