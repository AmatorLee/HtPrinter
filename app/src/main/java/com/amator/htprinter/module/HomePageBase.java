package com.amator.htprinter.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AmatorLee on 2018/4/12.
 */

public class HomePageBase implements Serializable{

    private static final long serialVersionUID = HomePageBase.class.hashCode();

    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"08_cameralo","chapterId":260,"chapterName":"RxJava & Retrofit & MVP","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2736,"link":"https://www.jianshu.com/p/734d3693da02","niceDate":"2018-04-03","origin":"","projectLink":"","publishTime":1522726919000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"谈谈我理解的Android应用架构","type":0,"visible":1,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 61
     * size : 20
     * total : 1201
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<HomePage> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<HomePage> getDatas() {
        return datas;
    }

    public void setDatas(List<HomePage> datas) {
        this.datas = datas;
    }
}
