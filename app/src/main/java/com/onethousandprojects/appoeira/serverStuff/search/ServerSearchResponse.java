package com.onethousandprojects.appoeira.serverStuff.search;

import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchEventResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchOnlineResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchRodaResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchUserResponse;

import java.util.List;

public class ServerSearchResponse {
    private Integer check;
    private List<ServerSearchUserResponse> userResponses;
    private List<ServerSearchGroupResponse> groupResponses;
    private List<ServerSearchRodaResponse> rodaResponses;
    private List<ServerSearchEventResponse> eventResponses;
    private List<ServerSearchOnlineResponse> onlineResponses;

    public ServerSearchResponse(Integer check,
                                List<ServerSearchUserResponse> userResponses,
                                List<ServerSearchGroupResponse> groupResponses,
                                List<ServerSearchRodaResponse> rodaResponses,
                                List<ServerSearchEventResponse> eventResponses,
                                List<ServerSearchOnlineResponse> onlineResponses) {
        super();
        this.check = check;
        this.userResponses = userResponses;
        this.groupResponses = groupResponses;
        this.rodaResponses = rodaResponses;
        this.eventResponses = eventResponses;
        this.onlineResponses = onlineResponses;
    }

    public Integer getCheck() { return check; }

    public void setCheck(Integer check) { this.check = check; }

    public List<ServerSearchUserResponse> getUserResponses() { return userResponses; }

    public void setUserResponses(List<ServerSearchUserResponse> userResponses) { this.userResponses = userResponses; }

    public List<ServerSearchGroupResponse> getGroupResponses() { return groupResponses; }

    public void setGroupResponses(List<ServerSearchGroupResponse> groupResponses) { this.groupResponses = groupResponses; }

    public List<ServerSearchRodaResponse> getRodaResponses() { return rodaResponses; }

    public void setRodaResponses(List<ServerSearchRodaResponse> rodaResponses) { this.rodaResponses = rodaResponses; }

    public List<ServerSearchEventResponse> getEventResponses() { return eventResponses; }

    public void setEventResponses(List<ServerSearchEventResponse> eventResponses) { this.eventResponses = eventResponses; }

    public List<ServerSearchOnlineResponse> getOnlineResponses() { return onlineResponses; }

    public void setOnlineResponses(List<ServerSearchOnlineResponse> onlineResponses) { this.onlineResponses = onlineResponses; }

}
