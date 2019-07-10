package com.ccc.community;

import com.ccc.community.mapper.QuestionMapper;
import com.ccc.community.model.Question;
import com.ccc.community.model.QuestionExample;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {
    @Autowired
    QuestionMapper questionMapper;

    @Test
    public void contextLoads() {
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(0, 5));
        for (Question question : questions) {
            System.out.println(question);

        }
    }

}
