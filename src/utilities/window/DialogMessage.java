package utilities.window;

import messages.MessagesBuilder;

import javax.swing.*;
import java.awt.*;

public class DialogMessage {


    public static void showError(Window owner, String message){
        String title = MessagesBuilder.getMessagesProperty("dialog.error");
        JOptionPane.showMessageDialog(owner, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorEditor(Window owner){
        String message = MessagesBuilder.getMessagesProperty("editor.check.datas.error");
        showError(owner, message);
    }

    public static void showOk(Window owner, String message){
        String title = MessagesBuilder.getMessagesProperty("dialog.information");
        JOptionPane.showMessageDialog(owner, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmYesNo_Yes(Window owner, String message) {
        String title = MessagesBuilder.getMessagesProperty("dialog.confirm");
        return JOptionPane.showConfirmDialog(owner, message, title, JOptionPane.YES_NO_OPTION);
    }

    public static int showConfirmYesNo_No(Window owner, String message) {
        String title = MessagesBuilder.getMessagesProperty("dialog.confirm");
        String jButtonOui = "Oui";
        String jButtonNon = "Non";
        Object[] options = {"Oui", "Non"};
        return JOptionPane.showOptionDialog(owner, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[1] );
    }


}
