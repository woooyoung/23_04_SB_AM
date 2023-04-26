<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<div class="flex mb-4">
				<div>
					게시물 갯수 :
					<span class="badge">${articlesCount }</span>
					개
				</div>
				<div class="flex-grow"></div>
				<form action="">
					<input type="hidden" name="boardId" value="${param.boardId }" />
					<select data-value="${param.searchKeywordTypeCode }" name="searchKeywordTypeCode" class="select select-ghost">
						<option value="title">제목</option>
						<option value="body">내용</option>
						<option value="title,body">제목 + 내용</option>
					</select>
					<input value="${param.searchKeyword }" maxlength="20" name="searchKeyword" class="input input-bordered" type="text"
						placeholder="검색어를 입력해주세요" />
					<button class="btn btn-ghost" type=submit>검색</button>
				</form>
			</div>
			<table class="table table-zebra w-full">
				<colgroup>
					<col width="70" />
					<col width="140" />
					<col width="140" />
					<col width="140" />
					<col width="140" />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="article" items="${articles }">
						<tr class="hover">
							<td>
								<div class="badge">${article.id}</div>
							</td>
							<td>${article.regDate.substring(2,16)}</td>
							<td>
								<a class="hover:underline" href="../article/detail?id=${article.id}">${article.title}</a>
							</td>
							<td>${article.extra__writer}</td>
							<td>${article.hitCount}</td>

						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
		<div class="pagination flex justify-center mt-3">
			<div class="btn-group">

				<c:set var="paginationLen" value="4" />
				<c:set var="startPage" value="${page - paginationLen >= 1 ? page - paginationLen : 1}" />
				<c:set var="endPage" value="${page + paginationLen <= pagesCount ? page + paginationLen : pagesCount}" />

				<c:set var="baseUri" value="?boardId=${boardId }" />
				<c:set var="baseUri" value="${baseUri }&searchKeywordTypeCode=${searchKeywordTypeCode}" />
				<c:set var="baseUri" value="${baseUri }&searchKeyword=${searchKeyword}" />

				<c:if test="${startPage > 1 }">
					<a class="btn" href="${baseUri }&page=1">1</a>
					<button class="btn btn-disabled">...</button>
				</c:if>

				<c:forEach begin="${startPage }" end="${endPage }" var="i">
					<a class="btn ${page == i ? 'btn-active' : '' }" href="${baseUri }&page=${i}">${i }</a>
				</c:forEach>

				<c:if test="${endPage < pagesCount }">
					<button class="btn btn-disabled">...</button>
					<a class="btn" href="${baseUri }&page=${pagesCount}">${pagesCount }</a>
				</c:if>
			</div>
		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf"%>