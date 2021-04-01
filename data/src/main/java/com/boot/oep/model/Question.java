package com.boot.oep.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.boot.oep.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
* @author ccjr
* @date 2021年03月20 06:11 
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class Question extends BaseEntity {

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 题库id
	 */
	private String bankId;

	/**
	 * 题目
	 */
	private String question;

	/**
	 * 正确答案
	 */
	private String rightAnswer;

	/**
	 * 错误答案Json存储
	 */
	private String wrongAnswer;


}
