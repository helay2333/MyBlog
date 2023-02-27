package com.example.green.controller;

import com.example.green.common.AjaxResult;
import com.example.green.common.ConstantVariable;
import com.example.green.common.SessionUtil;
import com.example.green.model.ArticleInfo;
import com.example.green.model.UserInfo;
import com.example.green.service.ArticleService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Green写代码
 * @date 2023-01-11 16:15
 */
@RestController
@RequestMapping("/art")
public class ArticleController {
    @Autowired
    public ArticleService articleService;
    @RequestMapping("/mylist")
    public List<ArticleInfo> myList(HttpServletRequest servletRequest){
        System.out.println("=====走到后端");
        UserInfo userInfo = SessionUtil.getLoginUser(servletRequest);
        if(userInfo != null) {
            return articleService.getMyList(userInfo.getId());
        }
        return null;
    }

    @RequestMapping("/detail")
    public Object getDetail(Integer aid){
        if(aid != null && aid > 0) {
            return AjaxResult.success(articleService.getDetail(aid));
        }
        return AjaxResult.fail(-1, "查询失败");
    }
    @RequestMapping("/detailbyid")
    public Object getDetailById(HttpServletRequest request, Integer aid) {
        if(aid != null && aid > 0) {
            //根据文章查询文章的详情
            ArticleInfo articleInfo = articleService.getDetail(aid);
            //文章归属人验证
            UserInfo userInfo = SessionUtil.getLoginUser(request);
            System.out.println(userInfo.getId() + "   " + articleInfo.getUid());
            if(userInfo != null && articleInfo != null &&  userInfo.getId() == articleInfo.getUid()) {
                return AjaxResult.success(articleService.getDetail(aid));
            }else{
                //没有登录, 执行最终的return
            }

        }
        return AjaxResult.fail(-1, "查询失败");
    }

    @RequestMapping("/update")
    public int update(HttpServletRequest request, Integer aid, String title, String content){
        //todo: 非空校验
        UserInfo userInfo = SessionUtil.getLoginUser(request);
        if(userInfo != null && userInfo.getId() > 0) {
            return articleService.update(aid, userInfo.getId(), title, content);
        }
        return 0;
    }

    @RequestMapping("/list")
    public List<ArticleInfo> getList(Integer pIndex, Integer pSize) {
        if(pIndex == null || pSize == null) {
            return null;
        }
        int offset = (pIndex-1)*pSize;//分页公式. 计算偏移量
        System.out.println("===="+pSize);
        System.out.println("===="+offset);
        return articleService.getList(pSize, offset);
    }

    @RequestMapping("/getTotalPage")
    public Integer getTotalPage(Integer pSize){
        if(pSize != null){
            int totalCount = articleService.getTotalCount();
            System.out.println(totalCount);
            int totalPage = (int)Math.ceil(totalCount * 1.0 / pSize);
            System.out.println(totalPage);
            return totalPage;
        }
        return null;
    }
    @RequestMapping("/addArticleInfo")
    public Integer addArticleInfo(String title, String content, HttpServletRequest httpServletRequest){
        UserInfo userInfo = SessionUtil.getLoginUser(httpServletRequest);
        if(userInfo != null && userInfo.getId() > 0){
            return articleService.addArt(title, content, userInfo.getId());
        }
        return 0;
    }
    @RequestMapping("/deleteArticleInfo")
    public Integer deleteArticleInfo(Integer id, HttpServletRequest request) {
        UserInfo userInfo = SessionUtil.getLoginUser(request);
        System.out.println(id);
        if(userInfo != null && userInfo.getId() > 0) {
            return articleService.delete(id);
        }
        return 0;
    }

    @RequestMapping("/getAuthor")
    public Object getAuthor(Integer id){
        if(id != null && id > 0){
            return AjaxResult.success("获取成功",articleService.getAuthor(id));
        }
        return AjaxResult.fail(-1, "获取失败");
    }

    @RequestMapping("/getArtCount")
    public Object getArtCount(Integer id){
        if(id != null && id > 0 ){
            return AjaxResult.success("获取成功",articleService.getArtCount(id));
        }
        return AjaxResult.fail(-1, "获取失败");
    }
}
