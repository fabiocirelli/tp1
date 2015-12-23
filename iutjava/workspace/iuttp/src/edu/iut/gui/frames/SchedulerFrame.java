package edu.iut.gui.frames;

import edu.iut.app.ApplicationSession;
import edu.iut.app.IDateProvider;
import edu.iut.gui.actions.ManagePersonsAction;
import edu.iut.gui.actions.NewEventAction;
import edu.iut.gui.listeners.ApplicationErrorMessageDialog;
import edu.iut.gui.widget.agenda.AgendaPanelFactory;
import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.gui.widget.generic.DatePicker;
import edu.iut.gui.widget.agenda.TimePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;


public class SchedulerFrame extends JFrame implements IDateProvider{
	JPanel contentPane;
	CardLayout layerLayout;
	AgendaPanelFactory agendaPanelFactory;
	TimePanel dayView;
	TimePanel weekView;
	TimePanel monthView;

	Date currentDate;

	private ViewChangeListener viewChangeListener = new ViewChangeListener();
	private NotImplementedListener notImplementedListener = new NotImplementedListener();

	/**
	 * Génère l'interface graphique
	 */
	protected void setupUI() {

		Calendar calendar = Calendar.getInstance();
		currentDate = calendar.getTime();

		contentPane = new JPanel();
		layerLayout = new CardLayout();
		contentPane.setLayout(layerLayout);

		agendaPanelFactory = new AgendaPanelFactory(ApplicationSession.instance().getAgenda());

		dayView = agendaPanelFactory.getAgendaView(ActiveView.DAY_VIEW, currentDate);
		weekView = agendaPanelFactory.getAgendaView(ActiveView.WEEK_VIEW, currentDate);
		monthView = agendaPanelFactory.getAgendaView(ActiveView.MONTH_VIEW, currentDate);

		JPanel agendaViewPanel = new JPanel();
		DatePicker datePicker = new DatePicker();
		datePicker.setDateProvider(this);

		JButton addButton = new JButton(ApplicationSession.instance().getString("new"));
		addButton.addActionListener(new NewEventAction(ApplicationSession.instance().getAgenda(), this, this));

		agendaViewPanel.add(datePicker);
		agendaViewPanel.add(addButton);
		
		contentPane.add(dayView,ActiveView.DAY_VIEW.name());
		contentPane.add(weekView,ActiveView.WEEK_VIEW.name());
		contentPane.add(monthView,ActiveView.MONTH_VIEW.name());
	
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,agendaViewPanel, contentPane);
		this.setContentPane(splitPane);

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(ApplicationSession.instance().getString("file"));
		fileMenu.add(new JMenuItem(new NewEventAction(ApplicationSession.instance().getAgenda(), this, this)));
		fileMenu.add(new NotImplementedMenuItem("load"));
		fileMenu.add(new NotImplementedMenuItem("save"));
		fileMenu.add(new NotImplementedMenuItem("quit"));

		JMenu editMenu = new JMenu(ApplicationSession.instance().getString("edit"));


		editMenu.add(new JMenu(ApplicationSession.instance().getString("view")){{
			add(new ViewChangeMenuItem(ActiveView.MONTH_VIEW));
			add(new ViewChangeMenuItem(ActiveView.WEEK_VIEW));
			add(new ViewChangeMenuItem(ActiveView.DAY_VIEW));
		}});

		editMenu.add(new JMenuItem(new ManagePersonsAction(ApplicationSession.instance().getAgenda(), this)));

		JMenu helpMenu = new JMenu(ApplicationSession.instance().getString("help"));
		helpMenu.add(fileMenu.add(new NotImplementedMenuItem("display")));
		helpMenu.add(fileMenu.add(new NotImplementedMenuItem("about")));

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		
		this.setJMenuBar(menuBar);
		this.pack();
		layerLayout.next(contentPane);

		ApplicationSession.instance().getAgenda().addChangeListener(e -> {
            dayView.refresh();
            weekView.refresh();
            monthView.refresh();

            dayView.revalidate();
            weekView.revalidate();
            monthView.revalidate();

        });
	}

	public SchedulerFrame(String title) {
		super(title);
		addWindowListener (new WindowAdapter(){
			public void windowClosing (WindowEvent e){
				System.exit(0);
			}
		});
		setupUI();
	}

	/**
	 * Renvoie la date courante sélectionné dans l'interface
	 * @return
     */
	public Date getDate(){
		return currentDate;
	}

	/**
	 * Change la date courante sélectionné dans l'interface
	 * @param date
     */
	public void setDate(Date date){
		dayView.setDate(date);
		weekView.setDate(date);
		monthView.setDate(date);

		this.currentDate = date;
	}

	/**
	 * Elément de menu permettant de changer de vue
	 */
	private class ViewChangeMenuItem extends JMenuItem{

		private ActiveView view;

		public ViewChangeMenuItem(ActiveView view){
			super(ApplicationSession.instance().getString(view.name()));
			addActionListener(viewChangeListener);
			this.view = view;
		}

		public ActiveView getView() {
			return view;
		}
	}

	/**
	 * Elément de menu non implémenté
	 */
	private class NotImplementedMenuItem extends JMenuItem{

		public NotImplementedMenuItem(String name){
			super(ApplicationSession.instance().getString(name));
			addActionListener(notImplementedListener);

		}
	}

	/**
	 * Listener appelé quand la vue change
	 */
	private class ViewChangeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			ViewChangeMenuItem item = (ViewChangeMenuItem) e.getSource();
			layerLayout.show(contentPane, item.getView().name());

		}
	}

	/**
	 * Interaction à une une fonctionnalité non implémenté.
	 */
	private class NotImplementedListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new ApplicationErrorMessageDialog().newMessage("Erreur", ApplicationSession.instance().getString("notImplementedError"));
		}
	}
	
}
