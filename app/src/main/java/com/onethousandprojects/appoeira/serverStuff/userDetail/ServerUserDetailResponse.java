package com.onethousandprojects.appoeira.serverStuff.userDetail;

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

public class ServerUserDetailResponse {
    private String apelhido;
    private String email;
    private String name;
    private String lastName;
    private String picUrl;
    private String joined;
    private boolean premium;
    private String rank;
    private String school;
    private List<ServerUserDetailFollowResponse> followers;
    private List<ServerUserDetailFollowResponse> followed;
    private List<ServerUserDetailGroupsResponse> groups;
    private List<ServerUserDetailGroupVotesResponse> groupVotes;
    private List<ServerUserDetailGroupCommentResponse> groupComments;
    private List<ServerUserDetailRodasResponse> rodas;
    private List<ServerUserDetailRodaVotesResponse> rodaVotes;
    private List<ServerUserDetailRodaCommentResponse> rodaComments;
    private List<ServerUserDetailEventsResponse> events;
    private List<ServerUserDetailEventVotesResponse> eventVotes;
    private List<ServerUserDetailEventCommentResponse> eventComments;
    private List<ServerUserDetailOnlinesResponse> onlines;
    private List<ServerUserDetailOnlineVotesResponse> onlineVotes;
    private List<ServerUserDetailOnlineCommentResponse> onlineComments;


    public ServerUserDetailResponse(String apelhido, String email, String name, String lastName,
                                    String picUrl, String joined, boolean premium,
                                    String rank, String school,
                                    List<ServerUserDetailFollowResponse> followers,
                                    List<ServerUserDetailFollowResponse> followed,
                                    List<ServerUserDetailGroupsResponse> groups,
                                    List<ServerUserDetailGroupVotesResponse> groupVotes,
                                    List<ServerUserDetailGroupCommentResponse> groupComments,
                                    List<ServerUserDetailRodasResponse> rodas,
                                    List<ServerUserDetailRodaVotesResponse> rodaVotes,
                                    List<ServerUserDetailRodaCommentResponse> rodaComments,
                                    List<ServerUserDetailEventsResponse> events,
                                    List<ServerUserDetailEventVotesResponse> eventVotes,
                                    List<ServerUserDetailEventCommentResponse> eventComments,
                                    List<ServerUserDetailOnlinesResponse> onlines,
                                    List<ServerUserDetailOnlineVotesResponse> onlineVotes,
                                    List<ServerUserDetailOnlineCommentResponse> onlineComments) {
        super();
        this.apelhido = apelhido;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.picUrl = picUrl;
        this.joined = joined;
        this.premium = premium;
        this.rank = rank;
        this.school = school;
        this.followers = followers;
        this.followed = followed;
        this.groups = groups;
        this.groupVotes = groupVotes;
        this.groupComments = groupComments;
        this.rodas = rodas;
        this.rodaVotes = rodaVotes;
        this.rodaComments = rodaComments;
        this.events = events;
        this.eventVotes = eventVotes;
        this.eventComments = eventComments;
        this.onlines = onlines;
        this.onlineVotes = onlineVotes;
        this.onlineComments = onlineComments;
    }

    public String getApelhido() {
        return apelhido;
    }

    public void setApelhido(String apelhido) {
        this.apelhido = apelhido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<ServerUserDetailGroupsResponse> getGroups() {
        return groups;
    }

    public void setGroups(List<ServerUserDetailGroupsResponse> groups) {
        this.groups = groups;
    }

    public List<ServerUserDetailGroupVotesResponse> getGroupVotes() {
        return groupVotes;
    }

    public void setFollowers(List<ServerUserDetailFollowResponse> followers) {
        this.followers = followers;
    }

    public List<ServerUserDetailFollowResponse> getFollowed() {
        return followed;
    }

    public void setFollowed(List<ServerUserDetailFollowResponse> followed) {
        this.followed = followed;
    }

    public List<ServerUserDetailFollowResponse> getFollowers() {
        return followers;
    }

    public void setGroupVotes(List<ServerUserDetailGroupVotesResponse> groupVotes) {
        this.groupVotes = groupVotes;
    }

    public List<ServerUserDetailGroupCommentResponse> getGroupComments() {
        return groupComments;
    }

    public void setGroupComments(List<ServerUserDetailGroupCommentResponse> groupComments) {
        this.groupComments = groupComments;
    }

    public List<ServerUserDetailRodasResponse> getRodas() {
        return rodas;
    }

    public void setRodas(List<ServerUserDetailRodasResponse> rodas) {
        this.rodas = rodas;
    }

    public List<ServerUserDetailRodaVotesResponse> getRodaVotes() {
        return rodaVotes;
    }

    public void setRodaVotes(List<ServerUserDetailRodaVotesResponse> rodaVotes) {
        this.rodaVotes = rodaVotes;
    }

    public List<ServerUserDetailRodaCommentResponse> getRodaComments() {
        return rodaComments;
    }

    public void setRodaComments(List<ServerUserDetailRodaCommentResponse> rodaComments) {
        this.rodaComments = rodaComments;
    }

    public List<ServerUserDetailEventsResponse> getEvents() {
        return events;
    }

    public void setEvents(List<ServerUserDetailEventsResponse> events) {
        this.events = events;
    }

    public List<ServerUserDetailEventVotesResponse> getEventVotes() {
        return eventVotes;
    }

    public void setEventVotes(List<ServerUserDetailEventVotesResponse> eventVotes) {
        this.eventVotes = eventVotes;
    }

    public List<ServerUserDetailEventCommentResponse> getEventComments() {
        return eventComments;
    }

    public void setEventComments(List<ServerUserDetailEventCommentResponse> eventComments) {
        this.eventComments = eventComments;
    }

    public List<ServerUserDetailOnlinesResponse> getOnlines() {
        return onlines;
    }

    public void setOnlines(List<ServerUserDetailOnlinesResponse> onlines) {
        this.onlines = onlines;
    }

    public List<ServerUserDetailOnlineVotesResponse> getOnlineVotes() {
        return onlineVotes;
    }

    public void setOnlineVotes(List<ServerUserDetailOnlineVotesResponse> onlineVotes) {
        this.onlineVotes = onlineVotes;
    }

    public List<ServerUserDetailOnlineCommentResponse> getOnlineComments() {
        return onlineComments;
    }

    public void setOnlineComments(List<ServerUserDetailOnlineCommentResponse> onlineComments) {
        this.onlineComments = onlineComments;
    }
}
