package com.mncomunity1.api;

import com.mncomunity1.model.Category;
import com.mncomunity1.model.CheckRead;
import com.mncomunity1.model.Delete;
import com.mncomunity1.model.DetailsImage;
import com.mncomunity1.model.DetailsVendor;
import com.mncomunity1.model.GetCode;
import com.mncomunity1.model.GetLog;
import com.mncomunity1.model.GetVendorBanner;
import com.mncomunity1.model.HistoryOrder;
import com.mncomunity1.model.MSG;
import com.mncomunity1.model.ModelSpare;
import com.mncomunity1.model.ModelSpareDetails;
import com.mncomunity1.model.ModelVP;
import com.mncomunity1.model.News;
import com.mncomunity1.model.NewsRc;
import com.mncomunity1.model.Order;
import com.mncomunity1.model.OrderForDetails;
import com.mncomunity1.model.OrderVendor;
import com.mncomunity1.model.Post;
import com.mncomunity1.model.PostError;
import com.mncomunity1.model.PostOrder;
import com.mncomunity1.model.PostS;
import com.mncomunity1.model.PostUpdateStatus;
import com.mncomunity1.model.Profile;
import com.mncomunity1.model.Register;
import com.mncomunity1.model.SearchModel;
import com.mncomunity1.model.SearchSpare;
import com.mncomunity1.model.SubGroup;
import com.mncomunity1.model.Update;
import com.mncomunity1.model.UpdateToken;
import com.mncomunity1.model.getOrder;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {


    //    @GET("/community_service/select_feed.php")
//    void getFeed(Callback<Post> callback);
    @FormUrlEncoded
    @POST("/web/api/getNewsDemo2.php")
    Call<News> getFeed(@Field("user_id") String cat);


    @FormUrlEncoded
    @POST("/web/api/getNewsArr.php")
    Call<NewsRc> getNewsArr(@Field("cat") String cat, @Field("id_content") String id_content);


    @FormUrlEncoded
    @POST("/web/api/postUpdateCount.php")
    Call<PostUpdateStatus> getUpdateStatus(@Field("id_user") String id, @Field("id_content") String cat);

    @FormUrlEncoded
    @POST("/web/api/update_member_edittext.php")
    Call<PostOrder> postUpdateEdit(@Field("user_id") String user_id,
                                   @Field("nameth") String nameth,
                                   @Field("email") String email,
                                   @Field("pass") String pass,
                                   @Field("phone") String phone,
                                   @Field("address") String address);


    @FormUrlEncoded
    @POST("/web/api/check_read.php")
    Call<CheckRead> getCheckRead(@Field("user_id") String user_id);

    @GET("/community_service/baner_feed.php")
    Call<ModelVP> getBandner();

    @GET("/web/api/get_vendor_banner.php")
    Call<GetVendorBanner> getVendorBandner();


    @GET("/community_service/select_train.php?user=chonlakant&art=mt")
    Call<PostS> getTrian();

    @GET("/community_service/select_art_list.php")
    Call<Post> getArt();


    @GET("/community_service/select_success.php")
    Call<Post> getSuccess();

    @GET("/community_service/select_art.php?user=&sel=article_is&nam=บทความเชิงเทคนิค (Technical Skills)&art=article_is")
    Call<Post> getArtList();

    @GET("/community_service/list_techical.php")
    Call<Post> getArtList2();

    @GET("/community_service/select_art_new.php?user=&sel=com_cmms&nam=บทความ CMMS&art=article_cmms")
    Call<Post> getArtList3();


    @GET("/community_service/select_tip.php?user=")
    Call<Post> getTip();

    @GET("")
    Call<Post> getVideo();

    @GET("/web/api/getSpareCompany2.php?keyword=&cat")
    Call<SearchModel> getSearch(@Query("keyword") String keyword,
                                @Query("cat") String cat, @Query("vendor") String vendor);


    @GET("/web/api/getSpareImage.php?")
    Call<DetailsImage> getDetailsImage(@Query("code") String keyword);


    @FormUrlEncoded
    @POST("/web/api/getSpareCompany.php")
    Call<ModelSpare> getSpare(@Field("company_code") String company_code);


    @GET("/web/getagory.php")
    Call<Category> getCategory();

    @FormUrlEncoded
    @POST("/web/api/serarch_spare.php")
    Call<SearchSpare> getSearchSpare(@Field("cat") String cat);


    @FormUrlEncoded
    @POST("/web/get_ver_code.php")
    Call<GetCode> getCode(@Field("code_company") String code_company);

    @FormUrlEncoded
    @POST("web/api/getCopany.php")
    Call<ModelSpareDetails> getSpareCat(@Field("cat") String cat, @Field("sub") String sub);

    @FormUrlEncoded
    @POST("web/api/getSubGroup.php")
    Call<SubGroup> getGroupSub(@Field("cat") String cat);

    @FormUrlEncoded
    @POST("/web/api/post_order.php")
    Call<PostOrder> postOrder(@Field("user_id") String userId,
                              @Field("nameth") String nameTh, @Field("code") String code, @Field("total") String total, @Field("image") String image, @Field("company_code") String company_code);


    @FormUrlEncoded
    @POST("/web/api/post_order_edit.php")
    Call<PostOrder> postOrderEdit(@Field("total") String total, @Field("id") String id);

    @FormUrlEncoded
    @POST("/web/api/post_order_delete.php")
    Call<PostOrder> postOrderDelete(@Field("id") String id);


    @FormUrlEncoded
    @POST("/web/api/post_order_complete.php")
    Call<PostOrder> postCompalte(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("/web/api/post_msg_chat.php")
    Call<PostOrder> postMsgChat(@Field("email") String user_id, @Field("msg") String msg, @Field("roomId") String roomId, @Field("userName") String userName);


    @FormUrlEncoded
    @POST("/web/api/delete_order_by_id.php")
    Call<Delete> deleteOrder(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("/web/api/get_order_by_id.php")
    Call<getOrder> getOrder(@Field("user_id") String getOrder);


    @FormUrlEncoded
    @POST("/web/api/login.php")
    Call<MSG> userLogIn(@Field("email") String email,
                        @Field("pass") String password,
                        @Field("regid") String regid);


    @FormUrlEncoded
    @POST("/web/api/update_token_user_id.php")
    Call<UpdateToken> updateToken(@Field("user_id") String user_id,
                                  @Field("token") String token);

    @FormUrlEncoded
    @POST("/web/insert/post_error.php")
    Call<PostError> getError(@Field("name") String name,
                             @Field("phone") String phone,
                             @Field("email") String email,
                             @Field("details") String details,
                             @Field("name_company") String name_company,
                             @Field("userId") String userId, @Field("type") String type);


    @FormUrlEncoded
    @POST("/web/insert/post_po.php")
    Call<PostError> postPo(@Field("title_product") String name,
                           @Field("company") String phone,
                           @Field("name") String email,
                           @Field("email") String details,
                           @Field("phone") String name_company,
                           @Field("user_id") String userId,
                           @Field("number") String number);


    @FormUrlEncoded
    @POST("/web/get/get_error.php")
    Call<GetLog> getLogError(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("/web/api/get_order_complete.php")
    Call<HistoryOrder> getHistoryVender(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("/web/api/get_order_user.php")
    Call<Order> getOrderHistory(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("/web/api/get_order.php")
    Call<DetailsVendor> getOrderHistoryMe(@Field("user_id") String userId, @Field("company_code") String company_code);

    @FormUrlEncoded
    @POST("/web/api/get_order_for_vendor.php")
    Call<OrderForDetails> getOrderHistoryVendor(@Field("user_id") String userId, @Field("company_code") String company_code);


    @FormUrlEncoded
    @POST("/web/api/get_profile.php")
    Call<Profile> getProfile(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("/web/api/get_order_complete.php")
    Call<OrderVendor> getOrderForVendor(@Field("company_code") String company_code);

    @FormUrlEncoded
    @POST("/web/api/update_order_status.php")
    Call<Update> upDate(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("/web/insert/post_contact.php")
    Call<PostError> getContact(@Field("name") String name,
                               @Field("phone") String phone,
                               @Field("email") String email,
                               @Field("details") String details,
                               @Field("name_company") String name_company);


    @FormUrlEncoded
    @POST("/web/api/register_menber.php")
    Call<Register> getRegister(@Field("nameth") String name,
                               @Field("lastName") String lastname,
                               @Field("email") String email,
                               @Field("phone") String phone,
                               @Field("address") String address,
                               @Field("regid") String regid,
                               @Field("company_nameth") String company_nameth,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("/web/api/register_menber_update.php")
    Call<Register> getRegisterUpdate(@Field("nameth") String name,
                                     @Field("lastName") String lastname,
                                     @Field("email") String email,
                                     @Field("phone") String phone,
                                     @Field("address") String address,
                                     @Field("regid") String regid,
                                     @Field("company_nameth") String company_nameth,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("/web/api/register_menber_one.php")
    Call<Register> postRegisterOne(@Field("regid") String regid);


}
