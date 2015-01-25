package com.guozg.wheelview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.guozg.wheelview.views.ArrayWheelAdapter;
import com.guozg.wheelview.views.OnWheelChangedListener;
import com.guozg.wheelview.views.WheelView;

public class MainActivity extends Activity {
    public String category1[] = new String[] { "   餐饮   ", "  交通  ", "  购物  ", "  娱乐  ", "  医疗  ", "  教育  ", "  居家  ",
            "  投资  ", "  人情  " };

    public String category2[][] = new String[][] {
            new String[] { "  早餐   ", " 午餐  ", " 晚餐  ", " 夜宵  ", "饮料水果", " 零食  ", "蔬菜原料", "油盐酱醋", "其他.." },
            new String[] { "地铁", "公交", "打的", "加油", "停车", "过路过桥", "罚款", "包养维修", "火车", "车款车贷", "车险", "航空", "船舶", "自行车",
                    "其他.." },
            new String[] { "服装鞋帽", "日用百货", "婴幼用品", "数码产品", "化妆护肤", "首饰", "烟酒", "电器", "家具", "书籍", "玩具", "摄影文印", "其他.." },
            new String[] { "看电影", "KTV", "网游电玩", "运动健身", "洗浴足浴", "茶酒咖啡", "旅游度假", "演出", "其他.." },
            new String[] { "求医", "买药", "体检", "化验", "医疗器材", "其他.." },
            new String[] { "培训", "考试", "书籍", "学杂费", "家教", "补习", "助学贷款", "其他.." },
            new String[] { "美容美发", "手机电话", "宽带", "房贷", "水电燃气", "物业", "住宿租房", "保险费", "贷款", "材料建材", "家政服务", "快递邮政",
                    "漏记款", "其他.." }, new String[] { "证券期货", "保险", "外汇", "出资", "黄金实物", "书画艺术", "投资贷款", "利息支出", "其他.." },
            new String[] { "礼金", "物品", "慈善捐款", "代付款", "其他.." }, };

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.main_btn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDialog(MainActivity.this, "消费类别", category1, category2);
            }
        });
    }

    private void showSelectDialog(Context context, String title, final String[] left, final String[][] right) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(title);
        LinearLayout llContent = new LinearLayout(context);
        llContent.setOrientation(LinearLayout.HORIZONTAL);
        final WheelView wheelLeft = new WheelView(context);
        wheelLeft.setVisibleItems(5);
        wheelLeft.setCyclic(false);
        wheelLeft.setAdapter(new ArrayWheelAdapter<String>(left));
        final WheelView wheelRight = new WheelView(context);
        wheelRight.setVisibleItems(5);
        wheelRight.setCyclic(true);
        wheelRight.setAdapter(new ArrayWheelAdapter<String>(right[0]));
        LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        paramsLeft.gravity = Gravity.LEFT;
        LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, (float) 0.6);
        paramsRight.gravity = Gravity.RIGHT;
        llContent.addView(wheelLeft, paramsLeft);
        llContent.addView(wheelRight, paramsRight);
        wheelLeft.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                wheelRight.setAdapter(new ArrayWheelAdapter<String>(right[newValue]));
                wheelRight.setCurrentItem(right[newValue].length / 2);
            }
        });
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int leftPosition = wheelLeft.getCurrentItem();
                String vLeft = left[leftPosition];
                String vRight = right[leftPosition][wheelRight.getCurrentItem()];
                btn.setText(vLeft + "-" + vRight);
                dialog.dismiss();
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(llContent);
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

}
