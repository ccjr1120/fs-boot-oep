package com.boot.oep.webapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.oep.mapper.QuestionMapper;
import com.boot.oep.model.Question;
import com.boot.oep.webapi.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * @author ccjr
 * @date 2021/3/20 7:52 下午
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
}
