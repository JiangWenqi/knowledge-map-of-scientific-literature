package cn.edu.bistu.node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Paper {


	private String title;
	private String date;
	private LinkedList<Author> authors = new LinkedList<Author>();
	private String meeting;
	private String abstractInformation;

	public Paper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Paper(String title, String date, LinkedList<Author> authors, String meeting, String abstractInformation) {
		this.title = title;
		this.date = date;
		this.authors = authors;
		this.meeting = meeting;
		this.abstractInformation = abstractInformation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public LinkedList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(LinkedList<Author> authors) {
		this.authors = authors;
	}

	public String getMeeting() {
		return meeting;
	}

	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}

	public String getAbstractInformation() {
		return abstractInformation;
	}

	public void setAbstractInformation(String abstractInformation) {
		this.abstractInformation = abstractInformation;
	}
	
	@Override
	public String toString() {
		return "Paper [title=" + title + ", date=" + date + ", authors=" + authors + ", meeting=" + meeting
				+ ", abstractInformation=" + abstractInformation + "]";
	}
	
	public final String TEMPLATE = "CREATE (p:Paper {title: $title,date:$date,meeting:$meeting,abstractInformation:$abstractInformation})";

	public Map<String, Object> toNode() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("title", this.title);
		parameters.put("date", this.date);
		parameters.put("meeting", this.meeting);
		parameters.put("abstractInformation", this.abstractInformation);
		return parameters;
	}
}
