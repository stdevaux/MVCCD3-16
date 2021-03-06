package window.editor.preferences.general;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefGeneralInputContent extends PanelInputContent {
    private JPanel panel = new JPanel();

    public PrefGeneralInputContent(PrefGeneralInput prefGeneralInput) {
        super(prefGeneralInput);
        prefGeneralInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
    }

    private void createContent() {

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

    }

    @Override
    protected boolean checkDatas() {
        return true;
    }

    @Override
    public boolean checkDatasPreSave() {
        return true;
    }

    @Override
    protected void changeField(DocumentEvent e) {

    }

    @Override
    protected void changeField(ChangeEvent e) {

    }

    @Override
    protected void changeField(ItemEvent e) {

    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

    }

    @Override
    protected void initDatas(MVCCDElement mvccdElement) {

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

    }


}

