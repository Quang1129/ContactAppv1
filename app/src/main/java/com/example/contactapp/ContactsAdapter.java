package com.example.contactapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


// making adapter
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> implements Filterable {

    private ArrayList<Contact> contactList;
    private ArrayList<Contact> contactListOld;
    public ContactsAdapter(ArrayList<Contact> contactList) {
        this.contactList = contactList;
        this.contactListOld = contactList;

    }
    // create string arraylist of contact list


    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.tvName.setText(contactList.get(position).getName());
        holder.tvPhone.setText(contactList.get(position).getMobile());
        holder.tvEmail.setText(contactList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public TextView tvPhone;
        public TextView tvEmail;

        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvPhone = (TextView) view.findViewById(R.id.tv_phone_number);
            tvEmail = (TextView) view.findViewById(R.id.tv_email);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    contactList = contactListOld;
                } else {
                    ArrayList<Contact> resultList = new ArrayList<Contact>();

                    for (Contact contact : contactListOld) {
                        if (contact.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            resultList.add(contact);
                        }
                    }
                    contactList = resultList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = contactList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactList = (ArrayList<Contact>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
