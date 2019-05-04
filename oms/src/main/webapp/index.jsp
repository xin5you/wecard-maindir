<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>  
<html>  
<head>  
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<title>Hello, World</title>  
<style type="text/css">  
html{height:100%}  
body{height:100%;margin:0px;padding:0px}  
#container{height:100%}  
</style>  
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=url9qQ8rjCrI4mavovKttZG3y9zIW15O"></script>
</head>  
 
<body>  
<div id="container"></div> 
<script type="text/javascript"> 

var map = new BMap.Map("container");    
var point = new BMap.Point(116.404, 39.915);    
map.centerAndZoom(point, 15);    
var marker = new BMap.Marker(point);        // 创建标注    
map.addOverlay(marker);

marker.enableDragging();    
marker.addEventListener("dragend", function(e){    
 alert("当前位置：" + e.point.lng + ", " + e.point.lat);    
})

</script>  
</body>  
</html>