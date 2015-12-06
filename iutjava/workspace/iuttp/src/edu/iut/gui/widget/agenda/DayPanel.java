package edu.iut.gui.widget.agenda;

import edu.iut.app.Agenda;
import edu.iut.app.ApplicationSession;
import edu.iut.app.ExamEvent;
import edu.iut.gui.widget.agenda.AgendaPanelFactory.ActiveView;
import edu.iut.gui.widget.generic.DeleteButton;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DayPanel extends TimePanel {

    private final static SimpleDateFormat formater = new SimpleDateFormat("EE d");

    private JLabel label;
    private HashMap<Integer, List<ExamEvent>> eventsHours;

    public DayPanel(ActiveView activeView, Agenda agenda, Date date){
        this(activeView, agenda, date, null);
    }

    public DayPanel(ActiveView activeView, Agenda agenda, Date date, Color color) {
        super(activeView, agenda, date);
        refresh();

        if(color != null && label != null){
            label.setForeground(color);
        }
    }

    @Override
    public void refresh() {

        removeAll();
        classifyByHour(agenda.getByDay(date));

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

    protected void setupUIStandardView() {
        GridLayout daysLayout;
        if(date == null) {
            daysLayout = new GridLayout(ApplicationSession.NB_HOURS, 1);
            this.setLayout(daysLayout);
            return;
        }else{
            daysLayout = new GridLayout(ApplicationSession.NB_HOURS+1,1);
            this.setLayout(daysLayout);
            label = new JLabel(formater.format(date));
            this.add(label);
        }

        this.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int hi = ApplicationSession.DAY_START;hi<=ApplicationSession.DAY_END;hi++) {
            this.add(new HourPanel(hi, eventsHours.get(hi)));
        }
    }

    protected void setupUISingleDayView() {

        setLayout(new BorderLayout());

        LeftHoursPanel hours = new LeftHoursPanel(ApplicationSession.DAY_START, ApplicationSession.DAY_END);
        JPanel dayHours = new JPanel(new GridLayout(ApplicationSession.NB_HOURS+1,1));
        dayHours.add(new JLabel(formater.format(date)));

        for (int hi = ApplicationSession.DAY_START;hi<=ApplicationSession.DAY_END;hi++) {
            dayHours.add(new HourPanel(hi, eventsHours.get(hi)));
        }

        add(hours, BorderLayout.WEST);
        add(dayHours, BorderLayout.CENTER);
    }

    public void setupUIMinifiedView(){

    }

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
                    JPanel eventPanel = new JPanel(new BorderLayout());

                    JLabel eventName = new JLabel(event.getStudent().getFullName());
                    DeleteButton<ExamEvent> delete = new DeleteButton<>(event, agenda);
                    Dimension d = new Dimension(25,25);
                    delete.setOpaque(false);
                    delete.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.RED));
                    delete.setBackground(new Color(0,0,0,0));
                    delete.setForeground(Color.RED);
                    delete.setMaximumSize(d);
                    delete.setPreferredSize(d);
                    delete.setMinimumSize(d);

                    eventPanel.add(eventName, BorderLayout.CENTER);
                    eventPanel.add(delete, BorderLayout.EAST);

                    d = eventPanel.getPreferredSize();
                    d.height = 15;
                    eventPanel.setMinimumSize(d);
                    eventPanel.setPreferredSize(d);
                    eventPanel.setMaximumSize(d);



                    //eventPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

                    add(eventPanel, gbc);
                    gbc.gridy++;
                }
            }


            //JLabel eventName = new JLabel();

            /*if(events != null && !events.isEmpty()){
                eventName.setText(events.get(0).getStudent().getFullName());
                eventName.setBackground(Color.CYAN);
            }*/

            //setBackground(Color.CYAN);

            /*hourLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            eventName.setAlignmentX(Component.LEFT_ALIGNMENT);
            JButton test = new JButton();*/
			/*setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
			setPreferredSize(new Dimension(30, 20));
			setMinimumSize(new Dimension(30, 20));*/
            //add(hourLbl);
            //add(test);
        }
    }


}
