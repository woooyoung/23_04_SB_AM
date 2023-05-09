<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API Test" />

<script>
	const API_KEY = 'yIGj7lJBJIcshuAYg2rAxIOkX81W14YthO8jdaPqMNOxbp52KQZaUPkf83%2Bs27TMVMLnNvSzzRoUmbAuju%2F30A%3D%3D';

	async function getData() {
		const url = 'http://apis.data.go.kr/1790387/covid19CurrentStatusKorea/covid19CurrentStatusKoreaJason?serviceKey='
				+ API_KEY;
		const response = await
		fetch(url);
		const data = await
		response.json();
		console.log("data", data);
	}
	getData();
</script>

<%@ include file="../common/head.jspf"%>

<%@ include file="../common/foot.jspf"%>