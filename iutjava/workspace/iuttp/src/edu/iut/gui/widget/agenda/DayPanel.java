package edu.iut.gui.widget.agenda;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.gui.widget.agenda.WeekPanel.WeekDayNames;

public class DayPanel extends EventPanel {

	private final static SimpleDateFormat formater = new SimpleDateFormat("EE d");

	private JLabel label;

	public DayPanel(ActiveView activeView, Date date){
		this(activeView, date, null);
	}

	public DayPanel(ActiveView activeView,Date date, Color color) {
		super(activeView, date);
		switch (activeView) {
		case DAY_VIEW:
		case WEEK_VIEW:
			GridLayout daysLayout;
			if(date == null) {
				daysLayout = new GridLayout(24, 1);
				this.setLayout(daysLayout);
				break;
			}else{
				daysLayout = new GridLayout(25,1);
				this.setLayout(daysLayout);
				label = new JLabel(formater.format(date));
				this.add(label);
			}

			for (int hi = 0;hi<24;hi++) {
				JPanel hour = new JPanel();
				hour.add(new JLabel(Integer.toString(hi)));
				this.add(hour);
			}
			break;
		case MONTH_VIEW:
			JPanel hour = new JPanel();
			label = new JLabel(formater.format(date));
			hour.add(label);
			this.add(hour);

		}

		if(color != null && label != null){
			label.setForeground(color);
		}
	}

	@Override
	public void refresh() {

	}

	protected void setupUIDayView() {
		
	}
	protected void setupUIWeekView() {
		
	}
	protected void setupUIMonthView() {
		
	}


}
