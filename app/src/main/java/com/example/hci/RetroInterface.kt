package com.example.hci

import com.example.hci.data.model.*
import com.example.hci.data.model.FriendReq.FriendReqResult
import com.example.hci.data.model.FriendReqRefuse.FriendReqRefuseModel
import com.example.hci.data.model.ProfileImg.ProfileImgModel
import com.example.hci.data.model.ProfileImg.ProfileImgResult
import com.example.hci.data.model.Register.RegisterModel
import com.example.hci.data.model.Register.RegisterResult
import com.example.hci.data.model.Setlocation.SetlocationModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.IOException

interface RetroInterface {

    companion object {
        private const val BASE_URL = "http://ec2-54-180-114-74.ap-northeast-2.compute.amazonaws.com:3000/";

        fun create():RetroInterface{
            val gson : Gson = GsonBuilder().setLenient().create();

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.client(provideOkHttpClient(AppInterceptor()))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetroInterface::class.java);
        }

        fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient
                = OkHttpClient.Builder().run {
            addInterceptor(interceptor)
            build()
        }

        class AppInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("Connection", "close")
                    .build()
                proceed(newRequest)
            }
        }
    }

    @POST("/register")
    fun register(
        @Body jsonparams: RegisterModel
    ) : Call<RegisterResult>

    @POST("/login")
    fun login(
        @Body jsonparms : LoginModel
    ) : Call<LoginResult>

    @GET("/location/all")
    fun locationAll() : Call<List<LocationAllResult>>

    @POST("/user/location")
    fun setlocation(
        @Body jsonparams: SetlocationModel
    ) : Call<String>

    @POST("/group/idList")
    fun groupIdList(
        @Body jsonparams : GroupIdListModel
    ) : Call<List<GroupIdListResult>>

    @POST("/group/info")
    fun groupInfo(
        @Body jsonparams : GroupInfoModel
    ) : Call<GroupInfoResult>

    @POST("/group/sendRequest")
    fun groupSendRequest(
        @Body jsonparms : GroupSendRequestModel
    ) : Call<String>

    @POST("/group/reqList")
    fun groupReqList(
        @Body jsonparms : GroupReqListModel
    ) : Call<List<GroupReqListResult>>

    @POST("/group/req/accept")
    fun groupReqAccept(
        @Body jsonparms : GroupReqAcceptModel
    ) : Call<String>

    @POST("/group/req/refuse")
    fun groupReqRefuse(
        @Body jsonparms : GroupReqRefuseModel
    ) : Call<String>

    @POST("/group/exit")
    fun groupExit(
        @Body jsonparms : GroupExitModel
    ) : Call<String>

    @POST("/group/delete")
    fun groupDelete(
        @Body jsonparms : GroupDeleteModel
    ) : Call<String>

    @POST("/group/create")
    fun groupCreate(
        @Body jsonparms : GroupCreateModel
    ) : Call<String>

    @POST("/group/modify")
    fun groupModify(
        @Body jsonparms : GroupModifyModel
    ) : Call<String>

    @POST("/user")
    fun user(
        @Body jsonparms : UserModel
    ): Call<UserResult>

    @POST("/group/created")
    fun groupCreated(
            @Body jsonparms : GroupCreatedModel
    ) : Call<List<GroupCreatedResult>>

    @POST("/group/joined")
    fun groupJoined(
        @Body jsonparms : GroupJoinedModel
    ) : Call<List<GroupJoinedResult>>

    @POST("/user/review/write")
    fun userReviewWrite(
        @Body jsonparms : UserReviewWriteModel
    ) : Call<String>

    @POST("/user/modify")
    fun userModify(
        @Body jsonparms : UserModifyModel
    ) : Call<String>

    @POST("/friend/req")
    fun friendReq(
        @Body jsonparms : FriendReqModel
    ) : Call<String>

    @POST("/friend/reqList")
    fun friendReqList(
        @Body jsonparms : FriendReqListModel
    ) : Call<List<FriendReqListResult>>

    @POST("/friend/req/accept")
    fun friendReqAccept(
        @Body jsonparms : FriendReqAcceptModel
    ) : Call<String>

    @POST("/friend/req/refuse")
    fun friendReqRefuse(
        @Body jsonparms : FriendReqRefuseModel
    ) : Call<String>

    @POST("/group/invite")
    fun groupInvite(
        @Body jsonparms : GroupInviteModel
    ) : Call<String>

    @POST("/group/inviteList")
    fun groupInviteList(
        @Body jsonparms : GroupInviteListModel
    ) : Call<List<GroupInviteListResult>>

    @POST("/group/invite/accept")
    fun groupInviteAccept(
        @Body jsonparms : GroupInviteAcceptModel
    ) : Call<String>

    @POST("/group/invite/refuse")
    fun groupInviteRefuse(
        @Body jsonparms : GroupInviteRefuseModel
    ) : Call<String>

    @POST("/friend/list")
    fun friendList(
        @Body jsonparms : FriendListModel
    ) : Call<List<FriendListResult>>

    @POST("/notification")
    fun notification(
        @Body jsonparms : NotificationModel
    ) : Call<String>

    @POST("/auth/sendEmail")
    fun authSendEmail(
        @Body jsonparms : AuthSendEmailModel
    ) : Call<String>

    @POST("/auth/match")
    fun authMatch(
        @Body jsonparms : AuthMatchModel
    ) : Call<String>

    @POST("/group/search")
    fun groupSearch(
        @Body jsonparms : GroupSearchModel
    ) : Call<List<GroupSearchResult>>

    @GET("/chat/header")
    fun chatHeader(
        @Body jsonparms : ChatHeaderModel
    ) : Call<List<ChatHeaderResult>>

    @POST("/chat/send")
    fun chatSend(
        @Body jsonparms : ChatSendModel
    ) : Call<ChatSendResult>

    @GET("/chat/get")
    fun chatGet(
        @Body jsonparms : ChatGetModel
    ) : Call<List<ChatGetResult>>

    @GET("/user/img/get")
    fun profileImgGet(
        @Body jsonparms : UserModel
    ):Call<ProfileImgResult>

    @POST("/user/img/post")
    fun profileImgPost(
        @Body jsonparms: ProfileImgModel
    ):Call<String>
}