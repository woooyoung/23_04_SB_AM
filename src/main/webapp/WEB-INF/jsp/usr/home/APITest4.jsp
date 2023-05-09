<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API Test4" />

<%@ include file="../common/head.jspf"%>

<div id="map" style="width: 100%; height: 350px;"></div>
<p>
	<button onclick="setCenter()">지도 중심좌표 카카오로 이동시키기</button>
	<br />
	<button onclick="panTo()">지도 중심좌표 부드럽게 현충원으로 이동시키기</button>
</p>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9cb7a158e6102f18491a3f7057d13c6a"></script>
<script>
	// 현충원
	const API_KEY = 'iSvQ7Vi3JSjHu%2Bn8GvolNyc7HAMHoPmx1dfWwe5LsjMacuyt%2B2GCHhwxhWagMkk81l6HdmyxwexsjZF75F4Dmw%3D%3D';

	async function getData() {
		const url = 'http://apis.data.go.kr/1180000/DaejeonNationalCemetery/Burialinfo042?name=홍길동&pageNo=1&numOfRows=50&serviceKey='
				+ API_KEY;
		const response = await fetch(url);
		const data = await response.json();

		// TODO : 위도 경도 데이터 가져오기

		console.log("data", data);
	}

	getData();

	// 	현충원
	// 카카오
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		level : 3
	// 지도의 확대 레벨
	};

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

	// 마커가 표시될 위치입니다 
	var markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
		position : markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);

	// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
	// marker.setMap(null);

	function setCenter() {
		// 이동할 위도 경도 위치를 생성합니다 
		var moveLatLon = new kakao.maps.LatLng(33.452613, 126.570888);

		// 지도 중심을 이동 시킵니다
		map.setCenter(moveLatLon);
	}

	function panTo() {
		// 이동할 위도 경도 위치를 생성합니다 
		var moveLatLon = new kakao.maps.LatLng(35.452613, 124.570888);

		// 지도 중심을 부드럽게 이동시킵니다
		// 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
		map.panTo(moveLatLon);
	}
</script>

<%@ include file="../common/foot.jspf"%>