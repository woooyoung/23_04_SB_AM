<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<div>${articlesCount }개</div>
			<table class="table table-zebra w-full">
				<colgroup>
					<col width="70" />
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

						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
		<div class="pagination flex justify-center mt-3">
			<div class="btn-group">
				<c:forEach begin="1" end="20" var="i">
					<a class="btn ${param.page == i ? 'btn-active' : '' }" href="?page=${i }">${i }</a>
				</c:forEach>

			</div>
		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf"%>