package com.KoreaIT.cwy.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.KoreaIT.cwy.demo.service.ArticleService;
import com.KoreaIT.cwy.demo.service.BoardService;
import com.KoreaIT.cwy.demo.service.GenFileService;
import com.KoreaIT.cwy.demo.service.ReactionPointService;
import com.KoreaIT.cwy.demo.service.ReplyService;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.Board;
import com.KoreaIT.cwy.demo.vo.Reply;
import com.KoreaIT.cwy.demo.vo.ResultData;
import com.KoreaIT.cwy.demo.vo.Rq;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;

	@Autowired
	private GenFileService genFileService;

	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.jsHitoryBackOnView("없는 게시판이야");
		}

		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

		int itemsInAPage = 10;

		int pagesCount = (int) Math.ceil(articlesCount / (double) itemsInAPage);

		List<Article> articles = articleService.getForPrintArticles(boardId, itemsInAPage, page, searchKeywordTypeCode,
				searchKeyword);

		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("board", board);
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHitoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다!", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHitoryBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);

		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다@", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}

		articleService.modifyArticle(id, title, body);

		return rq.jsReplace(Ut.f("%d번 글을 수정 했습니다", id), Ut.f("../article/detail?id=%d", id));
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 글을 삭제 했습니다", id), "../article/list?boardId=1");
	}

	@RequestMapping("/usr/article/write")
	public String showWrite(String title, String body) {

		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(int boardId, String title, String body, String replaceUri,
			MultipartRequest multipartRequest) {

		if (Ut.empty(title)) {
			return rq.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-2", "내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);

		int id = (int) writeArticleRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, id);
			}
		}

		return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다", id), replaceUri);
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				"article", id);

		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);

		int repliesCount = replies.size();

		model.addAttribute("repliesCount", repliesCount);
		model.addAttribute("replies", replies);
		model.addAttribute("article", article);
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());

		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();

			if (sumReactionPointByMemberId > 0) {
				model.addAttribute("actorCanCancelGoodReaction", true);
			} else {
				model.addAttribute("actorCanCancelBadReaction", true);
			}
		}

		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData doIncreaseHitCountRd(int id) {

		ResultData increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}

}
