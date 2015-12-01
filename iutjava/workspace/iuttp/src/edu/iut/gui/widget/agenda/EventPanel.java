package edu.iut.gui.widget.agenda;
import edu.iut.app.ExamEvent;

import javax.swing.JPanel;

public class EventPanel extends JPanel {
	
	protected AgendaPanelFactory.ActiveView activeView;
	private ExamEvent event;

    public EventPanel(AgendaPanelFactory.ActiveView activeView) {
        this.activeView = activeView;


	}

    public EventPanel(){

    }



}
