// 更新价格
function updatePrice() {
    total = 0;
    $("input[name='price']").each(function(){
        var weight = $(this).val();
        if(weight !== "") {
            if(!isNaN(weight)) {
                weight = parseFloat(weight);
                var tmp = 0;
                // 如果小于500g，2元
                if(weight < 0.5)
                    tmp = 2;
                else if(weight < 1)
                    tmp = 3;
                else if(weight >= 1)
                    // 向上取整
                    tmp = 4 * Math.ceil(weight);
                total += tmp;
            } else {
                // 如果不是数字，将input值修改为空
                $(this).val("");
            }
        }
    });
    // 两位小数，四舍五入
    total = total.toFixed(2);
    $("span[lang='totalPrice']").each(function() {
        $(this).text(total);
    });
}

function addPackage() {
    var html = '<div class="form-group">\n' +
        '                            <label class="col-sm-3 control-label">预估重量（单位：KG）</label>\n' +
        '                            <div class="col-sm-9">\n' +
        '                                <input type="number" class="form-control" name="price" onchange="updatePrice()" onkeyup="updatePrice()">\n' +
        '                            </div>\n' +
        '                        </div>';
    $("#packageForm").append(html);
}

function deletePackage() {
    $("#packageForm").children().last().remove();
    updatePrice();
}

// 判断订单是否存在
function checkExpressValid() {
    if(express == null) {
        layer.msg("订单已失效，请刷新页面。如无法解决，请重新下单",{icon:2});
        return false;
    }
    return true;
}

function OfflinePay() {
    layer.confirm("您选择了线下支付方式，请准备好现金。是否确认下单？", {
        btn: ['确定','取消']
    }, function(){
        if(checkExpressValid()) {
            sendJson("post", "/express/payment/offline", {"money":total}, false, function (res) {
                if (!res.status) {
                    layer.msg(res.info,{icon:2});
                } else {
                    window.location.href = res.data;
                }
            }, function () {
                layer.msg("未知错误",{icon:2});
            });
        }
    }, function(){
    });
}

function alipay() {
    if(checkExpressValid()) {
        if(total === 0)
            total = 0.01;
        $("#alipayMoney").val(total);
        $("#alipayForm").submit();
    }
}