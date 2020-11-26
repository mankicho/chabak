<%--
  Created by IntelliJ IDEA.
  User: 82102
  Date: 2020-11-18
  Time: 오후 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Kakao 지도 시작하기</title>
</head>
<body>
<p style="margin-top:+15px">
  <input id="position" value = "입력" size="30">
  <button type="button" id="search_button" onclick="search()">  Submit </button>
  <button type="button" id="start_button" onclick="start_route()" style= "width:150px;height:50px;">  Start </button>
  <button type="button" id="end_button" onclick="end_route()" disabled="disabled" style="width:150px;height:50px;">  End </button>
</p>
<p class="textArea"> aaa </p>
<p id="lat">--경도가 표시--</p>
<p id="lon">--위도가 표시--</p>


</body>
<div id="map" style="width:500px;height:400px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=00ce7c732d16714582aa00d8d9d5a9d5&libraries=services"></script>

<script>
  var action_layout = {
    layout_lat : document.getElementById("lat"),
    layout_lon : document.getElementById("lon"),
    layout_start_button : document.getElementById('start_button'),
    layout_end_button : document.getElementById('end_button')
  };
  var container = document.getElementById('map');
  var options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
  };

  var map = new kakao.maps.Map(container, options);

  function search(){
    var geocoder = new kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다
    var position = document.getElementById("position"); // 검색할 변수
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(position.value, function(result, status) {
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        var marker = new kakao.maps.Marker({
          // 결과값으로 받은 위치를 마커로 표시합니다
          map: map,
          position: coords
        });
        var infowindow = new kakao.maps.InfoWindow({
          // 인포윈도우로 장소에 대한 설명을 표시합니다
          content: '<div style="width:150px;text-align:center;padding:6px 0;">검색위치</div>'
        });
        infowindow.open(map, marker);
        map.setCenter(coords); // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        action_layout.layout_lat.innerHTML = coords.getLat();
        action_layout.layout_lon.innerHTML = coords.getLng();
      }else{
        action_layout.layout_lat.innerHTML = 'fail';
        action_layout.layout_lon.innerHTML = 'fail';
      } // end if
    }); // end geocoder.addressSearch
  }
</script>
</html>