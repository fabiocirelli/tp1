package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.gui.frames.SchedulerFrame;
import edu.iut.gui.listeners.ApplicationErrorMessageDialog;
import edu.iut.io.XMLPersistence;
import edu.iut.utils.I18N;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;

public class LoadAction extends AbstractAction {

    private SchedulerFrame parent;
    private Agenda agenda;

    public LoadAction(Agenda agenda, SchedulerFrame parent){
        super(I18N.get("load"));
        this.agenda = agenda;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML", "xml");
        // add filters
        fileChooser.addChoosableFileFilter(xmlFilter);
        fileChooser.setFileFilter(xmlFilter);

        int result = fileChooser.showOpenDialog(parent);
        if(result == JFileChooser.APPROVE_OPTION){
            agenda.reset();
            agenda.setFile(fileChooser.getSelectedFile());
            XMLPersistence persistence = new XMLPersistence(agenda, fileChooser.getSelectedFile());
            if(!persistence.load()){
                new ApplicationErrorMessageDialog().newMessage(I18N.get("load"), I18N.get("loadError"));
            }
        }
    }
}
