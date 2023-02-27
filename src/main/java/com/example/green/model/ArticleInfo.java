package com.example.green.model;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-01-11 16:14
 */
@Data
public class ArticleInfo {
    private int id;
    private String title;
    private String content;
    private String createtime;
    private String updatetime;
    private int uid;
    private int rcount;
    private int state;
}
