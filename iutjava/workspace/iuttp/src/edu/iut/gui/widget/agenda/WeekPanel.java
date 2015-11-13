package edu.iut.gui.widget.agenda;

import java.awt.GridLayout;
import javax.swing.JPanel;


import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.app.ApplicationSession;

public class WeekPanel extends EventPanel {

	// Exercice 4
	public enum WeekDayNames {
		EMPTYDAY("",""),
		MONDAY("monday","mon"),
		TUESDAY("tuesday","tue"),
		WEDNESDAY("wednesday", "wed"),
		THURSDAY("thursday","thu"),
		FRIDAY("friday","fri"),
		SATURDAY("saturday","sat"),
		SUNDAY("sunday","sun");
		
		private String name;
		private String shortName;
		
		WeekDayNames(String name,String shortName) {

			// Pour tous les noms de jour sauf le jour vide, on récupère le nom dans les ressources

			this.name = name.length() > 0 ? ApplicationSession.instance().getString(name) : name;
			this.shortName = shortName.length() > 0 ? ApplicationSession.instance().getString(shortName) : shortName;
		}
		
		public String getShortName() {
			return shortName;
		}
		
		public String toString() {
			return name;
		}
	}
	
	public WeekPanel() {
		super(ActiveView.WEEK_VIEW);
		GridLayout daysOfWeekLayout = new GridLayout(1,7);		
		this.setLayout(daysOfWeekLayout);
		for (int di = 0;di<8;di++)	{
			this.add(new DayPanel(ActiveView.WEEK_VIEW,WeekDayNames.values()[di]));
		}
	}
}
