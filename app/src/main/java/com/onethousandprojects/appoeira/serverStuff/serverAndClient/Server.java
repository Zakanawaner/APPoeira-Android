package com.onethousandprojects.appoeira.serverStuff.serverAndClient;

import com.onethousandprojects.appoeira.serverStuff.eventDetail.ClientEventDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.eventDetail.ServerEventDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.eventList.ClientLocationEventRequest;
import com.onethousandprojects.appoeira.serverStuff.eventList.ServerLocationEventResponse;
import com.onethousandprojects.appoeira.serverStuff.eventModification.ClientEventModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.eventModification.ServerEventModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ClientRatedByUserRequest;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ServeRatedByUserResponse;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientCommentsRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientNewCommentRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerCommentsResponse;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerNewCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.groupDetail.ClientGroupDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetail.ServerGroupDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ClientGroupDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ServerGroupDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ClientJoinRequest;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ServerJoinResponse;
import com.onethousandprojects.appoeira.serverStuff.loginTransaction.ClientLoginRequest;
import com.onethousandprojects.appoeira.serverStuff.loginTransaction.ServerLoginResponse;
import com.onethousandprojects.appoeira.serverStuff.onlineModification.ClientOnlineModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineModification.ServerOnlineModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaDetail.ClientRodaDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaDetail.ServerRodaDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaDetailMore.ClientRodaDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaDetailMore.ServerRodaDetailMoreResponse;
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
    Call<ServerJoinResponse> post_join_group(@Body ClientJoinRequest clientJoinRequest);

    @POST("/group-comments")
    Call<List<ServerCommentsResponse>> post_group_comments(@Body ClientCommentsRequest clientGroupCommentsRequest);

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

    @POST("/location-event")
    Call<List<ServerLocationEventResponse>> post_location_event(@Body ClientLocationEventRequest clientLocationEventRequest);

    @POST("/event-detail")
    Call<ServerEventDetailResponse> post_event_detail(@Body ClientEventDetailRequest clientEventDetailRequest);

    @POST("/user-detail")
    Call<ServerUserDetailResponse> post_user_detail(@Body ClientUserDetailRequest clientUserDetailRequest);

    @POST("/user-rated-group")
    Call<ServeRatedByUserResponse> post_user_rated_group(@Body ClientRatedByUserRequest clientRatedByUserRequest);

    @POST("/new-comment-group")
    Call<ServerNewCommentResponse> post_new_comment_group(@Body ClientNewCommentRequest clientNewCommentRequest);

    @POST("/user-follow")
    Call<ServerUserFollowUserResponse> post_user_follow_user(@Body ClientUserFollowUserRequest clientUserFollowUserRequest);

    @POST("/user-unfollow")
    Call<ServerUserUnFollowUserResponse> post_user_unfollow_user(@Body ClientUserUnFollowUserRequest clientUserUnFollowUserRequest);

    @POST("/profile-update")
    Call<ServerUserModificationResponse> post_user_update_profile(@Body ClientUserModificationRequest clientUserModificationRequest);

    @POST("/user-search")
    Call<List<ServerUserSearchResponse>> post_user_search(@Body ClientUserSearchRequest clientUserSearchRequest);

    @POST("/user-rated-roda")
    Call<ServeRatedByUserResponse> post_user_rated_roda(@Body ClientRatedByUserRequest clientRatedByUserRequest);

    @POST("/new-comment-roda")
    Call<ServerNewCommentResponse> post_new_comment_roda(@Body ClientNewCommentRequest clientNewCommentRequest);

    @POST("/roda-detail-more")
    Call<List<ServerRodaDetailMoreResponse>> post_roda_detail_more(@Body ClientRodaDetailMoreRequest clientRodaDetailMoreRequest);

    @POST("/roda-join")
    Call<ServerJoinResponse> post_join_roda(@Body ClientJoinRequest clientJoinRequest);

    @POST("/roda-comments")
    Call<List<ServerCommentsResponse>> post_roda_comments(@Body ClientCommentsRequest clientGroupCommentsRequest);
}
