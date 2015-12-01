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

		JComboBox<String> monthsCombo = new JComboBox<>(ApplicationSession.instance().getMonths());
		JComboBox<String> daysCombo = new JComboBox<>(ApplicationSession.instance().getDays());


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
