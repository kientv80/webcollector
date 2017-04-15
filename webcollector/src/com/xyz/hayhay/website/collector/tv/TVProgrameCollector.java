package com.xyz.hayhay.website.collector.tv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class TVProgrameCollector {
	Logger log = Logger.getLogger(TVProgrameCollector.class);
	public TVProgrameCollector() {

	}

	public void startCollector() {
		new Thread() {
			public void run() {
				try {
					Reader reader = new InputStreamReader(new URL("http://vtv.vn/lich-phat-song.htm").openStream(), "UTF-8");
					BufferedReader fin = new BufferedReader(reader);
					Writer writer = new OutputStreamWriter(new FileOutputStream("d:\\test.txt"), "UTF-8");
					BufferedWriter fout = new BufferedWriter(writer);
					String s;
					while ((s = fin.readLine()) != null) {
						fout.write(s);
					}

					// Remember to call close.
					// calling close on a BufferedReader/BufferedWriter
					// will automatically call close on its underlying stream
					fin.close();
					fout.close();

				} catch (Exception e) {
					log.error("", e);
				}

			};
		}.start();
	}

	public static void main(String[] args) {
		// new TVProgrameCollector().startCollector();
		/*
		 * Source source; try { source = new Source(new
		 * URL("http://vtv.vn/lich-phat-song.htm"));
		 * 
		 * // Call fullSequentialParse manually as most of the source will be //
		 * parsed.
		 * 
		 * source.fullSequentialParse();
		 * 
		 * System.out.println("Document title:"); String title = ""; Element
		 * titleElement = source.getFirstElement(HTMLElementName.TITLE); //
		 * TITLE element never contains other tags so just decode it //
		 * collapsing whitespace: title =
		 * CharacterReference.decodeCollapseWhiteSpace
		 * (titleElement.getContent()); Writer writer = new
		 * OutputStreamWriter(new FileOutputStream("d:\\test.txt"), "UTF-8");
		 * BufferedWriter fout = new BufferedWriter(writer); fout.write(title);
		 * List<Element> lis = source.getAllElements(HTMLElementName.LI);
		 * for(Element li : lis){ if("" .equals(li.getAttributeValue("class"))){
		 * for(Element e : li.getAllElements()){
		 * System.out.print(e.getContent().getTextExtractor().toString() + "|");
		 * } System.out.println(); } } // System.out.println(
		 * "\nSame again but this time extend the TextExtractor class to also exclude text from P elements and any elements with class=\"control\":\n"
		 * ); // TextExtractor textExtractor=new TextExtractor(source) { //
		 * public boolean excludeElement(StartTag startTag) { // return
		 * startTag.getName()==HTMLElementName.SPAN ||
		 * "title".equalsIgnoreCase(startTag.getAttributeValue("class")); // }
		 * // }; //
		 * fout.write(textExtractor.setIncludeAttributes(false).toString()); //
		 * fout.close(); } catch (MalformedURLException e) { // TODO
		 * Auto-generated catch block log.error("", e); } catch (IOException
		 * e) { // TODO Auto-generated catch block log.error("", e); }
		 */

		URL url;
		try {
			url = new URL("http://tv24.vn/Lich_Phat_Song_SCTV_VTV.html");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			String urlParameters = "page=lps&channel_id=11&token=207ceb43c9a5fdd2726ce9e68309dc4f&date=2015-04-30";
//			System.out.println(urlParameters.getBytes().length);
//			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Cookie","ASP.NET_SessionId=31xmy3vlrfegmd552fgypjqi");
			connection.setRequestProperty("Cookie","__utma=62855913.1016966512.1430420049.1430463913.1430467873.5");
			connection.setRequestProperty("Cookie","__utmb=62855913.1.10.1430467873	");
			connection.setRequestProperty("Cookie","__utmc=62855913");
			connection.setRequestProperty("Cookie","__utmt=1");
			connection.setRequestProperty("Cookie","__utmz=62855913.1430463913.4.2.utmcsr=sctv.com.vn|utmccn=(referral)|utmcmd=referral|utmcct=/vn/");
			
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("__EVENTTARGET", "ctl16$ddlChannel"));
			params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "ctl16$CA0B0334"));
			params.add(new BasicNameValuePair("ctl16$ddlChannel", "5"));
			params.add(new BasicNameValuePair("ctl16$cDate", "2015-05-01"));
			params.add(new BasicNameValuePair("ctl16_cDate_calendar_SD:", "[]"));
			params.add(new BasicNameValuePair("ctl15$txtSearch", "Tên Phim/ Diễn Viên"));
			params.add(new BasicNameValuePair("ctl16$cDate$dateInput", "2015-05-01-00-00-00"));
			params.add(new BasicNameValuePair("ctl16_cDate_calendar_AD", "[[1900,1,1],[2099,12,30],[2015,5,1]]"));
			params.add(new BasicNameValuePair("ctl16_cDate_ClientState", "{\"minDateStr\":\"1/1/1900 0:0:0\",\"maxDateStr\":\"12/31/2099 0:0:0\"}"));
			params.add(new BasicNameValuePair("ctl16_cDate_dateInput_ClientState", "{\"enabled\":true,\"emptyMessage\":\"\",\"minDateStr\":\"1/1/1900 0:0:0\",\"maxDateStr\":\"12/31/2099 0:0:0\",\"enteredText\":\"\"}"));
			
			params.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWMgKk+oT8CAL23pqTDwKTi6XFCgLf1JCxDQLalYbtAgLhlO5EAuSPzIoNAvut+t0FAvXTuPUJAuW8kpsFAvu8kpsFAvi8kpsFAvm8kpsFAv68kpsFAv+8kpsFAvy8kpsFAu28kpsFAuK8kpsFAvq86pgFAvy89pgFAv+83pgFAv+82pgFAsXvmtEHAvi86pgFAvq80pgFAo+63gwC+rySmwUCmZ6E0A0Ck93yuwsCmZ6Q0A0C5bySmwUC5bySmwUC5bySmwUC+by+mwUC/LyymwUC+LyymwUC/7zmmAUC/ry+mwUC4ryymwUCpLeu5QMC7by+mwUC+bzSmAUC/LzSmAUC/LzqmAUC/rz2mAUC/LzemAUC/ryymwUC7bzamAUC7bzemAUC4rzemAUZpT96bCRwRJiAZNt15/hyTUlLdg=="));
			params.add(new BasicNameValuePair("__EVENTARGUMENT",""));
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
			        new OutputStreamWriter(os, "UTF-8"));
			writer.write(getQuery(params));
			writer.flush();
			writer.close();
			os.close();

			BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while (r.read() > 0) {
				System.out.println(r.readLine());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

	}
	public static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}
}
