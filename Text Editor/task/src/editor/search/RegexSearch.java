package editor.search;

import editor.TextEditorController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSearch extends SearchMethod {

    private final Map<Integer, Integer> index;
    private final Map<Integer, Integer> length;
    private int currentKey;
    private int totalFound;

    public RegexSearch(String text, String pattern, TextEditorController controller) {
        super(controller);
        currentKey = 1;
        index = new HashMap<>();
        length = new HashMap<>();

        SwingWorker<Void, Void> regexWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                final Pattern regex = Pattern.compile(pattern);
                final Matcher matcher = regex.matcher(text);
                totalFound = 0;
                while (matcher.find()) {
                    totalFound++;
                    index.put(totalFound, matcher.start());
                    length.put(totalFound, matcher.group().length());
                }
                return null;
            }

            @Override
            protected void done() {
                highlightTextArea();
            }

        };

        regexWorker.execute();

    }

    @Override
    public void findNext() {
        if (totalFound > 0) {
            executor.execute(() -> {
                currentKey = currentKey == totalFound ? 1 : currentKey + 1;
                highlightTextArea();
            });
        }
    }

    @Override
    public void findPrev() {
        if (totalFound > 0) {
            executor.execute(() -> {
                currentKey = currentKey == 1 ? totalFound : currentKey - 1;
                highlightTextArea();
            });
        }
    }

    @Override
    public int getPos() {
        return index.get(currentKey);
    }

    @Override
    public int getEndIndex() {
        return index.get(currentKey) + length.get(currentKey);
    }
}
