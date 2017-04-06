-- 数据库初始化脚本
-- 注意1：执行sql命令时候，字段不能加引号。可以不加或者加反引号，在esc下边 ``。
--        加反引号是防止有时候定义的表名或者字段名时与系统关键字发生冲突,如果能确保不使用关键字就可以不用反引号
--        进行查询，插入等操作时，可以加也可以不加.
-- 注意2：在命令行执行sql语句时候，后边必须加 ; 号才会执行。
-- 注意3：开发中建表直接用工具建，提高效率。可以转存成sql文件。便于开源或协作。

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
	seckill_id bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
	name varchar(120) NOT NULL COMMENT '商品名称',
	number int NOT NULL COMMENT '库存数量',
	start_time timestamp NOT NULL COMMENT '秒杀开启时间',
	end_time timestamp NOT NULL COMMENT '秒杀结束时间',
	create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (seckill_id),
	KEY idx_start_time (start_time),
	KEY idx_end_time (end_time),
	KEY idx_create_time (create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT="秒杀库存表";

-- 初始化数据
INSERT INTO 
	seckill (name, number, start_time, end_time)
VALUES
	('1000元秒杀iPhone6', 100, '2017-4-10 00:00:00', '2017-4-11 00:00:00'),
	('500元秒杀ipad2', 200, '2017-4-10 00:00:00', '2017-4-11 00:00:00'),
	('300元秒杀小米4', 300, '2017-4-10 00:00:00', '2017-4-11 00:00:00'),
	('200元秒杀红米note', 400, '2017-4-10 00:00:00', '2017-4-11 00:00:00')

-- 秒杀成功明细表
-- 用户登录认证相关信息
CREATE TABLE success_killed(
	`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
	`user_phone` bigint NOT NULL COMMENT '用户手机号',
	`state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标志：-1:无效 0:成功 1:已付款 2:已发货',
	`create_time` timestamp NOT NULL COMMENT '创建时间',
	PRIMARY KEY (seckill_id, user_phone), /*-联合主键:防止用户重复秒杀*/
	KEY idx_create_time (create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- 连接数据库控制台
mysql -uroot -p ;
show create TABLE table_name;
show tables;
show databases;
\c 退出当前命令
-- 手写ddl
-- 记录每次上线的ddl修改
-- 假设修改索引
ALTER TABLE seckill;
DROP INDEX idx_end_time;
ADD INDEX idx_c_s (start_time, create_time);