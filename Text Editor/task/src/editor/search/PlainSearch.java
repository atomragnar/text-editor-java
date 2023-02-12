package editor.search;

import editor.TextEditorController;

import javax.swing.*;
import java.util.*;

public class PlainSearch extends SearchMethod {
    private final String text;
    private final String pattern;
    private int currentIndex;

    private boolean isInitialIndexSet;

    private class SearchNextPos extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() {
            if (!isInitialIndexSet) {
                setCurrentIndex(text.indexOf(pattern));
                isInitialIndexSet = true;
            } else {
                updateNextPos();
            }
            return null;
        }

        @Override
        protected void done() {
            if (currentIndex != -1) {
                highlightTextArea();
            }
        }

    }

    private class SearchPrevPos extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() {
            updatePrevPos();
            return null;
        }

        @Override
        protected void done() {
            if (currentIndex != -1) {
                highlightTextArea();
            }
        }


    }


    public PlainSearch(String text, String pattern, TextEditorController controller) {
        super(controller);
        this.text = text;
        this.pattern = pattern;
        this.isInitialIndexSet = false;
        findNext();
    }


    private void setCurrentIndex(int index) {
        this.currentIndex = index;
    }


    private void updateNextPos() {
        if (currentIndex + pattern.length() > text.length()) {
            this.currentIndex = text.indexOf(text);
        } else {
            int newIndex = currentIndex + pattern.length();
            String substring = text.substring(newIndex);
            this.currentIndex = substring.contains(pattern)
                    ? newIndex + substring.indexOf(pattern)
                    : text.indexOf(text);
        }
    }


    private void updatePrevPos() {
        if (currentIndex - pattern.length() < 0) {
            this.currentIndex = text.lastIndexOf(pattern);
        } else {
            String substring = text.substring(0, currentIndex);
            this.currentIndex = substring.contains(pattern)
                    ? substring.lastIndexOf(pattern)
                    : text.lastIndexOf(pattern);
        }
    }

    @Override
    public void findNext() {
        if (currentIndex != -1) {
            executor.execute(new SearchNextPos());
        }
    }


    @Override
    public void findPrev() {
        if (currentIndex != -1) {
            executor.execute(new SearchPrevPos());
        }
    }

    @Override
    public int getPos() {
        return currentIndex;
    }

    @Override
    public int getEndIndex() {
        return currentIndex + pattern.length();
    }
}
