package com.KoreaIT.cwy.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.vo.Article;

@Controller
public class UsrArticleController {
	int lastArticleId;
	List<Article> articles;

	// 생성자
	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();

		makeTestData();
	}

	// 서비스 메서드
	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목 " + i;
			String body = "내용 " + i;

			writeArticle(title, body);
		}
	}

	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;

		return article;
	}

	private Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	private void deleteArticle(int id) {
		Article article = getArticle(id);
		articles.remove(article);
	}

	private void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);

		article.setTitle(title);
		article.setBody(body);
	}

	// 액션메서드
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {
		Article article = getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		modifyArticle(id, title, body);

		return article;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		deleteArticle(id);

		return id + "번 글을 삭제했습니다";
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticleAction(int id) {

		Article article = getArticle(id);

		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		return article;
	}

}
