package com.example.wellnesstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ExerciseDetailsAdapter extends ArrayAdapter<Exercise> {

    private String selectedOptionStr;
    private Context mContext;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Exercise selectedExercise = new Exercise();
    private int mResource;

    public ExerciseDetailsAdapter(@NonNull Context context, int resource, String selectedOption ,@NonNull ArrayList<Exercise> objects) {
        super(context, resource,objects);
        this.selectedOptionStr = selectedOption;
        mContext = context;
        mResource = resource;
        exercises = objects;
    }

    public ExerciseDetailsAdapter(@NonNull Context context, int resource, Exercise exercise) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        selectedExercise=exercise;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            for (int i = 0; i < exercises.size(); i++) {
                if ((exercises.get(i).getName().compareTo(selectedOptionStr)) == 0) {
                    selectedExercise.setName(exercises.get(i).getName());
                    selectedExercise.setDescription(exercises.get(i).getDescription());
                    selectedExercise.setCategory(exercises.get(i).getCategory());
                    selectedExercise.setEquipment(exercises.get(i).getEquipment());
                }

            }

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView tvName = convertView.findViewById(R.id.tv_name);
            TextView tvDescription = convertView.findViewById(R.id.tv_description);
            TextView tvCategory = convertView.findViewById(R.id.tv_category);
            TextView tvEquipment = convertView.findViewById(R.id.tv_equipment);

            tvName.setText(selectedExercise.getName());
            tvDescription.setText(selectedExercise.getDescription());
            tvCategory.setText(R.string.selectedExercise_category);
            tvCategory.append(selectedExercise.getCategory());
            tvEquipment.setText(R.string.selectedExercise_equipment);
            tvEquipment.append(selectedExercise.getEquipment());


            return convertView;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
