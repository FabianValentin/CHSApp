package com.example.fabian.chsapp.SendContact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.fabian.chsapp.Contact;
import com.example.fabian.chsapp.MainActivity;
import com.example.fabian.chsapp.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private int resourceLayout;
    private Context mContext;
    private List<Contact> contacts;
    private ArrayList<Contact> checked_contacts;

    public ContactAdapter(Context context, int resource, List<Contact> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.contacts = items;
        this.checked_contacts = new ArrayList<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Contact contact = getItem(position);

        if (contact != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);
            TextView tt2 = (TextView) v.findViewById(R.id.number);
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checked);
            if (tt1 != null) {
                tt1.setText(contact.getName());
            }
            if (tt2 != null) {
                tt2.setText(contact.getNumber());
            }

            checkBox.setChecked(contacts.get(position).isChecked());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checked_contacts.add(contacts.get(position));
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
}
