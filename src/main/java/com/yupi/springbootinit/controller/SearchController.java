package com.yupi.springbootinit.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.DataSource.PictureDatasource;
import com.yupi.springbootinit.DataSource.PostDatasource;
import com.yupi.springbootinit.DataSource.UserDatasource;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.picture.PictureQueryRequest;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchQueryRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.enums.DataSourceEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import com.yupi.springbootinit.model.enums.DataSourceEnum;

import static com.yupi.springbootinit.model.enums.DataSourceEnum.*;


@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PostDatasource postDatasource;
    @Resource
    private PictureDatasource pictureDatasource;
    @Resource
    private UserDatasource userDatasource;

    /**
     * 分页获取列表（封装类）
     *
     * @param request
     * @return
     */
    @PostMapping("/get/all")
    public BaseResponse<SearchVO> listPostVOByPage(@RequestBody SearchQueryRequest searchQueryRequest,
                                                   HttpServletRequest request) throws ExecutionException, InterruptedException {
        String searchText = searchQueryRequest.getSearchText();
        String type = searchQueryRequest.getType();
        int pageNum = searchQueryRequest.getCurrent();
        long size = searchQueryRequest.getPageSize();
        SearchVO searchVO = new SearchVO();

        if (StrUtil.isBlank(searchText)) {
            //全部搜索
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            // 限制爬虫
            ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
            Page<UserVO> userVOPage = userDatasource.doSearch(searchText, pageNum, size);
            searchVO.setUserVOS(userVOPage.getRecords());
            Page<PostVO> postVOPage = postDatasource.doSearch(searchText, pageNum, size);
            searchVO.setPostVOS(postVOPage.getRecords());
            Page<Picture> picturePage = pictureDatasource.doSearch(searchText, pageNum, size);
            searchVO.setPictures(picturePage.getRecords());
        } else {
            DataSourceEnum enumByValue = getEnumByValue(type);
            //根据类型获取
            switch (enumByValue) {
                case POST:
                    Page<PostVO> postVOPage = postDatasource.doSearch(searchText, pageNum, size);
                    searchVO.setPostVOS(postVOPage.getRecords());
                    break;
                case PICTURE:
                    Page<Picture> picturePage = pictureDatasource.doSearch(searchText, pageNum, size);
                    searchVO.setPictures(picturePage.getRecords());
                    break;
                case USER:
                    Page<UserVO> userVOPage = userDatasource.doSearch(searchText, pageNum, size);
                    searchVO.setUserVOS(userVOPage.getRecords());
                    break;
            }
        }
        return ResultUtils.success(searchVO);
    }


}
