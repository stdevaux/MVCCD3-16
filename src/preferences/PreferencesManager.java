package preferences;

import profile.ProfileFileChooser;
import project.ProjectFileChooser;

import java.io.*;

public class PreferencesManager {

    private static PreferencesManager instance ;

    private Preferences defaultPref;
    private Preferences applicationPref;
    private Preferences profilePref;
    private Preferences projectPref;

    public static synchronized PreferencesManager instance(){
        if(instance == null){
            instance = new PreferencesManager();
        }
        return instance;
    }

    public PreferencesManager() {
        defaultPref = new Preferences(null, null);
    }

    public Preferences preferences (){
        if (projectPref != null){
            return projectPref;
        } else  if (applicationPref != null){
            return applicationPref;
        } else  if (defaultPref != null){
            return defaultPref;
        } else {
            return null;
        }
    }

    public Preferences getDefaultPref() {
        return defaultPref;
    }

    public Preferences getApplicationPref() {
        return applicationPref;
    }

    public Preferences getProfilePref() {
        return profilePref;
    }

    public void setProfilePref(Preferences profilePref) {

        this.profilePref = profilePref;
    }

    public Preferences getProjectPref() {

        return projectPref;
    }

    public void setProjectPref(Preferences projectPref) {
        this.projectPref = projectPref;
        // Toujours recopier de la dernière version des préférences d'application
        copyApplicationPref();
    }

    public void copyApplicationPref() {
        projectPref.setDEBUG(applicationPref.getDEBUG());
        projectPref.setDEBUG_PRINT_MVCCDELEMENT(applicationPref.getDEBUG_PRINT_MVCCDELEMENT());
        projectPref.setDEBUG_BACKGROUND_PANEL(applicationPref.getDEBUG_BACKGROUND_PANEL());
    }

    public void copyProfilePref() {
        copyPref(profilePref, projectPref);
    }

    public void copyDefaultPref() {
        copyPref(defaultPref, projectPref);
     }

    private void copyPref(Preferences from, Preferences to) {
        to.setMCD_JOURNALIZATION(from.getMCD_JOURNALIZATION());
        to.setMCD_JOURNALIZATION_EXCEPTION(from.getMCD_JOURNALIZATION_EXCEPTION());
        to.setMCD_AUDIT(from.getMCD_AUDIT());
        to.setMCD_AUDIT_EXCEPTION(from.getMCD_AUDIT_EXCEPTION());
    }

    public void loadOrCreateFileApplicationPreferences() {
        try{
            PreferencesLoader loader = new PreferencesLoader();
            applicationPref = loader.load(new File(Preferences.FILE_APPLICATION_PREF_NAME));
        } catch (FileNotFoundException e) {
            applicationPref = new Preferences(null, null);
            PreferencesSaver saver = new PreferencesSaver();
            saver.save(new File(Preferences.FILE_APPLICATION_PREF_NAME), applicationPref);
        }
    }


    public void createProfile() {
        ProfileFileChooser fileChooser = new ProfileFileChooser(ProjectFileChooser.SAVE);
        File fileChoose = fileChooser.fileChoose();
        if (fileChoose != null){
            new PreferencesSaver().save(fileChoose, projectPref);
        }
    }


}
