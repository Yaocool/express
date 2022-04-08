package edu.hevttc.express.controller.staff;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import edu.hevttc.express.controller.GlobalFunction;
import edu.hevttc.express.dto.FeedbackDto;
import edu.hevttc.express.enums.FeedbackTypeEnum;
import edu.hevttc.express.interactive.FeedbackSelectWrapper;
import edu.hevttc.express.interactive.Msg;
import edu.hevttc.express.pojo.Feedback;
import edu.hevttc.express.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/staff/feedback")
public class StaffFeedbackController {
    @Autowired
    private GlobalFunction globalFunction;
    @Autowired
    private FeedbackService feedbackService;

    /**
     * 提交反馈
     */
    @PostMapping("/postFeedback")
    public Msg feedback(Feedback feedback) {
        String userId = globalFunction.getUserId();
        feedback.setUserId(userId);
        feedback.setCreateDate(new Date());
        feedbackService.insert(feedback);
        return Msg.ok(null, feedback.getId());
    }

    /**
     * 获取当前用户所有反馈
     */
    @GetMapping("/getUserFeedbackList")
    public Map<String, Object> listFeedback(Integer rows, Integer page, FeedbackSelectWrapper fsw, @RequestParam(defaultValue = "createDate") String order) {
        // Get请求中文编码
        try {
            fsw.setName(globalFunction.iso8859ToUtf8(fsw.getName()));
            fsw.setStaffName(globalFunction.iso8859ToUtf8(fsw.getStaffName()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 得到筛选条件
        EntityWrapper<Feedback> feedbackWrapper = globalFunction.getFeedbackWrapper(fsw);
        feedbackWrapper.eq("user_id", globalFunction.getUserId());
        Page<Feedback> selectPage = feedbackService.selectPage(new Page<>(page, rows, order, false), feedbackWrapper);
        List<FeedbackDto> list = globalFunction.feedback2dto(selectPage.getRecords());
        Map<String, Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", list);
        return map;
    }

    /**
     * 获取所有反馈类型
     */
    @GetMapping("/type/list")
    public Msg listFeedbackType() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (FeedbackTypeEnum enums : FeedbackTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", enums.getIndex());
            map.put("name", enums.getName());
            result.add(map);
        }
        return Msg.ok(null, result);
    }

}
