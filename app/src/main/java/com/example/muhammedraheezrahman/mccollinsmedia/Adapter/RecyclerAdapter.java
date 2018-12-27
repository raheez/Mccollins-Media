package com.example.muhammedraheezrahman.mccollinsmedia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.mccollinsmedia.UI.DetailActivity;
import com.example.muhammedraheezrahman.mccollinsmedia.Model.Destination;
import com.example.muhammedraheezrahman.mccollinsmedia.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ViewHolder extends RecyclerView.ViewHolder{

    CircleImageView imageView;
    TextView titleTv,descTv,contactTv,distanceTv,timingDetailsTv;
    RelativeLayout contactRv;
    public ViewHolder(@NonNull final View itemView) {
        super(itemView);
        titleTv = (TextView) itemView.findViewById(R.id.titletv);
        descTv = (TextView) itemView.findViewById(R.id.descTv);
        contactTv = (TextView) itemView.findViewById(R.id.contactno);
        distanceTv = (TextView) itemView.findViewById(R.id.distanceTv);
        imageView = (CircleImageView) itemView.findViewById(R.id.imageicon);
        contactRv = (RelativeLayout) itemView.findViewById(R.id.contactrv);
        timingDetailsTv = (TextView) itemView.findViewById(R.id.timingDetails);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Destination.DestinationDetail detail = RecyclerAdapter.list.get(getAdapterPosition());

                String url = detail.getSitelink().toString();
                Bundle ePzl= new Bundle();
                ePzl.putString("key_url", url);
                Intent i = new Intent(v.getContext(),DetailActivity.class);
                i.putExtras(ePzl);
                v.getContext().startActivity(i);
            }
        });

        contactRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Destination.DestinationDetail detail = RecyclerAdapter.list.get(getAdapterPosition());

                String number = detail.getContact().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));

                v.getContext().startActivity(intent);
            }
        });
    }


}
public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder>  {

    static Context context;
    static List<Destination.DestinationDetail> list = new ArrayList<>();
    public RecyclerAdapter(Context context, List<Destination.DestinationDetail> list){

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Destination.DestinationDetail details = list.get(i);
        viewHolder.titleTv.setText(details.getTitle());
        viewHolder.contactTv.setText(details.getContact());
        viewHolder.descTv.setText(details.getDescription());
        Glide.with(context).load(details.getImage()).into(viewHolder.imageView);

        if (!details.getTiming().isEmpty()){
            viewHolder.timingDetailsTv.setText(details.getTiming());
        }


    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void addtolist(List<Destination.DestinationDetail> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
