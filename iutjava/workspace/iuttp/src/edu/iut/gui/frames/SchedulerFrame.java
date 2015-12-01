package edu.iut.gui.frames;

import edu.iut.app.ApplicationSession;
import edu.iut.gui.listeners.ApplicationErrorMessageDialog;
import edu.iut.gui.widget.agenda.AgendaPanelFactory;
import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.gui.widget.agenda.ControlAgendaViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SchedulerFrame extends JFrame {
	JPanel contentPane;
	CardLayout layerLayout;
	AgendaPanelFactory agendaPanelFactory;	
	JPanel dayView;
	JPanel weekView;
	JPanel monthView;

	private ViewChangeListener viewChangeListener = new ViewChangeListener();
	private NotImplementedListener notImplementedListener = new NotImplementedListener();
	
	protected void setupUI() {
		
		contentPane = new JPanel();
		layerLayout = new CardLayout();
		contentPane.setLayout(layerLayout);
		ControlAgendaViewPanel agendaViewPanel = new ControlAgendaViewPanel(layerLayout,contentPane);
		agendaPanelFactory = new AgendaPanelFactory();
		dayView = agendaPanelFactory.getAgendaView(ActiveView.DAY_VIEW);
		weekView = agendaPanelFactory.getAgendaView(ActiveView.WEEK_VIEW);
		monthView = agendaPanelFactory.getAgendaView(ActiveView.MONTH_VIEW);
		
		contentPane.add(dayView,ActiveView.DAY_VIEW.name());
		contentPane.add(weekView,ActiveView.WEEK_VIEW.name());
		contentPane.add(monthView,ActiveView.MONTH_VIEW.name());
	
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,agendaViewPanel, contentPane);
		this.setContentPane(splitPane);
		
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(ApplicationSession.instance().getString("file"));
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
	}
	
	public SchedulerFrame() {
		super();
		
		addWindowListener (new WindowAdapter(){
			public void windowClosing (WindowEvent e){
				System.exit(0);
			}
		});
		contentPane = null;
		dayView = null;
		weekView = null;
		monthView = null;
		agendaPanelFactory = null;
		setupUI();

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
