package br.com.leonardomiyagi.gridwithheaders;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import br.com.leonardomiyagi.gridwithheaders.databinding.ListItemHeaderBinding;
import br.com.leonardomiyagi.gridwithheaders.databinding.ListItemItemBinding;

/**
 * Created by SES\leonardom on 02/08/17.
 */

public class CustomAdapter extends RecyclerView.Adapter implements Filterable {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private List<SampleObjectListItem> items;
    private List<SampleObjectListItem> filteredItems;
    private int lastPosition = 0;

    public CustomAdapter(List<SampleObject> items) {
        this.items = handleItems(items);
        filteredItems = this.items;
    }

    public List<SampleObjectListItem> handleItems(List<SampleObject> sampleObjects) {
        List<SampleObjectListItem> items = new ArrayList<>();
        for (int i = 0; i < sampleObjects.size(); i++) {
            if (i == 0 || (sampleObjects.size() != 0 && !sampleObjects.get(i).getCategory().equals(sampleObjects.get(i - 1).getCategory()))) {
                items.add(new SampleObjectListItem(sampleObjects.get(i), true));
            }
            items.add(new SampleObjectListItem(sampleObjects.get(i), false));
        }
        return items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder((ListItemHeaderBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_header, parent, false));
        } else {
            return new ItemViewHolder((ListItemItemBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).setObject(filteredItems.get(position).getSampleObject());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).setObject(filteredItems.get(position).getSampleObject());
        }
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isHeader) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (TextUtils.isEmpty(charString.trim())) {
                    setFilteredItems(items);
                } else {
                    List<SampleObject> filteredList = new ArrayList<>();
                    for (SampleObjectListItem item : items) {
                        if (item.isHeader) {
                            continue;
                        }
                        if (String.valueOf(item.getSampleObject().getId()).contains(charString) || item.getSampleObject().getCategory().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(item.getSampleObject());
                        }
                    }
                    setFilteredItems(handleItems(filteredList));
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredItems = (List<SampleObjectListItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setFilteredItems(List<SampleObjectListItem> filteredItems) {
        this.filteredItems = filteredItems;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private ListItemHeaderBinding binding;

        public HeaderViewHolder(ListItemHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setObject(SampleObject sampleObject) {
            binding.setSampleObject(sampleObject);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ListItemItemBinding binding;

        public ItemViewHolder(ListItemItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setObject(SampleObject sampleObject) {
            binding.setSampleObject(sampleObject);
        }
    }

    class SampleObjectListItem {
        private SampleObject sampleObject;
        private boolean isHeader;

        public SampleObjectListItem(SampleObject sampleObject, boolean isHeader) {
            this.sampleObject = sampleObject;
            this.isHeader = isHeader;
        }

        public SampleObject getSampleObject() {
            return sampleObject;
        }

        public void setSampleObject(SampleObject sampleObject) {
            this.sampleObject = sampleObject;
        }

        public boolean isHeader() {
            return isHeader;
        }

        public void setHeader(boolean header) {
            isHeader = header;
        }
    }
}
