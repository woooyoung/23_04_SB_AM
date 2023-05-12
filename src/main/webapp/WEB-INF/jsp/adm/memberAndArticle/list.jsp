<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="관리자 페이지 - 회원,게시글 리스트" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl flex">
	<div>
		<div class="container mx-auto px-3">
			<div class="flex">
				<div>
					회원 수 :
					<span class="badge">${membersCount }명</span>
				</div>
			</div>
			<div>
				<form class="flex">

					<select data-value="${authLevel }" name="authLevel" class="select select-bordered">
						<option disabled="disabled">회원 타입</option>
						<option value="3">일반</option>
						<option value="7">관리자</option>
						<option value="0">전체</option>

					</select>
					<select data-value="${searchKeywordTypeCode }" name="MsearchKeywordTypeCode" class="select select-bordered">
						<option disabled="disabled">검색 타입</option>
						<option value="loginId">아이디</option>
						<option value="name">이름</option>
						<option value="nickname">닉네임</option>
						<option value="loginId,name,nickname">전체</option>
					</select>


					<input name="MsearchKeyword" type="text" class="ml-2 w-96 input input-borderd" placeholder="검색어를 입력해주세요"
						maxlength="20" value="${param.searchKeyword }" />
					<button type="submit" class="ml-2 btn btn-ghost">검색</button>
				</form>
			</div>
			<div class="table-box-type-1 mt-3">
				<table class="table table-fixed w-full">
					<colgroup>
						<col width="70" />
						<col width="140" />
						<col width="140" />
						<col width="140" />
						<col width="140" />
						<col width="140" />
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>가입날짜</th>
							<th>수정날짜</th>
							<th>아이디</th>
							<th>이름</th>
							<th>닉네임</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="member" items="${members }">
							<tr class="hover">
								<td>${member.id}</td>
								<td>${member.forPrintType1RegDate}</td>
								<td>${member.forPrintType1UpdateDate}</td>
								<td>${member.loginId}</td>
								<td>${member.name}</td>
								<td>${member.nickname}</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>
			<div class="page-menu mt-3 flex justify-center">
				<div class="btn-group">

					<c:set var="pageMenuLen" value="6" />
					<c:set var="startPage" value="${Mpage - pageMenuLen >= 1 ? Mpage- pageMenuLen : 1}" />
					<c:set var="endPage" value="${Mpage + pageMenuLen <= pagesCount ? Mpage + pageMenuLen : pagesCount}" />

					<c:set var="pageBaseUri" value="?boardId=${boardId }" />
					<c:set var="pageBaseUri" value="${pageBaseUri }&MsearchKeywordTypeCode=${param.MsearchKeywordTypeCode}" />
					<c:set var="pageBaseUri" value="${pageBaseUri }&MsearchKeyword=${param.MsearchKeyword}" />

					<c:if test="${startPage > 1}">
						<a class="btn btn-sm" href="${pageBaseUri }&Mpage=1">1</a>
						<c:if test="${startPage > 2}">
							<a class="btn btn-sm btn-disabled">...</a>
						</c:if>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<a class="btn btn-sm ${Mpage == i ? 'btn-active' : '' }" href="${pageBaseUri }&page=${i }">${i }</a>
					</c:forEach>
					<c:if test="${endPage < pagesCount}">
						<c:if test="${endPage < pagesCount - 1}">
							<a class="btn btn-sm btn-disabled">...</a>
						</c:if>
						<a class="btn btn-sm" href="${pageBaseUri }&page=${pagesCount }">${pagesCount }</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div>
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
						<select data-value="${param.searchKeywordTypeCode }" name="AsearchKeywordTypeCode" class="select select-ghost">
							<option value="title">제목</option>
							<option value="body">내용</option>
							<option value="title,body">제목 + 내용</option>
						</select>
						<input value="${param.searchKeyword }" maxlength="20" name="AsearchKeyword" class="input input-bordered"
							type="text" placeholder="검색어를 입력해주세요" />
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
							<th>좋아요</th>
							<th>싫어요</th>
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
									<a class="hover:underline" href="${rq.getArticleDetailUriFromArticleList(article) }">${article.title}</a>
								</td>
								<td>${article.extra__writer}</td>
								<td>${article.hitCount}</td>
								<td>${article.goodReactionPoint}</td>
								<td>${article.badReactionPoint}</td>

							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>


			<div class="pagination flex justify-center mt-3">
				<div class="btn-group">

					<c:set var="paginationLen" value="4" />
					<c:set var="startPage" value="${Apage - paginationLen >= 1 ? Apage - paginationLen : 1}" />
					<c:set var="endPage" value="${Apage + paginationLen <= pagesCount ? Apage + paginationLen : pagesCount}" />

					<c:set var="baseUri" value="?boardId=${boardId }" />
					<c:set var="baseUri" value="${baseUri }&AsearchKeywordTypeCode=${AsearchKeywordTypeCode}" />
					<c:set var="baseUri" value="${baseUri }&AsearchKeyword=${AsearchKeyword}" />

					<c:if test="${startPage > 1 }">
						<a class="btn" href="${baseUri }&Apage=1">1</a>
						<button class="btn btn-disabled">...</button>
					</c:if>

					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<a class="btn ${Apage == i ? 'btn-active' : '' }" href="${baseUri }&page=${i}">${i }</a>
					</c:forEach>

					<c:if test="${endPage < pagesCount }">
						<button class="btn btn-disabled">...</button>
						<a class="btn" href="${baseUri }&page=${pagesCount}">${pagesCount }</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>