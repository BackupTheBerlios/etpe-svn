from org.chub.etpe.engines import Engine

class wordcount(Engine):
	def getReplaceType(self):
		return Engine.REPLACE_NONE
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		sel = text[startOffset:endOffset]
		print len(sel.split()), "words" 
		return ""