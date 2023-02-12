package editor;


/*

JTextArea component to "TextArea"
Search field to "SearchField"
Button that saves the file to "SaveButton"
Button that opens a filemanager to "OpenButton"
Start search button to "StartSearchButton"
Previous match button to "PreviousMatchButton"
Next match button to "NextMatchButton"
Use regex checkbox to "UseRegExCheckbox"
JFileChooser to "FileChooser"
ScrollPane to "ScrollPane"
File option in menu to "MenuFile"
Search option in menu to "MenuSearch"
Open option in menu to "MenuOpen"
Save option in menu to "MenuSave"
Exit option in menu to "MenuExit"
Start search option in menu to "MenuStartSearch"
Previous match option in menu to "MenuPreviousMatch"
Next match option in menu to "MenuNextMatch"
Use regex option in menu to "MenuUseRegExp"

 */

public interface View {


    void closeApp();

    void displayWarning(String message);

}
