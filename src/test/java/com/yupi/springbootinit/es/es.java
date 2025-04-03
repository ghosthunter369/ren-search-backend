package com.yupi.springbootinit.es;


import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@SpringBootTest
public class es {
    @Autowired
    PostEsDao esDao;
    @Test
    public void add(){
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setTitle("我太帅咯");
        postEsDTO.setContent("张三学习java，学习使我快乐！");
        postEsDTO.setTags(Arrays.asList("java", "python"));
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        esDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }
    @Test
    public void delete(){
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(1L);
//        postEsDTO.setTitle("我是章三");
//        postEsDTO.setContent("张三学习java，学习使我快乐！");
//        postEsDTO.setTags(Arrays.asList("java", "python"));
//        postEsDTO.setUserId(1L);
//        postEsDTO.setCreateTime(new Date());
//        postEsDTO.setUpdateTime(new Date());
//        postEsDTO.setIsDelete(0);
//        esDao.save(postEsDTO);
        esDao.delete(postEsDTO);
        System.out.println(postEsDTO.getId());
    }
    @Test
    public void update(){
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(1L);
        postEsDTO.setTitle("我是王五");
        postEsDTO.setContent("王五不高兴咯");
        postEsDTO.setTags(Arrays.asList("java", "python"));
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        esDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }
    @Test
    public void find(){
        PostEsDTO postEsDTO = esDao.findById(1L).get();
        System.out.println(postEsDTO);
    }
    @Test
    public void findAll(){
        Iterator<PostEsDTO> iterator = esDao.findAll().iterator();
        while (iterator.hasNext()){
            PostEsDTO next = iterator.next();
            System.out.println(next);
        }

    }

}
