package com.example.installed_apps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Apps_Adapter extends RecyclerView.Adapter<Apps_Adapter.ViewHolder>{

    private Context context;
    private List<String> list;

    public Apps_Adapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public Apps_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view2 = LayoutInflater.from(context).inflate(R.layout.cardview_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view2);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        ApkInfo apkInfoExtractor = new ApkInfo(context);
        final String ApplicationPackageName = (String) list.get(position);

        String ApplicationLabelName = apkInfoExtractor.GetAppName(ApplicationPackageName);
        Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);
        viewHolder.textView_App_Name.setText(ApplicationLabelName);
        viewHolder.textView_App_Package_Name.setText(ApplicationPackageName);
        viewHolder.imageView.setImageDrawable(drawable);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] colors = {"Open App", " App Info"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose Action")
                        .setItems(colors, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                                    if(intent != null){
                                        context.startActivity(intent);
                                    }else {
                                        Toast.makeText(context,ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                                if (which==1){
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + ApplicationPackageName));
                                    context.startActivity(intent);
                                }
                            }
                        });

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView imageView;
        public TextView textView_App_Name;
        public TextView textView_App_Package_Name;

        public ViewHolder (View view){
            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
            textView_App_Name = (TextView) view.findViewById(R.id.Apk_Name);
            textView_App_Package_Name = (TextView) view.findViewById(R.id.Apk_Package_Name);
        }
    }
}
