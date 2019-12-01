package br.edu.devmedia.tccdepressionmvp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatMessage> {
    StorageReference storageRef;
    public MessageAdapter(Context context, int resource, List<ChatMessage> objects) {
        super(context, resource, objects);
        FirebaseStorage storage  = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }

        //ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.message_text);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.message_user);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.message_time);

        ChatMessage message = getItem(position);
        messageTextView.setText(message.getMessageText());
        authorTextView.setText(message.getMessageUser());
        timeTextView.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",message.getMessageTime()));




        return convertView;
    }
}
