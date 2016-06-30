package com.boredream.borecontact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boredream.borecontact.PinnedSectionListView.PinnedSectionListAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter implements PinnedSectionListAdapter{
	
	private static final int VIEW_TYPE_CONTACT = 0;
	private static final int VIEW_TYPE_LETTER = 1;

	private Context context;
	private List<ContactBean> contacts;
	
	private List<Object> datas;
	private Map<String, Integer> letterPosition;
	
	private void initList() {
		datas = new ArrayList<Object>();
		letterPosition = new HashMap<String, Integer>();
		Collections.sort(contacts, new Comparator<ContactBean>() {

			@Override
			public int compare(ContactBean lhs, ContactBean rhs) {
				String lhsName = PinYinUtils.trans2PinYin(lhs.getName()).toUpperCase();
				String rhsName = PinYinUtils.trans2PinYin(rhs.getName()).toUpperCase();
				return lhsName.compareTo(rhsName);
			}
		});
		
		for(int i=0; i<contacts.size(); i++) {
			ContactBean contact = contacts.get(i);
			
			String firstLetter = getFirstLetter(contact.getName());
			if(!letterPosition.containsKey(firstLetter)) {
				letterPosition.put(firstLetter, datas.size());
				datas.add(firstLetter);
			}
			
			datas.add(contact);
		}
		
	}
	
	private String getFirstLetter(String name) {
		String firstLetter = "";
		char c = PinYinUtils.trans2PinYin(name).toUpperCase().charAt(0);
		if(c >= 'A' && c <= 'Z') {
			firstLetter = String.valueOf(c);
		}
		return firstLetter;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		return datas.get(position) instanceof ContactBean ? 
				VIEW_TYPE_CONTACT : VIEW_TYPE_LETTER;
	}
	
	public int getLetterPosition(String letter) {
		Integer positoin = letterPosition.get(letter);
		return positoin == null ? -1 : positoin;
	}
	
	public void updateList() {
		initList();
		notifyDataSetChanged();
	}
	
	public ContactAdapter(Context context, List<ContactBean> contacts) {
		this.context = context;
		this.contacts = contacts;
		initList();
	}
	
	@Override 
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int itemViewType = getItemViewType(position);
		return itemViewType == VIEW_TYPE_CONTACT ? 
				getContactView(position, convertView) : getLetterView(position, convertView);
	}

	private View getContactView(int position, View convertView) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_contact, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ContactBean contact = (ContactBean) getItem(position);
		
		holder.tv_name.setText(contact.getName());
		holder.tv_phone.setText(contact.getPhone());
		
		return convertView;
	}
	
	private View getLetterView(int position, View convertView) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_letter, null);
			holder.tv_letter = (TextView) convertView.findViewById(R.id.tv_letter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String letter = (String) getItem(position);
		
		holder.tv_letter.setText(letter);
		
		return convertView;
	}
	
	
	static class ViewHolder {
		TextView tv_letter;
		TextView tv_name;
		TextView tv_phone;
	}


	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == VIEW_TYPE_LETTER;
	}

}
