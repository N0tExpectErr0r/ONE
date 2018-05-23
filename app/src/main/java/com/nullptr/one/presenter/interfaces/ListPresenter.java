package com.nullptr.one.presenter.interfaces;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/22
 * @DESCRIPTION 各种列表对应presenter的基类
 */
public interface ListPresenter {
    void loadList();

    void loadMore(String lastId);
}
