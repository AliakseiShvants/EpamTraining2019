package com.epam.themes.backend;

import android.os.Handler;
import android.os.Looper;

import com.epam.cleancodetest.R;
import com.epam.themes.backend.entities.Student;
import com.epam.themes.util.ICallback;

import java.util.ArrayList;
import java.util.List;

public class StudentsWebService implements IWebService<Student> {

    private List<Student> students = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    {
        students.add(new Student(1, R.drawable.icon1, "Aliaksei Sh", 6));
        students.add(new Student(2, R.drawable.icon2, "Maryia", 5));
        students.add(new Student(3, R.drawable.icon3, "Pavel", 5));
        students.add(new Student(4, R.drawable.icon4, "Yahor Sh", 4));
        students.add(new Student(5, R.drawable.icon5, "Anton", 4));
        students.add(new Student(6, R.drawable.icon6, "Yahor B", 4));
        students.add(new Student(7, R.drawable.icon1, "Maksim Zh", 4));
        students.add(new Student(8, R.drawable.icon2, "Uladislau", 5));
        students.add(new Student(9, R.drawable.icon3, "Alexander", 5));
        students.add(new Student(10, R.drawable.icon4, "Maksim N", 6));
        students.add(new Student(11, R.drawable.icon5, "Vitali", 4));
        students.add(new Student(12, R.drawable.icon6, "Aliaksandr", 6));
        students.add(new Student(13, R.drawable.icon1, "Kiryl", 5));
        students.add(new Student(14, R.drawable.icon2, "Aleksei", 5));
        students.add(new Student(15, R.drawable.icon3, "Natallia", 4));
        students.add(new Student(16, R.drawable.icon4, "Aliaksei H", 5));
        students.add(new Student(17, R.drawable.icon5, "Maksim S", 6));
        students.add(new Student(18, R.drawable.icon6, "Vladyslav", 5));

//        for (int i = 0; i < 19; i++) {
//            Student student = new Student();
//            student.setId((long) i);
//            student.setHwCount(1 + mRandom.nextInt(5));
//            student.setName(String.valueOf(i));
//            students.add(student);
//        }
    }

    @Override
    public void getEntities(final ICallback<List<Student>> callback) {
    }

    @Override
    public void getEntities(final int startRange,
                            final int endRange,
                            final ICallback<List<Student>> callback) {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                callback.onResult(students.subList(startRange, endRange));
            }
        }, 2000);
    }

    @Override
    public void removeEntity(final Long id) {
        Student removedStudent = null;

        for (final Student student : students) {
            if (student.getId().equals(id)) {
                removedStudent = student;
            }
        }

        students.remove(removedStudent);
    }
}
