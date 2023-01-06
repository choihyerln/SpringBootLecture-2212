package com.mulcam.demo.crawling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class Crawling {
	
//	public List<Interpark> interpark() throws Exception {
//		
//		String url = "http://book.interpark.com/display/collectlist.do?_method=BestsellerHourNew201605&bestTp=1&dispNo=028#";
//		Document doc = Jsoup.connect(url).get();
//		Elements lis = doc.select(".rankBestContentList > ol > li");		// > : 자식 셀렉터
//		
//		List<Interpark> list = new ArrayList<>();
//		for (Element li: lis) {
//			Elements spans = li.select(".rankNumber.digit2").select("span");		// class -> 클래스 마다 꼭 . 해줘야함!
//			String rank_ = "";
//			for (Element span: spans) {
//				String classes = span.attr("class").strip();
//				rank_ += classes.substring(classes.length() -1);
//			}
//			int rank = Integer.parseInt(rank_);
//			String src = li.select(".coverImage img").attr("src");		// > 화살표 없음 : 자손 셀렉터
//			String title = li.select(".itemName").text().strip();
//			String company = li.select(".company").text().strip();
//			String author = li.select(".author").text().strip();
//			String price_ = li.select(".price > em").text().strip();
//			int price = Integer.parseInt(price_.replace(",", ""));
//			Interpark book = new Interpark(rank, src, title, author, company, price);
//			list.add(book);
//		}
//		return list;
	
	public List<Genie> genie() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		String ymd = now.toString().substring(0, 10).replace("-", "");
		String hh = now.toString().substring(11,13);
		
		//https://genie.co.kr/chart/top200?ditc=D&ymd=20230106&hh=13&rtm=Y&pg=i
		List<Genie> list = new ArrayList<>();
		// 페이지에 대한 Loop
		for (int i=1; i<=4; i++) {
			String url = "https://genie.co.kr/chart/top200?ditc=D&ymd=" + ymd
						+ "&hh=" + hh + "=13&rtm=Y&pg=" + i;
			Document doc = Jsoup.connect(url).get();
			Elements trs = doc.select("tr.list");		// 50개씩 추출됨
			
			for (Element tr: trs) {
				String rank_ = tr.select("td.number").text().split(" ")[0];
				int rank = Integer.parseInt(rank_);
				
				String src = "https:" + tr.select(".cover > img").attr("src");
				String title = tr.select(".title.ellipsis").text().strip();
				String artist = tr.select(".artist.ellipsis").text().strip();
				String album = tr.select(".albumtitle.ellipsis").text().strip();
				Genie genie = new Genie(rank, src, title, artist, album);
				list.add(genie);
			}
		}
		return list;
	}
}