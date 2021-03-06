package com.infinite.common.utils;


import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: ModelMapperUtil
* @Description: 类属性copy工具类
* @author chenliqiao
* @date 2018年11月28日 上午11:18:34
*
 */
public class ModelMapperUtil {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    
    static{
        /**
         * MatchResult有三种结果：FULL、PARTIAL和NONE（即全部匹配，部分匹配和不匹配）。
         * 注意，这里有一个部分匹配，也就是我所踩到的坑，在对like进行匹配是，likeNum就被定义为部分匹配。因此，当likeNum大于2时，就不能被转换成boolean类型。
                                这里解决方法有两种，一种是在设置中，规定必须字段名完全匹配；另一种就是将匹配策略定义为严格。
         */
        MODEL_MAPPER.getConfiguration().setFullTypeMatchingRequired(true);
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <T> T map(Object t, Class<T> r) {
        if (null == t) {
            return null;
        }
        return MODEL_MAPPER.map(t, r);
    }

    public static <T> List<T> mapToList(List<Object> t, Class<T> r) {
        List<T> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(t)) {
            for(Object obj:t) {
                list.add(map(obj,r));
            }
        }
        return list;
    }
    
    public static void main(String[] args) {
        PostDO pdo=new PostDO();
        pdo.setId(1L);
        pdo.setLikeNum(1);
        pdo.setPostId(1L);
        pdo.setCommentId("1");
        PostVO pvo=ModelMapperUtil.map(pdo, PostVO.class);
        System.out.println(pvo);
        
        /**
         * 会报错，原因参考：https://www.jianshu.com/p/ea3b9347215e
         */
        PostDO pdo1=new PostDO();
        pdo1.setId(2L);
        pdo1.setLikeNum(2);
        pdo1.setPostId(2L);
        pdo1.setCommentId("2");
        PostVO pvo1=ModelMapperUtil.map(pdo1, PostVO.class);
        System.out.println(pvo1);
        
        /**
         * BeanUtils.copyProperties()实现
         */
        PostVO pvo2=new PostVO();
        BeanUtils.copyProperties(pdo1, pvo2);
        System.out.println(pvo2);
    } 
    
    public static class PostDO {
        
        private Long id;
        private String commentId;
        private Long postId;
        private int likeNum;
        
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getCommentId() {
            return commentId;
        }
        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }
        public Long getPostId() {
            return postId;
        }
        public void setPostId(Long postId) {
            this.postId = postId;
        }
        public int getLikeNum() {
            return likeNum;
        }
        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }
        
        
    }
    
    public static class PostVO {
        
        private Long id;
        private boolean like;
        private int likeNum;
        
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public boolean isLike() {
            return like;
        }
        public void setLike(boolean like) {
            this.like = like;
        }
        public int getLikeNum() {
            return likeNum;
        }
        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }
        @Override
        public String toString() {
            return "PostVO [id=" + id + ", like=" + like + ", likeNum=" + likeNum + "]";
        }       
    }
}
