
 DROP TABLE IF EXISTS `exam`;  
 CREATE TABLE `exam`  ( 
  
 `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `random_str` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `minutes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `people_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `question_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `is_random` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `source_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `part_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `average` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `create_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `create_time` datetime , 
 `update_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL, 
 `update_time` datetime , 
 `del_flag` tinyint(1) default 0, 
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