package com.lyh.utils;

import com.lyh.domain.entity.Article;
import com.lyh.domain.vo.HotArticleVo;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:crushlyh
 * Date:2023/2/20 17:06
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){}

    public static <V> V copyBean(Object source,Class<V> classz) {
        //创建目标对象
        V result = null;
        try {
            result = classz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
            //返回对象结果

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> classz){
        //Stream流
        return list.stream()
                .map(o -> copyBean(o, classz)).collect(Collectors.toList());
    }

    /*public static void main(String[] args) {
        Article article=new Article();
        article.setId(1L);
        article.setTitle("java");
        article.setViewCount(23L);
        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);
    }*/
}
