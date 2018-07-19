package com.nullptr.one.article.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import com.nullptr.one.R;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailPresenter;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailView;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.comment.CommentActivity;
import com.nullptr.one.util.HtmlUtil;

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
    private WebView mWvContent;
    // private TextView mTvContent;
    private TextView mTvCopyright;
    private TextView mTvDate;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private FloatingActionButton mFabComment;
    private ArticleDetailPresenter mPresenter;

    public static void actionStart(Context context, String itemId) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("item_id", itemId);
        context.startActivity(intent);
    }

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

        mWvContent = findViewById(R.id.article_detail_wv_content);
        mWvContent.getSettings().setSupportZoom(false);//禁止缩放

        mTvCopyright = findViewById(R.id.article_detail_tv_copyright);
        mTvDate = findViewById(R.id.article_detail_tv_date);
        mSrlSwipeRefreshLayout = findViewById(R.id.article_detail_srl_swipe_refresh);
        mFabComment = findViewById(R.id.article_detail_fab_comment);
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getArticleDetail(mItemId);
            }
        });

        mFabComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(ArticleDetailActivity.this, mItemId, "ARTICLE");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
        mTvDate.setText(article.getDate());
        mWvContent.loadDataWithBaseURL(null,HtmlUtil.getNewContent(article.getContent())
                ,"text/html", "UTF-8",null);
    }

    @Override
    public void showError(final String errorMsg) {
        //网络出错的处理
        Toast.makeText(this, "网络出错，请检查网络设置", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mSrlSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSrlSwipeRefreshLayout.setRefreshing(false);
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
