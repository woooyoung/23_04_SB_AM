package com.KoreaIT.cwy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.ArticleRepository;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.ResultData;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {

		articleRepository.writeArticle(memberId, boardId, title, body);

		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다", id), "id", id);

	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public ResultData modifyArticle(int id, String title, String body) {

		articleRepository.modifyArticle(id, title, body);

		Article article = getArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 글을 수정 했습니다", id), "article", article);
	}

	public List<Article> articles() {
		return articleRepository.getArticles();
	}

	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);

		controlForPrintData(actorId, article);

		return article;
	}

	private void controlForPrintData(int actorId, Article article) {
		if (article == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setActorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, article);
		article.setActorCanModify(actorCanModifyRd.isSuccess());
	}

	public ResultData actorCanModify(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 글에 대한 권한이 없습니다"));
		}
		return ResultData.from("S-1", "수정 가능");
	}

	private ResultData actorCanDelete(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}

	public List<Article> getForPrintArticles(int boardId) {
		return articleRepository.getForPrintArticles(boardId);
	}

	public int getArticlesCount(int boardId) {
		return articleRepository.getArticlesCount(boardId);
	}

}
