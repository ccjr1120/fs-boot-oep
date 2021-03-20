package com.boot.oep.model;


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

	private String id;

	/**
	 * 模板
	 */
	private String templateId;

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
