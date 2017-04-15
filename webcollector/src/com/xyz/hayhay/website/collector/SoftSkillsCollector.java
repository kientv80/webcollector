package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.KyNangParser;

import net.htmlparser.jericho.Source;

public class SoftSkillsCollector extends ArticleCollector {
	public SoftSkillsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return new KyNangParser().collectArticle(source, url, fromWebsite);
	}

	@Override
	public String[] collectedUrls() {
		String[] t = new String[0];
		return KyNangParser.categories.keySet().toArray(t);
	}

}
