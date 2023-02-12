package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class TextEditor extends JFrame implements View {

    private final Dimension SCREEN_SIZE = new Dimension(500, 500);
    private static final Dimension BUTTON_SIZE = new Dimension(25, 25);
    private static final Dimension SEARCH_FIELD_SIZE = new Dimension(30, 25);
    private final Controller controller = new TextEditorController();

    private static final Map<String, String> NAME_TO_ICON_PATH = Map.of(
            "OpenButton", "C:/Users/Johannes/IdeaProjects/Text Editor1/Text Editor/task/src/editor/icons/open.png",
            "SaveButton", "C:/Users/Johannes/IdeaProjects/Text Editor1/Text Editor/task/src/editor/icons/save.png",
            "StartSearchButton", "C:/Users/Johannes/IdeaProjects/Text Editor1/Text Editor/task/src/editor/icons/search.png",
            "PreviousMatchButton", "C:/Users/Johannes/IdeaProjects/Text Editor1/Text Editor/task/src/editor/icons/left-arrow.png",
            "NextMatchButton", "C:/Users/Johannes/IdeaProjects/Text Editor1/Text Editor/task/src/editor/icons/right-arrow.png"
    );


    private static final Map<String, String> NAME_TO_TEXT = Map.of(
            "MenuFile", "File",
            "MenuSearch", "Search",
            "MenuOpen", "Open",
            "MenuSave", "Save",
            "MenuExit", "Exit",
            "MenuStartSearch", "Start search",
            "MenuPreviousMatch", "Previous match",
            "MenuNextMatch", "Next match",
            "MenuUseRegExp", "Use regular expressions"
    );


    public TextEditor() {
        this.controller.setView(this);
        setJFrame();
        createFileChooser();
        createTextArea();
        createOperationsPanel();
        createJMenuBar();
        setVisible(true);

    }

    private static <T extends AbstractButton> T createAbstractButton(final T button, final String name, final ActionListener actionListener) {
        button.setName(name);
        button.addActionListener(actionListener);
        return button;
    }

    private static JButton createButton(final String name, final ActionListener actionListener) {
        final JButton button = new JButton(new ImageIcon(NAME_TO_ICON_PATH.get(name)));
        button.setPreferredSize(BUTTON_SIZE);
        return createAbstractButton(button, name, actionListener);
    }

    private static void createMenuItem(final JMenu menu, final String name, final ActionListener actionListener) {
        final JMenuItem item = new JMenuItem();
        item.setText(NAME_TO_TEXT.get(name));
        menu.add(createAbstractButton(item, name, actionListener));
    }

    private void setJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Text Editor");
        setSize(SCREEN_SIZE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

    }


    private void createTextArea() {
        JPanel textPanel = new JPanel();



        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        setMargin(textArea, 1, 1, 1, 1);


        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setName("ScrollPane");
        setMargin(textArea, 1, 1, 1, 1);

        textPanel.add(scrollPane);

        add(scrollPane, BorderLayout.CENTER);
        this.controller.setTextArea(textArea);

    }

    private JTextField createSearchTextField() {
        final JTextField searchField = new JTextField(15);
        searchField.setPreferredSize(SEARCH_FIELD_SIZE);
        searchField.setName("SearchField");
        setMargin(searchField, 1, 1, 1, 1);

        this.controller.setSearchTextField(searchField);

        return searchField;
    }

    private JCheckBox createCheckBox() {
        final JCheckBox regexCheckBox = new JCheckBox("Use regex");
        regexCheckBox.setName("UseRegExCheckbox");
        this.controller.setRegexCheckBox(regexCheckBox);
        return regexCheckBox;
    }


    private void createOperationsPanel() {
        final JPanel operationsPanel = new JPanel(new FlowLayout());
        operationsPanel.add(createButton("SaveButton", this.controller.getSaveAction()));
        operationsPanel.add(createButton("OpenButton", this.controller.getOpenAction()));
        operationsPanel.add(createSearchTextField());
        operationsPanel.add(createButton("StartSearchButton", this.controller.getSearchAction()));
        operationsPanel.add(createButton("PreviousMatchButton", this.controller.getPrevMatchAction()));
        operationsPanel.add(createButton("NextMatchButton", this.controller.getNextMatchAction()));
        operationsPanel.add(createCheckBox());

        add(operationsPanel, BorderLayout.NORTH);
    }

    private static JMenu setupMenu(final String name) {
        final JMenu menu = new JMenu(NAME_TO_TEXT.get(name));
        menu.setName(name);
        return menu;
    }

    private JMenu createFileMenu() {
        final JMenu menuFile = setupMenu("MenuFile");
        createMenuItem(menuFile, "MenuOpen", this.controller.getOpenAction());
        createMenuItem(menuFile, "MenuSave", this.controller.getSaveAction());
        createMenuItem(menuFile, "MenuExit", this.controller.getExitAction());
        return menuFile;

    }

    private JMenu createSearchMenu() {
        final JMenu menuSearch = setupMenu("MenuSearch");
        createMenuItem(menuSearch, "MenuStartSearch", this.controller.getSearchAction());
        createMenuItem(menuSearch, "MenuPreviousMatch", this.controller.getPrevMatchAction());
        createMenuItem(menuSearch, "MenuNextMatch", this.controller.getNextMatchAction());
        createMenuItem(menuSearch, "MenuUseRegExp", this.controller.getCheckBoxAction());
        return menuSearch;
    }

    private void createJMenuBar() {
        final JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(createFileMenu());
        jMenuBar.add(createSearchMenu());
        setJMenuBar(jMenuBar);
    }

    private void createFileChooser() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        add(fileChooser, BorderLayout.PAGE_END);
        fileChooser.setVisible(false);
        this.controller.setJFileChooser(fileChooser);
    }


    public void closeApp() {
        this.dispose();
    }

    public void displayWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.WARNING_MESSAGE);
    }


    public static void setMargin(JComponent aComponent, int aTop,
                                 int aRight, int aBottom, int aLeft) {
        Border border = aComponent.getBorder();
        Border marginBorder = new EmptyBorder(new Insets(aTop, aLeft,
                aBottom, aRight));
        aComponent.setBorder(border == null ? marginBorder
                : new CompoundBorder(marginBorder, border));
    }
}

