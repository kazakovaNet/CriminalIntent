package android.bignerdranch.com.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView crimeRecyclerView;
    private CrimeAdapter adapter;
    private ImageView solvedImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        adapter = new CrimeAdapter(crimes);
        crimeRecyclerView.setAdapter(adapter);

    }


    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView dateTextView;
        private Crime crime;

        CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            // Для itemView — представления View всей строки —
            // CrimeHolder назначается получателем событий щелчка
            itemView.setOnClickListener(this);

            // Извлечение представлений
            titleTextView = itemView.findViewById(R.id.crime_title);
            dateTextView = itemView.findViewById(R.id.crime_date);
            solvedImageView = itemView.findViewById(R.id.crime_solved);
        }

        /*
        Метод вызывается каждый раз, когда в CrimeHolder должен отображаться новый объект Crime
         */
        private void bind(Crime crime) {
            this.crime = crime;
            titleTextView.setText(crime.getTitle());
            solvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);

            dateTextView.setText(DateFormat.format("EEEE, MMM dd, yyyy", crime.getDate()));
        }

        @Override
        public void onClick(View view) {
            // Когда пользователь щелкает на элементе списка преступлений,
            // на экране возникает новый экземпляр CrimeActivity,
            // который является хостом для экземпляра CrimeFragment
            // с подробной информацией о конкретном экземпляре Crime
            Intent intent = CrimeActivity.newIntent(getActivity(), crime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> crimes;

        CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        // Этот метод вызывается на втором шаге диалога RecyclerView-Adapter
        // для создания нового объекта ViewHolder
        // вместе с его «полезной нагрузкой»: отображаемым представлением
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater, parent);
        }

        // Этот метод вызывается на третьем шаге диалога RecyclerView-Adapter.
        // Адаптер получает данные модели для указанной позиции (position)
        // и связывает их с представлением View объекта ViewHolder (holder).
        // Чтобы выполнить связывание, адаптер заполняет View
        // в соответствии с данными из объекта модели
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = crimes.get(position);
            holder.bind(crime);
        }

        // Этот метод вызывается на первом шаге диалога RecyclerView-Adapter
        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }
}
