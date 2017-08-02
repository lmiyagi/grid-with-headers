package br.com.leonardomiyagi.gridwithheaders;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.leonardomiyagi.gridwithheaders.databinding.ListItemHeaderBinding;
import br.com.leonardomiyagi.gridwithheaders.databinding.ListItemItemBinding;

/**
 * Created by SES\leonardom on 02/08/17.
 */

public class CustomAdapter extends RecyclerView.Adapter {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private List<SampleObjectListItem> items;

    public void setItems(List<SampleObject> sampleObjects) {
        List<SampleObjectListItem> items = new ArrayList<>();
        for (int i = 0; i < sampleObjects.size(); i++) {
            if (i == 0 || (sampleObjects.size() != 0 && !sampleObjects.get(i).getCategory().equals(sampleObjects.get(i - 1).getCategory()))) {
                items.add(new SampleObjectListItem(sampleObjects.get(i), true));
            }
            items.add(new SampleObjectListItem(sampleObjects.get(i), false));
        }
        this.items = items;
        notifyDataSetChanged();
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
            ((HeaderViewHolder) holder).setObject(items.get(position).getSampleObject());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).setObject(items.get(position).getSampleObject());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isHeader) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
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
