package editor.search;

import editor.TextEditorController;

public class EmptySearch implements Search {

    @Override
    public void findNext() {

    }

    @Override
    public void findPrev() {

    }

    @Override
    public int getPos() {
        return 0;
    }

    @Override
    public int getEndIndex() {
        return 0;
    }
}
