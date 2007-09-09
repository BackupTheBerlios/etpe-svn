from org.chub.etpe.engines import Engine
from java.awt import Toolkit
from java.awt.datatransfer import StringSelection
import string
import StringIO

class square_copy(Engine):
	def _setClipboard(self, txt):
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(StringSelection(txt), None)

	def getReplaceType(self):
		return Engine.REPLACE_NONE
	
	def filter(self, text, startOffset, endOffset, startLine, endLine):
		inBuf = StringIO.StringIO(text)
		strarrray = inBuf.readlines()
		startPad = startOffset - reduce(lambda x, y : x + y, [len(l) for l in strarrray[:startLine]])
		endPad = endOffset - reduce(lambda x, y : x + y, [len(l) for l in strarrray[:endLine]])
		txt = reduce(lambda x, y: x + "\n" +  y, [s[startPad:endPad] for s in strarrray[startLine:endLine]])
		self._setClipboard(txt)