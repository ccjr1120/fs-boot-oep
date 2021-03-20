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
public class QuesBank extends BaseEntity {

	private String id;

	/**
	 * 题库
	 */
	private String bankName;


}
