package com.example.hw1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.hw1.Interfaces.BoardCallback;
import com.example.hw1.Interfaces.BoardCallback;
import com.example.hw1.Models.Board;
import com.example.hw1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
        private ArrayList<Board> scores;

        public BoardAdapter(ArrayList<Board> board) {
            this.scores = board;
        }

        private BoardCallback boardCallback;

        public void setBoardCallback(BoardCallback boardCallback) {
            this.boardCallback = boardCallback;
        }

        @NonNull
        @Override
        public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item, parent, false);
            BoardViewHolder boardViewHolder = new BoardViewHolder(view);
            return boardViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
            Board p = getItem(position);
            holder.board_BTN_place.setText(p.getPlace()+"");
            holder.board_LBL_name.setText(p.getName() + "");
            holder.board_LBL_score.setText(p.getScore()+ "");
        }

        @Override
        public int getItemCount() {
            return this.scores == null ? 0 : this.scores.size();
        }

        private Board getItem(int position) {
            return this.scores.get(position);
        }

        public class BoardViewHolder extends RecyclerView.ViewHolder {
            private MaterialButton board_BTN_place;
            private MaterialTextView board_LBL_name;
            private MaterialTextView board_LBL_score;


            public BoardViewHolder(@NonNull View itemView) {
                super(itemView);
                board_BTN_place = itemView.findViewById(R.id.board_BTN_place);
                board_LBL_name = itemView.findViewById(R.id.board_LBL_name);
                board_LBL_score = itemView.findViewById(R.id.board_LBL_score);
                itemView.setOnClickListener(v -> {
                    if (boardCallback != null)
                        boardCallback.itemClicked(getItem(getAdapterPosition()), getAdapterPosition()); //calling
                });
            }
        }
    }
