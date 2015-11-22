package edu.iut.app.criteria;

import java.util.List;

public interface Criteria<E> {

    List<E> meetCriteria(List<E> elements);

}