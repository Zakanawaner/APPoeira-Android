package com.onethousandprojects.appoeira.serverStuff.userSearch;

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

public class ServerUserSearchResponse {
    private Integer id;
    private String apelhido;
    private String picUrl;
    private boolean premium;
    private String rank;

    public ServerUserSearchResponse(Integer id, String apelhido,
                                    String picUrl, boolean premium,
                                    String rank) {
        super();
        this.id = id;
        this.apelhido = apelhido;
        this.picUrl = picUrl;
        this.premium = premium;
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApelhido() {
        return apelhido;
    }

    public void setApelhido(String apelhido) {
        this.apelhido = apelhido;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
