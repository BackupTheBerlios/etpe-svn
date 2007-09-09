from org.chub.etpe.engines import Engine
import string
import StringIO

class flipline(Engine):
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