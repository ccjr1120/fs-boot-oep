package com.boot.oep.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.boot.oep.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
* @author ccjr
* @date 2021年4月6日 下午01:15
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class ExamRecord extends BaseEntity {

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	private String bankId;

	private String optionList;

	private String answers;

	private Integer state;


}
