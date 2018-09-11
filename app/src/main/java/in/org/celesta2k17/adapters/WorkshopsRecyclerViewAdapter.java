package in.org.celesta2k17.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.org.celesta2k17.R;
import in.org.celesta2k17.data.lAndwData;

public class WorkshopsRecyclerViewAdapter extends RecyclerView.Adapter<WorkshopsRecyclerViewAdapter.WorkshopsViewHolder> {
    private final ListCardClick mOnClickListener;
    ArrayList<lAndwData> dataList = new ArrayList<>();
    String eventHeader[];
    String eventDate[];
    String eventTime[];
    String eventVenue[];
    String eventIntro[];
    String eventDescription[];
    String intent[];
    String eventTopic[];
    TypedArray images;
    Context context;

    public WorkshopsRecyclerViewAdapter(Context context, ListCardClick listCardClick, String eventTopic[] , String eventHeader[], String eventDate[], String eventTime[], String eventVenue[], String eventIntro[], String eventDescription[] , String intent[], TypedArray img) {
        this.eventHeader = eventHeader;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventTime= eventTime;
        this.eventVenue= eventVenue;
        this.eventIntro= eventIntro;
        this.eventTopic= eventTopic;
        this.intent = intent;
        mOnClickListener = listCardClick;
        images = img;
        this.context = context;
    }

    @Override
    public WorkshopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean attachToParent = false;
        View view = layoutInflater.inflate(R.layout.card_view_lec, parent, attachToParent);
        WorkshopsViewHolder workshopViewHolder = new WorkshopsViewHolder(view);
        return workshopViewHolder;
    }

    @Override
    public void onBindViewHolder(WorkshopsViewHolder holder, int position) {
        lAndwData clubsData = new lAndwData(eventHeader[position],eventDate[position],eventTime[position],eventVenue[position],eventIntro[position],eventDescription[position],eventTopic[position],intent[position],images.getResourceId(position, -1));
        dataList.add(clubsData);
        holder.textViewHeader.setText(eventHeader[position]);
        holder.textViewDate.setText(eventDate[position]);
        holder.textViewVenue.setText(eventVenue[position]);
        holder.textViewTime.setText(eventTime[position]);
        holder.textViewTopic.setText(eventTopic[position]);
        holder.textViewIntro.setText(eventIntro[position]);
        holder.imageView.setImageResource(images.getResourceId(position, -1));
    }
    @Override
    public int getItemCount() {
        return eventHeader.length;
    }
    public interface ListCardClick {
        void onListClick(String intent) throws ClassNotFoundException;
    }
    class WorkshopsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewHeader;
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewVenue;
        TextView textViewTopic;
        TextView textViewIntro;
        ImageView imageView;

        public WorkshopsViewHolder(View itemView) {
            super(itemView);
            textViewHeader = (TextView) itemView.findViewById(R.id.card_header);
            textViewDate = (TextView) itemView.findViewById(R.id.card_text_date);
            textViewTime = (TextView) itemView.findViewById(R.id.card_text_time);
            textViewTopic = (TextView) itemView.findViewById(R.id.card_text_topic);
            textViewVenue = (TextView) itemView.findViewById(R.id.card_text_venue);
            textViewIntro= (TextView) itemView.findViewById(R.id.card_text_intro);
            imageView = (ImageView) itemView.findViewById(R.id.card_cardeventimage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            try {
                mOnClickListener.onListClick(dataList.get(position).intentClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
