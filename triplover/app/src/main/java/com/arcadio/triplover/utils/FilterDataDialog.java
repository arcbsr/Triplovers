package com.arcadio.triplover.utils;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.R;
import com.arcadio.triplover.adapter.BasicAdapter;
import com.arcadio.triplover.models.FilterModule;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import it.mirko.rangeseekbar.OnRangeSeekBarListener;

public class FilterDataDialog {


    public interface FilterListener {
        void onApply(FilterModule filterModule);
    }

    public void showFilterUI(Context context, FilterModule filterModule, String[] airlines, FilterListener filterListener) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.layout_filter_items);
        it.mirko.rangeseekbar.RangeSeekBar rangeSeekBar = bottomSheetDialog.findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setStartProgress(filterModule.priceMin);
        rangeSeekBar.setEndProgress(filterModule.priceMax);
        rangeSeekBar.setMax(filterModule.priceHighest);
        rangeSeekBar.setMinDifference(100);

        View[] views = {bottomSheetDialog.findViewById(R.id.filter_tab_price),
                bottomSheetDialog.findViewById(R.id.filter_tab_stops),
                bottomSheetDialog.findViewById(R.id.filter_tab_airline),
                bottomSheetDialog.findViewById(R.id.filter_tab_time)};
        View[] layer = {bottomSheetDialog.findViewById(R.id.layer_price),
                bottomSheetDialog.findViewById(R.id.layer_stops),
                bottomSheetDialog.findViewById(R.id.layer_airline),
                bottomSheetDialog.findViewById(R.id.layer_time)};
        for (int j = 0; j < views.length; j++) {
            views[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < views.length; i++) {
                        if (views[i].getId() == view.getId()) {
                            view.setEnabled(false);
                            layer[i].setVisibility(View.VISIBLE);
                        } else {
                            views[i].setEnabled(true);
                            layer[i].setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
        KLog.w(filterModule.toString());

        TextView min = bottomSheetDialog.findViewById(R.id.filter_minprice);
        TextView max = bottomSheetDialog.findViewById(R.id.filter_maxprice);
        rangeSeekBar.setOnRangeSeekBarListener(new OnRangeSeekBarListener() {
            @Override
            public void onRangeValues(it.mirko.rangeseekbar.RangeSeekBar rangeSeekBar, int start, int end) {
                min.setText(start + "");
                max.setText(end + "");
                filterModule.priceMin = start;
                filterModule.priceMax = end;
            }
        });

        min.setText(filterModule.priceMin + "");
        max.setText(filterModule.priceMax + "");
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.flight_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(bottomSheetDialog.getContext()));
        if (airlines != null) {
            recyclerView.setAdapter(new BasicAdapter(new BasicAdapter.BasicListener() {
                @Override
                public int getItem() {
                    return airlines.length;
                }

                @Override
                public int getLayoutId() {
                    return R.layout.layout_item_check;
                }

                @Override
                public void onBindViewHolder(BasicAdapter.ViewHolder holder, int position) {
                    CheckBox checkBox = holder.itemView.findViewById(R.id.list_item_check);
                    checkBox.setText(airlines[position]);
                    if (filterModule.flightName.contains(airlines[position])) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (!b) {
                                filterModule.flightName.remove(airlines[position]);
                            } else {
                                filterModule.flightName.add(airlines[position]);
                            }
                        }
                    });
                }
            }));
        }
        views[0].performClick();
        bottomSheetDialog.findViewById(R.id.filter_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterListener.onApply(filterModule);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.findViewById(R.id.filter_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterListener.onApply(null);
                bottomSheetDialog.dismiss();
            }
        });
        CheckBox[] stops = {bottomSheetDialog.findViewById(R.id.filter_stop_non),
                bottomSheetDialog.findViewById(R.id.filter_stop_one),
                bottomSheetDialog.findViewById(R.id.filter_stop_more)};
        for (int i = 0; i < stops.length; i++) {
            stops[i].setChecked(filterModule.stops[i]);
        }
        for (CheckBox stop : stops) {
            stop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    for (int i = 0; i < stops.length; i++) {
                        if (stops[i].getId() == compoundButton.getId()) {
                            filterModule.stops[i] = b;
                        }
                    }
                }
            });
        }
        bottomSheetDialog.show();

    }
}
