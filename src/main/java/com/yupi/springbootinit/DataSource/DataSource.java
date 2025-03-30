package com.yupi.springbootinit.DataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface DataSource<T> {
    Page<T> doSearch(String searchText, long page, long pageSize);
}
