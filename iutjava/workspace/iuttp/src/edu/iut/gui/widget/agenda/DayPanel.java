package edu.iut.gui.widget.agenda;

import edu.iut.app.Agenda;
import edu.iut.app.ApplicationSession;
import edu.iut.app.ExamEvent;
import edu.iut.gui.actions.EditEventAction;
import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.gui.widget.generic.DeleteButton;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.List;


public class DayPanel extends TimePanel {

    private final static SimpleDateFormat formater = new SimpleDateFormat("EE d");
    private final static SimpleDateFormat longFormater = new SimpleDateFormat("EEEE d MMMM");

    private JLabel label;
    private List<ExamEvent> events;
    private HashMap<Integer, List<ExamEvent>> eventsHours;


    public DayPanel(ActiveView activeView, Agenda agenda, Window owner, Date date){
        this(activeView, agenda, owner, date, null);
    }

    /**
     * Vue d'une journée
     * @param activeView le type de vue global
     * @param agenda l'agenda
     * @param date la date de la vue
     * @param color couleur dans laquelle le nom du jour sera coloré (facultatif)
     */
    public DayPanel(ActiveView activeView, Agenda agenda, Window owner, Date date, Color color) {
        super(activeView, agenda, owner, date);
        refresh();

        if(color != null && label != null){
            label.setForeground(color);
        }
    }

    @Override
    public void refresh() {
        removeAll();
        events = agenda.getByDay(date);
        classifyByHour(events);

        switch (activeView) {
            case DAY_VIEW:
                setupUISingleDayView();
                break;
            case WEEK_VIEW:
                setupUIStandardView();
                break;
            case MONTH_VIEW:
                setupUIMinifiedView();

        }
    }

    /**
     * Classe les évènements dans une map avec l'heure comme clé
     * @param events
     */
    private void classifyByHour(List<ExamEvent> events){
        eventsHours = new HashMap<>();
        Calendar calendar = new GregorianCalendar();

        events.stream().forEach(e -> {
            calendar.setTime(e.getExamDate());
            Integer h = calendar.get(Calendar.HOUR_OF_DAY);
            if(eventsHours.containsKey(h)){
                eventsHours.get(h).add(e);
            }else{
                eventsHours.put(h, new ArrayList<ExamEvent>(){{ add(e); }});
            }

        });

    }

    /**
     * Génère une vue jour dans une semaine
     */
    protected void setupUIStandardView() {
        GridLayout daysLayout;
        if(date == null) {
            daysLayout = new GridLayout(ApplicationSession.NB_HOURS, 1);
            this.setLayout(daysLayout);
            return;
        }else{
            daysLayout = new GridLayout(ApplicationSession.NB_HOURS+1,1);
            this.setLayout(daysLayout);
            label = new JLabel(formater.format(date), SwingConstants.CENTER);
            this.add(label);
        }

        this.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int hi = ApplicationSession.DAY_START;hi<=ApplicationSession.DAY_END;hi++) {
            this.add(new HourPanel(hi, eventsHours.get(hi)));
        }
    }

    /**
     * Génère une vue jour "seule"
     */
    protected void setupUISingleDayView() {

        setLayout(new BorderLayout());

        LeftHoursPanel hours = new LeftHoursPanel(ApplicationSession.DAY_START, ApplicationSession.DAY_END);
        JPanel dayHours = new JPanel(new GridLayout(ApplicationSession.NB_HOURS+1,1));
        dayHours.add(new JLabel(longFormater.format(date), SwingConstants.CENTER));

        for (int hi = ApplicationSession.DAY_START;hi<=ApplicationSession.DAY_END;hi++) {
            dayHours.add(new HourPanel(hi, eventsHours.get(hi)));
        }

        add(hours, BorderLayout.WEST);
        add(dayHours, BorderLayout.CENTER);
    }

    /**
     * Génère une vue jour dans un mois
     */
    public void setupUIMinifiedView(){
        setLayout(new BorderLayout());
        label = new JLabel(formater.format(date), SwingConstants.CENTER);

        JPanel dayHours = new JPanel(new GridLayout(ApplicationSession.NB_HOURS,1));

        for(ExamEvent event : events){
            dayHours.add(new EventPanel(event, true));
        }

        add(label, BorderLayout.NORTH);
        add(dayHours, BorderLayout.CENTER);


    }

    /**
     * Représente un crénau horaire dans les vues Semaines et Jour
     */
    public class HourPanel extends JPanel{

        public HourPanel(int hour, List<ExamEvent> events){

            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createMatteBorder(1,1,0,0,Color.LIGHT_GRAY));
            setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;


            if(events != null) {
                for (ExamEvent event : events) {
                    JButton eventPanel = new EventPanel(event, false);

                    add(eventPanel, gbc);
                    gbc.gridy++;
                }
            }
        }
    }

    /**
     * Vue représentant un évènement
     */
    public class EventPanel extends JButton{
        public EventPanel(ExamEvent event, boolean showHour) {

            setLayout(new BorderLayout());
            JLabel eventName = new JLabel(event.getStudent().getFullName());
            addActionListener(new EditEventAction(agenda, owner, event));

            DeleteButton<ExamEvent> delete = new DeleteButton<>(event, agenda);
            Dimension d = new Dimension(25,25);
            delete.setOpaque(false);
            delete.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.RED));
            delete.setBackground(new Color(0,0,0,0));
            delete.setForeground(Color.RED);
            delete.setMaximumSize(d);
            delete.setPreferredSize(d);
            delete.setMinimumSize(d);

            add(eventName, BorderLayout.CENTER);
            add(delete, BorderLayout.EAST);

            d = getPreferredSize();
            d.height = 15;
            setMinimumSize(d);
            setPreferredSize(d);
            setMaximumSize(d);
        }
    }


}
