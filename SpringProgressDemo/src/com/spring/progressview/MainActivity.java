package com.spring.progressview;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/***
 * 设置界面
 * @author smz
 * 创建时间：2014-1-7上午10:51:21
 */
public class MainActivity extends Activity implements OnClickListener {

	private TextView textView;
	private SpringProgressView progressView;
	private Random random = new Random(System.currentTimeMillis());
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		textView = (TextView) findViewById(R.id.textview);
		progressView = (SpringProgressView) findViewById(R.id.spring_progress_view);
		progressView.setMaxCount(1000.0f);
		findViewById(R.id.click).setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		progressView.setCurrentCount(random.nextInt(1000));
		textView.setText("最大值："+progressView.getMaxCount()+"   当前值："+progressView.getCurrentCount());
	}
}
