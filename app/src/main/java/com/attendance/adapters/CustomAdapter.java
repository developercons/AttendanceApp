package com.attendance.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.attendance.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
	private Context context;
	private final ArrayList<String> srcList;
	private final ArrayList<String> srcListAll;
	private final ArrayList<String> srcListSuggestion;

	public CustomAdapter(Context context, ArrayList<String> srcList) {
		super(context, 0, srcList);
		this.context = context;
		this.srcList = new ArrayList<>(srcList);
		this.srcListAll = new ArrayList<>(srcList);
		this.srcListSuggestion = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return srcList.size();
	}

	@Override
	public String getItem(int position) {
		return srcList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		TextView tv_name;
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		ViewHolder viewHolder;
		if ( convertView == null ) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.custom_dropdown_row, parent, false);
			viewHolder.tv_name = convertView.findViewById(R.id.textView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_name.setText(srcList.get(position));
		return convertView;
	}

	@NonNull
	@Override
	public Filter getFilter() {
		return new Filter() {

			@Override
			public CharSequence convertResultToString(Object resultValue) {
				return resultValue.toString();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				if ( constraint != null ) {
					srcListSuggestion.clear();
					for ( String name : srcListAll ) {
						if ( name.toLowerCase().startsWith(constraint.toString().toLowerCase()) ||
								name.toLowerCase().contains(constraint.toString().toLowerCase()) ) {
							srcListSuggestion.add(name);
						}
					}
					FilterResults filterResults = new FilterResults();
					filterResults.values = srcListSuggestion;
					filterResults.count = srcListSuggestion.size();
					return filterResults;
				} else {
					return new FilterResults();
				}
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				srcList.clear();
				if ( results != null && results.count > 0 ) {
					// avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
					List<String> result = (List<String> ) results.values;
					srcList.addAll(result);

//					for (int i = 0; i < result.size(); i++) {
//						if(i <= result.size()) {
//							if (result.get(i) instanceof String) {
//								srcList.add(result.get(i).toString());
//							}
//						}
//					}
//                    }
				} else if (constraint == null) {
					// no filter, add entire original list back in
					srcList.addAll(srcListAll);
				}
				notifyDataSetChanged();
			}
		};
	}
}
