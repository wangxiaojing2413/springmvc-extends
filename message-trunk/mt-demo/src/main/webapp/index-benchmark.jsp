<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${baseAdminPagesPath}js/jquery.min.js">
	
</script>
<script>
	var websocket = null;

	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://127.0.0.1:8080/mt\-demo/websocket");
	} else {
		alert('Not support websocket')
	}

	//连接发生错误的回调方法
	websocket.onerror = function() {
		setMessageInnerHTML("error");
	};

	//连接成功建立的回调方法
	websocket.onopen = function(event) {
		//setMessageInnerHTML("open");
	}

	//接收到消息的回调方法
	websocket.onmessage = function(event) {
		setMessageInnerHTML(event.data);
	}

	//连接关闭的回调方法
	websocket.onclose = function() {
		//setMessageInnerHTML("close");
	}

	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		websocket.close();
	}

	//将消息显示在网页上
	function setMessageInnerHTML(innerHTML) {
		document.getElementById('message').innerHTML += getNowFormatDate() + "  " + innerHTML + '<br/>';
	}

	//关闭连接
	function closeWebSocket() {
		websocket.close();
	}

	//发送消息
    function send() {
		var message = document.getElementById('text').value;
		$.ajax({
			type : "get",
			url : "/mt-demo/BenchimarkServlet",
			data : "message="+message,
			dataType : "text",
			success : function(data, status) {

			},
			complete : function() {

			}

		});
    }

    //发送消息
    function monitor() {
        $.ajax({
            type : "get",
            url : "/mt-demo/MonitorServlet",
            data : "",
            dataType : "text",
            success : function(data, status) {

            },
            complete : function() {

            }

        });
    }

    //清空console消息
    function clearConsole() {
        document.getElementById('message').innerHTML = '';
    }
	
	// 获取当前时间
	function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + date.getHours() + seperator2 + date.getMinutes()
	            + seperator2 + date.getSeconds()+seperator2+date.getMilliseconds();
	    return currentdate;
	}
</script>
</head>
<body>
	<h2>message-trunk 消息总线性能测试</h2>
	<br />
	消息:<input id="text" type="text" />
	<br />
	次数:<input id="times" type="times" />
	<button onclick="send()">发送消息</button><button onclick="clearConsole()">清空控制台</button><button onclick="monitor()">查询消息处理状况</button>
	<br /> 控制台：
	<div id="message"></div>
</body>
</html>