package edu.iut.app.criteria;

import edu.iut.app.ExamEvent;

import java.util.*;

public class DateCriteria implements Criteria<ExamEvent> {

    private Date begin;
    private Date end;

    public DateCriteria(Date date){
        this(date, date);
    }

    public DateCriteria(Date begin, Date end){
        this.begin = begin;
        this.end = end;

        Calendar cal = Calendar.getInstance();

        cal.setTime(begin);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        this.begin = cal.getTime();

        cal.setTime(end);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        this.end = cal.getTime();
    }

    @Override
    public List<ExamEvent> meetCriteria(List<ExamEvent> elements) {
        List<ExamEvent> out = new ArrayList<>(elements);
        ListIterator<ExamEvent> iterator = out.listIterator();
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

        return out;

    }
}
