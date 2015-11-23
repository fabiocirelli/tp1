package edu.iut.gui.widget.agenda;

import edu.iut.app.ApplicationSession;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class ControlAgendaViewPanel extends JPanel {

	CardLayout agendaViewLayout;
	JPanel contentPane;
	
	int selectedYear;
	int selectedMonth;
	int selectedDay;
	
	public ControlAgendaViewPanel(CardLayout layerLayout, final JPanel contentPane) {

		this.agendaViewLayout = layerLayout;
		this.contentPane = contentPane;

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int minYear = year-year%10;
		int maxYear = minYear+10;

		SpinnerNumberModel model = new SpinnerNumberModel(year, minYear, maxYear, 1);
		JSpinner spinner = new JSpinner(model);

		Integer[] days = new Integer[31];
		for(int i = 1; i <= 31; i++){
			days[i-1] = i;
		}

		JComboBox<String> monthsCombo = new JComboBox<>(ApplicationSession.instance().getMonths());
		JComboBox<Integer> daysCombo = new JComboBox<>(days);


		add(spinner);
		add(monthsCombo);
		add(daysCombo);
	}
	
	public int getYear() {
		return selectedYear;
	}
	public int getMonth() {
		return selectedMonth;
	}
	public int getDay() {
		return selectedDay;
	}
	
}
