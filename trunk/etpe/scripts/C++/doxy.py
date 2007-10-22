""" 
Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
Author: Pierre Lamot pierre.lamot@yahoo.fr
This software is licenced under the Eclipse Public License, v 1.0. 
See the LICENSE file for details.
"""
from org.chub.etpe.engines import Engine
import re
import string
import StringIO

class doxy(Engine):
	def getDescription(self):
		return "generate doxygen comment for selected method"
	
	def getReplaceType(self):
		return Engine.REPLACE_ALL
	
	def delimRemove(self, str):
		#FIXME
		sub = str.find("<")
		if sub != -1:
			return str[:sub] + delimRemove(str[sub + 1:])
		end = str.find(">")
		if end != -1:
			return str #WTF unbalanced delim
		return str[:sub] + str[end + 1:]
		
		
	
	def getArgsName(self, line):
		"""
		extract arguments name of the curent method
		"""
		#we look for the argument position foo(bar baz, bar2 baz2, ...)
		args_d = line.find("(")
		args_f = line.rfind(")")
		if args_d == -1 or args_f == -1:
			return []
		subline = line[args_d +1: args_f]
		# from here our line only contain bar baz, bar2 baz2
		args_txt = self.delimRemove(subline).split(",")
		#["bar baz", "bar2 baz2", ...]
		return [a
				.split()[-1]
				.replace("*", "")
				.strip() for a in args_txt]
		#we split every arguments and take the last word who might be the argument name (I hope)
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		inBuf = StringIO.StringIO(text)
		strarray = inBuf.readlines()
		line = strarray[startLine]
		for i in self.getArgsName(line):
			print i
		return text