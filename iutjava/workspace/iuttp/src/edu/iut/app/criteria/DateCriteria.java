package edu.iut.app.criteria;

import edu.iut.app.ExamEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class DateCriteria implements Criteria<ExamEvent> {

    private Date begin;
    private Date end;

    public DateCriteria(Date begin){
        this(begin, null);
    }

    public DateCriteria(Date begin, Date end){
        this.begin = begin;
        this.end = end;
    }

    @Override
    public List<ExamEvent> meetCriteria(List<ExamEvent> elements) {
        ListIterator<ExamEvent> iterator = new ArrayList<>(elements).listIterator();
        while (iterator.hasNext()){
            Date examDate = iterator.next().getExamDate();
            boolean match;

            if(end == null){
                match = examDate.equals(begin);
            }else{
                match = (examDate.after(begin) && examDate.before(end)) || examDate.equals(begin) || examDate.equals(end);
            }

            if(!match){
                iterator.remove();
            }

        }

        return elements;

    }
}
