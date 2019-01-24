package com.example.lib;

import android.view.View;

/**
 * author : echoMu
 * date   : 2019/1/22
 * desc   :
 */
public interface OnStatusChildClickListener {
    /**
     * 空数据布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onEmptyChildClick(View view);

    /**
     * 出错布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onErrorChildClick(View view);

    /**
     * 自定义状态布局布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onCustomerChildClick(View view);
}
