package main.window.repository;

import main.MVCCDElementApplicationPreferences;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.MCDDiagrams;
import mcd.MCDEntities;
import mcd.MCDEntity;
import mcd.MCDModels;
import mcd.services.MCDEntityService;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import project.Project;
import utilities.Debug;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import window.editor.entity.EntityEditor;
import window.editor.preferences.MCD.PrefMCDEditor;
import window.editor.preferences.application.PrefApplicationEditor;
import window.editor.preferences.general.PrefGeneralEditor;
import window.editor.project.ProjectEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinRepositoryPopupMenu extends JPopupMenu{
    private DefaultMutableTreeNode node ;

    public WinRepositoryPopupMenu(DefaultMutableTreeNode node) {
        this.node = node;
        init();
    }

    private void init() {
        MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        if(node.getUserObject() instanceof MVCCDElementApplicationPreferences){
            treatApplicationPref(mvccdWindow);
        }

        if(node.getUserObject() instanceof Preferences){
            treatPreferences(mvccdWindow);
        }

        if(node.getUserObject() instanceof Project){
            treatProject(mvccdWindow);
        }

        if (node.getUserObject() instanceof MCDModels) {
            if (Preferences.REPOSITORY_MCD_MODELS_MANY) {
                final JMenuItem mcdModelsCreate = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.model"));
                this.add(mcdModelsCreate);
                mcdModelsCreate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Debug.println("Création d'un nouveau modèle");
                    }
                });
            }
        }

        if (node.getUserObject() instanceof MCDDiagrams) {
            JMenuItem mcdDiagramsNewDiagram = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.diagram"));
            this.add(mcdDiagramsNewDiagram);
            mcdDiagramsNewDiagram.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Debug.println("Création d'un nouveau diagramme");
                }
            });
        }

        if (node.getUserObject() instanceof MCDEntities) {
            JMenuItem mcdEntitiesNewEntity = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.new.entity"));
            this.add(mcdEntitiesNewEntity);
            mcdEntitiesNewEntity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Debug.println("Création d'une nouvelle entité");
                    EntityEditor fen = showEditorEntity(mvccdWindow , node, EntityEditor.NEW);
                }
            });
        }

        if (node.getUserObject() instanceof MCDEntity) {
            MCDEntity mcdEntity = (MCDEntity) node.getUserObject();
            JMenuItem mcdEntityEdit = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.entity"));
            this.add(mcdEntityEdit);
            mcdEntityEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Debug.println("Modification de l'entité");
                    EntityEditor fen = showEditorEntity(mvccdWindow , node, EntityEditor.UPDATE);
                }
            });

            JMenuItem mcdEntityCheck = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.check.entity"));
            this.add(mcdEntityCheck);
            mcdEntityCheck.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Debug.println("Contrôle de conformité de l'entité");
                    if (MCDEntityService.check(mcdEntity).size() == 0){
                        String message = MessagesBuilder.getMessagesProperty ("dialog.check.entity.ok", new String[] {mcdEntity.getName()});
                        DialogMessage.showOk(mvccdWindow, message);
                    } else {
                        String message = MessagesBuilder.getMessagesProperty ("dialog.check.entity.error", new String[] {mcdEntity.getName()});
                        if (DialogMessage.showConfirmYesNo_Yes(mvccdWindow, message) == JOptionPane.YES_OPTION){
                            Debug.println("Correction de l'entité");
                            EntityEditor fen = showEditorEntity(mvccdWindow , node, EntityEditor.UPDATE);
                        }
                    }
                }
            });

        }

    }

    private void treatProject(MVCCDWindow mvccdWindow) {
        JMenuItem editProject = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.project"));
        this.add(editProject);
        editProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ProjectEditor fen = new ProjectEditor(mvccdWindow, node, DialogEditor.UPDATE);
                fen.setVisible(true);
            }
        });


    }

    private void treatApplicationPref(MVCCDWindow mvccdWindow) {
        Preferences preferences = PreferencesManager.instance().getApplicationPref();
        JMenuItem preferencesApplication = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.application.preferences"));
        this.add(preferencesApplication);
        preferencesApplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PrefApplicationEditor fen = new PrefApplicationEditor(mvccdWindow , node, DialogEditor.UPDATE);
                fen.setVisible(true);
            }
        });
    }


    private void treatPreferences(MVCCDWindow mvccdWindow) {

        DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();

        String menuText = "";
        boolean editProfile = false;
        boolean editProject = false;
        if (nodeParent.getUserObject() instanceof Profile){
            menuText = MessagesBuilder.getMessagesProperty("menu.edit.preferences.profile");
            editProfile = true;
        } else if (nodeParent.getUserObject() instanceof Project){
            menuText = MessagesBuilder.getMessagesProperty("menu.edit.preferences.project");
            editProject = true;
        }
        boolean finalEditProfile = editProfile;


        Preferences preferences = (Preferences) node.getUserObject();
        JMenu preferencesEdit = new JMenu(menuText);
        this.add(preferencesEdit);

        JMenuItem preferencesEditGeneral = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.general.preferences"));
        preferencesEdit.add(preferencesEditGeneral);
        preferencesEditGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PrefGeneralEditor fen = new PrefGeneralEditor(mvccdWindow , node, DialogEditor.UPDATE);
                if (finalEditProfile){
                   fen.setTitle(MessagesBuilder.getMessagesProperty("preferences.profile.general.read"));
                   fen.setReadOnly(true);
                }
               fen.setVisible(true);
            }
        });

        JMenuItem preferencesEditMCD = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.edit.mcd.preferences"));
            preferencesEdit.add(preferencesEditMCD);
            preferencesEditMCD.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    PrefMCDEditor fen = new PrefMCDEditor(mvccdWindow, node, DialogEditor.UPDATE);
                    if (finalEditProfile) {
                        fen.setTitle(MessagesBuilder.getMessagesProperty("preferences.profile.mcd.read"));
                        fen.setReadOnly(true);
                    }
                    fen.setVisible(true);
                }
        });

        if (editProject) {
            JMenuItem preferencesExportProfil = new JMenuItem(MessagesBuilder.getMessagesProperty("menu.export.profil.preferences"));
            this.add(preferencesExportProfil);
            preferencesExportProfil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    PreferencesManager.instance().createProfile();
                }
            });
        }

    }


    private EntityEditor showEditorEntity(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode) {
        EntityEditor fen = new EntityEditor(mvccdWindow , node, mode);
        fen.setVisible(true);
        return fen;
    }
}
