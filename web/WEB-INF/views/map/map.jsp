<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta charset="utf-8">
    <title>지도 생성하기</title>
    <link href="/static/css/custom_overlay.css" rel="stylesheet" type="text/css">
</head>
<body>
<!-- 지도 표시할 div태그 -->
<div id="map" style="width:100%;height:80%;"></div>
<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=28c7a8a13fd45258a42b15ec36bd466f"></script>
<script>
var nameTemp = "";
var pointsTemp = [];
var polygonTemp;

function BestAndCount(cityProvince, bestPlaceName, bestPlaceImage, count, numOfJJIM) {
    this.cityProvince = cityProvince;
    this.bestPlaceName = bestPlaceName;
    this.bestPlaceImage = bestPlaceImage;
    this.count = count;
    this.numOfJJIM = numOfJJIM;
}
var list = new Array();
<c:forEach items="${BestAndCount}" var="item">
    var bestAndCount = new BestAndCount("${item.value.cityProvince}", "${item.value.bestPlaceName}",
      "${item.value.bestPlaceImage}", ${item.value.count}, ${item.value.numOfJJIM});
    list.push(bestAndCount);
</c:forEach>

var customOverlay = new kakao.maps.CustomOverlay({
    position: null,
    content: null,
    xAnchor: 0.3,
    yAnchor: 0.91
});
    // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(35.470507, 127.582846) // 지도의 중심좌표
        };

    var map = new kakao.maps.Map(mapContainer, mapOption);

    // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
    var bounds = new kakao.maps.LatLngBounds();
    bounds.extend(new kakao.maps.LatLng(38.493487983622714, 129.62012593938164));
    bounds.extend(new kakao.maps.LatLng(33.198604099249614, 125.80787037497862));
    map.setBounds(bounds);

    var jsonLocation = "/static/json/TL_SCCO_CTPRVN.json";

    // JSON에서 좌표 읽어서 폴리곤마다 폴리곤 그리는 함수 호출
	$.getJSON(jsonLocation, function(data){
		var data = data.features;
		var coordinates = [];
		var name = '';
	    $.each(data, function(i, val){
            if(val.geometry.type == "MultiPolygon"){
                name = val.properties.CTP_KOR_NM;
                var polygons = val.geometry.coordinates;

        		var polygons_co = [];
                $.each(polygons, function(i, val){

                    displayArea(val, name);
                });
            }else if(val.geometry.type == "Polygon"){
                coordinates = val.geometry.coordinates;
                name = val.properties.CTP_KOR_NM;

                displayArea(coordinates, name);
            }
	    });
	});

	var polygons = [];

    // 다각형 그리는 함수
    function displayArea(coordinates, name) {
    var path = [];
    var points = [];

        $.each(coordinates[0], function(i, coordinate) {
            var point = new Object();
            point.x = coordinate[1];
            point.y = coordinate[0];
            points.push(point);
            path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
        });

        var polygon = new kakao.maps.Polygon({
            path: path, // 그려질 다각형의 좌표 배열입니다
            strokeWeight: 2, // 선의 두께입니다
            strokeColor: '#004C80', // 선의 색깔입니다
            strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            fillColor: '#fff', // 채우기 색깔입니다
            fillOpacity: 0.7 // 채우기 불투명도 입니다
        });

        polygons.push(polygon);
		polygon.setMap(map);

<%----%>
<%--kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
    polygon.setOptions({
        fillColor : '#09f'
    });
    customOverlay.setContent('<div class="area">' + name + '</div>');
    customOverlay.setPosition(mouseEvent.latLng);
    customOverlay.setMap(map);
});--%>
<%--kakao.maps.event.addListener(polygon, 'mousemove', function(mouseEvent) {
    customOverlay.setPosition(mouseEvent.latLng);
});--%>
<%--kakao.maps.event.addListener(polygon, 'mouseout', function() {
    polygon.setOptions({
        fillColor : '#fff'
    });
    customOverlay.setMap(null);
});--%>

// 폴리곤 클릭 이벤트
kakao.maps.event.addListener(polygon, 'click', function() {
    // 색 변화시키기 (선택: 파란색, 비선택: 하얀색)
    deletePolygon(polygons);
    polygon.setOptions({ fillColor : '#09f' });

    // 기존 CustomOverlay 제거
    customOverlay.setMap(null);

    // 폴리곤 내의 차박지 개수 + 가장 찜이 많은 차박지 정보 가져오기
    var count = 0;
    var bestPlaceName = "";
    var bestPlaceImage = "";
    var numOfJJIM = 0;
	    $.each(list, function(i, val){
	        if(val.cityProvince == name){
                count = val.count;
                bestPlaceName = val.bestPlaceName;
                bestPlaceImage = val.bestPlaceImage;
                numOfJJIM = val.numOfJJIM;
                return false;
	        }
            count = 0;
	    });

	nameTemp = name;
	pointsTemp = points;
	polygonTemp = polygon;

    // 커스텀 오버레이에 그릴 내용
    var content = '<div class="overlaybox">' +
        '    <div class="boxtitle">' + name + '</div>' +
        '    <div class="first" style="background-image: url(' + bestPlaceImage + '); background-size : cover" >' +
        '        <div class="triangle text">1</div>' +
        '        <li class="movietitle text">' +
        '           <span>' + bestPlaceName + '</span>' +
        '           <span class="count">' + numOfJJIM + '</span>' +
        '        </li>' +
        '    </div>' +
        '    <ul>' +
        '        <li class="up">' +
        '            <span class="number">총 ' + count + '개의 차박지</span>' +
        '            <input type="button" value="클릭" onclick="zoomAndDetail()"/>' +
<%--
        '            <input type="button" value="클릭" onclick="zoomAndDetail(' + 'nameTemp' + ')"/>' +
--%>
        '        </li>' +
        '    </ul>' +
        '</div>';

    // 새로운 CustomOverlay 생성
    customOverlay.setContent(content);
    customOverlay.setPosition(centroid(points));
    customOverlay.setMap(map);

    map.setLevel(10, {anchor: centroid(points), animate: {
        duration: 350
    }});
});
// 폴리곤 클릭 리스너 끝

    // centroid 알고리즘 (폴리곤 중심좌표)
    function centroid(points){
        var i, j, len, p1, p2, f, area, x, y;
        area = x = y = 0;
        for(i = 0, len = points.length, j = len-1; i<len; j = i++){
            p1 = points[i];
            p2 = points[j];

            f = p1.y * p2.x - p2.y * p1.x;
            x += (p1.x + p2.x) * f;
            y += (p1.y + p2.y) * f;
            area += f * 3;
        }

        return new kakao.maps.LatLng(x/area, y/area);
    }

    // 전체 폴리곤 색 변경
    function deletePolygon(polygons) {
        for(var i = 0; i < polygons.length; i++) {
            polygons[i].setOptions({ fillColor : '#fff' });
        }
    }

    // 커스텀 오버레이를 닫기 위해 호출되는 함수
    function closeOverlay() {
        customOverlay.setMap(null);
    }
}
    // 다각형 그리는 함수 끝
function zoomAndDetail() {
   console.log(nameTemp);
   console.log(pointsTemp);
   console.log(polygonTemp);

    console.log(centroid(pointsTemp));
}

</script>
</body>
</html>