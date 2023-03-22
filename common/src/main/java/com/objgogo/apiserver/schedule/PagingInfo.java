package com.objgogo.apiserver.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagingInfo<T> {

    private int total; // 쿼리 결과 count
    private List<T> content; // 쿼리 결과 contents
    private int pageNum; // 현재 페이지 번호
    private int pageSize; // 한 페이지당 보여 줄 row 수
    private int pages; // 총 페이지 수
    private int size;
    private int startRow;
    private int endRow;
    private int prePage; // 이전 페이지
    private int nextPage; // 다음 페이지
    private boolean isFirstPage; // 첫 페이지
    private boolean isLastPage; // 마지막 페이지
    private boolean hasPreviousPage; // 이전 페이지 유무
    private boolean hasNextPage; // 다음 페이지 유무
    private int navigatePages; // 화면에 보여질 페이지 범위 [1,2,3,4,5]
    private int[] navigatepageNums;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int firstPage;
    private int lastPage;

    public PagingInfo() {
    }

    public PagingInfo(int total, List<T> content, int pageNum, int pageSize) {

        this(total, content, pageNum, pageSize, 10);
    }

    public PagingInfo(int total, List<T> content, int pageNum, int pageSize, int navigatePages) {
        this.total = total;
        this.content = content;
        this.pageNum = pageNum < 1 ? 1 : pageNum;
        this.pageSize = pageSize;
        this.startRow = total - (this.pageNum-1)*pageSize;
        this.endRow = startRow - pageSize+1;
        this.pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        //        this.pages = content.getTotalPages();
        this.size = content.size();

        //        this.prePage = pageNum - 1 == 0 ? 1 : pageNum-1;
        //        this.nextPage = pageNum + 1 >total ? total : pageNum +1;
        this.navigatePages = navigatePages;

        judgePageBoundary();
        calcNavigatepageNums();
        calcPage();

    }

    private void calcNavigatepageNums() {

        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else {
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;

                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;

                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {

                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
        //        this.firstPage = navigatepageNums[0];
        //        this.lastPage = navigatepageNums[navigatePages-1];
    }

    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            navigateFirstPage = navigatepageNums[0];
            navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
    }

    private void judgePageBoundary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages || pages == 0;
        ;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }

}
