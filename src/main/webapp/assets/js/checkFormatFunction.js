// 邮箱格式验证
function checkEmailFormat(id) {
    var email = $("#" + id).val();
    var emailRegex = /^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
    if(!emailRegex.test(email)) {
        layer.msg("邮箱格式不正确！", {icon:0});
        return false;
    }
}

// 手机号格式验证
function checkPhoneFormat(id) {
    var tel = $("#" + id).val();
    var phoneReg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if(!phoneReg.test(tel)) {
        layer.msg("手机号格式不正确！", {icon:0});
        return false;
    }
}

// 用户名验证
function checkUsername(id) {
    var username = $("#" + id).val();
    if (username.length > 10 | username.length < 2) {
        layer.msg("用户名必须在2-10个字符以内且不可为空！", {icon:0});
        return false;
    }
}

// 地址验证
function checkAddress(id) {
    var address = $("#" + id).val();
    if (address.length>200 | address.length <=0) {
        layer.msg("地址必须在200个字符以内且不可为空！", {icon:0});
        return false;
    }
}