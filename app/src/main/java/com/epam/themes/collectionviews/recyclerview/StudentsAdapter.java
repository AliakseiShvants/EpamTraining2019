package com.epam.themes.collectionviews.recyclerview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.cleancodetest.R;
import com.epam.themes.backend.IWebService;
import com.epam.themes.backend.StudentsWebService;
import com.epam.themes.backend.entities.Student;
import com.epam.themes.uicomponents.StudentView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Student> students = new ArrayList<>();
    private final IWebService<Student> service = new StudentsWebService();
    private boolean isShowLastViewAsLoading = false;

    public StudentsAdapter(final Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                         @ViewType final int viewType) {
        if (viewType == ViewType.STUDENT) {
            return new ViewHolder(new StudentView(parent.getContext()));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.layout_progress, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        if (getItemViewType(position) == ViewType.STUDENT) {

            final Student student = students.get(position);

            ((StudentView) viewHolder.itemView)
                    .setStudentAvatar(student.getAvatarId())
                    .setStudentName(student.getName())
                    .setStudentHwCount(student.getHwCount());
        }
    }

    @ViewType
    @Override
    public int getItemViewType(final int position) {
        if (position < students.size()) {
            return ViewType.STUDENT;
        } else {
            return ViewType.LOADING;
        }
    }


    @Override
    public int getItemCount() {
//        if (isShowLastViewAsLoading) {
//            return students.size() + 1;
//        } else {
//            return students.size();
//        }
        return students.size();
    }

    public void setShowLastViewAsLoading(final boolean isShow) {
        if (isShow != isShowLastViewAsLoading) {
            isShowLastViewAsLoading = isShow;
            notifyDataSetChanged();
        }
    }

    public void addItems(final List<Student> result) {
        students.addAll(result);
        notifyDataSetChanged();
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(students, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(students, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    public void onItemDismiss(int adapterPosition) {
        deleteByIndex(adapterPosition);
    }

    public void deleteByIndex(int i) {
        students.remove(i);
        notifyItemRemoved(i);
    }

    public void updateItems(final List<Student> items) {
        students.clear();
        students.addAll(items);
        /*
           notifyDataSetChanged();
           notifyItemRangeChanged();
           notifyItemMoved();
         */
        DiffUtil.calculateDiff(new DiffUtil.Callback() {

            @Override
            public int getOldListSize() {
                return 0;
            }

            @Override
            public int getNewListSize() {
                return 0;
            }

            @Override
            public boolean areItemsTheSame(int i, int i1) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(int i, int i1) {
                return false;
            }
        }).dispatchUpdatesTo(this);
    }

    @IntDef({ViewType.STUDENT, ViewType.LOADING})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {

        int STUDENT = 0;
        int LOADING = 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(final View view) {
            super(view);
        }
    }

    public void addStudent(final Student student) {
        service.addEntity(student);
        students.add(student);
        notifyDataSetChanged();
    }
}
