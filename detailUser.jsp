<%@page import="com.test.sku.servlet.User"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% User user = (User) request.getAttribute("deail"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이용자 상세보기</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<style type="text/css">
	table { border:1px solid black; padding:10px; border-collapse:collapse; border-spacing: 0; margin:0.2em auto;}
	th:first-child {background-color:#ddd; border-right:3px double black;}
	th,td {border:1px solid black; padding: 0.2em 1em;}
	td>a {text-decoration: none;}
	div#main {width:fit-content; margin:1em auto;}
	h3 { text-align: center; text-decoration: underline;}
	
</style>
<script type="text/javascript">
    function goList(){
    	location.href = "user?cmd=List"; 
    }

    function deleteUser(){
        if(!confirm('정말로 삭제하시겠어요?')) return;

        let obj = {};  
        obj.cmd = 'delete'; 
        obj.uid = '<%=user.getUid()%>';
		console.log('delete ready!');
        $.ajax({ 
            url : 'user',
            method: 'post',
            cache : false,
            data: obj,
            dataType: 'json',
            success: function(res){
                alert(res.deleted ? '삭제 성공':'삭제 실패');
            },
            error: function(xhr, status, err){
                alert('에러: ' + err);
            }
        }); 
    }
</script>
</head>
<body>
<div id="main">

<h3>이용자 상세보기</h3>

<table>
    <tr><th>아이디</th><th><%=user.getUid()%></th></tr>
    <br><tr><th>암 호</th><th><%=user.getPwd()%></td></tr>
</table>

<p>
<a href="user?cmd=List">목록보기</a>
<a href="user?cmd=List"><button>목록보기</button></a>
<a href="user?cmd=List"><button onclick="goList();">목록보기2</button></a>
<a href="user?cmd=edit&uid=<%=user.getUid()%>"><button>수정</button></a>
<a href="javascript:deleteUser();">삭제</a>
</p>

</div>
</body>
</html>
