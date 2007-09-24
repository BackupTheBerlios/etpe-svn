// Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
// Author: Pierre Lamot pierre.lamot@yahoo.fr
// This software is licenced under the Eclipse Public License, v 1.0. 
// See the LICENSE file for details.

package org.chub.etpe.engines;


import java.util.HashMap;
import java.util.Properties;

import org.chub.etpe.Activator;
import org.python.core.PyException;
import org.python.util.PythonInterpreter;



public class EngineFactory
{
	private static HashMap<String, Engine> loadedEngines = new HashMap<String, Engine>();
	private static EngineFactory instance = null;
	
	private EngineFactory()
	{
		Properties props = new Properties();
		props.setProperty("python.path", Activator.getPluginPath() + "/Lib/");
		PythonInterpreter.initialize(System.getProperties(), props,
		                             new String[] {""});
	}
	
	public static EngineFactory getInstance()
	{
		if (instance == null)
			instance = new EngineFactory();
		return instance;
	}
	
	public Engine getEngine(String cat, String name) 
	{
//		if (loadedEngines.containsKey(name))
//			return loadedEngines.get(name);
		if (name.endsWith(".py"))
		{
			Engine engine = getPythonEngine(cat, name);
			loadedEngines.put(name, engine);
			return engine;
		}
		throw (new Error("No engine for running " + name));
	}
	
	private Engine getPythonEngine(String cat, String name)
	{
		try
		{
			PythonInterpreter interpreter = new PythonInterpreter();
			if (cat != null)
				interpreter.execfile(Activator.getPluginPath() + "/scripts/" + cat + "/"+ name);
			else
				interpreter.execfile(Activator.getPluginPath() + "/scripts/" + name);
			interpreter.exec("obj = " + name.substring(0, name.lastIndexOf(".")) + "()");
			return (Engine) interpreter.get("obj").__tojava__(Engine.class);
		}
		catch (NoClassDefFoundError e) {
			System.err.println(e.toString());
			throw e;
		}
		catch (PyException e) {
			System.err.println(e.toString());
			throw e;
		}
	}
}
