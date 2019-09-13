//js模块化
//seckill.detail.init(param)
var seckill = {
    //封装秒杀相关ajax的url
    url: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (commodityId) {
            return '/seckill/' + commodityId + '/exposer';
        },
        execution: function (commodityId, md5) {
            return '/seckill/' + commodityId + '/' + md5 + '/execute';
        },
        status: function (commodityId) {
            return '/seckill/' + commodityId + '/seckillStatus';
        }
    },
    validatePhone: function (phone) {
        //phone是否为空
        //为空就是undefine
        //inNaN是非数字就为true
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }

    },
    detail: {
        //详情页初始化
        init: function (params) {
            //用户手机验证、计时交互
            var killPhone = $.cookie('killPhone');
            //验证手机号
            if (!seckill.validatePhone(killPhone)) {
                //没有登录，立即绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true, //显示弹出层
                    backdrop: 'static', //禁止位置关闭弹出
                    keyboard: false //禁止通过键盘关闭弹出层
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    //填写手机号之后再次判断
                    if (seckill.validatePhone(inputPhone)) {
                        //验证手机号通过
                        //电话写入cookie
                        $.cookie("killPhone", inputPhone, {expires: 7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }
            //已经登录、计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var commodityId = params['commodityId'];
            $.get(seckill.url.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //计时交互
                    seckill.countDown(commodityId, nowTime, startTime, endTime);
                } else {
                    console.log('result: ' + result);
                }
            });

        }
    },
    handleSeckill: function (commodityId, node) {
        console.log('result: ' + node);
        //var node = $('#seckill-Box')
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.url.exposer(commodityId), {}, function (result) {
            //在回调函数中、执行交互流程
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //开启秒杀、获取秒杀地址
                    var md5 = exposer['md5'];
                    //TODO
                    console.log(md5)
                    var killUrl = seckill.url.execution(commodityId, md5);
                    var seckillStatus = seckill.url.status(commodityId);
                    var userPhone = $.cookie('killPhone')
                    var message = commodityId + userPhone;
                    //绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        //执行秒杀请求
                        //1、禁用按钮
                        $(this).addClass('disabled');
                        //2、发送秒杀请求
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                console.log(killResult)
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                        //3、查询秒杀结果
                        var ws = new WebSocket("ws://localhost:8092/ws");
                        // 客户端向服务端发送消息
                        ws.onopen = function () {
                            console.log('已连接服务器');
                            ws.send(message);
                        }
                        //服务端向客户端发送消息
                        ws.onmessage = function (e) {
                            var seckillStatus = e.data;
                            console.log("服务端向客户端发送消息:" + seckillStatus);
                            var obj = JSON.parse(seckillStatus);
                            console.log(obj.state)
                            console.log(obj.stateInfo)

                            if (obj.state == 1) {
                                alert("恭喜您、购买成功")
                            }
                            if (obj.state == 0) {
                                alert("不对起、该秒杀活动已结束")
                            }
                            if (obj.state == -1) {
                                alert("对不起、不能重复购买")
                            }
                            if (obj.state == -2) {
                                alert("系统异常、请稍后重试")
                            }
                        }
                    });
                    node.show();
                } else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    commodityId.countDown(commodityId, now, start, end);
                }
            } else {

            }
        })
    },
    countDown: function (commodityId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        //时间判断
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html('秒杀结束');
        } else if (nowTime < startTime) {
            //秒杀未开启、开始计时
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                //格式化日期
                var format = event.strftime('秒杀计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                //时间完成后回调时间
            }).on('finish.countdown', function () {
                //获取秒杀地址、控制现实逻辑、执行秒杀
                seckill.handleSeckill(commodityId, seckillBox);
            });
        } else {
            //秒杀开始
            seckill.handleSeckill(commodityId, seckillBox);
        }
    }
}