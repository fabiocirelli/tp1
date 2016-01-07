package edu.iut.gui.widget.agenda;

import edu.iut.app.Agenda;
import edu.iut.app.DateUtils;
import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MonthPanel extends TimePanel {


	/**
	 * Vue d'un mois
	 * @param agenda
	 * @param date
     */
	public MonthPanel(Agenda agenda, Window window, Date date) {
		super(ActiveView.MONTH_VIEW, agenda, window, date);

		GridLayout daysOfMonthLayout = new GridLayout(6,7);
		this.setLayout(daysOfMonthLayout);

		refresh();

	}

	public void refresh(){
		this.removeAll();

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		calendar.set(Calendar.DAY_OF_MONTH, 1);

		Date d = calendar.getTime();

		int originalMonth = calendar.get(Calendar.MONTH);
		int originalDayOfWeek = Math.floorMod(calendar.get(Calendar.DAY_OF_WEEK)-2,7);
		int filled = 0;

		while (filled != originalDayOfWeek){
			this.add(new JPanel());
			filled++;
		}

		do{
			this.add(new DayPanel(ActiveView.MONTH_VIEW, agenda, owner, d, DateUtils.isSameDate(d, date) ? Color.RED : null));

			calendar.add(Calendar.DATE, 1);
			d = calendar.getTime();
			filled++;
		}while(calendar.get(Calendar.MONTH) == originalMonth);

		while (filled < 6*7){
			this.add(new JPanel());
			filled++;
		}
	}
}
