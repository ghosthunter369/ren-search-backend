package com.yupi.springbootinit.job.once;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全量同步帖子到 es
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class GetPassageOnce implements CommandLineRunner {

    @Resource
    private PostService postService;
    @Override
    public void run(String... args) {
        String json = "{\n" +
                "    \"reviewStatus\": 1,\n" +
                "    \"needNotInterests\": true,\n" +
                "    \"hiddenContent\": true,\n" +
                "    \"needCursor\": true,\n" +
                "    \"needFilterVipContent\": true,\n" +
                "    \"needOnlyRecommend\": true,\n" +
                "    \"cursorList\": [\n" +
                "        {\n" +
                "            \"field\": \"recommendTime\",\n" +
                "            \"asc\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"field\": \"id\",\n" +
                "            \"asc\": false\n" +
                "        }\n" +
                "    ],\n" +
                "    \"queryType\": \"recommend\"\n" +
                "}";
        String result2 = HttpRequest.post("https://api.codefather.cn/api/post/list/page/vo")
                .body(json)
                .execute().body();
        Map<String,Object> map = JSONUtil.toBean(result2, Map.class);
        JSONObject o =(JSONObject) map.get("data");
        JSONArray value =(JSONArray) o.get("records");
        System.out.println(value);
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            JSONObject o1 =(JSONObject) value.get(i);
            Post post = new Post();
            Post bean = BeanUtil.toBean(o1, Post.class);
            BeanUtil.copyProperties(bean,post);
            posts.add(post);
        }
        boolean b = postService.saveBatch(posts);
        if(b){
            log.info("初始化添加文章数据成功，共添加文章条数：{}",posts.size());
        }else {
            log.error("初始化添加文章数据失败");
        }
    }
}
