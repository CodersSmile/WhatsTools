package com.whatstools.captionStatusShare;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.whatstools.R;

class CustomAdapter1 extends BaseAdapter {
    private Context context;
    private LayoutInflater inflter;
    private String[] items;

    CustomAdapter1(Context applicationContext, String[] items) {
        this.context = applicationContext;
        this.items = items;
        this.inflter = LayoutInflater.from(applicationContext);
    }

    public int getCount() {
        return this.items.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = this.inflter.inflate(R.layout.listview_item, null);
        ((TextView) view.findViewById(R.id.textView)).setText(this.items[i]);
        ImageView icon2 = view.findViewById(R.id.share_button);
        view.findViewById(R.id.copy_button).setOnClickListener(new OnClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onClick(View view) {
                String str = CustomAdapter1.this.items[i];
                ((ClipboardManager) CustomAdapter1.this.context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Copied Text", str));
                Toast.makeText(CustomAdapter1.this.context, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        icon2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = CustomAdapter1.this.items[i];
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.setPackage("com.whatsapp");
                intent.putExtra("android.intent.extra.TEXT", str);
                if (intent.resolveActivity(CustomAdapter1.this.context.getPackageManager()) != null) {
                    CustomAdapter1.this.context.startActivity(intent);
                }
            }
        });
        return view;
    }
}
