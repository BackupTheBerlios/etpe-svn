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
		# count bracet
		def removeFirstBracet(str):
			b_cnt = 0
			b_open_pos = str.find("<")
			b_close_pos = 0
			for (i, c) in enumerate(str):
				if c == '<':
					b_cnt += 1
				elif c == '>':
					b_cnt -= 1
					b_close_pos = i
					if b_cnt == 0:
						str =  str[:b_open_pos] + str[b_close_pos + 1:]
						return str
			if b_cnt != 0:
				raise "unbalanced string"
		while str.find("<") != -1:
			str = removeFirstBracet(str)
		return str

	def getArgsName(self, line):
		"""
		extract arguments name of the curent method
		"""
		#we look for the argument's position "type foo(bar baz, bar2 baz2, ...)"
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

	def hasReturn(self, line):
		print line.split()[0]
		if line.split()[0] == "void":
			return False
		return True
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		inBuf = StringIO.StringIO(text)
		strarray = inBuf.readlines()
		line = strarray[startLine]
		doxytxt = """
/* 
 * @brief:
 * 
"""
		doxytxt +=  "\n".join([" * @param %(name)s:" % {"name" : i} for i in self.getArgsName(line)])
		if self.hasReturn(line):
			doxytxt += "\n *\n * @return:"
		doxytxt += "\n */\n"	 
		strarray.insert(startLine, doxytxt)
		return string.join(strarray, "")