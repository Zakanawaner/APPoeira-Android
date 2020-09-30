package com.onethousandprojects.appoeira.groupDetailMoreView.popUp;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.groupRatedByUser.ClientGroupRatedByUserRequest;
import com.onethousandprojects.appoeira.serverStuff.groupRatedByUser.ServeGroupRatedByUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupBeenHereRatingPopUp {
    //PopupWindow display method
    Integer stars;
    Client Client;
    Server Server;
    private ServeGroupRatedByUserResponse myResponse;

    public void showPopupWindow(final View view, String message, boolean flag, Integer group_id, Integer user_id) {

        retrofitinit();
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up_have_you_been_here, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler
        TextView tvPopUp = popupView.findViewById(R.id.popUpHaveYouBeenHereExplanation);
        tvPopUp.setText(message);

        ImageButton btnStar1 = popupView.findViewById(R.id.favorite_button_1);
        ImageButton btnStar2 = popupView.findViewById(R.id.favorite_button_2);
        ImageButton btnStar3 = popupView.findViewById(R.id.favorite_button_3);
        ImageButton btnStar4 = popupView.findViewById(R.id.favorite_button_4);
        ImageButton btnStar5 = popupView.findViewById(R.id.favorite_button_5);
        if (flag) {
            btnStar1.setImageResource(R.drawable.ic_star_void);
            btnStar2.setImageResource(R.drawable.ic_star_void);
            btnStar3.setImageResource(R.drawable.ic_star_void);
            btnStar4.setImageResource(R.drawable.ic_star_void);
            btnStar5.setImageResource(R.drawable.ic_star_void);
            stars = 0;
            btnStar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStar1.setImageResource(R.drawable.ic_star);
                    btnStar2.setImageResource(R.drawable.ic_star_void);
                    btnStar3.setImageResource(R.drawable.ic_star_void);
                    btnStar4.setImageResource(R.drawable.ic_star_void);
                    btnStar5.setImageResource(R.drawable.ic_star_void);
                    stars = 1;
                }
            });
            btnStar2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStar1.setImageResource(R.drawable.ic_star);
                    btnStar2.setImageResource(R.drawable.ic_star);
                    btnStar3.setImageResource(R.drawable.ic_star_void);
                    btnStar4.setImageResource(R.drawable.ic_star_void);
                    btnStar5.setImageResource(R.drawable.ic_star_void);
                    stars = 2;
                }
            });
            btnStar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStar1.setImageResource(R.drawable.ic_star);
                    btnStar2.setImageResource(R.drawable.ic_star);
                    btnStar3.setImageResource(R.drawable.ic_star);
                    btnStar4.setImageResource(R.drawable.ic_star_void);
                    btnStar5.setImageResource(R.drawable.ic_star_void);
                    stars = 3;
                }
            });
            btnStar4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStar1.setImageResource(R.drawable.ic_star);
                    btnStar2.setImageResource(R.drawable.ic_star);
                    btnStar3.setImageResource(R.drawable.ic_star);
                    btnStar4.setImageResource(R.drawable.ic_star);
                    btnStar5.setImageResource(R.drawable.ic_star_void);
                    stars = 4;
                }
            });
            btnStar5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStar1.setImageResource(R.drawable.ic_star);
                    btnStar2.setImageResource(R.drawable.ic_star);
                    btnStar3.setImageResource(R.drawable.ic_star);
                    btnStar4.setImageResource(R.drawable.ic_star);
                    btnStar5.setImageResource(R.drawable.ic_star);
                    stars = 5;
                }
            });

        } else {
            popupView.findViewById(R.id.favorite_button_1).setVisibility(View.INVISIBLE);
            popupView.findViewById(R.id.favorite_button_2).setVisibility(View.INVISIBLE);
            popupView.findViewById(R.id.favorite_button_3).setVisibility(View.INVISIBLE);
            popupView.findViewById(R.id.favorite_button_4).setVisibility(View.INVISIBLE);
            popupView.findViewById(R.id.favorite_button_5).setVisibility(View.INVISIBLE);
        }


        Button button = popupView.findViewById(R.id.popUpHaveYouBeenHereButton);
        if (flag) {
            button.setText("Enviar");
        } else {
            button.setText("¡Lo conozco!");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag) {
                    popupWindow.dismiss();
                    GroupDetailMoreActivity.groupDetailBeenHere(view, group_id, user_id);
                } else {
                    ClientGroupRatedByUserRequest clientGroupRatedByUserRequest = new ClientGroupRatedByUserRequest(user_id, group_id, stars);
                    Call<ServeGroupRatedByUserResponse> call = Server.post_user_rated_group(clientGroupRatedByUserRequest);
                    call.enqueue(new Callback<ServeGroupRatedByUserResponse>() {
                        @Override
                        public void onResponse(Call<ServeGroupRatedByUserResponse> call, Response<ServeGroupRatedByUserResponse> response) {
                            if (response.isSuccessful()){
                                myResponse = response.body();
                                popupWindow.dismiss();
                                GroupDetailMoreActivity.groupDetailBeenHereStarsGiven(view, myResponse.getStars(), myResponse.isOk());
                            } else {
                                Toast.makeText(view.getContext(), "Algo fue mal", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ServeGroupRatedByUserResponse> call, Throwable t) {
                            Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }
    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public ServeGroupRatedByUserResponse getMyResponse() {
        return myResponse;
    }

}
