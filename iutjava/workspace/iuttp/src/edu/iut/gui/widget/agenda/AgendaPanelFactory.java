package edu.iut.gui.widget.agenda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.iut.gui.widget.agenda.WeekPanel.WeekDayNames;

public class AgendaPanelFactory {

	public enum ActiveView{
		MONTH_VIEW("Month View"),
		WEEK_VIEW("Week View"),
		DAY_VIEW("Day View");
		
		private String activeView;
		
		ActiveView(String activeView) {
			this.activeView = activeView;
		}
		
		public String toString() {
			return activeView;
		}		
	}
	
	public AgendaPanelFactory() {
	}
	
	public EventPanel getAgendaView(ActiveView activeView, Date date) {
		EventPanel agendaView = null;
		switch (activeView) {
			case MONTH_VIEW:
				MonthPanel monthPanel = new MonthPanel(date);
				agendaView = monthPanel;
				break;
			case WEEK_VIEW:
				WeekPanel weekPanel = new WeekPanel(date);
				agendaView = weekPanel;
				break;
			case DAY_VIEW:
				DayPanel dayPanel = new DayPanel(activeView,date);
				agendaView = dayPanel;
				break;
			default:
				break;				
		}
		return agendaView;
	}

}
