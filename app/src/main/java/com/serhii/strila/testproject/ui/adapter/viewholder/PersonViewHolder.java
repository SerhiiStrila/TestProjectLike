package com.serhii.strila.testproject.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.model.Person;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Serhii Strila on 1/26/16
 */
public class PersonViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.ibtn_dislike)
    ImageButton dislike;
    @Bind(R.id.ibtn_like)
    ImageButton like;
    @Bind(R.id.img_photo)
    ImageView photo;
    @Bind(R.id.img_like)
    ImageView heart;

    public PersonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Person person) {
        Picasso.with(itemView.getContext())
                .load(person.getPhoto())
                .placeholder(R.drawable.ic_person)
                .into(photo);
        heart.setVisibility(
                Person.Status.valueOf(person.getStatus().toUpperCase()) == Person.Status.LIKE
                        ? View.VISIBLE
                        : View.INVISIBLE);
    }
}
