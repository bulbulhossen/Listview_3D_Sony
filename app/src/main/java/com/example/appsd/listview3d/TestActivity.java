package com.example.appsd.listview3d;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import java.util.ArrayList;

/**
 * Test activity to display the list view
 */
public class TestActivity extends Activity {

    /** The list view */
    private MyListView mListView;

    /**
     * Small class that represents a contact
     */
    private static class Contact {

        /** Name of the contact */
        String mName;

        /** Phone number of the contact */
        String mNumber;

        /**
         * Constructor
         *
         * @param name The name
         * @param number The number
         */
        public Contact(final String name, final String number) {
            mName = name;
            mNumber = number;
        }

    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ArrayList<Contact> contacts = createContactList(20);
        final MyAdapter adapter = new MyAdapter(this, contacts);

        mListView = (MyListView)findViewById(R.id.my_list);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, final long id) {
                final String message = "OnClick: " + contacts.get(position).mName;
                Toast.makeText(TestActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                           final int position, final long id) {
                final String message = "OnLongClick: " + contacts.get(position).mName;
                Toast.makeText(TestActivity.this, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * Creates a list of fake contacts
     *
     * @param size How many contacts to create
     * @return A list of fake contacts
     */
    private ArrayList<Contact> createContactList(final int size) {
        final ArrayList<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < size; i++) {
            contacts.add(new Contact("Contact Number " + i, "+46(0)"
                    + (int)(1000000 + 9000000 * Math.random())));
        }
        return contacts;
    }

    /**
     * Adapter class to use for the list
     */
    private static class MyAdapter extends ArrayAdapter<Contact> {

        /** Re-usable contact image drawable */
        private final Drawable contactImage;

        /**
         * Constructor
         *
         * @param context The context
         * @param contacts The list of contacts
         */
        public MyAdapter(final Context context, final ArrayList<Contact> contacts) {
            super(context, 0, contacts);
            contactImage = context.getResources().getDrawable(R.drawable.contact_image);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
            }

            final TextView name = (TextView)view.findViewById(R.id.contact_name);
            if (position == 14) {
                name.setText("This is a long text that will make this box big. "
                        + "Really big. Bigger than all the other boxes. Biggest of them all.");
            } else {
                name.setText(getItem(position).mName);
            }

            final TextView number = (TextView)view.findViewById(R.id.contact_number);
            number.setText(getItem(position).mNumber);

            final ImageView photo = (ImageView)view.findViewById(R.id.contact_photo);
            photo.setImageDrawable(contactImage);

            return view;
        }
    }
}
