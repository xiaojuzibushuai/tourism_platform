package cn.xiaojuzi.travel.mongo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 游记评论
 * author:xiaojuzi
 */
@Setter
@Getter
@Document("travel_comment")
public class TravelComment {
    public static final int TRAVLE_COMMENT_TYPE_COMMENT = 0; //普通评论
    public static final int TRAVLE_COMMENT_TYPE = 1; //评论的评论
    @Id
    private String id;  //id
    private Long travelId;  //游记id
    private String travelTitle; //游记标题
    private Long userId;    //用户id
    private String nickname; //用户名
    private String city;
    private int level;
    private String headImgUrl;   // 用户头像

    private int type = TRAVLE_COMMENT_TYPE_COMMENT; //评论类别

    private Date createTime; //创建时间
    private String content;  //评论内容
    private TravelComment refComment;  //关联的评论
}