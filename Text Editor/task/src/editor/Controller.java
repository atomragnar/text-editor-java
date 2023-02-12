package editor;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface Controller {

    void setView(final View view);

    void setTextArea(JTextArea textArea);

    void setSearchTextField(JTextField searchTextField);

    void setJFileChooser(JFileChooser jFileChooser);


    void setRegexCheckBox(JCheckBox regexCheckBox);

    ActionListener getSaveAction();

    ActionListener getOpenAction();

    ActionListener getExitAction();

    ActionListener getSearchAction();

    ActionListener getPrevMatchAction();

    ActionListener getNextMatchAction();

    ActionListener getCheckBoxAction();



}
