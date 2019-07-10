package com.ccc.community.dto;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 14:32
 */
@Data
public class PageDTO<T> {
    private List<T> data;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private boolean showFirst;
    private boolean showEnd;
    private boolean showNext;
    private boolean showPrevious;
    private Integer totalPage;
    public void setPageInfo(Integer totalCount , Integer page , Integer size){
        pages.add(page);
        for (int i =1 ; i <= 3 ; i++ ){
            if (page - i > 0){
                pages.add(0 , page - i);
            }
            if (page + i <= totalPage){
                pages.add(page + i);
            }
        }
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        if (page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        if (pages.contains(1)){
            showFirst = false;
        }else {
            showFirst = true;
        }
        if (pages.contains(totalPage)){
            showEnd = false;
        }else {
            showEnd = true;
        }
    }
}
