package editor.search;

import editor.TextEditorController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class SearchMethod implements Search {

    private final TextEditorController controller;

    protected final ExecutorService executor;


    public SearchMethod(TextEditorController controller) {
        this.controller = controller;
        this.executor = Executors.newSingleThreadExecutor();
    }


    protected synchronized void highlightTextArea() {
        controller.highlightText(getPos(), getEndIndex());
    }

}
