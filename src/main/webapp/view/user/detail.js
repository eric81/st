var url=window.location.search.substring(1).split('=');
id=url[1];

$(function() {
    $("#modifyBtn").click(function() {
    	var role = $("#roleselect option:selected").text();
    	userModify(id, role);
	});
    
	userDetail(id, function(data){
		if(null == data){
			return;
		}
		$("#name").attr("value", data.name);
		$("#roleselect").find("option[value='"+data.roles+"']").attr("selected", true);
	});
});

function userDetail(userid, callback){
    $.ajax({
        url : "http://localhost/st/user/detail",
        type : 'POST',
        contentType : "application/x-www-form-urlencoded; charset=utf-8",
        dataType : "jsonp",
        async : false,
        data : {
        	id: userid
        },
        cache : false,
        success : function(data) {
        	callback(data);
        }
    });
}

function userModify(userid, userRole){
    $.ajax({
        url : "http://localhost/st/user/modify",
        type : 'POST',
        contentType : "application/x-www-form-urlencoded; charset=utf-8",
        dataType : "jsonp",
        async : false,
        data : {
        	id: userid,
        	role : encodeURIComponent(userRole)
        },
        cache : false,
        success : function(rs) {
        	location.reload();
        },
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
		}
    });
}