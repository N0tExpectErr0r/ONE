package com.nullptr.one.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.nullptr.one.presenter.articledetail.ArticleDetailPresenter;
import com.nullptr.one.presenter.articledetail.ArticleDetailPresenterImpl;
import com.nullptr.one.R;
import com.nullptr.one.bean.ArticleDetail;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 文章详细内容Activity
 */
public class ArticleDetailActivity extends BaseActivity implements ArticleDetailView {

    private String mItemId;
    private TextView mTvTitle;
    private TextView mTvAuthorName;
    private TextView mTvAuthorDesc;
    private TextView mTvContent;
    private TextView mTvCopyright;
    private TextView mTvDate;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ArticleDetailPresenter mPresenter;

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        mItemId = intent.getStringExtra("item_id");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_article);
        initToolbar("一个文章");

        mPresenter = new ArticleDetailPresenterImpl(this);
        mTvTitle = findViewById(R.id.article_detail_tv_title);
        mTvAuthorName = findViewById(R.id.article_detail_tv_author_name);
        mTvAuthorDesc = findViewById(R.id.article_detail_tv_author_desc);
        mTvContent = findViewById(R.id.article_detail_tv_content);
        mTvCopyright = findViewById(R.id.article_detail_tv_copyright);
        mTvDate = findViewById(R.id.article_detail_tv_date);
        mSrlSwipeRefreshLayout = findViewById(R.id.article_detail_srl_swipe_refresh);
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getArticleDetail(mItemId);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //菜单按钮,返回
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void loadData() {
        mPresenter.getArticleDetail(mItemId);
    }

    @Override
    public void setArticle(final ArticleDetail article) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //有时文章作者 作者描述 授权许可为空，此时隐藏掉它们比较好看
                if (article.getAuthorName().equals("")) {
                    mTvAuthorName.setVisibility(View.GONE);
                } else {
                    mTvAuthorName.setText(article.getAuthorName());
                }

                if (article.getAuthorDesc().equals("")) {
                    mTvAuthorDesc.setVisibility(View.GONE);
                } else {
                    mTvAuthorDesc.setText(article.getAuthorDesc());
                }

                if (article.getCopyright().equals("")) {
                    mTvCopyright.setVisibility(View.GONE);
                } else {
                    mTvCopyright.setText(article.getCopyright());
                }

                mTvTitle.setText(article.getTitle());
                mTvContent
                        .setText(Html.fromHtml(article.getContent()));  //通过Android自带的Html解析工具解析成文本
                mTvDate.setText(article.getDate());
            }
        });

    }

    @Override
    public void showError(final String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(
                        ArticleDetailActivity.this);
                errorDialog
                        .setTitle("错误")
                        .setMessage(errorMsg)
                        .show();
                //关闭App
                finish();
            }
        });
    }

    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSrlSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSrlSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private Toolbar initToolbar(CharSequence title) {
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        return toolbar;
    }
}
