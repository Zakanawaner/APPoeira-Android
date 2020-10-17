package com.onethousandprojects.appoeira.serverStuff.serverAndClient;

import com.onethousandprojects.appoeira.serverStuff.eventDetail.ClientEventDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.eventDetail.ServerEventDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.eventDetailMore.ClientEventDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.eventDetailMore.ServerEventDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.eventList.ClientLocationEventRequest;
import com.onethousandprojects.appoeira.serverStuff.eventList.ServerLocationEventResponse;
import com.onethousandprojects.appoeira.serverStuff.eventModification.ClientEventModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.eventModification.ServerEventModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.leaveObject.ClientLeaveRequest;
import com.onethousandprojects.appoeira.serverStuff.leaveObject.ServerLeaveResponse;
import com.onethousandprojects.appoeira.serverStuff.news.ClientAreThereNewsRequest;
import com.onethousandprojects.appoeira.serverStuff.news.ServerAreThereNewsResponse;
import com.onethousandprojects.appoeira.serverStuff.onlineDetail.ClientOnlineDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineDetail.ServerOnlineDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.onlineDetailMore.ClientOnlineDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineDetailMore.ServerOnlineDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.onlineList.ClientLocationOnlineRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineList.ServerLocationOnlineResponse;
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
import com.onethousandprojects.appoeira.serverStuff.uploadPicture.ClientUploadPictureRequest;
import com.onethousandprojects.appoeira.serverStuff.uploadPicture.ServerUploadPictureResponse;
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
import com.onethousandprojects.appoeira.serverStuff.search.ClientSearchRequest;
import com.onethousandprojects.appoeira.serverStuff.search.ServerSearchResponse;
import com.onethousandprojects.appoeira.serverStuff.userUnfollowUser.ClientUserUnFollowUserRequest;
import com.onethousandprojects.appoeira.serverStuff.userUnfollowUser.ServerUserUnFollowUserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @POST("/group-leave")
    Call<ServerLeaveResponse> post_leave_group(@Body ClientLeaveRequest clientLeaveRequest);

    @POST("/group-comments")
    Call<List<ServerCommentsResponse>> post_group_comments(@Body ClientCommentsRequest clientGroupCommentsRequest);

    @POST("/location-roda")
    Call<List<ServerLocationRodaResponse>> post_location_rodas(@Body ClientLocationRodasRequest clientLocationRodasRequest);

    @Multipart
    @POST("/roda-create")
    Call<ServerRodaModificationResponse> post_roda_update(@Part("body") ClientRodaModificationRequest clientRodaModificationRequest, @Part MultipartBody.Part image);

    @Multipart
    @POST("/online-create")
    Call<ServerOnlineModificationResponse> post_online_update(@Part("body") ClientOnlineModificationRequest clientOnlineModificationRequest, @Part MultipartBody.Part image);

    @Multipart
    @POST("/event-create")
    Call<ServerEventModificationResponse> post_event_update(@Part("body") ClientEventModificationRequest clientEventModificationRequest, @Part MultipartBody.Part image);

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

    @POST("/search")
    Call<ServerSearchResponse> post_search(@Body ClientSearchRequest clientSearchRequest);

    @POST("/user-rated-roda")
    Call<ServeRatedByUserResponse> post_user_rated_roda(@Body ClientRatedByUserRequest clientRatedByUserRequest);

    @POST("/new-comment-roda")
    Call<ServerNewCommentResponse> post_new_comment_roda(@Body ClientNewCommentRequest clientNewCommentRequest);

    @POST("/roda-detail-more")
    Call<List<ServerRodaDetailMoreResponse>> post_roda_detail_more(@Body ClientRodaDetailMoreRequest clientRodaDetailMoreRequest);

    @POST("/roda-join")
    Call<ServerJoinResponse> post_join_roda(@Body ClientJoinRequest clientJoinRequest);

    @POST("/roda-leave")
    Call<ServerLeaveResponse> post_leave_roda(@Body ClientLeaveRequest clientLeaveRequest);

    @POST("/roda-comments")
    Call<List<ServerCommentsResponse>> post_roda_comments(@Body ClientCommentsRequest clientGroupCommentsRequest);

    @POST("/user-rated-event")
    Call<ServeRatedByUserResponse> post_user_rated_event(@Body ClientRatedByUserRequest clientRatedByUserRequest);

    @POST("/new-comment-event")
    Call<ServerNewCommentResponse> post_new_comment_event(@Body ClientNewCommentRequest clientNewCommentRequest);

    @POST("/event-detail-more")
    Call<List<ServerEventDetailMoreResponse>> post_event_detail_more(@Body ClientEventDetailMoreRequest clientEventDetailMoreRequest);

    @POST("/event-join")
    Call<ServerJoinResponse> post_join_event(@Body ClientJoinRequest clientJoinRequest);

    @POST("/event-leave")
    Call<ServerLeaveResponse> post_leave_event(@Body ClientLeaveRequest clientLeaveRequest);

    @POST("/event-comments")
    Call<List<ServerCommentsResponse>> post_event_comments(@Body ClientCommentsRequest clientGroupCommentsRequest);

    @POST("/are-there-news")
    Call<ServerAreThereNewsResponse> post_are_there_news(@Body ClientAreThereNewsRequest clientAreThereNewsRequest);

    @POST("/location-online")
    Call<List<ServerLocationOnlineResponse>> post_location_online(@Body ClientLocationOnlineRequest clientLocationOnlineRequest);

    @POST("/online-detail")
    Call<ServerOnlineDetailResponse> post_online_detail(@Body ClientOnlineDetailRequest clientOnlineDetailRequest);

    @POST("/user-rated-online")
    Call<ServeRatedByUserResponse> post_user_rated_online(@Body ClientRatedByUserRequest clientRatedByUserRequest);

    @POST("/new-comment-online")
    Call<ServerNewCommentResponse> post_new_comment_online(@Body ClientNewCommentRequest clientNewCommentRequest);

    @POST("/online-detail-more")
    Call<List<ServerOnlineDetailMoreResponse>> post_online_detail_more(@Body ClientOnlineDetailMoreRequest clientOnlineDetailMoreRequest);

    @POST("/online-join")
    Call<ServerJoinResponse> post_join_online(@Body ClientJoinRequest clientJoinRequest);

    @POST("/online-leave")
    Call<ServerLeaveResponse> post_leave_online(@Body ClientLeaveRequest clientLeaveRequest);

    @POST("/online-comments")
    Call<List<ServerCommentsResponse>> post_online_comments(@Body ClientCommentsRequest clientGroupCommentsRequest);

    @Multipart
    @POST("/upload-picture")
    Call<ServerUploadPictureResponse> post_picture(@Part("token") ClientUploadPictureRequest clientUploadPictureRequest, @Part MultipartBody.Part image);
}
