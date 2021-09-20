package com.example.myfoodpanda.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodpanda.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_show_Adpter extends RecyclerView.Adapter<Product_show_Adpter.MyviewHolder> {
    private Context context;
    private List<ProductShow_Model>modelList;
    private static int listposition=-1;
  private  static  onItemClickLisiner clickLisiner;
    public Product_show_Adpter(Context context, List<ProductShow_Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }


    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view=  layoutInflater.inflate(R.layout.restaurants_show,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {
       ProductShow_Model item=modelList.get(position);
        myviewHolder.textView_delivary_free.setText("Delivary Free:"+item.getDelivary_free());
        myviewHolder.textView_restudentName.setText("Restaurants:"+item.getRestaurants_name());

        Picasso.get().load(item.getRest_Image()).into(myviewHolder.imageView);
        setAnimiton(myviewHolder.itemView,position);
    }

    void setAnimiton(View viewAnimition,int position) {
        if (position > listposition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(2000);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }
    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class  MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
     private ImageView imageView;
     private TextView textView_restudentName,textView_delivary_free;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_delivary_free=(TextView)itemView.findViewById(R.id.delivary_free);
            textView_restudentName=(TextView)itemView.findViewById(R.id.restName);
            imageView=(ImageView)itemView.findViewById(R.id.rest_Image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getLayoutPosition();
            clickLisiner.OnClick_Lisiner(position);
        }
    }
   public interface  onItemClickLisiner{
        void OnClick_Lisiner(int position);
   }

    public  void setOnItemClick(onItemClickLisiner clickLisiner){
        this. clickLisiner=clickLisiner;
    }
}
