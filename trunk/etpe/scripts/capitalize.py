from org.chub.etpe.engines import Engine

class capitalize(Engine):
	def getReplaceType(self):
		return Engine.REPLACE_SELECTION
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		selection = text[startOffset : endOffset]
		return selection.capitalize()