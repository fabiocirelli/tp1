package edu.iut.gui.widget.agenda;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JPanel;


import edu.iut.app.DateUtils;
import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.app.ApplicationSession;

public class WeekPanel extends EventPanel {

	public enum WeekDayNames {
		EMPTYDAY("",""),
		MONDAY(ApplicationSession.instance().getString("monday"),ApplicationSession.instance().getString("mon")),
		TUESDAY(ApplicationSession.instance().getString("tuesday"),ApplicationSession.instance().getString("tue")),
		WEDNESDAY(ApplicationSession.instance().getString("wednesday"),ApplicationSession.instance().getString("wed")),
		THURSDAY(ApplicationSession.instance().getString("thursday"),ApplicationSession.instance().getString("thu")),
		FRIDAY(ApplicationSession.instance().getString("friday"),ApplicationSession.instance().getString("fri")),
		SATURDAY(ApplicationSession.instance().getString("saturday"),ApplicationSession.instance().getString("sat")),
		SUNDAY(ApplicationSession.instance().getString("sunday"),ApplicationSession.instance().getString("sun"));
		
		private String name;
		private String shortName;
		
		WeekDayNames(String name,String shortName) {
			this.name = name;
			this.shortName = shortName;
		}
		
		public String getShortName() {
			return shortName;
		}
		
		public String toString() {
			return name;
		}
	}
	
	public WeekPanel(Date date) {
		super(ActiveView.WEEK_VIEW, date);

		GridLayout daysOfWeekLayout = new GridLayout(1,7);		
		this.setLayout(daysOfWeekLayout);

		refresh();

	}

	public void refresh(){
		this.removeAll();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

		Date d = calendar.getTime();

		for (int di = 0;di<7;di++)	{
			this.add(new DayPanel(ActiveView.WEEK_VIEW,d, DateUtils.isSameDate(d, date) ? Color.ORANGE : null));
			calendar.add(Calendar.DATE, 1);
			d = calendar.getTime();
		}
	}
}
