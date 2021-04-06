package com.boot.oep.webapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.oep.mapper.ExamRecordMapper;
import com.boot.oep.model.ExamRecord;
import com.boot.oep.webapi.service.ExamRecordService;
import org.springframework.stereotype.Service;

/**
 * @author zrf
 * @date 2021/4/6
 */
@Service
public class ExamRecordServiceImpl extends ServiceImpl<ExamRecordMapper, ExamRecord> implements ExamRecordService {
}
