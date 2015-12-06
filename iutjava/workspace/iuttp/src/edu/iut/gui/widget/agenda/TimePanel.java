package edu.iut.gui.widget.agenda;
import edu.iut.app.Agenda;
import edu.iut.app.ExamEvent;

import javax.swing.JPanel;
import java.util.Date;
import java.util.List;

public abstract class TimePanel extends JPanel {
	
	protected AgendaPanelFactory.ActiveView activeView;
    protected Agenda agenda;
    protected Date date;

    public TimePanel(AgendaPanelFactory.ActiveView activeView, Agenda agenda, Date date) {
        this.activeView = activeView;
        this.date = date;
        this.agenda = agenda;
	}

    public TimePanel(){

    }

    public void setDate(Date date){
        this.date = date;
        refresh();
        revalidate();
    }

    public abstract void refresh();



}
