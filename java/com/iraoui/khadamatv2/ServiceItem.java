package com.iraoui.khadamatv2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iraoui.khadamatv2.entities.Service;

import java.util.List;

public class ServiceItem extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;

    public ServiceItem(Activity context, List<Service> services) {
        super(context, R.layout.activity_service_item, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_service_item, null, true);


        TextView titre = (TextView) listViewItem.findViewById(R.id.Afftitre);
        TextView description = (TextView) listViewItem.findViewById(R.id.Affdescr);
        TextView prixx = (TextView) listViewItem.findViewById(R.id.Affprix);


        Service service = services.get(position);
        titre.setText(service.service);
        description.setText(service.description);
        prixx.setText(service.prix);

        return listViewItem;



    }
}
