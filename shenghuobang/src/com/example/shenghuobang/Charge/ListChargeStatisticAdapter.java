package com.example.shenghuobang.Charge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sqliteDataBase.Bll.Charge;

import com.example.shenghuobang.CommonValue;
import com.example.shenghuobang.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * the adapter of the ListView
 * 
 * @author Fay {@link 1940125001@qq.com}
 */
public class ListChargeStatisticAdapter extends BaseAdapter {
	private String TAG = "ListMessageAdapter";
	private Context context = null;
	private Holder holder = null;
	private LayoutInflater inflater = null;

	// the last position clicked
	private int mLastPosition = -1;

	// check whether a touch action is finish
	private boolean loadFinish = false;

	// the position of click and move, start and end point
	private Point startPoint, endPoint;

	// the animation of removing the item
	private Animation animation = null;

	// the children item is common
	private final int TYPE_ITEM = 0;

	// the children item is searching
	private final int TYPE_SEARCH = 1;

	// the count of children item's type
	private final int TYPE_COUNT = TYPE_SEARCH + 1;

	// data container
	//private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private List<sqliteDataBase.Model.ChargeStatistic> list = null;
	public ListChargeStatisticAdapter(Context context) {
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.startPoint = new Point();
		this.endPoint = new Point();
		animation = AnimationUtils.loadAnimation(context, R.anim.push_out);
	}

	public void setData(List<sqliteDataBase.Model.ChargeStatistic> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void setDataTemp(List<sqliteDataBase.Model.ChargeStatistic> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void clearData() {
		list.clear();
		notifyDataSetChanged();
	}

	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return position == 0 ? TYPE_SEARCH : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if (null == convertView) {
			holder = new Holder();
			
			convertView = inflater
					.inflate(R.layout.list_item_message, null);
			holder.linearlayout = (LinearLayout) convertView
					.findViewById(R.id.message_linear);
			holder.content = (TextView) convertView
					.findViewById(R.id.message_title);
			holder.delete = (TextView) convertView
					.findViewById(R.id.message_delete);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		final int chickPosition = position;
		
		sqliteDataBase.Model.ChargeStatistic modelChargeStatistic = this.list.get(position);
		
		holder.content.setText("支出："+CommonValue.myFormatter.format(modelChargeStatistic.getOutSum())+"\n"
				+ "收入："+CommonValue.myFormatter.format(modelChargeStatistic.getInSum())+"\n"
				+ "时间："+modelChargeStatistic.getDataStr());
		
		
		final int finalPosition = position;
		if (position == mLastPosition) {
			holder.delete.setVisibility(View.VISIBLE);
		} else {
			holder.delete.setVisibility(View.GONE);
		}

		// 鍒犻櫎
		final View view = holder.linearlayout;

		holder.delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				view.startAnimation(animation);
				animation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation arg0) {
					}

					@Override
					public void onAnimationRepeat(Animation arg0) {
					}

					@Override
					public void onAnimationEnd(Animation arg0) {
						
						mListChargeAdapterListening.deleteItem(chickPosition);
						list.remove(chickPosition);
						mLastPosition = -1;
						notifyDataSetChanged();
					}
				});
			}
		});

		holder.linearlayout.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				v.setBackgroundResource(R.drawable.textview_norm);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.textview_press);
					loadFinish = false;
					startPoint.set((int) event.getX(), (int) event.getY());
					break;
				case MotionEvent.ACTION_MOVE:
					v.setBackgroundResource(R.drawable.textview_press);
					endPoint.set((int) event.getX(), (int) event.getY());
					if (Math.abs(endPoint.x - startPoint.x) > 30) {
						if (loadFinish == false) {
							loadFinish = true;
							if (finalPosition != mLastPosition) {
								mLastPosition = finalPosition;
							} else {
								mLastPosition = -1;
							}
							notifyDataSetChanged();
						} else {
							return true;
						}
						return true;
					}
					if (Math.abs(endPoint.y - startPoint.y) > 30) {
						return false;
					}
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.drawable.textview_norm);
					break;
				}
				return false;
			}
		});

		holder.linearlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mLastPosition != -1) {
					mLastPosition = -1;
					notifyDataSetChanged();
				} else {
					sqliteDataBase.Model.ChargeStatistic modelChargeStatistic = list.get(chickPosition);
					//Toast.makeText(getApplicationContext(), "year", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.putExtra("isUpdate", true);
					intent.putExtra("year", modelChargeStatistic.getYear());
					intent.putExtra("month", modelChargeStatistic.getMonth());
					intent.putExtra("day", modelChargeStatistic.getDay());
			        intent.setClass(context, AddChargeActivity.class);
			        
			        context.startActivity(intent);
				}
			}
		});

		return convertView;
	}

	private class Holder {
		TextView content;
		TextView delete;
		LinearLayout linearlayout;
		LinearLayout searchview;
	}
	private ListChargeAdapterListening mListChargeAdapterListening = null;
	public void setListChargeAdapterListening(ListChargeAdapterListening l){
		mListChargeAdapterListening=l;
	}

}
