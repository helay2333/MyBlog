package com.example.green.mapper;

import com.example.green.model.ArticleInfo;
import com.example.green.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    public List<ArticleInfo>getList(@Param("pSize")Integer pSize,
                                    @Param("offset")Integer offset);
    public List<ArticleInfo> getMyList(@Param("uid") Integer uid);

    public ArticleInfo getDetail(@Param("aid") Integer uid);

    public int update(@Param("aid")Integer id,
                      @Param("uid")Integer uid, //这是从后端session获取得到的,不是前端传的
                      @Param("title")String title,
                      @Param("content")String content);
    public int getTotalCount();

    public int addArt(@Param("title") String title, @Param("content") String content, @Param("uid") Integer uid);

    public int delete(@Param("id") Integer id);

    public UserInfo getUserByArtId(@Param("aid")Integer id);

    public int getArtCount(@Param("id") Integer id);

    //Todo:故意写错
    public int updateRcount(@Param("aid")Integer id,@Param("count")Integer rcount);
}
