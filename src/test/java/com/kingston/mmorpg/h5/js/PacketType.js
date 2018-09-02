/**
 * 与服务端的通信协议绑定
 */

PacketType = {}

PacketType.ReqPlayerLogin = "102_1";

PacketType.ResPlayerLogin = "102_201";

var self = PacketType;

var msgHandler = {}

PacketType.bind = function(msgId, handler) {
	msgHandler[msgId] = handler
}

self.bind(self.ResPlayerLogin, function(resp) {
	alert("角色登录成功-->" + resp)
})

PacketType.handle = function(msgId, msg) {
	msgHandler[msgId](msg);
}
