package edu.iut.gui.widget.generic;

import javax.swing.*;
import java.util.Collection;


public class DeleteButton<E> extends JButton {
    public DeleteButton(E element, Collection<E> collection) {
        super("X");
        addActionListener(e -> collection.remove(element));
    }
}
