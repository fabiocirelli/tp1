package edu.iut.app;

import java.util.ArrayList;
import java.util.Date;

public class ExamEvent {
	public ExamEvent() {		
	}

	public ExamEvent(Date date){
		this.examDate = date;
	}
	
	public ExamEvent(Date date, Person person, ArrayList<Person> jury,
					Classroom classRoom, ArrayList<Document> document) {
		
	}
	
	/** EX2: FAITE LES ACCESSEURS DES ATTRIBUTS, AJOUTER DES ATTRIBUT ? **/
	protected Date examDate;
	protected Person student;
	protected ArrayList<Person> jury = new ArrayList<>();
	protected Classroom classroom;
	protected ArrayList<Document> documents;

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public Person getStudent() {
		return student;
	}

	public void setStudent(Person student) {
		this.student = student;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public ArrayList<Person> getJury() {
		return jury;
	}

	public void setJury(ArrayList<Person> jury) {
		this.jury = jury;
	}

	public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}
}
