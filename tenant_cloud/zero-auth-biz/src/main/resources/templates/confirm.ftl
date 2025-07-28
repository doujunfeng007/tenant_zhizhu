<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>掌上智珠统一认证系统</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/iofrm-style.css">
    <link rel="stylesheet" type="text/css" href="/css/iofrm-theme.css">
</head>
<body>
<div class="form-body">
    <div class="row">
        <div class="form-holder">
            <div class="form-content">
                <div class="form-items">
                    <div class="website-logo-inside">
                        <h1>掌上智珠统一认证系统</h1>
                    </div>
                    <p>应用 [${client.clientId!'未定义应用名称'}] 请求授权</p>
                    <p>授权后该应用将取得系统操作权限</p>
                    <div class="page-links">
                        <a>授权账号 [${principal.username!'未获取到账号信息'}]</a>
                    </div>
                    <form action="/oauth/authorize" method="post">
                        <input name='user_oauth_approval' value='true' type='hidden'/>
                        <div class="form-button">
                            <button id="submit" type="submit" class="ibtn">同意/授权</button>
                        </div>
                    </form>
                    <div class="other-links">
                        <span>Copyrights © 2022 <a href="https://minigod.com" target="_blank">掌上智珠</a> All Rights Reserved.</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/jquery.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script>
    $(window).on("load", function () {
        // 初始化页面增加租户id传递
        updateUrlParam("state", "${principal.tenantId}");
    });

    // 添加url中参数的值
    function updateUrlParam(name, val) {
        let thisURL = document.location.href;
        let value = getUrlParam(name);
        if (value != null) {
            return false;
        } else {
            thisURL = thisURL + "&" + name + "=" + val;
        }
        window.location.href = thisURL;
    }

    // 获取url中参数的值
    function getUrlParam(name) {
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        let r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
</script>
</body>
</html>
