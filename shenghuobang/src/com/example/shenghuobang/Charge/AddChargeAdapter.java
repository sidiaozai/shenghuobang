package com.example.shenghuobang.Charge;
import java.util.List;

import com.example.shenghuobang.R;
import com.example.shenghuobang.R.drawable;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import sqliteDataBase.Bll.Charge;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddChargeAdapter extends BaseAdapter {
	private List<sqliteDataBase.Model.Charge> arrays = null;
	private Context mContext;
	private Button curDel_btn;
	private float x,ux;
	public AddChargeAdapter(Context mContext, List<sqliteDataBase.Model.Charge> arrays) {
		this.mContext = mContext;
		this.arrays = arrays;
	}
	
	public int getCount() {
		return this.arrays.size();
	}
	public Object getItem(int position) {
		return null;
	}
	public void setArrarys(List<sqliteDataBase.Model.Charge> arrays){		
		this.arrays = arrays;
	}
	public long getItemId(int position) {
		return position;
	}
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.view_list_view, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.lvContent);
			viewHolder.btnDel = (Button) view.findViewById(R.id.del);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		//为每一个view项设置触控监听
		view.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				final ViewHolder holder = (ViewHolder) v.getTag();
				//当按下时处理
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					//设置背景为选中状态
					v.setBackgroundResource(R.drawable.textview_press);
					//获取按下时的x轴坐标
					x = event.getX();
					//判断之前是否出现了删除按钮如果存在就隐藏
					if (curDel_btn != null) {
						curDel_btn.setVisibility(View.GONE);
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
					//设置背景为未选中正常状态
					v.setBackgroundResource(R.drawable.textview_norm);
					//获取松开时的x坐标
					ux = event.getX();
					//判断当前项中按钮控件不为空时
					if (holder.btnDel != null) {
						//按下和松开绝对值差当大于20时显示删除按钮，否则不显示
						if (Math.abs(x - ux) > 20) {
							holder.btnDel.setVisibility(View.VISIBLE);
							curDel_btn = holder.btnDel;
						}
					}
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {//当滑动时背景为选中状态
					v.setBackgroundResource(R.drawable.textview_press);
				} else {//其他模式
					//设置背景为未选中正常状态
					v.setBackgroundResource(R.drawable.textview_norm);
				}
				return true;
			}
		});
	
			sqliteDataBase.Model.Charge modelCharge = this.arrays.get(position);
			viewHolder.tvTitle.setText("id"+modelCharge.getId()+"金额："+modelCharge.getSum()+"    用途："+modelCharge.getDes());
		
		//为删除按钮添加监听事件，实现点击删除按钮时删除该项
		viewHolder.btnDel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(curDel_btn!=null)
					curDel_btn.setVisibility(View.GONE);
				sqliteDataBase.Model.Charge modelCharge = arrays.get(position);
				sqliteDataBase.Bll.Charge bllCharge = new Charge(mContext);
				
				bllCharge.delete(modelCharge.getId());
				
				arrays.remove(position);
				
				notifyDataSetChanged();
			}
		});
		return view;
	}
	final static class ViewHolder {
		TextView tvTitle;
		Button btnDel;
	}
}