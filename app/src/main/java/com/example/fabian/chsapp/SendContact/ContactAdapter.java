package com.example.fabian.chsapp.SendContact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fabian.chsapp.Contact;
import com.example.fabian.chsapp.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactAdapter extends BaseAdapter implements Filterable {
    private int resourceLayout;
    private Context mContext;
    private ArrayList<Contact> original_contacts;
    private ArrayList<Contact> checked_contacts;
    private ArrayList<Contact> filtered_contacts;

    public ContactAdapter(Context context, int resource, ArrayList<Contact> contacts) {
        this.resourceLayout = resource;
        this.mContext = context;
        this.original_contacts = contacts;
        this.checked_contacts = new ArrayList<>();
        this.filtered_contacts = contacts;
    }

    @Override
    public int getCount() {
        return filtered_contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return filtered_contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Contact contact = (Contact) getItem(position);

        if (contact != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);
            TextView tt2 = (TextView) v.findViewById(R.id.number);
            final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checked);
            if (tt1 != null) {
                tt1.setText(contact.getName());
            }
            if (tt2 != null) {
                tt2.setText(contact.getNumber());
            }

            checkBox.setChecked(filtered_contacts.get(position).isChecked());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked() == true) {
                        if (checked_contacts.size() == 20) {
                            Toast.makeText(view.getContext(), "Can't select more than 20 contacts.", Toast.LENGTH_SHORT).show();
                            checkBox.setChecked(false);
                        } else {
                            checked_contacts.add(filtered_contacts.get(position));
                        }
                    } else {
                        checked_contacts.remove(filtered_contacts.get(position));
                    }
                }
            });
        }
        return v;
    }

    public ArrayList<Contact> getCheckedContacts () {
        java.util.Collections.sort(checked_contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return this.checked_contacts;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filtered_contacts = (ArrayList<Contact>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<Contact> filteredArrList = new ArrayList<Contact>();

                 //If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 //else does the Filtering and returns FilteredArrList(Filtered)

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = original_contacts.size();
                    results.values = original_contacts;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (Contact contact : original_contacts) {
                        if (contact.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredArrList.add(contact);
                        }
                    }
                    // set the Filtered result to return
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
    }
}
