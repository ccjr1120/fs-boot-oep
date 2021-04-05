package com.boot.oep.webapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.oep.mapper.ExamMapper;
import com.boot.oep.model.Exam;
import com.boot.oep.webapi.service.ExamService;
import org.springframework.stereotype.Service;

/**
 * @author ccjr
 * @date 2021/4/5 4:44 下午
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
}
