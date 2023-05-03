package com.KoreaIT.cwy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.ReplyRepository;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.Reply;
import com.KoreaIT.cwy.demo.vo.ResultData;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		replyRepository.writeReply(actorId, relTypeCode, relId, body);

		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다", id), "id", id);
	}

	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId) {
		List<Reply> replies = replyRepository.getForPrintReplies(actorId, relTypeCode, relId);

		for (Reply reply : replies) {
			controlForPrintData(actorId, reply);
		}

		return replies;
	}

	private void controlForPrintData(int actorId, Reply reply) {
		if (reply == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actorId, reply);
		reply.setActorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, reply);
		reply.setActorCanModify(actorCanModifyRd.isSuccess());
	}

	public ResultData actorCanModify(int loginedMemberId, Reply reply) {
		if (reply.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 댓글에 대한 권한이 없습니다"));
		}
		return ResultData.from("S-1", "수정 가능");
	}

	private ResultData actorCanDelete(int actorId, Reply reply) {
		if (reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다");
		}

		if (reply.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}

	public Reply getReply(int id) {
		return replyRepository.getReply(id);
	}

	public ResultData deleteReply(int id) {
		replyRepository.deleteReply(id);
		return ResultData.from("S-1", Ut.f("%d번 댓글을 삭제했습니다", id));
	}

}
