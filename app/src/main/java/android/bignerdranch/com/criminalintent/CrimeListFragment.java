package android.bignerdranch.com.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView crimeRecyclerView;
    private CrimeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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


    private class CrimeHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView dateTextView;
        private Crime crime;

        CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            // Извлечение представлений
            titleTextView = itemView.findViewById(R.id.crime_title);
            dateTextView = itemView.findViewById(R.id.crime_date);
        }

        /*
        Метод вызывается каждый раз, когда в CrimeHolder должен отображаться новый объект Crime
         */
        private void bind(Crime crime) {
            this.crime = crime;
            titleTextView.setText(crime.getTitle());
            dateTextView.setText(crime.getDate().toString());
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
            Crime crime=crimes.get(position);
            holder.bind(crime);
        }

        // Этот метод вызывается на первом шаге диалога RecyclerView-Adapter
        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }
}
