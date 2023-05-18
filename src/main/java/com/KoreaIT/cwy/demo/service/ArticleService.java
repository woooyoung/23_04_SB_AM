package com.KoreaIT.cwy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.ArticleRepository;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.Member;
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

	public List<Article> getForPrintArticles(int boardId, int itemsInAPage, int page, String searchKeywordTypeCode,
			String searchKeyword) {

		int limitFrom = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;

		return articleRepository.getForPrintArticles(boardId, searchKeywordTypeCode, searchKeyword, limitFrom,
				limitTake);
	}

	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	public ResultData increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없음", "affectedRow", affectedRow);
		}

		return ResultData.from("S-1", "조회수 증가", "affectedRowRd", affectedRow);
	}

	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}

	public ResultData increaseGoodReationPoint(int relId) {
		int affectedRow = articleRepository.increaseGoodReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}

	public ResultData increaseBadReationPoint(int relId) {

		int affectedRow = articleRepository.increaseBadReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}

	public ResultData decreaseGoodReationPoint(int relId) {
		int affectedRow = articleRepository.decreaseGoodReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}

	public ResultData decreaseBadReationPoint(int relId) {
		int affectedRow = articleRepository.decreaseBadReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 감소", "affectedRow", affectedRow);

	}

	public Article getArticleById(int id) {
		return articleRepository.getArticle(id);
	}

	public void deleteArticles(List<Integer> articleIds) {
		for (int articleId : articleIds) {
			Article article = getArticleById(articleId);

			if (article != null) {
				deleteArticle(article);
			}
		}
	}

	private void deleteArticle(Article article) {
		articleRepository.deleteArticle(article.getId());
	}

}
