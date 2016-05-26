var url=window.location.search.substring(1).split('=');
id=url[1];

$(function() {
    $("#modifyBtn").click(function() {
    	var role = $("#roleselect option:selected").text();
        var permissions = "";
        $('input[name="permissions"]:checked').each(function() {
        	permissions += $(this).val();
        });
    	userModify(id, role, permissions);
    	//vote();
	});
    
	userDetail(id, function(data){
		if(null == data){
			return;
		}
		$("#name").attr("value", data.name);
		$("#roleselect").find("option[value='"+data.roles+"']").attr("selected", true);
		$("#userHeader").attr("src", "/user/viewPhoto?id=" + data.id);
		$("#upphoto").attr("action", "/user/uploadPhoto?id=" + data.id + "&name=" + data.name + "&v=/view/user/detail.html?id=" + data.id);
	});
});

function userDetail(userid, callback){
    $.ajax({
        url : "/user/detail",
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

function userModify(userid, userRole, permissions){
    $.ajax({
        url : "/user/modify",
        type : 'POST',
        contentType : "application/x-www-form-urlencoded; charset=utf-8",
        dataType : "jsonp",
        async : false,
        data : {
        	id: userid,
        	role : encodeURIComponent(userRole),
        	pm:encodeURIComponent(permissions)
        },
        cache : false,
        success : function(rs) {
        	if(rs.code == 200){
        		alert("user modify success");
        		location.reload();	
        	}
        },
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
		}
    });
}

//图片上传和预览
function preview(file){
	var prevDiv = document.getElementById('userHeader');
	if (file.files && file.files[0]){
		var reader = new FileReader();
		reader.onload = function(evt){
			prevDiv.setAttribute("src", evt.target.result); 
		}
		reader.readAsDataURL(file.files[0]);
	}else{
		prevDiv.innerHTML = '<div class="img"'
		+ 'style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
	}
}