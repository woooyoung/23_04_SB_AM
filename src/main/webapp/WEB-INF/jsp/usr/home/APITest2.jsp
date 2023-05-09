<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API Test2" />

<script>
	const API_KEY = 'iSvQ7Vi3JSjHu%2Bn8GvolNyc7HAMHoPmx1dfWwe5LsjMacuyt%2B2GCHhwxhWagMkk81l6HdmyxwexsjZF75F4Dmw%3D%3D';

	async function getData() {
		const url = 'http://apis.data.go.kr/1180000/DaejeonNationalCemetery/Burialinfo042?name=홍길동&pageNo=1&numOfRows=50&serviceKey='
				+ API_KEY;
		const response = await fetch(url);
		const data = await response.json();
		console.log("data", data);
	}

	getData();
</script>

<%@ include file="../common/head.jspf"%>

<%@ include file="../common/foot.jspf"%>