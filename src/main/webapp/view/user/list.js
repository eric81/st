var url = window.location.search.substring(1).split('=');
var page = url[1];

$(function() {
	if(page == null){
		page = 1;
	}
	if(page < 1){
		location.href = "list.html"
	}
	
	userlist(page, 10);
	showPager(page);
});

function userlist(page, pageSize){
    $.ajax({
        url : "/user/list",
        type : 'POST',
        contentType : "application/x-www-form-urlencoded; charset=utf-8",
        dataType : "jsonp",
        async : false,
        data : {
        	pg : page,
        	ps : pageSize
        },
        cache : false,
        success : function(data) {
            $(".table").html("");
            $(".table").append("<tr><td>用户</td><td>角色</td><td>权限</td><td>注册日期</td></tr>");
            $.each(data.entities, function(index, user) {
                $(".table").append("<tr><td><a href='detail.html?id=" + user.id + "'>" + user.name + "</a></td><td>" + user.roles + "</td><td>" + user.permissions + "</td><td>" + formatTime(user.registerDate) + "</td></tr>");
            }); 
        }
    });
}

function showPager(page){
	$(".pagination").html("");
	$(".pagination").append("<li><a href='list.html?pg=" + (page*1 - 1) + "' aria-label='Previous'> <spanaria-hidden='true'>&laquo;</span></a></li>");
	$(".pagination").append("<li class='active'><a href='list.html?pg=1'>1</a></li>");
	$(".pagination").append("<li><a href='list.html?pg=" + (page*1 + 1) + "' aria-label='Next'> <spanaria-hidden='true'>&raquo;</span></a></li>");
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