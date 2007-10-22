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

class getset(Engine):
	def getDescription(self):
		return "generate getters and setters in C++ context, select the attribute to be generated"
	
	def getReplaceType(self):
		return Engine.REPLACE_ALL
	
	def getClassName(self, strarray):
		pattern = re.compile(".*class(?!.*;).*", re.IGNORECASE)
		#we take the first ligne containing class word and no ";" which can be forward declaration, 
		#we assume there is only one class per file
		classLine = [l for l in strarray if pattern.match(l)][0] 
		classWordIndex = classLine.index("class")
		classLine = classLine[classWordIndex + 5:-1]
		classExtendIndex =  classLine.find(":") 
		if classExtendIndex != -1:
			classLine = classLine[0:classExtendIndex]
		classExtendIndex =  classLine.find("{") 
		if classExtendIndex != -1:
			classLine = classLine[0:classExtendIndex]
		return classLine.strip()
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		inBuf = StringIO.StringIO(text)
		strarray = inBuf.readlines()
		sel = text[startOffset:endOffset]
		words = sel.split()
		type = string.join(words[0:-1], " ")
		var = words[-1]
		if var[-1] == ";":
			var = var[0:-1]
		className = self.getClassName(strarray)
		params = {'type' : type, 'class' : className, 'var' : var}
		txtHeader = """
	%(type)s get%(var)s() const;
	void set%(var)s(%(type)s my%(var)s);
		""" % params
		
		
		txtclip="""
%(type)s %(class)s::get%(var)s() const
{
	return %(var)s;
}

void %(class)s::set%(var)s(%(type)s my%(var)s)
{
	%(var)s = my%(var)s;
}
		"""  % params

		self.setClipboard(txtclip)
		
		#we try to find where to insert the getter and the setter in the header
		pattern = re.compile(".*public:.*", re.IGNORECASE)
		lastPublic = [l[0] for l in enumerate(strarray) if pattern.match(l[1])][-1] 
		strarray.insert(lastPublic + 1, txtHeader)
		
		print "getter and setter's body copied to clipboard, paste it to", self.getDocumentTitle()[0:-3] + ".cc"
		
		return string.join(strarray, "")