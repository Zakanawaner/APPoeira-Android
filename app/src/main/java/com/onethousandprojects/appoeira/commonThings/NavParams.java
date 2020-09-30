package com.onethousandprojects.appoeira.commonThings;

import androidx.lifecycle.LifecycleOwner;

import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.authView.SignUpActivity;
import com.onethousandprojects.appoeira.eventDetailMoreView.EventDetailMoreActivity;
import com.onethousandprojects.appoeira.eventDetailView.EventDetailActivity;
import com.onethousandprojects.appoeira.eventListView.EventListActivity;
import com.onethousandprojects.appoeira.eventModificationView.EventModificationActivity;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;
import com.onethousandprojects.appoeira.groupModificationView.GroupModificationActivity;
import com.onethousandprojects.appoeira.helpView.HelpActivity;
import com.onethousandprojects.appoeira.newsView.NewsActivity;
import com.onethousandprojects.appoeira.onlineDetailMoreView.OnlineDetailMoreActivity;
import com.onethousandprojects.appoeira.onlineDetailView.OnlineDetailActivity;
import com.onethousandprojects.appoeira.onlineListView.OnlineListActivity;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.rodaDetailMoreView.RodaDetailMoreActivity;
import com.onethousandprojects.appoeira.rodaDetailView.RodaDetailActivity;
import com.onethousandprojects.appoeira.rodaListView.RodaListActivity;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.searchView.SearchActivity;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userModificationView.ProfileModificationActivity;

public class NavParams {
    GroupListActivity groupList;
    GroupDetailActivity groupDetail;
    GroupDetailMoreActivity groupDetailMore;
    GroupModificationActivity groupModification;
    EventListActivity eventList;
    EventDetailActivity eventDetail;
    EventDetailMoreActivity eventDetailMore;
    EventModificationActivity eventModification;
    RodaListActivity rodaList;
    RodaDetailActivity rodaDetail;
    RodaDetailMoreActivity rodaDetailMore;
    RodaModificationActivity rodaModification;
    OnlineListActivity onlineList;
    OnlineDetailActivity onlineDetail;
    OnlineDetailMoreActivity onlineDetailMore;
    OnlineModificationActivity onlineModification;
    UserDetailActivity userDetail;
    ProfileModificationActivity profileModification;
    SearchActivity search;
    NewsActivity news;
    HelpActivity help;
    LoginActivity login;
    SignUpActivity signUp;

    public NavParams(GroupListActivity groupList,
                     GroupDetailActivity groupDetail,
                     GroupDetailMoreActivity groupDetailMore,
                     GroupModificationActivity groupModification,
                     EventListActivity eventList,
                     EventDetailActivity eventDetail,
                     EventDetailMoreActivity eventDetailMore,
                     EventModificationActivity eventModification,
                     RodaListActivity rodaList,
                     RodaDetailActivity rodaDetail,
                     RodaDetailMoreActivity rodaDetailMore,
                     RodaModificationActivity rodaModification,
                     OnlineListActivity onlineList,
                     OnlineDetailActivity onlineDetail,
                     OnlineDetailMoreActivity onlineDetailMore,
                     OnlineModificationActivity onlineModification,
                     UserDetailActivity userDetail,
                     ProfileModificationActivity profileModification,
                     SearchActivity search,
                     NewsActivity news,
                     HelpActivity help,
                     LoginActivity login,
                     SignUpActivity signUp) {
        this.groupList = groupList;
        this.groupDetail = groupDetail;
        this.groupDetailMore = groupDetailMore;
        this.groupModification = groupModification;
        this.eventList = eventList;
        this.eventDetail = eventDetail;
        this.eventDetailMore = eventDetailMore;
        this.eventModification = eventModification;
        this.rodaList = rodaList;
        this.rodaDetail = rodaDetail;
        this.rodaDetailMore = rodaDetailMore;
        this.rodaModification = rodaModification;
        this.onlineList = onlineList;
        this.onlineDetail = onlineDetail;
        this.onlineDetailMore = onlineDetailMore;
        this.onlineModification = onlineModification;
        this.userDetail = userDetail;
        this.profileModification = profileModification;
        this.search = search;
        this.news = news;
        this.help = help;
        this.login = login;
        this.signUp = signUp;
    }

    public GroupListActivity getGroupList() {
        return groupList;
    }

    public void setGroupList(GroupListActivity groupList) {
        this.groupList = groupList;
    }

    public GroupDetailActivity getGroupDetail() {
        return groupDetail;
    }

    public void setGroupDetail(GroupDetailActivity groupDetail) {
        this.groupDetail = groupDetail;
    }

    public GroupDetailMoreActivity getGroupDetailMore() {
        return groupDetailMore;
    }

    public void setGroupDetailMore(GroupDetailMoreActivity gç) {
        this.groupDetailMore = groupDetailMore;
    }

    public GroupModificationActivity getGroupModification() {
        return groupModification;
    }

    public void setGroupModification(GroupModificationActivity groupModification) {
        this.groupModification = groupModification;
    }

    public EventListActivity getEventList() {
        return eventList;
    }

    public void setEventList(EventListActivity eventList) {
        this.eventList = eventList;
    }

    public EventDetailActivity getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(EventDetailActivity eventDetail) {
        this.eventDetail = eventDetail;
    }

    public EventDetailMoreActivity getEventDetailMore() {
        return eventDetailMore;
    }

    public void setEventDetailMore(EventDetailMoreActivity gç) {
        this.eventDetailMore = eventDetailMore;
    }

    public EventModificationActivity getEventModification() {
        return eventModification;
    }

    public void setEventModification(EventModificationActivity eventModification) {
        this.eventModification = eventModification;
    }

    public RodaListActivity getRodaList() {
        return rodaList;
    }

    public void setRodaList(RodaListActivity rodaList) {
        this.rodaList = rodaList;
    }

    public RodaDetailActivity getRodaDetail() {
        return rodaDetail;
    }

    public void setRodaDetail(RodaDetailActivity rodaDetail) {
        this.rodaDetail = rodaDetail;
    }

    public RodaDetailMoreActivity getRodaDetailMore() {
        return rodaDetailMore;
    }

    public void setRodaDetailMore(RodaDetailMoreActivity gç) {
        this.rodaDetailMore = rodaDetailMore;
    }

    public RodaModificationActivity getRodaModification() {
        return rodaModification;
    }

    public void setRodaModification(RodaModificationActivity rodaModification) {
        this.rodaModification = rodaModification;
    }

    public OnlineListActivity getOnlineList() {
        return onlineList;
    }

    public void setOnlineList(OnlineListActivity onlineList) {
        this.onlineList = onlineList;
    }

    public OnlineDetailActivity getOnlineDetail() {
        return onlineDetail;
    }

    public void setOnlineDetail(OnlineDetailActivity onlineDetail) {
        this.onlineDetail = onlineDetail;
    }

    public OnlineDetailMoreActivity getOnlineDetailMore() {
        return onlineDetailMore;
    }

    public void setOnlineDetailMore(OnlineDetailMoreActivity gç) {
        this.onlineDetailMore = onlineDetailMore;
    }

    public OnlineModificationActivity getOnlineModification() {
        return onlineModification;
    }

    public void setOnlineModification(OnlineModificationActivity onlineModification) {
        this.onlineModification = onlineModification;
    }

    public UserDetailActivity getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailActivity userDetail) {
        this.userDetail = userDetail;
    }

    public ProfileModificationActivity getProfileModification() {
        return profileModification;
    }

    public void setProfileModification(ProfileModificationActivity profileModification) {
        this.profileModification = profileModification;
    }

    public SearchActivity getSearch() {
        return search;
    }

    public void setSearch(SearchActivity search) {
        this.search = search;
    }

    public NewsActivity getNews() {
        return news;
    }

    public void setNews(NewsActivity news) {
        this.news = news;
    }

    public HelpActivity getHelp() {
        return help;
    }

    public void setHelp(HelpActivity help) {
        this.help = help;
    }

    public LoginActivity getLogin() {
        return login;
    }

    public void setLogin(LoginActivity login) {
        this.login = login;
    }

    public SignUpActivity getSignUp() {
        return signUp;
    }

    public void setSignUp(SignUpActivity signUp) {
        this.signUp = signUp;
    }

}
