package android.bignerdranch.com.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kazakova_net on 27.01.2019.
 */
public class CrimeListFragment extends Fragment {

    private RecyclerView crimeRecyclerView;
    private CrimeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view);

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        adapter = new CrimeAdapter(crimes);
        crimeRecyclerView.setAdapter(adapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime crime;

        private TextView titleTextView;
        private TextView dateTextView;
        private ImageView solvedImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            titleTextView = itemView.findViewById(R.id.crime_title);
            dateTextView = itemView.findViewById(R.id.crime_date);
            solvedImageView = itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime) {
            this.crime = crime;
            titleTextView.setText(crime.getTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            dateTextView.setText(dateFormat.format(crime.getDate()));
            solvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimeActivity.newIntent(getActivity(), crime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> crimes;

        public CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = crimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }
}
// 223