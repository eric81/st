$(function() {
	userlist();
});

function userlist(){
    $.ajax({
        url : "http://localhost/st/user/list",
        type : 'POST',
        contentType : "application/x-www-form-urlencoded; charset=utf-8",
        dataType : "jsonp",
        async : false,
        data : {
        	
        },
        cache : false,
        success : function(data) {
            $(".table").html("");
            $(".table").append("<tr><td>用户</td><td>角色</td><td>权限</td><td>注册日期</td></tr>");
            $.each(data, function(index, user) {
                $(".table").append("<tr><td>" + user.name + "</td><td>" + user.roles + "</td><td>" + user.permissions + "</td><td>" + formatTime(user.registerDate) + "</td></tr>");
            }); 
        }
    });
}

function formatTime(obj, IsMi) {
	var myDate = new Date(obj);
	var year = myDate.getFullYear();
	var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
	var day = ("0" + myDate.getDate()).slice(-2);
	var h = ("0" + myDate.getHours()).slice(-2);
	var m = ("0" + myDate.getMinutes()).slice(-2);
	var s = ("0" + myDate.getSeconds()).slice(-2);
	var mi = ("00" + myDate.getMilliseconds()).slice(-3);

	return year + "-" + month + "-" + day + " " + h + ":" + m;
}