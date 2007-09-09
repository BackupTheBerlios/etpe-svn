from org.chub.etpe.engines import Engine

class upper(Engine):
	def getReplaceType(self):
		return 2
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		selection = text[startOffset : endOffset]
		return selection.upper()