<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API Test4" />

<%@ include file="../common/head.jspf"%>

<div id="map" style="width: 100%; height: 350px;"></div>
<span>
	<button onclick="setCenter()">지도 중심좌표 카카오로 이동시키기</button>
	<br />
	<button onclick="panTo()">지도 중심좌표 부드럽게 현충원으로 이동시키기</button>


	<p id="result"></p>
</span>
<div class="msg-1"></div>



<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9cb7a158e6102f18491a3f7057d13c6a"></script>
<script>

	// 현충원
	const API_KEY = 'iSvQ7Vi3JSjHu%2Bn8GvolNyc7HAMHoPmx1dfWwe5LsjMacuyt%2B2GCHhwxhWagMkk81l6HdmyxwexsjZF75F4Dmw%3D%3D';
	
	var Lalocation;
	var Lolocation;
	
	async function getData() {
		const url = 'http://apis.data.go.kr/1180000/DaejeonNationalCemetery/Burialinfo042?name=홍길동&pageNo=1&numOfRows=50&serviceKey='
				+ API_KEY;
		const response = await fetch(url);
		const data = await response.json();

		// TODO : 위도 경도 데이터 가져오기
		Lalocation = data.body[0].latitude;
		Lolocation = data.body[0].longitude;

		console.log("data", data);
		
		$('.msg-1').html('<div>현충원 정보 불러오기!</div>')
		
	}

	getData();
	
	var lat = 36.3701311788239;
	var lot = 127.298272866466;

	// 	현충원
	// 카카오
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		level : 3
	// 지도의 확대 레벨
	};

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

	// 지도 타입 변경 컨트롤을 생성한다
	var mapTypeControl = new kakao.maps.MapTypeControl();

	// 지도의 상단 우측에 지도 타입 변경 컨트롤을 추가한다
	map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);	

	// 지도에 확대 축소 컨트롤을 생성한다
	var zoomControl = new kakao.maps.ZoomControl();

	// 지도의 우측에 확대 축소 컨트롤을 추가한다
	map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

	// 지도에 마커를 생성하고 표시한다
	var marker = new kakao.maps.Marker({
	    position: new kakao.maps.LatLng(lat, lot), // 마커의 좌표
	    map: map // 마커를 표시할 지도 객체
	});

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
		var moveLatLon = new kakao.maps.LatLng(lat,lot);

		// 지도 중심을 부드럽게 이동시킵니다
		// 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
		map.panTo(moveLatLon);
		
		
	}

	// 지도가 이동, 확대, 축소로 인해 중심좌표가 변경되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
	kakao.maps.event.addListener(map, 'center_changed', function() {

		// 지도의  레벨을 얻어옵니다
		var level = map.getLevel();

		// 지도의 중심좌표를 얻어옵니다 
		var latlng = map.getCenter();

		var message = '<p>지도 레벨은 ' + level + ' 이고</p>';
		message += '<p>중심 좌표는 위도 ' + latlng.getLat() + ', 경도 '
				+ latlng.getLng() + '입니다</p>';

		var resultDiv = document.getElementById('result');
		resultDiv.innerHTML = message;

	});
</script>

<%@ include file="../common/foot.jspf"%>