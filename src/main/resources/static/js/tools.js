function getURLParam(key) {
  var params = location.search;
  if (params.indexOf("?") >= 0) {
    params = params.substring(1);
    var paramArr = params.split("&");
    for (var i = 0; i < paramArr.length; i++) {
      var namevalues = paramArr[i].split("=");
      if (namevalues[0] == key) {
        return namevalues[1];
      }
    }
  } else {
    return "";
  }
}

function onExit() {
  if (confirm("确认退出?")) {
    // alert("退出成功")
    jQuery.ajax({
      url: "/user/logout",
      type: "POST",
      data: {}, //退出的时候不需要参数
      success: function (result) {
        alert(JSON.stringify(result));
        location.href = "/login.html";
      },
      error: function (error) {
        if (error != null && error.status == 401) {
          alert("用户未登录, 即将跳蛛请安登录页面");
          location.href = "/login.html";
        }
      },
    });
  }
}

function myInfo() {
  console.log("获取信息");
  jQuery.ajax({
    url: "/user/myinfo",
    type: "POST",
    data: {},
    success: function (result) {
      if (result.code == 200 && result.data != null) {
        console.log(result);
        jQuery("#photo").prop("src", result.data.photo);
      }
    },
  });
}
function onExit() {
  if (confirm("确认退出?")) {
    // alert("退出成功")
    jQuery.ajax({
      url: "/user/logout",
      type: "POST",
      data: {}, //退出的时候不需要参数
      success: function (result) {
        alert(JSON.stringify(result));
        location.href = "/login.html";
      },
      error: function (error) {
        if (error != null && error.status == 401) {
          alert("用户未登录, 即将跳蛛请安登录页面");
          location.href = "/login.html";
        }
      },
    });
  }
}
