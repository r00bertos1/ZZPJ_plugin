package com.github.r00bertos1.zzpjplugin.actions;

import com.github.r00bertos1.zzpjplugin.services.QuerySearch;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class SearchGoogleAction extends AnAction {
    @Override
    public void update(@NotNull final AnActionEvent e) {
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setEnabledAndVisible( project != null
                && editor != null
                && editor.getSelectionModel().hasSelection() );
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();

        String queryString = primaryCaret.getSelectedText();
        queryString = queryString.trim();
        if (StringUtil.isEmpty(queryString)) {
            return;
        }
        String stringURL = null;

        try {
            stringURL = QuerySearch.createSearchQuery(queryString);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        String finalStringURL = stringURL;
        WriteCommandAction.runWriteCommandAction(project, () ->{
                    try {
                        QuerySearch.search(finalStringURL);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
        );

        primaryCaret.removeSelection();
    }
}
