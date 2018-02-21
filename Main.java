package cn.edu.bistu.core;

import java.util.List;

import cn.edu.bistu.node.Author;
import cn.edu.bistu.node.Paper;
import cn.edu.bistu.util.JDBC;

public class Main {

	public static void main(String[] args) {
		JDBC neo4j = new JDBC("bolt://localhost:7687", "neo4j", "Neo4j+00");
		GrobidParse grobidParse = new GrobidParse();
//		grobidParse.parseXML(directoryPath, resultPath); // 处理PDF 英文论文
		grobidParse.parseXML("data/Papers", "paper");
		List<Paper> papers = grobidParse.toPapers("paper");

		for (Paper paper : papers) {
			neo4j.addPaper(paper);
			for (Author author : paper.getAuthors()) {
				neo4j.addAuthor(author);

				System.out.println("【作者】" + author.getForename() + " " + author.getSurname() + ": create successfuly!");
				neo4j.addRelationShip(author, paper);
			}

			System.out.println("【论文】" + paper.getTitle() + ":create successfuly!");
		}
		neo4j.close();
	}

}
