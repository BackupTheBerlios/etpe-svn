// Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
// Author: Pierre Lamot pierre.lamot@yahoo.fr
// This software is licenced under the Eclipse Public License, v 1.0. 
// See the LICENSE file for details.
package org.chub.etpe.views;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.chub.etpe.Activator;
import org.chub.etpe.engines.Engine;
import org.chub.etpe.engines.EngineFactory;
import org.chub.etpe.tools.RessourceAcces;
import org.chub.etpe.tools.TextBean;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import swing2swt.layout.BorderLayout;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart
{

	private Text text;
	private Text descr;
	private Tree tree;


	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	/**
	 * The constructor.
	 */
	public SampleView()
	{
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent)
	{

		parent.setLayout(new FormLayout());
		text = new Text(parent, SWT.BORDER);
		final FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(100, -5);
		fd_text.bottom = new FormAttachment(0, 24);
		fd_text.top = new FormAttachment(0, 5);
		text.setLayoutData(fd_text);
		text.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent e)
			{
				String txt = text.getText();
				Pattern pattern = Pattern.compile(txt + ".*");
				for (TreeItem t : tree.getItems())
				{
					String name = t.getText();
					Matcher m = pattern.matcher(name);
					if (m.matches())
					{
						tree.setSelection(t);
						if (!txt.isEmpty() && e.keyCode == SWT.CR)
						{
							String catName = null;
							TreeItem treei = tree.getSelection()[0].getParentItem();
							if (treei != null)
								catName = treei.getText();
							runScrit(catName, name);
						}
						return;
					}
				}
			}
		});

		tree = new Tree(parent, SWT.FULL_SELECTION | SWT.BORDER);
		final FormData fd_tree = new FormData();
		fd_tree.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_tree.top = new FormAttachment(text, 5, SWT.BOTTOM);
		fd_tree.left = new FormAttachment(0, 5);
		tree.setLayoutData(fd_tree);
		tree.setLayout(new RowLayout());
		tree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e)
			{
				String scriptName = tree.getSelection()[0].getText();
				String catName = null;
				TreeItem treei = tree.getSelection()[0].getParentItem();
				if (treei != null)
					catName = treei.getText();
				descr.setText(getScriptDescription(catName, scriptName));
				tree.setToolTipText(getScriptDescription(catName, scriptName));
			}
		});
		tree.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent e)
			{
				if (e.keyCode == SWT.CR)
				{
					String catName = null;
					TreeItem treei = tree.getSelection()[0].getParentItem();
					if (treei != null)
						catName = treei.getText();
					String scriptName = tree.getSelection()[0].getText();
					runScrit(catName ,scriptName);
				}
			}
		});
		tree.addMouseListener(new MouseAdapter()
		{
			public void mouseDoubleClick(MouseEvent e)
			{
				String catName = null;
				TreeItem treei = tree.getSelection()[0].getParentItem();
				if (treei != null)
					catName = treei.getText();
				String scriptName = tree.getSelection()[0].getText();
				runScrit(catName, scriptName);
			}
		});
		tree.setLinesVisible(true);
		Label searchLabel;
		searchLabel = new Label(parent, SWT.NONE);
		fd_text.left = new FormAttachment(searchLabel, 5, SWT.RIGHT);
		final FormData fd_searchLabel = new FormData();
		fd_searchLabel.bottom = new FormAttachment(text, 13, SWT.TOP);
		fd_searchLabel.top = new FormAttachment(text, 0, SWT.TOP);
		fd_searchLabel.right = new FormAttachment(0, 42);
		fd_searchLabel.left = new FormAttachment(0, 10);
		searchLabel.setLayoutData(fd_searchLabel);
		searchLabel.setText("search");

		descr = new Text(parent, SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY | SWT.MULTI | SWT.BORDER);
		fd_tree.bottom = new FormAttachment(descr, 0, SWT.TOP);
		final FormData fd_descr = new FormData();
		fd_descr.top = new FormAttachment(72, 0);
		fd_descr.right = new FormAttachment(tree, 0, SWT.RIGHT);
		fd_descr.bottom = new FormAttachment(100, -5);
		fd_descr.left = new FormAttachment(tree, 0, SWT.LEFT);
		descr.setLayoutData(fd_descr);

		for (String c : buildCatList())
		{
			TreeItem newItemTreeItem = new TreeItem(tree, SWT.NONE);
			newItemTreeItem.setText(c);
			for (String s: buildFilterList(c))
			{
				TreeItem treeItem2 = new TreeItem(newItemTreeItem, SWT.NONE);
				treeItem2.setText(s);
			}
		}
		for (String s: buildFilterList("."))
		{
			TreeItem treeItem2 = new TreeItem(tree, SWT.NONE);
			treeItem2.setText(s);
		}
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu()
	{
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener()
		{
			public void menuAboutToShow(IMenuManager manager)
			{
				SampleView.this.fillContextMenu(manager);
			}
		});
	}

	private void contributeToActionBars()
	{
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager)
	{

	}

	private void fillContextMenu(IMenuManager manager)
	{
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager)
	{
	}

	private void makeActions()
	{

	}

	private void hookDoubleClickAction()
	{
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
	{
	}

	
	
	private ArrayList<String> buildCatList()
	{
		ArrayList<String> sliste = new ArrayList<String>();
		File dir = new File(Activator.getPluginPath() + "/scripts");
		for (File f : dir.listFiles())
		{
			if (f.isDirectory() && !f.getName().startsWith("."))
				sliste.add(f.getName());
		}
		return sliste;
	}

	private ArrayList<String> buildFilterList(String parent)
	{
		ArrayList<String> sliste = new ArrayList<String>();
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.endsWith(".py");
			}
		};
		File dir = new File(Activator.getPluginPath() + "/scripts/" + parent);
		String[] dirfiles = dir.list(filter);
		if (dirfiles == null)
		{
			System.err.println("dossier vide");
			return new ArrayList<String>();
		}
		for (String f : dirfiles)
		{
			sliste.add(f);
		}
		return sliste;
	}

	private String getScriptDescription(String cat,String name)
	{
			EngineFactory engineFactory = EngineFactory.getInstance();
			Engine engine = engineFactory.getEngine(cat, name);
			return engine.getDescription();
	}
	
	private void runScrit(String cat, String name)
	{
		EngineFactory engineFactory = EngineFactory.getInstance();
		Engine engine = engineFactory.getEngine(cat, name);
		TextBean txt = RessourceAcces.getTextBean();
		String filtered = engine.filter(txt.getText(), txt
				.getSelectionStartOffset(), txt.getSelectionEndOffset(), txt
				.getSelectionStartLine(), txt.getSelectionEndLine());
		switch (engine.getReplaceType())
		{
		case Engine.REPLACE_NONE:
			return;

		case Engine.REPLACE_SELECTION:
			RessourceAcces.replaceSelectionText(filtered);
			break;

		case Engine.REPLACE_ALL:
			RessourceAcces.setDocumentText(filtered);
			break;

		default:
			break;
		}
	}
}