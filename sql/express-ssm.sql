/*
Navicat MySQL Data Transfer

Source Server         : AliMysql
Source Server Version : 50642
Source Host           : 123.56.41.211:3306
Source Database       : express-ssm

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2020-05-27 17:48:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for express
-- ----------------------------
DROP TABLE IF EXISTS `express`;
CREATE TABLE `express` (
  `id` varchar(255) NOT NULL COMMENT '订单号',
  `name` varchar(16) NOT NULL COMMENT '姓名',
  `tel` varchar(24) NOT NULL COMMENT '手机号码',
  `message` varchar(255) NOT NULL COMMENT '快递短信',
  `address` varchar(255) NOT NULL COMMENT '配送地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '订单备注',
  `status` int(11) NOT NULL COMMENT '订单状态',
  `staff` varchar(255) DEFAULT NULL COMMENT '配送人员',
  `staff_remark` varchar(255) DEFAULT NULL COMMENT '配送人员备注',
  `has_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（默认false）',
  `create_date` datetime NOT NULL COMMENT '下单时间',
  `update_date` datetime DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_status` (`status`) USING BTREE,
  KEY `fk_staff` (`staff`) USING BTREE,
  CONSTRAINT `fk_staff` FOREIGN KEY (`staff`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';

-- ----------------------------
-- Records of express
-- ----------------------------
INSERT INTO `express` VALUES ('158988752190035', '张三', '15212121212', '【菜鸟驿站】您的韵达包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭3-3-6254及时取，询18716030589', '食堂1楼', '', '1', null, null, '1', '2020-05-19 19:25:22', '2020-05-25 22:40:40', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988760732666', '张某', '15211114444', '【菜鸟驿站】您的韵达包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭3-3-6282及时取，询18716030589', '北四楼下', '', '1', null, null, '1', '2020-05-19 19:26:47', '2020-05-25 22:17:14', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988767556335', '李四', '15233332222', '【菜鸟驿站】您的百世快递包裹到科师一食堂南侧后勤楼下菜鸟驿站，请20:30前凭1-7-7030及时取，询13292327800', '南1', '', '1', null, null, '1', '2020-05-19 19:27:56', '2020-05-25 22:40:21', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988771384869', '李某', '15211112222', '【菜鸟驿站】您的EMS包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭4-4-4081及时取，询18716030589', '北四', '', '1', null, null, '1', '2020-05-19 19:28:34', '2020-05-25 22:17:19', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988776247382', '王五', '15244447777', '【菜鸟驿站】您的中通包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭1-1-7061及时取，询18716030589', '北四', '', '1', null, null, '1', '2020-05-19 19:29:22', '2020-05-25 22:17:48', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988781940704', '王某', '17455554444', '【京东快递】您好，您有京东快递已到职院居4栋，也就是（女新宿舍楼）东边。麻烦自取一下。以学校放假，5点关门04月05日', '北一', '', '1', null, null, '1', '2020-05-19 19:30:19', '2020-05-25 22:17:35', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988789189279', '许一', '15811112222', '【快递超市】您的圆通快递快件已到第一餐厅对面燕大家属楼，寄快递全国9元量大优惠，请凭提货码E379来取，有问题电话15233050806', '北二', '', '1', null, null, '1', '2020-05-19 19:31:32', '2020-05-25 22:16:56', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988794184091', '许某', '15811113333', '【菜鸟驿站】您的韵达包裹到科师一食堂商业街菜鸟驿站，请20:30前凭2-3-2001及时取，询13292327800', '南六', '', '1', null, null, '1', '2020-05-19 19:32:22', '2020-05-25 22:41:23', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988800726668', '徐毅', '15844447777', '【快递超市】您的圆通快递快件已到第一餐厅对面燕大家属楼二栋一号楼，拒收件当天拒收，请凭提货码A85来取，有问题电话15233050806', '南八', '送到楼下谢谢', '1', null, null, '1', '2020-05-19 19:33:27', '2020-05-25 22:16:46', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988806070755', '徐某', '15811112222', '【菜鸟驿站】您的中通包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭1-1-6250及时取，询18716030589', '南三', '放到宿管阿姨那谢谢', '1', null, null, '1', '2020-05-19 19:34:21', '2020-05-25 22:40:31', '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `express` VALUES ('158988824439777', '李同学', '13811114444', '【菜鸟驿站】您的中通包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭1-1-6157及时取，询18716030589', '南五', '', '1', null, null, '0', '2020-05-19 19:37:24', null, 'b023ff013e9e475aaf92fa355813ebfb');
INSERT INTO `express` VALUES ('158988828587164', '张思', '18344445555', '【快递超市】您的申通快递快件已到第一餐厅对面燕大家属楼二栋一号楼，拒收件当天拒收，请凭提货码T03来取，有问题电话15233050806', '南三', '', '3', null, null, '0', '2020-05-19 19:38:06', '2020-05-25 22:41:14', 'b023ff013e9e475aaf92fa355813ebfb');
INSERT INTO `express` VALUES ('158988832734867', '李武武', '18366665555', '【菜鸟驿站】您的中通包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭1-1-1529及时取，询18716030589', '北二', '', '2', null, null, '0', '2020-05-19 19:38:47', '2020-05-19 20:21:09', 'b023ff013e9e475aaf92fa355813ebfb');
INSERT INTO `express` VALUES ('158988837834454', '王五五', '15811112222', '【菜鸟驿站】您的韵达包裹到科师新宿舍旁3号家属楼菜鸟驿站，请18:00前凭5-6-6029及时取，询13292327800', '北一', '', '1', null, null, '1', '2020-05-19 19:39:38', '2020-05-25 22:41:01', 'b023ff013e9e475aaf92fa355813ebfb');
INSERT INTO `express` VALUES ('158988844273317', '姚小龙', '15255556666', '【快递超市】您的圆通快递快件已到第一餐厅对面燕大家属楼二栋一号楼，拒收件当天拒收，请凭提货码A226来取，有问题电话15233050806', '北三', '放到宿管大爷那，谢谢！', '1', null, null, '1', '2020-05-19 19:40:43', '2020-05-19 20:31:50', 'b023ff013e9e475aaf92fa355813ebfb');
INSERT INTO `express` VALUES ('158988849516915', '童依依', '18377779999', '【快递超市】您的圆通快递快件已到第一餐厅对面燕大家属楼二栋一号楼，拒收件当天拒收，请凭提货码G206来取，有问题电话15233050806', '北五', '', '3', '28b9e8411cd542a4937a0745db1c1b82', null, '1', '2020-05-19 19:41:35', '2020-05-25 22:42:13', 'b023ff013e9e475aaf92fa355813ebfb');
INSERT INTO `express` VALUES ('158988853635865', '张柳璐', '15811112333', '【菜鸟驿站】您的百世快递包裹到科师新宿舍旁3号家属楼菜鸟驿站，请18:00前凭2-3-4004及时取，询13292327800', '北二', '', '1', null, null, '1', '2020-05-19 19:42:16', '2020-05-19 20:31:45', 'b023ff013e9e475aaf92fa355813ebfb');
INSERT INTO `express` VALUES ('158988858058354', '王五', '15322221111', '【菜鸟驿站】您的中通包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭1-1-6157及时取，询18716030589', '南七', '', '3', null, null, '1', '2020-05-19 19:43:01', '2020-05-25 22:41:57', 'b023ff013e9e475aaf92fa355813ebfb');
INSERT INTO `express` VALUES ('158988864254647', '高萨楠', '15896669999', '【菜鸟驿站】您的晟邦物流包裹到科技师范校医院东侧20米菜鸟驿站，请20:30前凭1-1-5189及时取，询18716030589', '南五', '', '4', null, '找不到相同收件人姓名', '0', '2020-05-19 19:44:03', '2020-05-19 20:26:19', 'b023ff013e9e475aaf92fa355813ebfb');

-- ----------------------------
-- Table structure for express_payment
-- ----------------------------
DROP TABLE IF EXISTS `express_payment`;
CREATE TABLE `express_payment` (
  `express_id` varchar(255) NOT NULL COMMENT '订单号',
  `status` int(11) DEFAULT NULL COMMENT '支付状态',
  `type` int(11) DEFAULT NULL COMMENT '支付方式',
  `online_payment` double DEFAULT NULL COMMENT '线上付款金额',
  `online_payment_num` varchar(255) DEFAULT NULL COMMENT '线上支付第三方的流水号',
  `online_seller` varchar(255) DEFAULT NULL COMMENT '线上收款方',
  `offline_payment` double DEFAULT NULL COMMENT '线下支付金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`express_id`) USING BTREE,
  KEY `fk_payment_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单支付表';

-- ----------------------------
-- Records of express_payment
-- ----------------------------
INSERT INTO `express_payment` VALUES ('158988715433332', null, '0', null, null, null, '0', null, '2020-05-19 19:19:15', null);
INSERT INTO `express_payment` VALUES ('158988752190035', null, '0', null, null, null, '0', null, '2020-05-19 19:25:22', null);
INSERT INTO `express_payment` VALUES ('158988760732666', null, '0', null, null, null, '0', null, '2020-05-19 19:26:48', null);
INSERT INTO `express_payment` VALUES ('158988767556335', null, '0', null, null, null, '0', null, '2020-05-19 19:27:56', null);
INSERT INTO `express_payment` VALUES ('158988771384869', null, '0', null, null, null, '0', null, '2020-05-19 19:28:34', null);
INSERT INTO `express_payment` VALUES ('158988776247382', null, '0', null, null, null, '0', null, '2020-05-19 19:29:23', null);
INSERT INTO `express_payment` VALUES ('158988781940704', null, '0', null, null, null, '0', null, '2020-05-19 19:30:19', null);
INSERT INTO `express_payment` VALUES ('158988789189279', null, '0', null, null, null, '0', null, '2020-05-19 19:31:32', null);
INSERT INTO `express_payment` VALUES ('158988794184091', null, '0', null, null, null, '0', null, '2020-05-19 19:32:22', null);
INSERT INTO `express_payment` VALUES ('158988800726668', null, '0', null, null, null, '0', null, '2020-05-19 19:33:27', null);
INSERT INTO `express_payment` VALUES ('158988806070755', null, '0', null, null, null, '0', null, '2020-05-19 19:34:21', null);
INSERT INTO `express_payment` VALUES ('158988824439777', null, '0', null, null, null, '0', null, '2020-05-19 19:37:24', null);
INSERT INTO `express_payment` VALUES ('158988828587164', null, '0', null, null, null, '0', null, '2020-05-19 19:38:06', '2020-05-25 22:41:14');
INSERT INTO `express_payment` VALUES ('158988832734867', null, '0', null, null, null, '0', null, '2020-05-19 19:38:47', null);
INSERT INTO `express_payment` VALUES ('158988837834454', null, '0', null, null, null, '0', null, '2020-05-19 19:39:38', null);
INSERT INTO `express_payment` VALUES ('158988844273317', null, '0', null, null, null, '0', null, '2020-05-19 19:40:43', null);
INSERT INTO `express_payment` VALUES ('158988849516915', null, '0', null, null, null, '0', null, '2020-05-19 19:41:35', '2020-05-25 22:41:51');
INSERT INTO `express_payment` VALUES ('158988853635865', null, '0', null, null, null, '0', null, '2020-05-19 19:42:16', null);
INSERT INTO `express_payment` VALUES ('158988858058354', null, '0', null, null, null, '0', null, '2020-05-19 19:43:01', '2020-05-19 20:25:47');
INSERT INTO `express_payment` VALUES ('158988864254647', null, '0', null, null, null, '0', null, '2020-05-19 19:44:03', null);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` varchar(255) NOT NULL,
  `name` varchar(16) NOT NULL COMMENT '姓名',
  `tel` varchar(32) NOT NULL COMMENT '联系电话',
  `type` int(1) NOT NULL COMMENT '反馈类型（1订单反馈；2意见反馈；3BUG反馈）',
  `message` varchar(255) NOT NULL COMMENT '反馈信息',
  `status` int(1) DEFAULT '0' COMMENT '反馈状态。0：等待处理；1：处理完成',
  `staff_id` varchar(255) DEFAULT NULL COMMENT '负责人',
  `result` varchar(255) DEFAULT '' COMMENT '处理结果',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_staff_id` (`staff_id`) USING BTREE,
  CONSTRAINT `fk_staff_id` FOREIGN KEY (`staff_id`) REFERENCES `sys_user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户反馈表';

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('5d1245b0c8444eccab717116c28657f8', '李四', '15388889999', '3', '暂未发现，加油', '0', null, '', '2020-05-19 20:36:28', null, '8b1b8530f5534419a8ce0cd17ae4708f');
INSERT INTO `feedback` VALUES ('f770581605ea4e8b840938c956ab723e', '高萨楠', '18355556666', '1', '订单收件人姓名写错，订单号158988864254647', '1', 'e652d4ac148a49329fc0f9da0f8531a3', '已解决', '2020-05-19 20:35:35', '2020-05-19 20:36:55', '8b1b8530f5534419a8ce0cd17ae4708f');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL COMMENT '登录名',
  `password` varchar(255) NOT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL COMMENT '居住地址',
  `birthday` datetime DEFAULT NULL,
  `role_id` int(11) NOT NULL COMMENT '角色；0：admin；1：staff',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态；0：在职；1：冻结；2：离职;3:审核中',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `pic1` varchar(200) DEFAULT NULL,
  `pic2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职员表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('28b9e8411cd542a4937a0745db1c1b82', 'testUser', '443f5ac2414ee06f3720f5eaa82dad3df65bc26809e29644ab0fcc86', '15288889999', null, null, null, '1', '0', '2020-05-23 11:45:31', '2020-05-23 11:46:03', '12345678@qq.com', '/upload/b9aa8cd6-edb8-4370-9a2a-9034354c5ff4test.jpg', '/upload/754eb179-ef0f-430a-8ec3-cc827ab86514test2.jpg');
INSERT INTO `sys_user` VALUES ('b023ff013e9e475aaf92fa355813ebfb', 'testUser', '443f5ac2414ee06f3720f5eaa82dad3df65bc26809e29644ab0fcc86', '15200001111', 'male', '南八', '1998-02-22 00:00:00', '1', '0', '2020-05-19 17:00:19', '2020-05-19 19:36:05', '88888888@qq.com', '/upload/c9f321e0-7fcd-4a56-9add-48b15c21bfc9test.jpg', '/upload/51ed032f-2f11-42c4-bc54-778fe0b18d7ctest2.jpg');
INSERT INTO `sys_user` VALUES ('e652d4ac148a49329fc0f9da0f8531a3', 'admin', 'b2d1f375db625e65a4a63ba0c7b29bdd738392cd83c38b8216c10c4d', '15299999999', 'male', null, '1998-02-22 00:00:00', '0', '0', '2020-04-23 00:26:20', '2020-05-14 15:55:28', 'admin@admin.com', null, null);
