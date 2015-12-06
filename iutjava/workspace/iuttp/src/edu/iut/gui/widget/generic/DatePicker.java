package edu.iut.gui.widget.generic;

import edu.iut.app.ApplicationSession;
import edu.iut.app.IDateProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePicker extends JPanel implements ChangeListener, ItemListener {

	private IDateProvider provider;

	private JSpinner yearSpinner;
	private JComboBox<String> monthsCombo;
	private JComboBox<Integer> daysCombo;

	private Calendar calendar;

	private final Border errorBorder = BorderFactory.createMatteBorder(1, 1, 1, 1,Color.RED);
	private final Border defaultBorder = BorderFactory.createEmptyBorder(1,1,1,1);
	
	public DatePicker() {

		calendar = new GregorianCalendar();
		calendar.setLenient(false);

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int minYear = year-year%10;
		int maxYear = minYear+10;

		SpinnerNumberModel model = new SpinnerNumberModel(year, minYear, maxYear, 1);
		yearSpinner = new JSpinner(model);

		monthsCombo = new JComboBox<>(ApplicationSession.instance().getMonths());

		Integer[] days = new Integer[31];
		for(int i = 1; i <= 31; i++) days[i-1] = i;
		daysCombo = new JComboBox<>(days);

		daysCombo.addItemListener(this);
		monthsCombo.addItemListener(this);
		yearSpinner.addChangeListener(this);

		add(daysCombo);
		add(monthsCombo);
		add(yearSpinner);


		setBorder(defaultBorder);
	}


	public void setDateProvider(IDateProvider provider){

		calendar.setTime(provider.getDate());
		this.provider = provider;
		this.yearSpinner.setValue(calendar.get(Calendar.YEAR));
		this.monthsCombo.setSelectedIndex(calendar.get(Calendar.MONTH));
		this.daysCombo.setSelectedIndex(calendar.get(Calendar.DAY_OF_MONTH)-1);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		calendar.set(Calendar.YEAR, (int) yearSpinner.getValue());
		update(Calendar.YEAR, (int) yearSpinner.getValue());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == monthsCombo) {
			calendar.set(Calendar.MONTH, monthsCombo.getSelectedIndex());
			update(Calendar.MONTH, monthsCombo.getSelectedIndex());
		}else if(e.getSource() == daysCombo) {
			calendar.set(Calendar.DAY_OF_MONTH, daysCombo.getSelectedIndex()+1);
			update(Calendar.DAY_OF_MONTH, daysCombo.getSelectedIndex()+1);
		}
	}

	private void update(int field, int value){
		try {

			calendar.set(field, value);

			if (provider != null)
				provider.setDate(calendar.getTime());

			setBorder(defaultBorder);

		}catch(IllegalArgumentException ex){

			setBorder(errorBorder);
		}
	}
}
