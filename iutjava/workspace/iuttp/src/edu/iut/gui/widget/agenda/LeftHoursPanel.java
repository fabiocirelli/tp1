package edu.iut.gui.widget.agenda;


import edu.iut.app.ApplicationSession;

import javax.swing.*;
import java.awt.*;

public class LeftHoursPanel extends JPanel {

    /**
     * Bandeau affichant toutes les heures de la journée
     * @param start heure minimale
     * @param end heure maximale
     */
    public LeftHoursPanel(int start, int end) {

        setLayout(new GridLayout(ApplicationSession.NB_HOURS+1,1));
        add(new JLabel());
        Dimension d = getPreferredSize();
        d.width = 35;

        setMinimumSize(d);
        setPreferredSize(d);

        setOpaque(true);
        setBackground(new Color(119, 13, 19));
        for(int i = start; i <= end; i++){
            JLabel lbl = new JLabel((i < 10 ? "0" : "") + String.valueOf(i), SwingConstants.CENTER);
            lbl.setVerticalAlignment(JLabel.TOP);
            lbl.setFont(new Font("Serif", Font.BOLD, 15));
            lbl.setForeground(Color.WHITE);
            add(lbl);
        }
    }
}
