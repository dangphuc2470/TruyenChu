package com.example.truyenchu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class VerticalContentAdapter extends RecyclerView.Adapter<VerticalContentAdapter.ViewHolder>
{

    static ArrayList<Story> arr;

    public VerticalContentAdapter(ArrayList<Story> dataSet)
    {
        arr = dataSet;
    }

    public static void updateData(Story e)
    {
        arr.add(e);
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        private LinearLayout storyInfo;
        private ImageView storyImage;
        private LinearLayout info;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvAuthor;
        private TextView tvGenre;

        public ViewHolder(View view)
        {
            super(view);
            // Define click listener for the ViewHolder's View

            storyInfo = view.findViewById(R.id.item_vertical_content_storyInfo);
            storyImage = view.findViewById(R.id.item_vertical_content_storyImage);
            info = view.findViewById(R.id.item_vertical_content_info);
            tvName = view.findViewById(R.id.item_vertical_content_tvName);
            tvTime = view.findViewById(R.id.item_vertical_content_tvTime);
            tvAuthor = view.findViewById(R.id.item_vertical_content_tvAuthor);
            tvGenre = view.findViewById(R.id.item_vertical_content_tvGenre);
        }



        public ImageView getStoryImage()
        {
            return storyImage;
        }


        public TextView getTvName()
        {
            return tvName;
        }

        public TextView getTvTime()
        {
            return tvTime;
        }

        public TextView getTvAuthor()
        {
            return tvAuthor;
        }

        public TextView getTvGenre()
        {
            return tvGenre;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     * <p>
     * param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vertical_content_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Story story = arr.get(position);
        //viewHolder.getStoryImage().setImageDrawable(story.getImage());
        viewHolder.getTvName().setText(story.getName());
        viewHolder.getTvTime().setText(story.getTime());
        viewHolder.getTvAuthor().setText(story.getAuthor());
        viewHolder.getStoryImage();
        viewHolder.getTvGenre().setText(story.getGenres()[0]);


//        viewHolder.getTextViewRole().setText(employee.getRole());
//        viewHolder.getImageView().setImageResource(R.drawable.ic_manager);
//
//        if (employee.getName()!=null) {
//            viewHolder.getTextViewName().setText(employee.getName());
//        }
//        else viewHolder.getTextViewName().setText("");
//        // If this is a manager -> show icon manager. Otherwise, show Staff in tvPosition
//        if (employee.isManager())
//        {
//            viewHolder.getImageView().setVisibility(View.VISIBLE);
//            viewHolder.getTextViewRole().setVisibility(View.GONE);
//        }
//        else
//        {
//            viewHolder.getImageView().setVisibility(View.GONE);
//            viewHolder.getTextViewRole().setVisibility(View.VISIBLE);
//            viewHolder.getTextViewRole().setText("staff");
//        }
//
//        if (position%2==0)
//        {
//            viewHolder.getLinearLayoutParent().setBackgroundResource(R.color.white);
//        }
//        else
//        {
//            viewHolder.linearLayout.setBackgroundResource(R.color.light_blue);
//        }


        Log.i("ABC", "onBindViewHolder: " + position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return arr.size();
    }
}
