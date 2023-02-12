package editor;

import editor.search.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextEditorController implements Controller {

    private View view;
    private JTextArea textArea;
    private JTextField searchTextField;
    private JFileChooser jFileChooser;
    private JCheckBox regexCheckBox;
    private Search searchMethod = new EmptySearch();


    private final ActionListener saveAction = actionEvent -> {
        actionOnSave();
    };
    private final ActionListener openAction = actionEvent -> {
        actionOnOpen();
    };
    private final ActionListener exitAction = actionEvent -> {
        this.view.closeApp();
    };

    private final ActionListener searchAction = actionEvent -> {
        actionOnSearch();
    };
    private final ActionListener prevMatchAction = actionEvent -> {
        actionOnPrevMatch();
    };
    private final ActionListener nextMatchAction = actionEvent -> {
        actionOnNextMatch();
    };
    private final ActionListener checkBoxAction = actionEvent -> {
        actionOnCheckBoxChecked();
    };

    public TextEditorController() {

    }

    public ActionListener getSaveAction() {
        return saveAction;
    }

    public ActionListener getOpenAction() {
        return openAction;
    }

    public ActionListener getExitAction() {
        return exitAction;
    }

    public ActionListener getSearchAction() {
        return searchAction;
    }

    public ActionListener getPrevMatchAction() {
        return prevMatchAction;
    }

    public ActionListener getNextMatchAction() {
        return nextMatchAction;
    }

    public ActionListener getCheckBoxAction() {
        return checkBoxAction;
    }

    public void setView(final View view) {
        this.view = view;
    }

    @Override
    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void setSearchTextField(JTextField searchTextField) {
        this.searchTextField = searchTextField;
    }

    public void setJFileChooser(JFileChooser jFileChooser) {
        this.jFileChooser = jFileChooser;
    }

    public void setRegexCheckBox(JCheckBox regexCheckBox) {
        this.regexCheckBox = regexCheckBox;
    }

    private void actionOnOpen() {

        final SwingWorker<Void, Void> openWorker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() {

                jFileChooser.setVisible(true);

                File file = jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
                        ? jFileChooser.getSelectedFile()
                        : null;

                if (file == null || !Files.isRegularFile(file.toPath()) || !file.exists()) {
//                    displayWarning("File cannot be loaded");
                    textArea.setText("");
                } else {
                    try {
                        Path path = Path.of(file.getPath());
                        textArea.setText(Files.readString(path));
                    } catch (IOException e) {
                        textArea.setText("");
//                        displayWarning("File cannot be loaded");
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                jFileChooser.setVisible(false);
            }
        };

        openWorker.execute();
    }

    private void actionOnSave() {

        final SwingWorker<Void, Void> saveWorker = new SwingWorker<Void, Void>() {


            @Override
            protected Void doInBackground() {

                jFileChooser.setVisible(true);

                File file = jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION
                        ? jFileChooser.getSelectedFile()
                        : null;

                if (file == null) {
//                    displayWarning("File cannot be saved");
                } else {

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath(), false))) {
                        if (file.createNewFile()) {
                            writer.write(textArea.getText());
                        } else if (file.exists() && file.isFile()) {
                            writer.write(textArea.getText());
                        }
                    } catch (IOException e) {
//                        displayWarning("File cannot be saved");
                    }
                }
                return null;
            }
        };

        saveWorker.execute();
    }

            private void actionOnSearch() {
                searchMethod = regexCheckBox.isSelected()
                        ? new RegexSearch(textArea.getText(), searchTextField.getText(), this)
                        : new PlainSearch(textArea.getText(), searchTextField.getText(), this);
            }

            private void actionOnPrevMatch() {
                searchMethod.findPrev();
            }

            private void actionOnNextMatch() {
                searchMethod.findNext();
            }

            private void actionOnCheckBoxChecked() {
                regexCheckBox.setSelected(!regexCheckBox.isSelected());
            }


            public void displayWarning(String message) {
                this.view.displayWarning(message);
            }

            public void highlightText(final int index, final int endIndex) {
                if (index >= 0) {
                    this.textArea.setCaretPosition(endIndex);
                    this.textArea.select(index, endIndex);
                    this.textArea.grabFocus();
                }
            }

        }


