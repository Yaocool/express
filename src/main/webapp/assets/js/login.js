function refresh(obj) {
    obj.src = "getVerifyCode?" + Math.random();
}

function mouseover(obj) {
    obj.style.cursor = "pointer";
}

function ischinese(s){
    var ret=true;
    for(var i=0;i<s.length;i++)
        ret=ret && (s.charCodeAt(i)>=10000);
    return ret;
}

// 登录
function submitLogin() {
    var email = $("#email").val();
    var password = $("#password ").val();
    var emailReg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/;
    if(email === "" || password === "") {
        layer.msg("邮箱或密码不能为空",{icon:0});
        return false;
    } else if (!emailReg.test(email)) {
        layer.msg("邮箱格式不正确",{icon:0});
        return false;
    }

    sendJson("post", "/express/checkVerifyCode", {"data": $("#inputVerify").val()}, false, function (res) {
        console.log()
        if (!res.status) {
            layer.msg(res.info,{icon:2});
        } else {
            sendJson("post", "/express/login", {"email":email,"password":password}, false, function (res) {
                if (!res.status) {
                    layer.msg(res.info,{icon:2});
                } else {
                    window.location.href=res.data;
                }
            }, function () {
                layer.msg("未知错误",{icon:2});
            });
        }
    }, function () {
        layer.msg("未知错误",{icon:2});
    });
}

// 注册
function submitRegister() {
    var username = $("#username").val();
    var password = $("#password").val();
    var email = $("#email").val();
    var emailReg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/;
    var telReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
    var tel = $("#tel").val();
    if(username === "" || password === "" || email === "" || tel === "") {
        layer.msg("用户名、密码、邮箱、手机号均不能为空",{icon:0});
        return false;
    } else if (!emailReg.test(email)) {
        layer.msg("邮箱格式不正确",{icon:0});
        return false;
    } else if (!telReg.test(tel)) {
        layer.msg("手机号格式不正确",{icon:0});
        return false;
    } else if (username.length>10 || username.length<2) {
        layer.msg("用户名需在2-10个字符之间",{icon:0});
        return false;
    } else if (password.length>16 || username.length<6) {
        layer.msg("密码需在6-16个字符之间且不能为汉字",{icon:0});
        return false;
    }
    sendJson("post", "/express/checkVerifyCode", {"data": $("#inputVerify").val()}, false, function (res) {
        if (!res.status) {
            layer.msg(res.info,{icon:2});
        } else {
            sendJson("post", "/express/register", {"username":username,"password":password,"email":email,"tel":tel}, false, function (res) {
                if (!res.status) {
                    layer.msg(res.info,{icon:2});
                } else {
                    window.location.href=res.data;
                }
            }, function () {
                layer.msg("未知错误",{icon:2});
            });
        }
    }, function () {
        layer.msg("验证码错误",{icon:2});
    });
}

