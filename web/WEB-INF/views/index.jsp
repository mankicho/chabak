<%--
  Created by IntelliJ IDEA.
  User: 82102
  Date: 2020-10-26
  Time: 오후 3:58
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>

<html>
<head>
  <meta charset="utf-8">
  <title>주소로 장소 표시하기</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
</head>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

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

<div id="map" style="width:100%;height:800px;"></div>

</body>


<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=620d1830d3b920c9a60ffb13f82a1733&libraries=services"></script>
<script>
  var running_flag = false;
  var file_name = "";
  var linePath = [];
  var line_buffer = [];

  var action_layout = {
    layout_lat : document.getElementById("lat"),
    layout_lon : document.getElementById("lon"),
    layout_start_button : document.getElementById('start_button'),
    layout_end_button : document.getElementById('end_button')
  };

  var mapContainer = document.getElementById('map'), // 지도를 표시할 div
          mapOption = {
            center: new kakao.maps.LatLng(37.456928, 126.895458), // 지도의 중심좌표
            level: 4 // 지도의 확대 레벨
          };

  //지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
  var map = new kakao.maps.Map(mapContainer, mapOption);

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
        action_layout.layout_lat.innerHTML = coords['Ha'];
        action_layout.layout_lon.innerHTML = coords['Ga'];
      }else{
        action_layout.layout_lat.innerHTML = 'fail';
        action_layout.layout_lon.innerHTML = 'fail';
      } // end if
    }); // end geocoder.addressSearch
  } // end search function

  // 지도에 클릭 이벤트를 등록합니다
  // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
  kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
    if(running_flag == false){
      action_layout.layout_lat.innerHTML = 'start button click';
      action_layout.layout_lon.innerHTML = 'start button click';
    }else{
      // 클릭한 위도, 경도 정보를 가져옵니다
      var latlng = mouseEvent.latLng;

      action_layout.layout_lat.innerHTML = latlng.getLat();
      action_layout.layout_lon.innerHTML = latlng.getLng();

      line_buffer.push( new kakao.maps.LatLng(latlng.getLat(), latlng.getLng()) )
      update_map();
    }
  }); // end kakao.maps.event.addListener

  // 지도에 표시할 선을 생성하는 함수
  function update_map(){
    // 지도에 표시할 선을 생성합니다
    var polyline = new kakao.maps.Polyline({
      path: line_buffer, // 선을 구성하는 좌표배열 입니다
      strokeWeight: 5, // 선의 두께 입니다
      strokeColor: '#FF0000', // 선의 색깔입니다
      strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
      strokeStyle: 'solid' // 선의 스타일입니다
    });
    polyline.setMap(null); // 지도에서 제거한다.
    polyline.setMap(map); // 지도에 선을 표시합니다
  } // end update_map function

  function download(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
  } // end download function

  function start_route(){
    running_flag = true;
    var today = new Date();
    file_name = get_now_time() + ".txt";

    var str = document.getElementById("lat");
    str.innerHTML = '저장 파일명 : ' + file_name;

    action_layout.layout_start_button.disabled = true;
    action_layout.layout_end_button.disabled = false;
  } // end start_route function

  function end_route(){
    var message_buufer = "";
    for (var i = 0 ; i < line_buffer.length ; i++){
      var lat =  line_buffer[i].getLat();
      var lon =  line_buffer[i].getLng();
      var message = lat + '\t' + lon + '\n';
      message_buufer += message;
    }
    linePath.push( line_buffer );
    download( file_name, message_buufer );

    action_layout.layout_lat.innerHTML =  'success file make.';
    action_layout.layout_lon.innerHTML = '버퍼 초기화.';

    // 초기화
    running_flag = false;
    file_name = "";
    action_layout.layout_start_button.disabled = false;
    action_layout.layout_end_button.disabled = true;
    line_buffer = [];
  } // end end_route function

  function leading_zeros(n, digits) {
    var zero = '';
    n = n.toString();

    if (n.length < digits) {
      for (i = 0; i < digits - n.length; i++)
        zero += '0';
    }
    return zero + n;
  } // end leading_zeros function

  function get_now_time() {
    var d = new Date();
    var s =
            leading_zeros(d.getDate(), 2) +
            leading_zeros(d.getHours(), 2) +
            leading_zeros(d.getMinutes(), 2) +
            leading_zeros(d.getSeconds(), 2);

    return s;
  } // end get_now_time function
</script>


<script type="text/javascript">
  $(function() {
    var parseName = ".\\data\\금천구 주차금지 경위도.csv";// 파일 이름 + 숫자 (var -> int 할거)
    $.ajax({
      url:parseName,
      dataType:'text',
      success: function(data) {
        var allRow = data;
        //.split(/\r?\n|\r/)
        var textLine = "";
        for(var singleRow = 0; singleRow < allRow.length; singleRow++) {
          var collapse = allRow[singleRow].split(",");

          for(var count = 0; count < collapse.length; count++) {
            textLine += collapse[count];
          }
        }
        $('#textArea').append(textLine);
        $('#textArea').append("<br>");
      }
    });
  });

</script>
</html>