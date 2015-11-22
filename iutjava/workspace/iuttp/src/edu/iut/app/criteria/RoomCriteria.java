package edu.iut.app.criteria;

import edu.iut.app.Classroom;
import edu.iut.app.ExamEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RoomCriteria implements Criteria<ExamEvent> {

    private Classroom room;

    public RoomCriteria(Classroom room){
        this.room = room;
    }



    @Override
    public List<ExamEvent> meetCriteria(List<ExamEvent> elements) {
        ListIterator<ExamEvent> iterator = new ArrayList<>(elements).listIterator();
        while (iterator.hasNext()){
            ExamEvent exam = iterator.next();

            if(!exam.getClassroom().equals(room)){
                iterator.remove();
            }

        }

        return elements;
    }
}