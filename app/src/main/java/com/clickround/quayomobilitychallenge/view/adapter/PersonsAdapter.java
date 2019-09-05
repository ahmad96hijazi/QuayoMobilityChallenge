package com.clickround.quayomobilitychallenge.view.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.clickround.quayomobilitychallenge.R;
import com.clickround.quayomobilitychallenge.data.local.room.model.Person;
import com.clickround.quayomobilitychallenge.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.ViewHolder> {

    private List<Person> persons;
    private LayoutInflater layoutInflater;
    private OnPersonListener listener;

    public PersonsAdapter(List<Person> persons, OnPersonListener listener) {
        this.persons = persons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View root = layoutInflater.inflate(R.layout.item_person, parent, false);
        return new ViewHolder(root, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drawable manDrawable = layoutInflater.getContext().getResources().getDrawable(R.drawable.ic_man);
        manDrawable.setBounds(0, 0, 60, 60);
        Drawable womenDrawable = layoutInflater.getContext().getResources().getDrawable(R.drawable.ic_women);
        womenDrawable.setBounds(0, 0, 60, 60);

        holder.bindItem(persons.get(position));
        holder.genderImageView.setImageDrawable(holder.getPerson().isGender() ? manDrawable : womenDrawable);
        holder.nameTextView.setText(String.format("%s %s",
                StringUtils.capitalize(holder.getPerson().getFirstName()),
                holder.getPerson().getLastName().toUpperCase()));
        holder.dobTextView.setText(DateUtil.getInstance().getAge(holder.getPerson().getDob(), DateUtil.getInstance().getTodayObject()));
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
        notifyDataSetChanged();
    }

    public interface OnPersonListener {
        void onClick(int position, Person person);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.card)
        CardView personCardView;
        @BindView(R.id.name)
        TextView nameTextView;
        @BindView(R.id.dob)
        TextView dobTextView;
        @BindView(R.id.gender)
        ImageView genderImageView;

        private Person person;
        private OnPersonListener listener;

        ViewHolder(View itemView, OnPersonListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            personCardView.setOnClickListener(this);
        }

        void bindItem(Person person) {
            this.person = person;
        }

        @Override
        public void onClick(View v) {
            Timber.e("item pressed of position %s", getAdapterPosition());
            listener.onClick(getAdapterPosition(), person);
        }

        Person getPerson() {
            return person;
        }
    }
}
