$(function () {
    checkLoginStatus();
});

/**
 * 检查登陆状态，否则更换导航栏
 */
function checkLoginStatus() {
    $.ajax({
        url: "/seckill/user/check",
        method: "POST",
        success: function (res) {
            if (res.data == null) {
                $('#nav-right').append('<li>\n' +
                    '                    <a href="#" id="login">登陆</a>\n' +
                    '                </li>\n' +
                    '                <li>\n' +
                    '                    <a href="#" id="reg">注册</a>\n' +
                    '                </li>');
            } else {
                $('#nav-right').append('<li class="dropdown">\n' +
                    '                     <a href="#" id="userName" class="dropdown-toggle" data-toggle="dropdown">你好，' + res.data.userName + '<strong class="caret"></strong></a>\n' +
                    '                     <ul class="dropdown-menu" style="background-color: #000;text-align: center;">\n' +
                    '                       <li>\n' +
                    '                          <a href="#">我的资料</a>\n' +
                    '                       </li>\n' +
                    '                       <li>\n' +
                    '                          <a href="orderlist.html" target="_blank">我的订单</a>\n' +
                    '                       </li>\n' +
                    '                       <li>\n' +
                    '                          <a href="#" id="logout">注销登陆</a>\n' +
                    '                       </li>\n' +
                    '                       <li>\n' +
                    '                          <a href="#" id="userId" style="display:none;">' + res.data.id + '</a>\n' +
                    '                       </li>\n' +
                    '                     </ul>\n' +
                    '                 </li>');
            }
        }
    })
}


/**
 * 登陆弹窗和登陆按钮的点击事件
 */
$(document).on("click", '#login', function () {
    clearForm("#loginModal form");
    $("#loginModal").modal({
        backdrop: "static"
    });

});
$("#user_login_btn").click(function () {
    $.ajax({
            url: "/seckill/user/login",
            type: "post",
            data: $("#loginModal form").serialize(),
            success: function (result) {
                alert(result.msg);
                $("#loginModal").modal('hide');
                window.location.reload();
            }
        }
    )
});

/**
 * 注销的点击事件
 */
$(document).on("click", '#logout', function () {
    $.ajax({
        url: "/seckill/user/logout",
        method: "get",
        success: function (res) {
            alert(res.msg);
            window.location.reload();
        }
    })
});

/**
 * 注册弹窗和注册按钮的点击事件
 */
//注册的点击事件
$(document).on("click", '#reg', function () {
    //1.清除上一次的查询结果
    clearForm("#regModal form");
    $('#captcha_img_holder').html('<img id="captcha_img" src="/seckill/user/kaptcha" onclick="changeVerifyCode(this)"/>');
    //3.弹出模态框
    $("#regModal").modal({
        backdrop: "static"
    });
});
$(document).on("click", '#captcha_img', function () {

});

//清空表单
function clearForm(ele) {
    $(ele)[0].reset();
    //还原输入框样式和清除文字提示
    $(ele).find("*").removeClass("has-error has-success");
}

//确认注册点击事件
$("#user_reg_btn").click(function () {
    //正则表达式的验证
    if (!isAccountReg || !isPwdReg) {
        alert("请更换正确的用户名或密码注册");
        return false;
    }
    $.ajax({
        url: "/seckill/user/register",
        type: "post",
        data: $("#regModal form").serialize(),
        success: function (res) {
            alert(res.msg);
            $("#regModal").modal('hide');
        }
    })
});

//正则表达式检验的结果
var isAccountReg = false;
var isPwdReg = false;
var isVal = false;

//账号的正则检验
$("#reg_account_input").change(function () {
    var account = $("#reg_account_input").val();
    var RegAccount = /^[a-zA-Z0-9_-]{6,16}$/;
    if (!RegAccount.test(account)) {
        show_validate_msg("#reg_account_input", "error", "用户名为6-16位英文和数字组合");
    }
    else {
        $.ajax({
            url: "/seckill/user/checkAccount",
            data: "account=" + account,
            type: "post",
            success: function (result) {
                if (result.code == 110) {
                    isAccountReg = true;
                    show_validate_msg("#reg_account_input", "success", "");
                } else {
                    show_validate_msg("#reg_account_input", "error", "该账号已被注册");
                }
            }
        })
    }
});

//密码的正则检验
$("#reg_password_input").change(function () {
    var pwd = $("#reg_password_input").val();
    var regPwd = /^[a-zA-Z0-9_-]{6,16}$/;
    if (!regPwd.test(pwd)) {
        show_validate_msg("#reg_password_input", "error", "密码为6-16位英文和数字组合");
    }
    else {
        show_validate_msg("#reg_password_input", "success", "");
        isPwdReg = true;
    }
});

function show_validate_msg(ele, status, msg) {
    //消除当前元素的样式
    $(ele).parent().removeClass("has-success has-error");
    $(ele).next("span").text("");
    if ("success" == status) {
        // $(ele).parent().addClass("has-success");
        // $(ele).next("span").text(msg);
    } else if ("error" == status) {
        $(ele).parent().addClass("has-error");
        $(ele).next("span").text(msg);
    }
}

$("#user_reg_close").click(function () {
    isNameReg = false;
    isPwdReg = false;
    isVal = false;
});

//验证码检验
$("#reg_val_input").change(function () {
    var val = $("#reg_val_input").val();
    $.ajax({
        url: "/seckill/user/checkVal",
        data: "val=" + val,
        type: "post",
        success: function (result) {
            if (result) {
                isVal = true;
                show_validate_msg("#reg_val_input", "success", "");
            } else {
                isVal = false;
                show_validate_msg("#reg_val_input", "error", "验证码错误，请注意区分大小写");
            }
        }
    })
});

//更新验证码图片
function changeVerifyCode(ele) {
    ele.src = "/seckill/user/kaptcha";
}


