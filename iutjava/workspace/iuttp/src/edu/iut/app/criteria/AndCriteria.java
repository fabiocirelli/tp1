package edu.iut.app.criteria;

import java.util.List;

public class AndCriteria<E> implements Criteria<E> {

    private Criteria<E> criteria1;
    private Criteria<E> criteria2;

    public AndCriteria(Criteria<E> criteria1, Criteria<E> criteria2) {
        this.criteria1 = criteria1;
        this.criteria2 = criteria2;
    }

    @Override
    public List<E> meetCriteria(List<E> elements) {
        return criteria2.meetCriteria(criteria1.meetCriteria(elements));
    }
}
