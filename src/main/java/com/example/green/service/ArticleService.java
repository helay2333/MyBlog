package com.example.green.service;

import com.example.green.mapper.ArticleMapper;
import com.example.green.model.ArticleInfo;
import com.example.green.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Green写代码
 * @date 2023-01-11 16:18
 * 文章表的服务层
 */
@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    public List<ArticleInfo> getMyList(Integer uid){
        return articleMapper.getMyList(uid);
    }

    public ArticleInfo getDetail(Integer aid){
        ArticleInfo articleInfo = articleMapper.getDetail(aid);
        //更新访问量
        articleMapper.updateRcount(aid, articleInfo.getRcount()+1);
        return articleInfo;
    }

    /*写博客*/
    public int addArt(String title, String content, int uid){
        return articleMapper.addArt(title, content, uid);
    }

    public int update(Integer id, Integer uid, String title, String content) {
        return articleMapper.update(id, uid,title,content);
    }
    public List<ArticleInfo> getList(Integer pSize, Integer offset){
        return articleMapper.getList(pSize, offset);
    }
    public int getTotalCount(){
        return articleMapper.getTotalCount();
    }
    //删除文章
    public int delete(Integer id){
        return articleMapper.delete(id);
    }

    /*获取文章作者信息*/
    public UserInfo getAuthor(Integer id){
        return articleMapper.getUserByArtId(id);
    }

    public int getArtCount(Integer id){
        return articleMapper.getArtCount(id);
    }
}
