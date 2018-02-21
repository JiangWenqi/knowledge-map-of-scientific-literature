package cn.edu.bistu.util;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.graphdb.Relationship;

import cn.edu.bistu.node.Author;
import cn.edu.bistu.node.Paper;


//import java.util.HashMap;
//import java.util.Map;

public class JDBC implements AutoCloseable {
	// Driver objects are thread-safe and are typically made available
	// application-wide.
	Driver driver;
	Session session;
//	Transaction tx;
	Relationship relationship;
	public JDBC() {
	}

	public JDBC(String uri, String user, String password) {
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
		session = driver.session();
//		tx = session.beginTransaction();

		System.out.println("Connect to database successfully!");
	}

	/*
	 * 向数据库添加作者
	 */
	public void addAuthor(final Author author) {
		//先查询一下作者是否已经存在数据库
		String authorName = author.getForename() + " " + author.getSurname();
		StatementResult sr = session.run("MATCH (a:Author) where a.name = '" + authorName + "' return a");
		if(sr.hasNext()) {
		}else {
			session.run(author.TEMPLATE, author.toNode());
//			System.out.println("Insert " + author.getForename() + " " + author.getSurname() + " successfully!");
		}
	}

	/*
	 * 向数据库添加Paper
	 */
	public void addPaper(final Paper paper) {
		StatementResult sr = session.run("MATCH (p:Paper) where p.title = '" + paper.getTitle() + "' return p");
		if(sr.hasNext()) {
			
		}else {
			session.run(paper.TEMPLATE, paper.toNode());
//			System.out.println("Insert " + paper.getTitle() + " successfully!");
		}
	}
	
	public void addRelationShip(Author author, Paper paper) {
		String authorName = author.getForename() + " " + author.getSurname();
		String sypher =  "match (a:Author{name:'"+authorName+"'}), (p:Paper{title:'"+paper.getTitle()+"'}) merge (a)-[r:published]->(p)";
		session.run(sypher);
	}
//	private void printPeople(String initial) {
//		try (Session session = driver.session()) {
//			// Auto-commit transactions are a quick and easy way to wrap a read.
//			StatementResult result = session.run("MATCH (a:Author) WHERE a.name STARTS WITH {x} RETURN a.name AS name",
//					parameters("x", initial));
//			// Each Cypher execution returns a stream of records.
//			while (result.hasNext()) {
//				Record record = result.next();
//				// Values can be extracted from a record by index or name.
//				System.out.println(record.get("name").asString());
//			}
//		}
//	}

	public void close() {
		// Closing a driver immediately shuts down all open connections.
		driver.close();
	}

	public static void main(String... args) {
		
//		JDBC neo4j = new JDBC("bolt://localhost:7687", "neo4j", "Neo4j+00");
//		Author author = new Author("Vinci3", "Jiang", "jiangwenqi1995@gmail.com", "Bistu", 100000, "China");
//		neo4j.addAuthor(author,1);
//		Paper paper = new Paper("JiangWenqi_Paper4", "2014.10.8", null, "Student Meeting", "Good");
		
//		neo4j.addPaper(paper,2);
		
//		neo4j.close();
	}
}