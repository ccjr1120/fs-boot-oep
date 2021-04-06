package com.boot.oep.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.boot.oep.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
* @author ccjr
* @date 2021年04月05 09:46 
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class Exam extends BaseEntity {

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	private String randomStr;

	private String name;

	private Integer minutes;

	private Integer peopleNum;

	/**
	 * 是否随机，讲觉得sourceIds是questionId还是bankId
	 */
	private Integer isRandom;

	private String sourceIds;

	private Integer partNum;

	private Integer average;

	private Integer state;


}
