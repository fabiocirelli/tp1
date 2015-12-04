package edu.iut.gui.widget.agenda;
import edu.iut.app.ExamEvent;

import javax.swing.JPanel;
import java.util.Date;

public abstract class EventPanel extends JPanel {
	
	protected AgendaPanelFactory.ActiveView activeView;
    protected Date date;
	private ExamEvent event;

    public EventPanel(AgendaPanelFactory.ActiveView activeView, Date date) {
        this.activeView = activeView;
        this.date = date;


	}

    public EventPanel(){

    }

    public void setDate(Date date){
        this.date = date;
        refresh();
        revalidate();
    }

    public abstract void refresh();



}
