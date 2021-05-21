
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (

                         `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL comment '用户UUID,主键',
                         `random_str` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL comment '考试参与口令',
                         `name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL comment '试卷名称',
                         `minutes` int DEFAULT NULL comment '时间限制',
                         `people_num` int DEFAULT NULL comment '人数限制',
                         `question_num` int DEFAULT NULL comment '题目数量',
                         `is_random` int DEFAULT NULL comment '试卷是否针对每个参与人随机',
                         `source_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL comment '试卷所属id',
                         `part_num` int  DEFAULT NULL comment '当前参与人数',
                         `average` int DEFAULT NULL comment '平均分(四舍五入)',
                         `state` int DEFAULT NULL comment '当前考试状态(0:未开始;1:正在进行;2:已结束;)' ,
                         `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL comment '创建人id',
                         `create_time` datetime comment '创建时间',
                         `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL comment '更新人id',
                         `update_time` datetime comment '更新时间',
                         `del_flag` tinyint(1) default 0 comment '逻辑删除标识',
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

DROP TABLE IF EXISTS `question_bank`;
CREATE TABLE `question_bank`  (

                                  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                  `bank_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                  `question_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                  `create_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                  `create_time` datetime ,
                                  `update_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                  `update_time` datetime ,
                                  `del_flag` tinyint(1) default 0,
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;null
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (

                          `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                          `paper_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                          `question_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                          `create_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                          `create_time` datetime ,
                          `update_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                          `update_time` datetime ,
                          `del_flag` tinyint(1) default 0,
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (

                             `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `bank_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `right_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `wrong_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `create_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `create_time` datetime ,
                             `update_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `update_time` datetime ,
                             `del_flag` tinyint(1) default 0,
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (

                             `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `lock_flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `create_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `create_time` datetime ,
                             `update_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `update_time` datetime ,
                             `del_flag` tinyint(1) default 0,
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (

                             `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `sort` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `roles` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `create_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `create_time` datetime ,
                             `update_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `update_time` datetime ,
                             `del_flag` tinyint(1) default 0,
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;
DROP TABLE IF EXISTS `exam_record`;
CREATE TABLE `exam_record`  (

                                `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `bank_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `option_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `answers` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `exam_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `create_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `create_time` datetime ,
                                `update_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `update_time` datetime ,
                                `del_flag` tinyint(1) default 0,
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;