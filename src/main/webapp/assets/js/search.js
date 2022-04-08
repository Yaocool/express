function search() {
    var content = $("#inputSearch").val();
    if(content === "") {
        layer.msg("查询内容不能为空",{icon:0});
        return false;
    }
    sendJson("post", "/express/search", {"id":content}, false, function (res) {
        if (!res.status) {
            layer.msg(res.info,{icon:2});
        } else {
            // 0:订单信息;1:反馈信息
            var type = res.info;
            if(type === "0") {
                express = res.data;
                $("#expressName").text(express.name);
                $("#expressTel").text(express.tel);
                $("#expressAddress").text(express.address);
                $("#expressMessage").text(express.message);
                $("#expressRemark").text(express.remark);
                $("#expressPaymentType").text(express.paymentType);
                $("#expressPaymentStatus").text(express.paymentStatus);
                $("#expressOnline").text(express.onlinePayment);
                $("#expressOffline").text(express.offlinePayment);

                $("#expressStatus").text(express.statusName);
                $("#expressStaff").text(express.staffName);
                $("#expressStaffTel").text(express.staffTel);
                $("#expressStaffRemark").text(express.staffRemark);
            } else if(type === "1") {
                feedback = res.data;
                $("#feedbackName").text(feedback.name);
                $("#feedbackTel").text(feedback.tel);
                $("#feedbackType").text(feedback.typeName);
                $("#feedbackMessage").text(feedback.message);
                $("#feedbackCreate").text(feedback.createDate);

                $("#feedbackStatus").text(feedback.statusName);
                $("#feedbackStaff").text(feedback.staffName);
                $("#feedbackResult").text(feedback.result);
                $("#feedbackUpdate").text(feedback.updateDate);
            }
            parserPanel(type);
        }
    }, function () {
        layer.msg("未知错误",{icon:2});
    });
}

function parserPanel(type) {
    // 0:订单信息;1:反馈信息
    if(type === "0") {
        // 打开expressPanel,关闭feedbackPanel
        if($("#expressPanel").hasClass("hidden")) {
            $("#expressPanel").removeClass("hidden");
        }
        if(!$("#feedbackPanel").hasClass("hidden")) {
            $("#feedbackPanel").addClass("hidden");
        }
    } else if(type === "1") {
        // 打开feedbackPanel,关闭expressPanel
        if($("#feedbackPanel").hasClass("hidden")) {
            $("#feedbackPanel").removeClass("hidden");
        }
        if(!$("#expressPanel").hasClass("hidden")) {
            $("#expressPanel").addClass("hidden");
        }
    }
}