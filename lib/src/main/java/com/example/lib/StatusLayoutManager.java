package com.example.lib;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * author : echoMu
 * date   : 2019/1/22
 * desc   :
 */
public class StatusLayoutManager {
    /**
     * 三种默认布局 ID
     */
    private static final int DEFAULT_LOADING_LAYOUT_ID = R.layout.layout_status_layout_manager_loading;
    private static final int DEFAULT_EMPTY_LAYOUT_ID = R.layout.layout_status_layout_manager_empty;
    private static final int DEFAULT_ERROR_LAYOUT_ID = R.layout.layout_status_layout_manager_error;

    /**
     * 默认布局中可点击的 view ID
     */
    private static final int DEFAULT_EMPTY_CLICKED_ID = R.id.status_layout_manager_bt_status_empty_click;
    private static final int DEFAULT_ERROR_CLICKED_ID = R.id.status_layout_manager_bt_status_error_click;

    /**
     * 默认颜色
     */
    private static final int DEFAULT_CLICKED_TEXT_COLOR = R.color.status_layout_manager_click_view_text_color;
    private static final int DEFAULT_BACKGROUND_COLOR = R.color.status_layout_manager_background_color;

    /**
     * 默认图片
     */
    private static final int DEFAULT_EMPTY_IMG_ID = R.drawable.status_layout_manager_ic_empty;
    private static final int DEFAULT_ERROR_IMG_ID = R.drawable.status_layout_manager_ic_error;

    private View contentLayout;

    private int loadingLayoutID;
    private View loadingLayout;
    private String loadingText;

    private int emptyClickViewId;
    private int emptyLayoutID;
    private View emptyLayout;
    private String emptyText;
    private String emptyClickViewText;
    private int emptyClickViewTextColor;
    private boolean isEmptyClickViewVisible;
    private int emptyImgID;

    private int errorClickViewId;
    private int errorLayoutID;
    private View errorLayout;
    private String errorText;
    private String errorClickViewText;
    private int errorClickViewTextColor;
    private boolean isErrorClickViewVisible;
    private int errorImgID;

    private int defaultBackgroundColor;

    private OnStatusChildClickListener onStatusChildClickListener;

    private ReplaceLayoutHelper replaceLayoutHelper;

    private LayoutInflater inflater;

    public StatusLayoutManager(Builder builder) {
        this.contentLayout = builder.contentLayout;

        this.loadingLayoutID = builder.loadingLayoutID;
        this.loadingLayout = builder.loadingLayout;
        this.loadingText = builder.loadingText;

        this.emptyClickViewId = builder.emptyClickViewId;
        this.emptyLayoutID = builder.emptyLayoutID;
        this.emptyLayout = builder.emptyLayout;
        this.emptyText = builder.emptyText;
        this.emptyClickViewText = builder.emptyClickViewText;
        this.emptyClickViewTextColor = builder.emptyClickViewTextColor;
        this.isEmptyClickViewVisible = builder.isEmptyClickViewVisible;
        this.emptyImgID = builder.emptyImgID;

        this.errorClickViewId = builder.errorClickViewId;
        this.errorLayoutID = builder.errorLayoutID;
        this.errorLayout = builder.errorLayout;
        this.errorText = builder.errorText;
        this.errorClickViewText = builder.errorClickViewText;
        this.errorClickViewTextColor = builder.errorClickViewTextColor;
        this.isErrorClickViewVisible = builder.isErrorClickViewVisible;
        this.errorImgID = builder.errorImgID;

        this.defaultBackgroundColor = builder.defaultBackgroundColor;
        this.onStatusChildClickListener = builder.onStatusChildClickListener;
        this.replaceLayoutHelper = new ReplaceLayoutHelper(contentLayout);
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingLayout() {
        createLoadingLayout();
        replaceLayoutHelper.showStatusLayout(loadingLayout);
    }

    /**
     * 显示空数据布局
     */
    public void showEmptyLayout() {
        createEmptyLayout();
        replaceLayoutHelper.showStatusLayout(emptyLayout);
    }

    /**
     * 显示出错布局
     *
     */
    public void showErrorLayout() {
        createErrorLayout();
        replaceLayoutHelper.showStatusLayout(errorLayout);
    }

    /**
     * 显示原有布局
     *
     */
    public void showSuccessLayout() {
        replaceLayoutHelper.restoreLayout();
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义布局 ID
     * @param clickViewID    点击按钮 ID
     */
    public View showCustomLayout(@LayoutRes int customLayoutID, @IdRes int... clickViewID) {
        View customLayout = inflate(customLayoutID);
        showCustomLayout(customLayout, clickViewID);
        return customLayout;
    }

    /**
     * 创建加载中布局
     */
    private void createLoadingLayout() {
        if (loadingLayout==null){
            loadingLayout=inflate(loadingLayoutID);
        }
        if (loadingLayoutID == DEFAULT_LOADING_LAYOUT_ID) {
            loadingLayout.setBackgroundColor(defaultBackgroundColor);
        }
        if (!TextUtils.isEmpty(loadingText)) {
            TextView loadingTextView = loadingLayout.findViewById(R.id.status_layout_manager_tv_status_loading_content);
            if (loadingTextView != null) {
                loadingTextView.setText(loadingText);
            }
        }
    }

    /**
     * 创建空数据布局
     */
    private void createEmptyLayout() {
        if (emptyLayout == null) {
            emptyLayout = inflate(emptyLayoutID);
        }
        if (emptyLayoutID == DEFAULT_EMPTY_LAYOUT_ID) {
            emptyLayout.setBackgroundColor(defaultBackgroundColor);
        }

        // 点击事件回调
        View view = emptyLayout.findViewById(emptyClickViewId);
        if (view != null && onStatusChildClickListener != null) {
            // 设置点击按钮点击时事件回调
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStatusChildClickListener.onEmptyChildClick(view);
                }
            });
        }

        // 设置默认空数据布局的提示文本
        if (!TextUtils.isEmpty(emptyText)) {
            TextView emptyTextView = emptyLayout.findViewById(R.id.status_layout_manager_tv_status_empty_content);
            if (emptyTextView != null) {
                emptyTextView.setText(emptyText);
            }
        }

        // 设置默认空数据布局的图片
        ImageView emptyImageView = emptyLayout.findViewById(R.id.status_layout_manager_iv_status_empty_img);
        if (emptyImageView != null) {
            emptyImageView.setImageResource(emptyImgID);
        }

        TextView emptyClickViewTextView = emptyLayout.findViewById(DEFAULT_EMPTY_CLICKED_ID);
        if (emptyClickViewTextView != null) {
            // 设置点击按钮的文本和可见性
            if (isEmptyClickViewVisible) {
                emptyClickViewTextView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(emptyClickViewText)) {
                    emptyClickViewTextView.setText(emptyClickViewText);
                }
                emptyClickViewTextView.setTextColor(emptyClickViewTextColor);
            } else {
                emptyClickViewTextView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 创建出错布局
     */
    private void createErrorLayout() {
        if (errorLayout == null) {
            errorLayout = inflate(errorLayoutID);
        }
        if (errorLayoutID == DEFAULT_ERROR_LAYOUT_ID) {
            errorLayout.setBackgroundColor(defaultBackgroundColor);
        }

        View view = errorLayout.findViewById(errorClickViewId);
        if (view != null && onStatusChildClickListener != null) {
            // 设置点击按钮点击时事件回调
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStatusChildClickListener.onErrorChildClick(view);
                }
            });
        }

        // 设置默认出错布局的提示文本
        if (!TextUtils.isEmpty(errorText)) {
            TextView errorTextView = errorLayout.findViewById(R.id.status_layout_manager_tv_status_error_content);
            if (errorTextView != null) {
                errorTextView.setText(errorText);
            }
        }

        // 设置默认出错布局的图片
        ImageView errorImageView = errorLayout.findViewById(R.id.status_layout_manager_iv_status_error_image);
        if (errorImageView != null) {
            errorImageView.setImageResource(errorImgID);
        }

        TextView errorClickViewTextView = errorLayout.findViewById(DEFAULT_ERROR_CLICKED_ID);
        if (errorClickViewTextView != null) {
            // 设置点击按钮的文本和可见性
            if (isErrorClickViewVisible) {
                errorClickViewTextView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(errorClickViewText)) {
                    errorClickViewTextView.setText(errorClickViewText);
                }
                errorClickViewTextView.setTextColor(errorClickViewTextColor);
            } else {
                errorClickViewTextView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     * @param clickViewID  可点击 View ID
     */
    public void showCustomLayout(@NonNull View customLayout, @IdRes int... clickViewID) {
        replaceLayoutHelper.showStatusLayout(customLayout);
        if (onStatusChildClickListener == null) {
            return;
        }

        for (int aClickViewID : clickViewID) {
            View clickView = customLayout.findViewById(aClickViewID);
            if (clickView == null) {
                return;
            }

            // 设置点击按钮点击时事件回调
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStatusChildClickListener.onCustomerChildClick(view);
                }
            });
        }
    }

    private View inflate(int resource) {
        if (inflater==null){
            inflater=LayoutInflater.from(contentLayout.getContext());
        }
        return inflater.inflate(resource,null);
    }

    public static final class Builder {
        private View contentLayout;

        private int loadingLayoutID;
        private View loadingLayout;
        private String loadingText;

        private int emptyClickViewId;
        private int emptyLayoutID;
        private View emptyLayout;
        private String emptyText;
        private String emptyClickViewText;
        private int emptyClickViewTextColor;
        private boolean isEmptyClickViewVisible;
        private int emptyImgID;

        private int errorClickViewId;

        private int errorLayoutID;
        private View errorLayout;
        private String errorText;
        private String errorClickViewText;
        private int errorClickViewTextColor;
        private boolean isErrorClickViewVisible;
        private int errorImgID;

        private int defaultBackgroundColor;

        private OnStatusChildClickListener onStatusChildClickListener;

        /**
         * 创建状态布局 Build 对象
         * @param contentLayout 原有布局，内容布局
         */
        public Builder(View contentLayout){
            this.contentLayout=contentLayout;
            // 设置默认布局
            this.loadingLayoutID = DEFAULT_LOADING_LAYOUT_ID;
            this.emptyLayoutID = DEFAULT_EMPTY_LAYOUT_ID;
            this.errorLayoutID = DEFAULT_ERROR_LAYOUT_ID;
            // 默认布局图片
            this.emptyImgID = DEFAULT_EMPTY_IMG_ID;
            this.errorImgID = DEFAULT_ERROR_IMG_ID;
            // 设置默认点击点击view id
            this.emptyClickViewId = DEFAULT_EMPTY_CLICKED_ID;
            this.errorClickViewId = DEFAULT_ERROR_CLICKED_ID;
            // 设置默认点击按钮属性
            this.isEmptyClickViewVisible = true;
            this.emptyClickViewTextColor = contentLayout.getContext().getResources().getColor(DEFAULT_CLICKED_TEXT_COLOR);
            this.isErrorClickViewVisible = true;
            this.errorClickViewTextColor = contentLayout.getContext().getResources().getColor(DEFAULT_CLICKED_TEXT_COLOR);
            // 设置默认背景色
            this.defaultBackgroundColor = contentLayout.getContext().getResources().getColor(DEFAULT_BACKGROUND_COLOR);
        }

        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }

        /**
         * 设置点击事件监听器
         *
         * @param listener 点击事件监听器
         * @return 状态布局 Build 对象
         */
        public Builder setOnStatusChildClickListener(OnStatusChildClickListener listener) {
            this.onStatusChildClickListener = listener;
            return this;
        }
    }
}
