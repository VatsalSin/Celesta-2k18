package in.org.celesta2k17.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import in.org.celesta2k17.R;
import in.org.celesta2k17.activities.Faq_answer;

public class FaqRecyclerAdapter extends RecyclerView.Adapter<FaqRecyclerAdapter.FaqViewHolder>{

    private List<Faq_answer> Faq;
    private LayoutInflater mInflater;
    public ItemClickListener mClickListener;
    Context context;
    public FaqRecyclerAdapter(Context context, List<Faq_answer> faq) {
        this.Faq = faq;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context  = parent.getContext();
        this.mInflater = LayoutInflater.from(context);

        View view = mInflater.inflate(R.layout.activity_faq, parent, false);
        return new FaqViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        Faq_answer faq = Faq.get(position);
        holder.question_textView.setText(faq.getQuestion());
        holder.answer_textView.setText(faq.getAnswer());
    }

    @Override
    public int getItemCount() {
        return Faq.size();
    }


    public class FaqViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView question_textView;
        TextView answer_textView;

        FaqViewHolder(View itemView) {
            super(itemView);
            question_textView = itemView.findViewById(R.id.question_textview);
            answer_textView = itemView.findViewById(R.id.answer_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

//    Faq_answer getItem(int id) {
//        return Faq.get(id);
//    }

    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


