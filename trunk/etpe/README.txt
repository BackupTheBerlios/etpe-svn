ETPE (Eclipse Text Processor Engine) is a plugin for eclipse wich allow to run script to process the text buffer. 

INSTALATION
just put it in your eclipse plugin directoy

MAKE YOUR OWN SCRIPT
just put them in "<ETPE Directory>/scripts/"
all scripts must extend Engine from package org.chub.etpe.engines, and implement getReplaceType and filter methods

filter: take the text buffer process it (your job) and return it

getReplaceType give the action to do after exectuting the script :
	Engine.REPLACE_ALL: replace the whole text by filter ouput
	Engine.REPLACE_SELECTED: replace the selection by filter ouput
	Engine.REPLACE_NONE: don't replace anything
	
If you made some usefull (or useless ;-) ) script you can send them at chub@users.berlios.de I'll be very glad to add them to the next release