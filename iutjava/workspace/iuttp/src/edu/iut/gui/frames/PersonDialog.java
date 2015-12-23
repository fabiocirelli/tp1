package edu.iut.gui.frames;

import edu.iut.app.Person;
import edu.iut.gui.widget.generic.Field;
import edu.iut.gui.widget.generic.Form;
import edu.iut.gui.widget.generic.IFormFilledListener;
import edu.iut.utils.I18N;

import javax.swing.*;
import java.awt.*;

public class PersonDialog extends JDialog {

    private Person.PersonFunction function;
    private IFormFilledListener<Person> listener;

    private Field<JTextField> lastname;
    private Field<JTextField> firstname;
    private Field<JTextField> mail;
    private Field<JTextField> phone;

    public PersonDialog(Person.PersonFunction function, Window owner){

        super(owner, ModalityType.APPLICATION_MODAL);
        Form form = new Form();

        this.function = function;
        this.lastname = new Field<>(I18N.get("lastname"), new JTextField());
        this.firstname = new Field<>(I18N.get("firstname"), new JTextField());
        this.mail = new Field<>(I18N.get("mail"), new JTextField());
        this.phone = new Field<>(I18N.get("phone"), new JTextField());

        form.createFieldset(I18N.get("general"));
        form.createFieldset(I18N.get("contact"));

        form.addField(0, lastname);
        form.addField(0, firstname);

        form.addField(1, mail);
        form.addField(1, phone);

        form.addButton(I18N.get("addItem"), e -> {
            Person person = new Person(function, firstname.getComponent().getText(), lastname.getComponent().getText());
            person.setEmail(mail.getComponent().getText());
            person.setPhone(phone.getComponent().getText());

            if(listener != null) listener.onFormFilled(person);
            PersonDialog.this.dispose();
        });

        setTitle(I18N.get("add_" + function.toString().toLowerCase()));
        setContentPane(form);
        setResizable(false);
        pack();
    }

    public void setFormListener(IFormFilledListener<Person> listener){
        this.listener = listener;
    }


}
