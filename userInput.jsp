<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이용자 정보 추가</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
function addUser() {
   var ser = $('#addForm').serialize();
   $.ajax({
      url:'user',
      method:'post',
      cache:false,
      data:ser,
      dataType:'json',
      success:function(res){

         alert(res.added ? '이용자 정보 추가 성공':'추가실패');
         if((!res.added) && res.cause!=""){
            alert(res.cause);
            location.href="user?cmd=loginForm";
         }
         if(res.added) location.href='user?cmd=detail&uid=' + $('#uid').val();

      },
      error:function(xhr,status,err){
         alert('에러:' + err);
      }
   });
}
</script>
</head>
<body>
<main>
<h3>이용자 정보 추가</h3>
<form id="addForm" action="user" method="post">
   <input type="hidden" name="cmd" value="add">
   <div>
      <label for="uid">아이디</label>
      <input type="text" name="uid" id="uid" value="andy">
   </div>
   <div>
      <label for="pwd">암 호</label>
      <input type="password" name="pwd" id="pwd" value="1234">
   </div>
   <div>
      <button type="reset">취소</button>
      <button type="button" onclick="addUser();">저장</button>
   </div>
</form>
</main>
</body>
</html>