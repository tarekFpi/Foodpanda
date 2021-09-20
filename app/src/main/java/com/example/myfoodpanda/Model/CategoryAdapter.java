package com.example.myfoodpanda.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodpanda.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private List<Category_model>category_modelList;
  private onItemClickLisiner clickLisiner;
    private static int listposition=-1;
    public CategoryAdapter(Context context, List<Category_model> category_modelList) {
        this.context = context;
        this.category_modelList = category_modelList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
     View view= layoutInflater.inflate(R.layout.sub_menu,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
       Category_model item=category_modelList.get(position);

       myViewHolder.textView_productName.setText("Name:"+item.getProduct_Name());
        myViewHolder.textView_pdDeatils.setText("Details:"+item.getDeatils());
        myViewHolder.textView_price.setText("price:"+item.getSell_price());
      //myViewHolder.textView_submenu.setText("Menu:"+item.getSub_menu());
        Picasso.get().load(item.getImage()).into(myViewHolder.imageView_product);


/*  boolean isExpands=category_modelList.get(position).isExpandable();
        myViewHolder.relativeLayout_exandes.setVisibility(isExpands ? View.VISIBLE:View.GONE);

    myViewHolder.textView_submenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setExpandable(!item.isExpandable());
                notifyDataSetChanged();
            }
        });*/

        setAnimiton(myViewHolder.itemView,position);
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
        return category_modelList.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
     private TextView textView_submenu,textView_productName,textView_pdDeatils,textView_price;
      private ImageView imageView_product;
      private RelativeLayout relativeLayout_exandes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout_exandes=(RelativeLayout)itemView.findViewById(R.id.Expandable_id);
            imageView_product=(ImageView)itemView.findViewById(R.id.product_image_sub);
            textView_productName=(TextView)itemView.findViewById(R.id.product_name);

            textView_pdDeatils=(TextView)itemView.findViewById(R.id.product_details);
            textView_price=(TextView)itemView.findViewById(R.id.pd_sellprice);
           // textView_submenu=(TextView)itemView.findViewById(R.id.text_submenu_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            clickLisiner.OnClick_Lisiner(position);
        }
    }
    public interface  onItemClickLisiner{
        void OnClick_Lisiner(int position);
    }

    public  void setOnItemClick(onItemClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }

/*          <TextView
    android:id="@+id/text_submenu_id"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="18sp"
    android:text="sub Menu Name"
    android:textColor="#121211"/>*/
}

