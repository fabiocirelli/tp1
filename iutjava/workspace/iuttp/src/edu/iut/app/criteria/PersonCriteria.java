package edu.iut.app.criteria;

import edu.iut.app.ExamEvent;
import edu.iut.app.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PersonCriteria implements Criteria<ExamEvent> {

    private Person person;

    public PersonCriteria(Person person){
        this.person = person;
    }

    @Override
    public List<ExamEvent> meetCriteria(List<ExamEvent> elements) {
        ListIterator<ExamEvent> iterator = new ArrayList<>(elements).listIterator();
        while (iterator.hasNext()){
            ExamEvent exam = iterator.next();
            boolean match;

            if(exam.getStudent().equals(person)){
                match = true;
            }else{
                match = exam.getJury().contains(person);
            }

            if(!match){
                iterator.remove();
            }

        }

        return elements;
    }
}
