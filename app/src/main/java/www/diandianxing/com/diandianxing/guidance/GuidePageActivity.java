package www.diandianxing.com.diandianxing.guidance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.zackratos.ultimatebar.UltimateBar;

import www.diandianxing.com.diandianxing.Login.LoginActivity;
import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class GuidePageActivity extends BaseActivity {
    private ViewPager vp;
    private GuidePagerAdapter mGuidePagerAdapter;
    private int[] imgurls = {R.drawable.guid1,R.drawable.guide2,R.drawable.guide3};

    private LinearLayout liner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

        setContentView(R.layout.activity_guidepage);
        vp = (ViewPager) findViewById(R.id.vp);
        liner = (LinearLayout) findViewById(R.id.liner);
        if (mGuidePagerAdapter == null) {
            mGuidePagerAdapter = new GuidePagerAdapter();
        }

        vp.setAdapter(mGuidePagerAdapter);
      //  vp.setOnPageChangeListener(new ViewPagerIndicator(this,vp,liner,5));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class GuidePagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(GuidePageActivity.this, R.layout.item_guide, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_guide);
            View tv_into = view.findViewById(R.id.tv_into);
            if (position ==2) {
                tv_into.setVisibility(View.VISIBLE);
            } else {
                tv_into.setVisibility(View.GONE);
            }
            tv_into.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                       存boolean值获取状态
                     */

                    SpUtils.putBoolean(GuidePageActivity.this,"sousou",false);
                    Intent intent = new Intent(GuidePageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            imageView.setImageResource(imgurls[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
