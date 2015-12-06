package edu.iut.gui.widget.agenda;

import edu.iut.app.Agenda;

import java.util.Date;

public class AgendaPanelFactory {

	private Agenda agenda;

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
	
	public AgendaPanelFactory(Agenda agenda) {
		this.agenda = agenda;
	}

	/**
	 * Crée une vue
	 * @param activeView la vue à créer
	 * @param date la date courante
     * @return vue
     */
	public TimePanel getAgendaView(ActiveView activeView, Date date) {
		TimePanel agendaView = null;
		switch (activeView) {
			case MONTH_VIEW:
				MonthPanel monthPanel = new MonthPanel(agenda, date);
				agendaView = monthPanel;
				break;
			case WEEK_VIEW:
				WeekPanel weekPanel = new WeekPanel(agenda, date);
				agendaView = weekPanel;
				break;
			case DAY_VIEW:
				DayPanel dayPanel = new DayPanel(activeView, agenda, date);
				agendaView = dayPanel;
				break;
			default:
				break;				
		}
		return agendaView;
	}

}
