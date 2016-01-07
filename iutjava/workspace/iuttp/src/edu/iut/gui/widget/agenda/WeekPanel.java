package edu.iut.gui.widget.agenda;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;


import edu.iut.app.Agenda;
import edu.iut.app.DateUtils;
import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.app.ApplicationSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WeekPanel extends TimePanel {

	private JPanel calendar;

	/**
	 * Vue d'une semaine
	 * @param agenda
	 * @param date
     */
	public WeekPanel(Agenda agenda, Window owner, Date date) {
		super(ActiveView.WEEK_VIEW, agenda, owner, date);

		setLayout(new BorderLayout());
		calendar = new JPanel();
		calendar.setLayout(new GridLayout(1,7));

		LeftHoursPanel hours = new LeftHoursPanel(ApplicationSession.DAY_START, ApplicationSession.DAY_END);

		refresh();

		add(calendar, BorderLayout.CENTER);
		add(hours, BorderLayout.WEST);



	}

	public void refresh(){
		calendar.removeAll();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

		Date d = cal.getTime();

		for (int di = 0;di<7;di++)	{
			calendar.add(new DayPanel(ActiveView.WEEK_VIEW, agenda, owner, d, DateUtils.isSameDate(d, date) ? Color.RED : null));
			cal.add(Calendar.DATE, 1);
			d = cal.getTime();
		}
	}
}
