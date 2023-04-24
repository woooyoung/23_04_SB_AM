<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE MODIFY" />
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="../article/doModify" method="POST">
				<input type="hidden" name="id" value="${article.id }" />
				<table>
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>번호</th>
							<td>${article.id }</td>
						</tr>
						<tr>
							<th>작성날짜</th>
							<td>${article.regDate }</td>
						</tr>
						<tr>
							<th>수정날짜</th>
							<td>${article.updateDate }</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td>${article.extra__writer }</td>
						</tr>
						<tr>
							<th>제목</th>
							<td>
								<input class="w-full" type="text" name="title" placeholder="제목을 입력해주세요" value="${article.title }" />
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td>
								<textarea class="w-full" type="text" name="body" placeholder="내용을 입력해주세요" />${article.body }</textarea>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button type="submit" value="수정" />
								수정
								</button>
							</td>
						</tr>
					</tbody>

				</table>
			</form>
		</div>
		<div class="btns">
			<button class="btn-text-link" type="button" onclick="history.back();">뒤로가기</button>

			<c:if test="${article.actorCanModify }">
				<a class="btn-text-link" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.actorCanDelete }">
				<a class="btn-text-link" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;"
					href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf"%>