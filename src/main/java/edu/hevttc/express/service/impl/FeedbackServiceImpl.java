package edu.hevttc.express.service.impl;

import edu.hevttc.express.service.FeedbackService;
import edu.hevttc.express.pojo.Feedback;
import edu.hevttc.express.mapper.FeedbackMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户反馈表 服务实现类
 */
@Service
@Transactional
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

}
