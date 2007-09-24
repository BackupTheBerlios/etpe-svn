// Copyright (c) 2007 by Pierre Lamot, All Rights Reserved.
// Author: Pierre Lamot pierre.lamot@yahoo.fr
// This software is licenced under the Eclipse Public License, v 1.0. 
// See the LICENSE file for details.
package org.chub.etpe.tools;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class RessourceAcces
{
	private static ITextEditor getTextEditor()
	{
		IWorkbench worbench = PlatformUI.getWorkbench();
		IEditorPart activeEditor = worbench.getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		if (activeEditor instanceof ITextEditor)
		{
			return (ITextEditor) activeEditor;
		}
//		if (activeEditor instanceof FormEditor)
//		{
//			FormEditor fe = (FormEditor) activeEditor;
//			return (ITextEditor) fe.getActiveEditor();
//		}
		// TODO gerer pour le multi editor cf FilterActionDelegate dans
		// eclipseExEditor
		return null;
	}

	private static ITextSelection getSelection()
	{
		ITextEditor textEditor = getTextEditor();
		final ISelectionProvider sp = textEditor.getEditorSite()
				.getSelectionProvider();
		// la selection
		return (ITextSelection) sp.getSelection();
	}

	public static String getDocumentText()
	{
		ITextEditor textEditor = getTextEditor();
		IDocumentProvider dp = textEditor.getDocumentProvider();
		IDocument doc = dp.getDocument(textEditor.getEditorInput());
		return doc.get();
	}
	
	public static String getDocumentTitle()
	{
		ITextEditor textEditor = getTextEditor();
		return textEditor.getTitle();
	}

	public static TextBean getTextBean()
	{
		TextBean txtBean = new TextBean();
		txtBean.setText(getDocumentText());

		ITextSelection txtSelection = getSelection();
		txtBean.setSelectionStartOffset(txtSelection.getOffset());
		txtBean.setSelectionEndOffset(txtSelection.getOffset()
				+ txtSelection.getLength());
		txtBean.setSelectionStartLine(txtSelection.getStartLine());
		txtBean.setSelectionEndLine(txtSelection.getEndLine());

		return txtBean;
	}

	public static void setDocumentText(String text)
	{
		ITextEditor textEditor = getTextEditor();
		IDocumentProvider dp = textEditor.getDocumentProvider();
		IDocument doc = dp.getDocument(textEditor.getEditorInput());
		doc.set(text);
	}

	public static void replaceSelectionText(String filtered)
	{
		try
		{
			ITextEditor textEditor = getTextEditor();

			IDocumentProvider dp = textEditor.getDocumentProvider();
			IDocument doc = dp.getDocument(textEditor.getEditorInput());

			final ISelectionProvider sp = textEditor.getEditorSite()
					.getSelectionProvider();
			ITextSelection sel = (ITextSelection) sp.getSelection();
			int offset = sel.getOffset();

			doc.replace(offset, sel.getLength(), filtered);
			sp.setSelection(new TextSelection(doc, offset, filtered.length()));
			
		} catch (BadLocationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}