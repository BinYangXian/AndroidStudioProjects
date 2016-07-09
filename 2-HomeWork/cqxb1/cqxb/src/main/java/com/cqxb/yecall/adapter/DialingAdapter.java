package com.cqxb.yecall.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.linphone.DialerFragment;

import android.content.Context;
import android.content.Intent;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxb.yecall.DetailDataActivity;
import com.cqxb.yecall.HistoryDetailActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.t9search.util.ViewUtil;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

public class DialingAdapter extends BaseAdapter{

	private Context mContext;
	private List<Contacts> cList;
	
	public DialingAdapter(Context context,List<Contacts> list){
		mContext=context;
		cList=list;
	}
	
	
	@Override
	public int getCount() {
		return cList.size();
	}

	@Override
	public Object getItem(int position) {
		return cList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Contacts contactBean = cList.get(position);
		convertView=LayoutInflater.from(mContext).inflate(R.layout.call_phone_recored,null);
		//来电号码
		TextView callNumber=(TextView)convertView.findViewById(R.id.cpr_callNumber);
		if(TextUtils.isEmpty(contactBean.getContactName())){
			callNumber.setText(contactBean.getNumber());
		}else {
			callNumber.setText(contactBean.getContactName());
		}
		//电话明细
		TextView callContent=(TextView)convertView.findViewById(R.id.cpr_callContext);
		//来电类型
		ImageView callType=(ImageView)convertView.findViewById(R.id.cpr_callType);
		ImageView cpr_contactImg=(ImageView)convertView.findViewById(R.id.cpr_contactImg);
		ImageView cpr_contactDetail=(ImageView)convertView.findViewById(R.id.cpr_contactDetail);
		TextView cpr_number=(TextView)convertView.findViewById(R.id.cpr_number);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String actCallNumber = BaseUntil.stringNoNull(contactBean.getNumber()).trim(); 
		String callTime  = BaseUntil.stringNoNull(contactBean.getStamp()).trim(); 
		if(!callTime.equals("")){
			callTime = sdf.format(new Date(Long.parseLong(callTime)));
			callTime = BaseUntil.formatDateTime(callTime);
		}
		if(actCallNumber.indexOf("sip:")!=-1){
			actCallNumber = actCallNumber.replaceFirst("sip:", "");
			if(actCallNumber.indexOf("@")!=-1){
				actCallNumber = actCallNumber.substring(0,actCallNumber.indexOf("@"));
			}
		}
		cpr_number.setText(actCallNumber.trim());
		if (contactBean.getType() == CallLog.Calls.INCOMING_TYPE) {
			//"呼入";
			callType.setBackgroundResource(R.drawable.icon_incoming_call);
			callContent.setText("  "+callTime);
		} else if (contactBean.getType() == CallLog.Calls.OUTGOING_TYPE) {
			//"呼出";
			callType.setBackgroundResource(R.drawable.icon_outgoing_call);
			callContent.setText("  "+callTime);
		} else if (contactBean.getType() == CallLog.Calls.MISSED_TYPE) {
			// "未接";
			callType.setBackgroundResource(R.drawable.icon_missed_call);
			callContent.setText("  "+callTime);
		}else if (contactBean.getType() == 4){
			//"其他" 联系人
			cpr_number.setVisibility(View.GONE);
			cpr_contactImg.setBackgroundResource(R.drawable.people);
			callNumber.setText(""+contactBean.getContactName());
			callType.setVisibility(View.GONE);
			callContent.setText(""+contactBean.getNumber());
		}
		
				//show the first alphabet of name
				//show name and phone number
				if(contactBean.getSearchByType()!=null){
					switch (contactBean.getSearchByType()) {
					case SearchByNull:
						ViewUtil.showTextNormal(callNumber, contactBean.getName());
						if(false==contactBean.isBelongMultipleContactsPhone()){
							ViewUtil.showTextNormal(callContent, contactBean.getPhoneNumber());
						}else{
							if(true==contactBean.isFirstMultipleContacts()){
								if(true==contactBean.getNextContacts().isHideMultipleContacts()){
									ViewUtil.showTextNormal(callContent, contactBean.getPhoneNumber()+mContext.getString(R.string.phone_number_count, Contacts.getMultipleNumbersContactsCount(contactBean)+1));
								}else{
									ViewUtil.showTextNormal(callContent, contactBean.getPhoneNumber()+"("+mContext.getString(R.string.click_to_hide)+")");
								}
							}else{
								if(false==contactBean.isHideMultipleContacts()){
								}else{
								}
								ViewUtil.showTextNormal(callContent, contactBean.getPhoneNumber());
							}
						}
						break;
					case SearchByPhoneNumber:
						ViewUtil.showTextNormal(callNumber, contactBean.getName());
						ViewUtil.showTextHighlight(callContent, contactBean.getPhoneNumber(), contactBean.getMatchKeywords().toString());
						break;
					case SearchByName:
						ViewUtil.showTextHighlight(callNumber, contactBean.getName(), contactBean.getMatchKeywords().toString());
						ViewUtil.showTextNormal(callContent, contactBean.getPhoneNumber());
						break;
					default:
						break;
					}		
				}
//				else{
//					callContent.setText(contactBean.getTelephoneNumber());
//				}		
		
		
		cpr_contactDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")){
					DialerFragment.instance().justLogin(mContext);
				}else{
	//				Intent intent = new Intent(mContext, DetailDataActivity.class);
	//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//				intent.putExtra("detail", contactBean.getContactName()+","+contactBean.getNumber());
	//				mContext.startActivity(intent);
					Intent intent = new Intent(mContext, HistoryDetailActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("contactName", contactBean.getContactName());
					intent.putExtra("number", contactBean.getNumber());
					intent.putExtra("beginTime", contactBean.getStamp());
					intent.putExtra("callType", String.valueOf(contactBean.getType()));
					intent.putExtra("recordFile", contactBean.getRecordFile());
					intent.putExtra("photoFile", contactBean.getPhotoFile());
					intent.putExtra("duration", contactBean.getDuration());
					mContext.startActivity(intent);
				}
			}
		});
		return convertView;
	}

	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<Contacts> list){
		this.cList = list;
		notifyDataSetChanged();
	}
}
