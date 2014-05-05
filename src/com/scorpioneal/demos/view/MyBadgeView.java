
package com.scorpioneal.demos.view;

import java.util.ArrayList;
import java.util.List;

import com.scorpioneal.demos.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ˼·�� ��¼���µ�λ�úͽ����Ĺ��ﳵ��λ����λ�ƶ��� BadgeView��ӡ�¿ؼ���һ��view��, ����΢����ʾ����
 */
public class MyBadgeView extends Activity
{
    private ListView mListView;// ��ƷlistView
    private Context mContext;
    private ProductAdapter productAdapter;
    private ImageView shopCart;// ���ﳵ
    private ViewGroup anim_mask_layout;// ������
    private ImageView buyImg;// �����ڽ������ܵ�СͼƬ
    private int buyNum = 0;// ��������
    private BadgeView buyNumView;// ��ʾ���������Ŀؼ�

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buycartoon_main);
        mContext = this;
        initView();
    }

    private void initView()
    {
        shopCart = (ImageView)findViewById(R.id.shopping_img_cart);
        // ��һ��view
        buyNumView = new BadgeView(mContext, shopCart);
        buyNumView.setTextColor(Color.WHITE);
        buyNumView.setBackgroundColor(Color.RED);
        buyNumView.setTextSize(12);
        mListView = (ListView)findViewById(R.id.product_list);
        productAdapter = new ProductAdapter(getData());
        mListView.setAdapter(productAdapter);
    }

    private List<String> getData()
    {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++)
        {
            list.add("����һ����Ʒ" + i);
        }
        return list;
    }

    private class ProductAdapter extends BaseAdapter
    {
        private LayoutInflater inflater;
        private List<String> dataLsit;

        public ProductAdapter(List<String> data)
        {
            inflater = LayoutInflater.from(mContext);
            dataLsit = data;
        }

        @Override
        public int getCount()
        {

            return dataLsit.size();
        }

        @Override
        public Object getItem(int position)
        {
            return dataLsit.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        /**
         * ʹ��ViewHolder�Ż�����
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            String name = dataLsit.get(position);
            ViewHolder holder = null;
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.product_item, null);
                holder = new ViewHolder();
                holder.nameTxt = (TextView)convertView.findViewById(R.id.name);
                holder.buyBtn = (Button)convertView.findViewById(R.id.product_buy_btn);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.nameTxt.setText(name);
            holder.buyBtn.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int[] start_location = new int[2];// һ���������飬�����洢��ť������Ļ��X��Y����
                    v.getLocationInWindow(start_location);// ���ǻ�ȡ����ť������Ļ��X��Y���꣨��Ҳ�Ƕ�����ʼ�����꣩
                    buyImg = new ImageView(mContext);// buyImg�Ƕ�����ͼƬ���ҵ���һ��С��R.drawable.sign��
                    buyImg.setImageResource(R.drawable.sign);// ����buyImg��ͼƬ
                    setAnim(buyImg, start_location);// ��ʼִ�ж���
                }
            });
            return convertView;
        }

        class ViewHolder
        {
            TextView nameTxt;
            Button buyBtn;
        }

    }

    /**
     * @Description: ����������
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout()
    {
        // ��ȡ���view
        ViewGroup rootView = (ViewGroup)this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                     LinearLayout.LayoutParams.MATCH_PARENT,
                                                                     LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vg, final View view, int[] location)
    {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                     LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                     LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private void setAnim(final View v, int[] start_location)
    {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);// �Ѷ���С����ӵ�������
        final View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
        int[] end_location = new int[2];// ���������洢��������λ�õ�X��Y����
        shopCart.getLocationInWindow(end_location);// shopCart���Ǹ����ﳵ

        // ����λ��
        int endX = 0 - start_location[0] + 40;// ����λ�Ƶ�X����
        int endY = end_location[1] - start_location[1];// ����λ�Ƶ�y����
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// �����ظ�ִ�еĴ���
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// �����ظ�ִ�еĴ���
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// ������ִ��ʱ��
        view.startAnimation(set);
        // ���������¼�
        set.setAnimationListener(new AnimationListener()
        {
            // �����Ŀ�ʼ
            @Override
            public void onAnimationStart(Animation animation)
            {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }

            // �����Ľ���
            @Override
            public void onAnimationEnd(Animation animation)
            {
                v.setVisibility(View.GONE);
                buyNum++;
                buyNumView.setText(buyNum + "");
                buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                buyNumView.show();
            }
        });

    }

}
