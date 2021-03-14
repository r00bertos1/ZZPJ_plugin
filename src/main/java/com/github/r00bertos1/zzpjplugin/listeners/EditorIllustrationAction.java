package com.github.r00bertos1.zzpjplugin.listeners;

import com.github.r00bertos1.zzpjplugin.services.QuerySearch;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.io.IOException;

public class EditorIllustrationAction extends AnAction {
    @Override
    public void update(@NotNull final AnActionEvent e) {
        // Get required data keys
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        // Set visibility only in case of existing project and editor and if a selection exists
        e.getPresentation().setEnabledAndVisible( project != null
                && editor != null
                && editor.getSelectionModel().hasSelection() );
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        // Get all the required data from data keys
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();


        // Work off of the primary caret to get the selection info
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();
        String queryString = primaryCaret.getSelectedText();
        String stringURL = null;
        try {
            stringURL = QuerySearch.createSearchQuery(queryString);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // Replace the selection with a fixed string.
        // Must do this document change in a write action context.

        String finalStringURL = stringURL;
        WriteCommandAction.runWriteCommandAction(project, () ->{
                    try {
                        QuerySearch.search(finalStringURL);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

        );

        // De-select the text range that was just replaced
        primaryCaret.removeSelection();
    }


}
