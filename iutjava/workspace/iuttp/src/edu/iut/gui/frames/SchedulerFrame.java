package edu.iut.gui.frames;

import edu.iut.app.ApplicationSession;
import edu.iut.app.IDateProvider;
import edu.iut.gui.actions.NewEventAction;
import edu.iut.gui.listeners.ApplicationErrorMessageDialog;
import edu.iut.gui.widget.agenda.AgendaPanelFactory;
import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.gui.widget.generic.DatePicker;
import edu.iut.gui.widget.agenda.TimePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

		DatePicker agendaViewPanel = new DatePicker();
		agendaViewPanel.setDateProvider(this);
		
		contentPane.add(dayView,ActiveView.DAY_VIEW.name());
		contentPane.add(weekView,ActiveView.WEEK_VIEW.name());
		contentPane.add(monthView,ActiveView.MONTH_VIEW.name());
	
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,agendaViewPanel, contentPane);
		this.setContentPane(splitPane);
		
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(ApplicationSession.instance().getString("file"));
		fileMenu.add(new JMenuItem(new NewEventAction(ApplicationSession.instance().getAgenda(), this)));
		fileMenu.add(new NotImplementedMenuItem("load"));
		fileMenu.add(new NotImplementedMenuItem("save"));
		fileMenu.add(new NotImplementedMenuItem("quit"));

		JMenu editMenu = new JMenu(ApplicationSession.instance().getString("edit"));


		editMenu.add(new JMenu(ApplicationSession.instance().getString("view")){{
			add(new ViewChangeMenuItem(ActiveView.MONTH_VIEW));
			add(new ViewChangeMenuItem(ActiveView.WEEK_VIEW));
			add(new ViewChangeMenuItem(ActiveView.DAY_VIEW));
		}});

		JMenu helpMenu = new JMenu(ApplicationSession.instance().getString("help"));
		helpMenu.add(fileMenu.add(new NotImplementedMenuItem("display")));
		helpMenu.add(fileMenu.add(new NotImplementedMenuItem("about")));

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		
		this.setJMenuBar(menuBar);
		this.pack();
		layerLayout.next(contentPane);

		ApplicationSession.instance().getAgenda().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				dayView.refresh();
				weekView.refresh();
				monthView.refresh();

				dayView.revalidate();
				weekView.revalidate();
				monthView.revalidate();

			}
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

	public Date getDate(){
		return currentDate;
	}

	public void setDate(Date date){
		dayView.setDate(date);
		weekView.setDate(date);
		monthView.setDate(date);
	}

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

	private class NotImplementedMenuItem extends JMenuItem{

		public NotImplementedMenuItem(String name){
			super(ApplicationSession.instance().getString(name));
			addActionListener(notImplementedListener);

		}
	}

	private class ViewChangeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			ViewChangeMenuItem item = (ViewChangeMenuItem) e.getSource();
			layerLayout.show(contentPane, item.getView().name());

		}
	}

	private class NotImplementedListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new ApplicationErrorMessageDialog().newMessage("Erreur", ApplicationSession.instance().getString("notImplementedError"));
		}
	}
	
}
