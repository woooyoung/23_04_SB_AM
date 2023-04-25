<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="../article/doWrite" method="POST">
				<table>
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>작성자</th>
							<td>
								<div>${rq.loginedMember.nickname }</div>
							</td>
						</tr>
						<tr>
							<th>게시판</th>
							<td>
								<select class="select select-ghost w-full max-w-xs" name="boardId">
									<!-- 									<option selected="selected" disabled>게시판을 선택해주세요</option> -->
									<option value="1">공지사항</option>
									<option value="2">자유</option>
									<option value="3">QNA</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td>
								<input class="input input-bordered w-full max-w-xs" type="text" name="title" placeholder="제목을 입력해주세요" />
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td>
								<textarea class="input input-bordered w-full max-w-xs" type="text" name="body" placeholder="내용을 입력해주세요" /></textarea>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button type="submit" value="작성" />
								작성
								</button>
							</td>
						</tr>
					</tbody>

				</table>
			</form>
		</div>
		<div class="btns">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>


		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf"%>