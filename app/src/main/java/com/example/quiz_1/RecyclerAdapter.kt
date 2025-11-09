package com.example.quiz_1

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

public class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.UserViewHolder>() {

    var usersList = listOf<UserData>();

    class UserViewHolder : RecyclerView.ViewHolder {
        var nameLabel: TextView;
        var emailLabel: TextView;
        var phoneLabel: TextView;

        var container: ConstraintLayout;
        public constructor(view: View) : super(view) {
            nameLabel = view.findViewById<TextView>(R.id.item_nameLabel);
            emailLabel = view.findViewById<TextView>(R.id.item_emailLabel);
            phoneLabel = view.findViewById<TextView>(R.id.item_phoneLabel);
            container = view.findViewById<ConstraintLayout>(R.id.itemContainer)

            container.setOnClickListener { v ->
                Toast.makeText(view.context, "You selected "+nameLabel.text.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.UserViewHolder {
        var itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false)
        return UserViewHolder(itemView);
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user = usersList.get(position)
        holder.nameLabel.setText(user.name);
        holder.emailLabel.setText("Email: "+user.email);
        holder.phoneLabel.setText("Phone: "+user.phone);

    }

    override fun getItemCount(): Int {
        return usersList.size;
    }


}