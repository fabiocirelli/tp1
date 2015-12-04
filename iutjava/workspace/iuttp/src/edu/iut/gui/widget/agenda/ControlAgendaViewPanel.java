package edu.iut.gui.widget.agenda;

import edu.iut.app.ApplicationSession;
import edu.iut.app.IDateProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ControlAgendaViewPanel extends JPanel implements ChangeListener, ItemListener {

	private IDateProvider provider;

	private CardLayout agendaViewLayout;
	private JPanel contentPane;

	private JSpinner yearSpinner;
	private JComboBox<String> monthsCombo;
	private JComboBox<Integer> daysCombo;

	private Calendar calendar;

	private final Border errorBorder = BorderFactory.createMatteBorder(1, 1, 1, 1,Color.RED);
	private final Border defaultBorder = BorderFactory.createEmptyBorder(1,1,1,1);
	
	public ControlAgendaViewPanel(CardLayout layerLayout, final JPanel contentPane) {

		calendar = new GregorianCalendar();
		calendar.setLenient(false);

		this.agendaViewLayout = layerLayout;
		this.contentPane = contentPane;

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int minYear = year-year%10;
		int maxYear = minYear+10;

		SpinnerNumberModel model = new SpinnerNumberModel(year, minYear, maxYear, 1);
		yearSpinner = new JSpinner(model);

		monthsCombo = new JComboBox<>(ApplicationSession.instance().getMonths());

		Integer[] days = new Integer[31];
		for(int i = 1; i <= 31; i++) days[i-1] = i;
		daysCombo = new JComboBox<>(days);

		yearSpinner.addChangeListener(this);
		monthsCombo.addItemListener(this);
		daysCombo.addItemListener(this);

		add(yearSpinner);
		add(monthsCombo);
		add(daysCombo);

		setBorder(defaultBorder);
	}


	public void setDateProvider(IDateProvider provider){
		this.provider = provider;
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
