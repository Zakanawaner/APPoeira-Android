package com.onethousandprojects.appoeira.serverStuff.news;

import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailEventResponse.ServerUserDetailEventCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailEventResponse.ServerUserDetailEventVotesResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailEventResponse.ServerUserDetailEventsResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailFollowResponse.ServerUserDetailFollowResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse.ServerUserDetailGroupCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse.ServerUserDetailGroupVotesResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse.ServerUserDetailGroupsResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailOnlineResponse.ServerUserDetailOnlineCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailOnlineResponse.ServerUserDetailOnlineVotesResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailOnlineResponse.ServerUserDetailOnlinesResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailRodaResponse.ServerUserDetailRodaCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailRodaResponse.ServerUserDetailRodaVotesResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailRodaResponse.ServerUserDetailRodasResponse;

import java.util.List;

public class ServerAreThereNewsResponse {
    private boolean response;

    public ServerAreThereNewsResponse(boolean response) {
        super();
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
