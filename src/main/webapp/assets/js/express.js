// 订单状态的格式化
function statusFormatter(value, row, index) {
    if(value === '订单异常')
        return '<span style="color:red">'+value+'</span>';
    else if (value === '订单完成')
        return '<span style="color:olivedrab">'+value+'</span>';
    else if (value === '派送中')
        return '<span style="color:#42afff">'+value+'</span>';
    else if (value === '等待派送')
        return '<span style="color:grey">'+value+'</span>';
}

// myExpress.html操作栏的格式化
function myExpressActionFormatter(value, row, index) {
    var id = "'" + value + "'";
    //只有状态为派送中（2），才能确认
    var result;
    if(row.status === 2) {
        result = '<button class="btn btn-xs btn-info" onclick="myExpressShowExpress(' + id + ')" title="查看"><span class="glyphicon glyphicon-pencil"></span></button>\n' +
            '         <button class="btn btn-xs btn-primary" onclick="confirmExpress(' + id + ')" title="确认"><span class="glyphicon glyphicon-ok"></span></button>';
    } else if (row.status === 3 || row.status === 1) {
        result = '<button class="btn btn-xs btn-info" onclick="myExpressShowExpress(' + id + ')" title="查看"><span class="glyphicon glyphicon-pencil"></span></button>\n' +
            '         <button class="btn btn-xs btn-danger" onclick="deleteExpress(' + id + ')" title="删除"><span class="glyphicon glyphicon-remove"></span></button>';
    } else if (row.status === 4) {
        result = '<button class="btn btn-xs btn-info" onclick="myExpressShowExpress(' + id + ')" title="查看"><span class="glyphicon glyphicon-pencil"></span></button>\n' +
            '         <button class="btn btn-xs btn-primary" onclick="confirmExpress(' + id + ')" title="确认"><span class="glyphicon glyphicon-ok"></span></button>';
    }
    return result;
}

// home.html操作栏的格式化
function homeActionFormatter(value, row, index) {
    var id = "'" + value + "'";
    var result = '<button class="btn btn-xs btn-info" onclick="showExpress('+id+')" title="查看"><span class="glyphicon glyphicon-search"></span></button>\n' +
        '         <button class="btn btn-xs btn-primary" onclick="acceptExpress('+id+')" title="接单"><span class="glyphicon glyphicon-ok"></span></button>';
    return result;
}

// express.html操作栏的格式化
function expressActionFormatter(value, row, index) {
    var id = "'" + value + "'";
    //只有状态为派送中（2），才能确认
    var result;
    if(row.status === 2) {
        result = '<button class="btn btn-xs btn-info" onclick="showExpress(' + id + ')" title="查看"><span class="glyphicon glyphicon-search"></span></button>\n' +
            '         <button class="btn btn-xs btn-primary" onclick="confirmExpress(' + id + ')" title="确认"><span class="glyphicon glyphicon-ok"></span></button>';
    } else {
        result = '<button class="btn btn-xs btn-info" onclick="showExpress(' + id + ')" title="查看"><span class="glyphicon glyphicon-search"></span></button>';
    }
    return result;
}

// home.html接受订单
function acceptExpress(id) {
    layer.confirm("即将接单：" + id + "，确认执行？", {
        btn: ['确定','取消']
    }, function(){
        sendArray("post", "/express/staff/express", {"ids": id}, false, function (res) {
            // 当操作成功时，刷新表格
            if(res.status) {
                layer.msg("操作成功",{icon:1});
                flushTable();
            } else {
                layer.msg("操作失败",{icon:2});
            }
        }, function () {
            layer.msg("未知错误",{icon:2});
        });
    }, function(){
    });
}

// 查看订单
function showExpress(id) {
    sendJson("get", "/express/staff/express/" + id, {}, false, function (res) {
        if (res.status) {
            // 初始化模态框信息
            var data = res.data;
            $("#inputInfoId").text(data.id);
            $("#inputInfoName").text(data.name);
            $("#inputInfoMessage").text(data.message);
            $("#inputInfoAddress").text(data.address);
            $("#inputInfoRemark").text(data.remark);
            $("#inputInfoPayment").text(data.paymentType);
            $("#inputInfoStatus").html(data.statusName);
            $("#inputInfoStaff").text(data.staffName);
            $("#inputInfoStaffTel").text(data.staffTel);
            $("#inputInfoStaffRemark").text(data.staffRemark);
            $("#inputInfoDate").text(data.createDate);
            $("#infoModel").modal("show");
        } else {
            $.message({message: '获取订单详情失败', type: 'error'});
        }
    }, function () {
        layer.msg("未知错误",{icon:2});
    });
}

// myexpress.html查看订单
function myExpressShowExpress(id) {
    sendJson("get", "/express/staff/express/" + id, {}, false, function (res) {
        if (res.status) {
            // 初始化模态框信息
            var data = res.data;
            $("#inputInfoId").text(data.id);
            $("#inputInfoName").attr("value", data.name);
            $("#inputInfoMessage").attr("value", data.message);
            $("#inputInfoAddress").attr("value", data.address);
            $("#inputInfoRemark").attr("value", data.remark);
            $("#inputInfoPayment").text(data.paymentType);
            $("#inputInfoPaymentOnline").text(data.onlinePayment);
            $("#inputInfoPaymentOffline").text(data.offlinePayment);
            $("#inputInfoStatus").html(data.statusName);
            $("#inputInfoStaff").text(data.staffName);
            $("#inputInfoStaffTel").text(data.staffTel);
            $("#inputInfoStaffRemark").text(data.staffRemark);
            $("#inputInfoDate").text(data.createDate);

            $("#infoModel").modal("show");
            $("#editExpressBtn").click(function () {
                $("#infoModel").modal("hide");
                var id = $("#inputInfoId").text();
                var name = $("#inputInfoName").val();
                var message = $("#inputInfoMessage").val();
                var address = $("#inputInfoAddress").val();
                var remark = $("#inputInfoRemark").val();
                sendJson("post", "/express/staff/express/editExpress",
                    {"id":id, "name":name, "message":message, "address":address, "remark":remark}, true, function (res) {
                        if (res.status) {
                            layer.msg("修改成功！", {icon:1})
                            flushTable();
                        } else {
                            layer.msg(res.info,{icon:2});
                        }
                    }, function () {
                        layer.msg("操作失败！",{icon:2});
                    });
            });

        } else {
            layer.msg("获取订单详情失败",{icon:2});
        }
    }, function () {
        layer.msg("操作失败",{icon:2});
    });
}

// 确认订单
function confirmExpress(id) {
    var msg = "即将确认订单：" + id + "，确认执行？";
    if(confirm(msg)) {
        sendJson("get", "/express/staff/express/" + id, {}, false, function (res) {
            if (res.status) {
                // 初始化模态框信息
                var data = res.data;
                $("#confirmId").val(data.id);
                $("#confirmName").text(data.name);
                $("#confirmPaymentType").text(data.paymentType);
                $("#confirmPaymentOnline").text(data.onlinePayment);
                $("#confirmPaymentOffline").val(data.offlinePayment);
                $("#confirmModel").modal("show");
            } else {
                layer.msg("获取订单详情失败",{icon:0});
            }
        }, function () {
            layer.msg("未知错误",{icon:2});
        });
        $("#confirmBtn").click(function () {
            var offlinePayment = $("#confirmPaymentOffline").val();
            if(offlinePayment === "") {
                layer.msg("线下支付金额不能为空",{icon:0});
                return false;
            }
            $("#confirmModel").modal("hide");
            sendArray("post", "/express/staff/express/confirm", $("#confirmForm").serialize() , false, function (res) {
                // 当操作成功时，刷新表格
                if(res.status) {
                    flushTable();
                }
            }, function () {
                layer.msg("未知错误",{icon:2});
            });
        });
    }
}

// 删除订单
function deleteExpress(id) {
    layer.confirm("即将删除订单：" + id + "，确认执行？", {
        btn: ['确定','取消']
    }, function(){
        sendJson("post", "/express/staff/express/deleteExpress", {"id": id}, false, function (res) {
            // 当操作成功时，刷新表格
            if (res.status) {
                layer.msg('删除成功！', {icon: 1});
                flushTable();
            } else {
                layer.msg(res.info, {icon: 2});
            }
        }, function () {
            layer.msg("操作失败！",{icon:2});
        });
    }, function(){
    });
}

