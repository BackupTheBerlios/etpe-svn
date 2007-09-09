from org.chub.etpe.engines import Engine
import string

class capwords(Engine):
	def getReplaceType(self):
		return 2
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		selection = text[startOffset : endOffset]
		return string.capwords(selection)