package com.onethousandprojects.appoeira.serverStuff.serverAndClient;

import com.onethousandprojects.appoeira.serverStuff.eventDetail.ClientEventDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.eventDetail.ServerEventDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.eventModification.ClientEventModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.eventModification.ServerEventModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.groupRatedByUser.ClientGroupRatedByUserRequest;
import com.onethousandprojects.appoeira.serverStuff.groupRatedByUser.ServeGroupRatedByUserResponse;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ClientGroupCommentsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ClientGroupNewCommentRequest;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ServerGroupCommentsResponse;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ServerGroupNewCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.groupDetail.ClientGroupDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetail.ServerGroupDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ClientGroupDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ServerGroupDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.joinGroup.ClientGroupJoinRequest;
import com.onethousandprojects.appoeira.serverStuff.joinGroup.ServerGroupJoinResponse;
import com.onethousandprojects.appoeira.serverStuff.loginTransaction.ClientLoginRequest;
import com.onethousandprojects.appoeira.serverStuff.loginTransaction.ServerLoginResponse;
import com.onethousandprojects.appoeira.serverStuff.onlineModification.ClientOnlineModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineModification.ServerOnlineModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaDetail.ClientRodaDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaDetail.ServerRodaDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaModification.ClientRodaModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaModification.ServerRodaModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.userModification.ClientUserModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.userModification.ServerUserModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.groupList.ClientLocationGroupsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaList.ClientLocationRodasRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaList.ServerLocationRodaResponse;
import com.onethousandprojects.appoeira.serverStuff.signupTransaction.ClientSignupRequest;
import com.onethousandprojects.appoeira.serverStuff.signupTransaction.ServerSignupResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.ClientUserDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.userDetail.ServerUserDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.userFollowUser.ClientUserFollowUserRequest;
import com.onethousandprojects.appoeira.serverStuff.userFollowUser.ServerUserFollowUserResponse;
import com.onethousandprojects.appoeira.serverStuff.userSearch.ClientUserSearchRequest;
import com.onethousandprojects.appoeira.serverStuff.userSearch.ServerUserSearchResponse;
import com.onethousandprojects.appoeira.serverStuff.userUnfollowUser.ClientUserUnFollowUserRequest;
import com.onethousandprojects.appoeira.serverStuff.userUnfollowUser.ServerUserUnFollowUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Server {

    @POST("/login")
    Call<ServerLoginResponse> post_login(@Body ClientLoginRequest clientLoginRequest);

    @POST("/sign-up")
    Call<ServerSignupResponse> post_sign_up(@Body ClientSignupRequest clientSignupRequest);

    @POST("/location-group")
    Call<List<ServerLocationGroupResponse>> post_location_groups(@Body ClientLocationGroupsRequest clientLocationGroupsRequest);

    @POST("/group-detail")
    Call<ServerGroupDetailResponse> post_group_detail(@Body ClientGroupDetailRequest clientGroupDetailRequest);

    @POST("/group-detail-more")
    Call<List<ServerGroupDetailMoreResponse>> post_group_detail_more(@Body ClientGroupDetailMoreRequest clientGroupDetailMoreRequest);

    @POST("/group-join")
    Call<ServerGroupJoinResponse> post_join_group(@Body ClientGroupJoinRequest clientGroupJoinRequest);

    @POST("/group-comments")
    Call<List<ServerGroupCommentsResponse>> post_group_comments(@Body ClientGroupCommentsRequest clientGroupCommentsRequest);

    @POST("/location-roda")
    Call<List<ServerLocationRodaResponse>> post_location_rodas(@Body ClientLocationRodasRequest clientLocationRodasRequest);

    @POST("/roda-create")
    Call<ServerRodaModificationResponse> post_roda_update(@Body ClientRodaModificationRequest clientRodaModificationRequest);

    @POST("/online-create")
    Call<ServerOnlineModificationResponse> post_online_update(@Body ClientOnlineModificationRequest clientOnlineModificationRequest);

    @POST("/event-create")
    Call<ServerEventModificationResponse> post_event_update(@Body ClientEventModificationRequest clientEventModificationRequest);

    @POST("/roda-detail")
    Call<ServerRodaDetailResponse> post_roda_detail(@Body ClientRodaDetailRequest clientRodaDetailRequest);

    @POST("/event-detail")
    Call<ServerEventDetailResponse> post_event_detail(@Body ClientEventDetailRequest clientEventDetailRequest);

    @POST("/user-detail")
    Call<ServerUserDetailResponse> post_user_detail(@Body ClientUserDetailRequest clientUserDetailRequest);

    @POST("/user-rated-group")
    Call<ServeGroupRatedByUserResponse> post_user_rated_group(@Body ClientGroupRatedByUserRequest clientGroupRatedByUserRequest);

    @POST("/new-comment")
    Call<ServerGroupNewCommentResponse> post_new_comment(@Body ClientGroupNewCommentRequest clientGroupNewCommentRequest);

    @POST("/user-follow")
    Call<ServerUserFollowUserResponse> post_user_follow_user(@Body ClientUserFollowUserRequest clientUserFollowUserRequest);

    @POST("/user-unfollow")
    Call<ServerUserUnFollowUserResponse> post_user_unfollow_user(@Body ClientUserUnFollowUserRequest clientUserUnFollowUserRequest);

    @POST("/profile-update")
    Call<ServerUserModificationResponse> post_user_update_profile(@Body ClientUserModificationRequest clientUserModificationRequest);

    @POST("/user-search")
    Call<List<ServerUserSearchResponse>> post_user_search(@Body ClientUserSearchRequest clientUserSearchRequest);
}
