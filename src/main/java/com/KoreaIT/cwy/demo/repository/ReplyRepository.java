package com.KoreaIT.cwy.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.cwy.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	@Insert("""
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId =#{relId},
				`body`= #{body}
			</script>
			""")

	void writeReply(int actorId, String relTypeCode, int relId, String body);

	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")
	int getLastInsertId();

	@Select("""
				SELECT R.*, M.nickname AS extra__writer
				FROM reply AS R
				LEFT JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.relTypeCode = #{relTypeCode}
				AND R.relId = #{relId}
				ORDER BY R.id DESC
			""")
	List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId);

}
