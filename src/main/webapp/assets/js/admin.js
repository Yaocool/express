// 注册管理js
// 展示图片
function imgFormatter(value, row, index) {
    var projectName = "/express";
    return "<img class='img-rounded' width='200px' height='150px' src='" + projectName + value + "'>";
}

// 用户状态的格式化
function statusFormatter(value, row, index) {
    if(value === '审核中')
        return '<span style="color:red">'+value+'</span>';
}

// 性别格式化
function sexFormatter(value, row, index) {
    if(value === "male")
        return "男";
    else if(value === "female")
        return "女";
}

// 操作格式化
function actionFormatter(value, row, index) {
    var id = "'" + value + "'";
    var result = '<button class="btn btn-xs btn-info" onclick="editStaff('+id+')" title="查看"><span class="glyphicon glyphicon-pencil"></span></button>';
    return result;
}

// 编辑用户信息
function editStaff(id) {
    sendJson("get", "/express/admin/staff/"+id, {}, false, function (res) {
        if(res.status) {
            var user = res.data;
            $("#inputUserId").val(user.id);
            $("#inputUserName").val(user.username);
            $("input:radio[name='sex'][value="+user.sex+"]").attr('checked','true');
            $("#inputUserTel").val(user.tel);
            $("#inputUserEmail").text(user.email);
            $("#inputUserAddress").val(user.address);
            $("#inputUserBirthday").val(user.birthday);
            $("#userModel").modal("show");
                $().ready(function() {
                    $.validator.addMethod("isPhone", function(value, element) {
                        var length = value.length;
                        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                        return this.optional(element) || (length == 11 && mobile.test(value));
                    }, "请填写正确的手机号码");
                    $("#userInfoForm").validate({
                        rules: {
                            username: {
                                required: true,
                                rangelength: [2, 10],
                            },
                            tel: {
                                required: true,
                                isPhone: true,
                            },
                            address: {
                                required: true,
                                rangelength: [2, 200],
                            },
                        },
                        messages: {
                            username: {
                                required: "用户名不能为空！",
                                rangelength: "用户名必须在2-10字符之间!",
                            },
                            tel: {
                                required: "电话号码不能为空",
                                isPhone: "请填写正确的手机号码",
                            },
                            address: {
                                required: "地址不能为空！",
                                rangelength: "地址必须在2-200字符之间！",
                            },
                        },
                        submitHandler: function () {
                            console.log("提交");
                            $("#userModel").modal("hide");
                            sendJson("post", "/express/admin/staff", $("#userInfoForm").serialize(), true, function (res) {
                                if (res.status) {
                                    flushTable();
                                } else {
                                    layer.msg(res.info, {icon: 2});
                                }
                            }, function () {
                                layer.msg("未知错误", {icon: 2});
                            });
                    }
                });
            });
        }
    }, function () {
        layer.msg("未知错误",{icon:2});
    });
}

// 用户状态格式化
function userStatusFormatter(value, row, index) {
    if(value === '冻结')
        return '<span style="color:red">'+value+'</span>';
    else if (value === '离职')
        return '<span style="color:#f0ad4e">'+value+'</span>';
    else if (value === '在职')
        return '<span style="color:#5bc0de">'+value+'</span>';
}



