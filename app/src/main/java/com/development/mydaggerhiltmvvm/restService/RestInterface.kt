package com.development.mydaggerhiltmvvm.restService


import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList
import java.util.HashMap

interface RestInterface {

    @POST("user/store")
    @FormUrlEncoded
    fun signUp(@FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("user/phoneverification")
    @FormUrlEncoded
    fun emailVerification(@FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("user/verifyOtpCodeForgotPassword")
    @FormUrlEncoded
    fun forgotPasswordVerification(@FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("user/resend-otp")
    @FormUrlEncoded
    fun resendOtp(@FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("user/signin")
    @FormUrlEncoded
    fun signIn(@FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("user/social_signup")
    @FormUrlEncoded
    fun socialSignup(@FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @GET("user/logout")
    fun logout(@Header("x-access-token") token: String): Call<ResponseBody>

    @POST("user/reset-password")
    @FormUrlEncoded
    fun forgotPassword(@FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("user/setPassword")
    @FormUrlEncoded
    fun resetPassword(@FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @GET("user/getProfile")
    fun getUserProfile(
        @Header("x-access-token") token: String
    ): Call<ResponseBody>

    @POST("user/update")
    @Multipart
    fun userUpdateProfile(
        @Header("x-access-token") token: String,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("full_name") full_name: RequestBody
       /* @Part("location") location: RequestBody,
        @Part("birth_date") birth_date: RequestBody,
        @Part("password") password: RequestBody*/
    ): Call<ResponseBody>

    @POST("user/update-email")
    @FormUrlEncoded
    fun updateEmail(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("user/verifyOtpCodeUpdateEmail")
    @FormUrlEncoded
    fun verifyUpdateEmail(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("user/list")
    @FormUrlEncoded
    fun getUserList(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("friend_request/save")
    @FormUrlEncoded
    fun sendFriendRequest(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String?>): Call<ResponseBody>

    @GET("friend_request/myFriendList")
    fun getMyFriendList(
        @Header("x-access-token") token: String
    ): Call<ResponseBody>

    @GET("friend_request/recievedFriendRequestList")
    fun getRecievedFriendRequestList(
        @Header("x-access-token") token: String
    ): Call<ResponseBody>

    @POST("friend_request/update")
    @FormUrlEncoded
    fun updateFriendRequest(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @GET("color/list")
    fun getColorList(): Call<ResponseBody>

    @POST("memorial/save")
    @Multipart
    fun saveMemorial(
        @Header("x-access-token") token: String,
        @Part pinnedMediaList: ArrayList<MultipartBody.Part>,
        @Part allMediaList: ArrayList<MultipartBody.Part>,
        @Part("name") name: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part("quote") quote: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("burial_locaion") burial_locaion: RequestBody,
        @Part("location_grew_up") location_grew_up: RequestBody,
        @Part("location_last_lived") location_last_lived: RequestBody,
        @Part("occupation") occupation: RequestBody,
        @Part("hobbies") hobbies: RequestBody,
        @Part("interests") interests: RequestBody,
        @Part("spouse") spouse: RequestBody,
        @Part("kids") kids: RequestBody,
        @Part("grand_kids") grandKids: RequestBody,
        @Part("sibling") sibling: RequestBody,
        @Part("parents") parents: RequestBody,
        @Part("pets") pets: RequestBody,
        @Part("favourite_food_drink") favourite_food_drink: RequestBody,
        @Part("favourite_vacation_spot") favourite_vacation_spot: RequestBody,
        @Part("college") college: RequestBody,
        @Part("dod") dod: RequestBody,
        @Part memorialProfileImage: MultipartBody.Part?,
        @Part("military") military: RequestBody,
        @Part("memorial_theme") memorial_theme: RequestBody,
        @Part("invitation_setting") invitation_setting: RequestBody,
        @Part("existing_memorials_settings") existing_memorials_settings: RequestBody,
        @Part("feed_settings") feed_settings: RequestBody,
        @Part("post_settings") post_settings: RequestBody,
        @Part("school") school: RequestBody
    ): Call<ResponseBody>

    @POST("memorial/list")
    @FormUrlEncoded
    fun getMyMemorialList(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("memorial/details/{id}")
    fun getMyMemorialDetails(
        @Path("id") id: String,
        @Header("x-access-token") token: String): Call<ResponseBody>

    @POST("memorial/edit/{id}")
    @Multipart
    fun editMemorial(
        @Path("id") id: String,
        @Header("x-access-token") token: String,
       /* @Part pinnedMediaList: ArrayList<MultipartBody.Part>,*/
        @Part("name") name: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part("quote") quote: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("burial_locaion") burial_locaion: RequestBody,
        @Part("location_grew_up") location_grew_up: RequestBody,
        @Part("location_last_lived") location_last_lived: RequestBody,
        @Part("occupation") occupation: RequestBody,
        @Part("hobbies") hobbies: RequestBody,
        @Part("interests") interests: RequestBody,
        @Part("spouse") spouse: RequestBody,
        @Part("kids") kids: RequestBody,
        @Part("grand_kids") grandKids: RequestBody,
        @Part("sibling") sibling: RequestBody,
        @Part("parents") parents: RequestBody,
        @Part("pets") pets: RequestBody,
        @Part("favourite_food_drink") favourite_food_drink: RequestBody,
        @Part("favourite_vacation_spot") favourite_vacation_spot: RequestBody,
        @Part("college") college: RequestBody,
        @Part("dod") dod: RequestBody,
        @Part memorialProfileImage: MultipartBody.Part?,
        @Part("military") military: RequestBody,
        @Part("memorial_theme") memorial_theme: RequestBody,
        @Part("invitation_setting") invitation_setting: RequestBody,
        @Part("existing_memorials_settings") existing_memorials_settings: RequestBody,
        @Part("feed_settings") feed_settings: RequestBody,
        @Part("post_settings") post_settings: RequestBody,
        @Part("school") school: RequestBody
    ): Call<ResponseBody>

    @POST("joined_memorial/invite")
    @FormUrlEncoded
    fun sendInvitationForJoinMemorial(
        @Header("x-access-token") token: String,
        @Field("memorial_id") memorialId: String,
        @Field("invite") invite: ArrayList<String>): Call<ResponseBody>

    @GET("joined_memorial/mySuggestionList")
    fun getMemorialSuggestionList(
        @Header("x-access-token") token: String
    ): Call<ResponseBody>

    @POST("joined_memorial/joinMemorial")
    @FormUrlEncoded
    fun requestForJoinMemorial(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("joined_memorial/joinedMemberList")
    @FormUrlEncoded
    fun getJoinedMemberList(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("joined_memorial/recievedJoinMemberRequestList")
    @FormUrlEncoded
    fun recievedJoinMemberRequestList(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @POST("joined_memorial/update")
    @FormUrlEncoded
    fun updateJoinedMemorial(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String>): Call<ResponseBody>

    @GET("joined_memorial/getRecievedMemberRequest")
    fun getRecievedMemberRequest(
        @Header("x-access-token") token: String): Call<ResponseBody>


    @GET("joined_memorial/inviteMemberRequestList")
    fun getInviteRequest(
        @Header("x-access-token") token: String): Call<ResponseBody>

    @GET("joined_memorial/myJoinMemberInviteList")
    fun getJoinedMemorialList(
        @Header("x-access-token") token: String): Call<ResponseBody>

    @GET("category/list")
    fun getCategoryList(): Call<ResponseBody>

    @GET(" reaction/list")
    fun getReactionList(): Call<ResponseBody>

    @POST("comments/save")
    @Multipart
    fun savePost(
        @Header("x-access-token") token: String,
        @Part("parent_comment_id") parentCommentId: RequestBody?,
        @Part("memorial_id") memorialId: RequestBody,
        @Part("repliedto_user_id") repliedtoUserId: RequestBody,
        @Part("message") quote: RequestBody,
        @Part allMediaList: ArrayList<MultipartBody.Part>
    ): Call<ResponseBody>

    @POST("comments/list")
    @FormUrlEncoded
    fun getCommentList(
        @Header("x-access-token") token: String,
        @FieldMap fields: HashMap<String, String?>
    ): Call<ResponseBody>
}