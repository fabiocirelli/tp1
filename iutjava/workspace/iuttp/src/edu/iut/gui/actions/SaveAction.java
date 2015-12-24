package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.gui.listeners.ApplicationErrorMessageDialog;
import edu.iut.io.XMLPersistence;
import edu.iut.utils.I18N;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SaveAction extends AbstractAction{

    private Agenda agenda;
    private Window parent;

    public SaveAction(Agenda agenda, Window parent){
        super(I18N.get("save"));
        this.agenda = agenda;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        // Si l'agenda est déjà enregistré, on propose de l'enregistrer au même endroit par défaut
        if(agenda.getFile() != null){
            fileChooser.setSelectedFile(agenda.getFile());
        }else{
            fileChooser.setSelectedFile(new File(agenda.getFile(), "agenda.xml"));
        }

        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML", "xml");
        // add filters
        fileChooser.addChoosableFileFilter(xmlFilter);
        fileChooser.setFileFilter(xmlFilter);

        int result = fileChooser.showSaveDialog(parent);
        if(result == JFileChooser.APPROVE_OPTION){
            agenda.setFile(fileChooser.getSelectedFile());
            XMLPersistence persistence = new XMLPersistence(agenda, fileChooser.getSelectedFile());
            if(!persistence.save()){
                new ApplicationErrorMessageDialog().newMessage(I18N.get("save"), I18N.get("saveError"));
            }
        }
    }
}
