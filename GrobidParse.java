package cn.edu.bistu.core;

import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.InputStreamReader;

import java.util.LinkedList;
import java.util.List;


import org.grobid.core.data.BiblioItem;
import org.grobid.core.engines.Engine;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;


import cn.edu.bistu.node.Author;
import cn.edu.bistu.node.Paper;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class GrobidParse {

	private final String pGrobidHome = "/Users/vinci/Lib/grobid-home";
	// The GrobidHomeFinder can be instantiate without parameters to verify the
	// grobid home in the standard
	// location (classpath, ../grobid-home, ../../grobid-home or in the environment
	// variable GROBID_HOME

	private Engine engine = null;

	// 初始化Grobid
	public GrobidParse() {
		try {
			// If the location is customized:
			GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
			// The GrobidProperties needs to be instantiate using the correct
			// grobidHomeFinder or it will use the default locations
			GrobidProperties.getInstance(grobidHomeFinder);
			System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
			engine = GrobidFactory.getInstance().createEngine();
		} catch (Exception e) {
			// If an exception is generated, print a stack trace
			e.printStackTrace();
		}
	}

	public String parseXML(String pdfPath) {
		String tei;
		// Biblio object for the result
		BiblioItem resHeader = new BiblioItem();
		tei = engine.processHeader(pdfPath, false, resHeader);
		return tei;
	}

	public void parseXML(String directoryPath, String resultPath) {
		boolean consolidate = false;
		try {
			engine.batchProcessHeader(directoryPath, resultPath, consolidate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Paper toPaper(String xmlString) {
		Paper paper = new Paper();
		LinkedList<Author> authors = new LinkedList<Author>(); // 一篇文章不止一个作者，所以用一个作者链表进行存储

		Html xml = new Html(xmlString); //  利用WebMagic里面的工具将XML字符串转化为HTML——（jsoup）
		// 然后就能利用Xpath进行解析出各项元素
		String title = xml.xpath("//titleStmt/title/text()").get();
		String date = xml.xpath("//publicationStmt/date/text()").get();
		String meeting = xml.xpath("//meeting/text()").get();
		String abstractInformation = xml.xpath("//abstract/p/text()").get();

		List<Selectable> authorsInfo = xml.xpath("//analytic/author").nodes(); // 所有作者信息链表
		int authorNumber = authorsInfo.size(); // 获取
		// 单独抽取每一个作者信息
		for (int i = 1; i <= authorNumber; i++) {
			Author author = new Author();
			// 取值
			String forename = xml.xpath("//analytic/author[" + i + "]/persName/forename/text()").get();
			String surname = xml.xpath("//analytic/author[" + i + "]/persName/surname/text()").get();
			String email = xml.xpath("//analytic/author[" + i + "]/persName/email/text()").get();
			String institution = xml.xpath("//analytic/author[" + i + "]/persName/affiliation/orgName/text()").get();
			String postCode = xml
					.xpath("//analytic/author[" + i + "]/persName/affiliation/orgName/address/postCode/text()").get();
			String country = xml
					.xpath("//analytic/author[" + i + "]/persName/affiliation/orgName/address/postCode/country/text()")
					.get();
			// 赋值
			author.setForename(forename);
			author.setSurname(surname);
			author.setEmail(email);
			author.setInstitution(institution);
			author.setPostCode(postCode == null ? 0 : Integer.parseInt(postCode.trim()));
			author.setCountry(country);
			authors.add(author);
		}
		paper.setTitle(title);
		paper.setDate(date);
		paper.setMeeting(meeting);
		paper.setAbstractInformation(abstractInformation);
		paper.setAuthors(authors);
		return paper;
	}

	public LinkedList<Paper> toPapers(String xmlPath) {
		LinkedList<Paper> papers = new LinkedList<>();
		File dir = new File(xmlPath);
		File[] xmls = dir.listFiles();
		for (File xml : xmls) {
			StringBuilder stringBuilder = new StringBuilder();
			try {
				FileInputStream is = new FileInputStream(xml);
				InputStreamReader streamReader = new InputStreamReader(is);
				BufferedReader reader = new BufferedReader(streamReader);
				String line = null;
				while ((line = reader.readLine()) != null) {
					// stringBuilder.append(line);
					stringBuilder.append(line);
				}
				reader.close();
				is.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Paper paper = toPaper(String.valueOf(stringBuilder));
			papers.add(paper);
		}

		return papers;

	}

	public static void main(String[] args) {
		GrobidParse grobidParse = new GrobidParse();
		// String xml = grobidParse.parseXML("data/Papers/2.pdf");
		// Paper paper = grobidParse.toPaper(xml);
		//
		// grobidParse.parseXML("data/Papers", "paper");
		List<Paper> papers = grobidParse.toPapers("paper");

		for (Paper paper : papers) {
			System.out.println(paper.toString());
		}
	}

}
