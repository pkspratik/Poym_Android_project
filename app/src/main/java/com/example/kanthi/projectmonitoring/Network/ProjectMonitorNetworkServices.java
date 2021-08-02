package com.example.kanthi.projectmonitoring.Network;

import com.example.kanthi.projectmonitoring.Canvas.Floor;
import com.example.kanthi.projectmonitoring.Utils.Constants;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/***
 * Created by kanthi on 21/2/18.
 */
public interface ProjectMonitorNetworkServices {

    @FormUrlEncoded
    @POST(Constants.URL_USER_LOGIN)
    Call<String> login(@Field("username") String username,
                       @Field("password") String password);

    @GET(Constants.URL_GET_USERS)
    Call<String> getUser(@Query("access_token") String accessToken, @Query("filter[where][id]") String UserId);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getRouteSalesViews(@Query("access_token") String accessToken, @Query("filter[where][routeassignmentsummaryid]") String salesCode);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getRouteSalesViewsUsername(@Query("access_token") String accessToken, @Query("filter[where][salesCode]") String salesCode);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getRouteSalesViewsId(@Query("access_token") String accessToken, @Query("filter[where][routeassignmentsummaryid]") String id);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getQARouteSalesViews(@Query("access_token") String accessToken, @Query("filter[where][consultUserId]") String salesCode,@Query("filter[where][submitflag]" )Boolean completedFlag);

     @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getQARouteSalesViewsRouteassignmentSummaryId(@Query("access_token") String accessToken, @Query("filter[where][routeassignmentsummaryid]") int routeassignmentsummaryid,@Query("filter[where][qasubmitflag]" )Boolean completedFlag);

    @GET(Constants.URL_GET_SALES_VIEWS)
    Call<String> getSalesViews(@Query("access_token") String accessToken,@Query("filter[where][id]") int partner_id);

    @GET(Constants.URL_GET_SURVEYS)
    Call<String> getSurveys(@Query("access_token") String accessToken,
                            @Query("filter[where][zoneid]") int zoneid,
                            @Query("filter[where][areaid]") int areaid,@Query("filter[where][distareaid]") int distid,
                            @Query("filter[order]") String order);

    @GET(Constants.URL_GET_SURVEYS)
    Call<String> getQASurveys(@Query("access_token") String accessToken,
                            @Query("filter[where][routeassignmentid]") long routesaleviewid, @Query("filter[order]") String order);

    @GET(Constants.URL_GET_STATUS)
    Call<String> getStatus(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_REMARKS)
    Call<String> getRemarks(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_TASK_RESOURCE_LINK_VIEWS)
    Call<String> getTaskResourceLinkViews(@Query("access_token") String accessToken,
                                          @Query("filter[where][tasktypeid]") int tasktypeid,@Query("filter[where][zoneId]") int zoneid);

    @POST(Constants.URL_POST_PROJECT_RESOURCES)
    Call<String> insertProjectResource(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_PUT_CHECKLIST_ANSWERS)
    Call<String> insertChecklistAnswers(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_PONUMBERS)
    Call<String> getPonumbers(@Query("access_token") String accessToken);

    @PUT(Constants.URL_PUT_PONUMBERS)
    Call<String> updatePoNumbers(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_POST_PROMOTIONS)
    Call<String> insertPromotions(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_POST_PROJECT_STATUS)
    Call<String> insertProjectStatus(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_PARAM_CATEGORIES)
    Call<String> getParamCategories(@Query("access_token") String accessToken);

    @POST(Constants.URL_POST_PARAM_DETAILS)
    Call<String> insertParamDetails(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_POST_SURVEYS)
    Call<String> insertSurveys(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_PROMOTIONS)
    Call<String> getPromotions(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zoneid,
                               @Query("filter[where][areaid]") int areaid,@Query("filter[where][distareaid]") int distareaid);

    @GET(Constants.URL_GET_PROMOTIONS)
    Call<String> getQAPromotions(@Query("access_token") String accessToken,@Query("filter[where][routeassignmentid]") long id);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_SUMMARIES_VIEWS)
//    Call<String> getRouteAssignmentsSummariesViews(@Query("access_token") String accessToken,@Query("filter[where][partnerid]") int partnerid,@Query("filter[where][completedFlag]") Boolean completedFlag);
    Call<String> getRouteAssignmentsSummariesViews(@Query("access_token") String accessToken,@Query("filter[where][partnerid]") int partnerid,@Query("filter[where][evFlag]") String  ev);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_SUMMARIES_VIEWS)
    Call<String> getRouteAssignmentsSummariesViewsId(@Query("access_token") String accessToken,@Query("filter[where][id]") int id);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_SUMMARIES_VIEWS)
    Call<String> getQARouteAssignmentsSummariesViews(@Query("access_token") String accessToken,@Query("filter[where][consultUserId]") int consultid,@Query("filter[where][completedFlag]") Boolean completedFlag);

    @POST(Constants.URL_POST_ROUTE_ASSIGNMENTS)
    Call<String> insertRouteAssigments(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @PUT(Constants.URL_PUT_ROUTE_ASSIGNMENTS)
    Call<String> updateRouteAssigments(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @PUT(Constants.URL_PUT_ROUTE_ASSIGMNENT_SUMMARIES)
    Call<String> updateRouteAssigmentSummaries(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_PROJECT_STATUS)
    Call<String> getProjectStatuses(@Query("access_token") String accessToken,@Query("filter[where][id]") long projectstatusId);

    @PUT(Constants.URL_PUT_PROJECT_STATUS)
    Call<String> updateProjectStatuses(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @PUT(Constants.URL_PUT_PROJECT_RESOURCES)
    Call<String> updateProjectResources(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @PUT(Constants.URL_PUT_CHECKLIST_ANSWERS)
    Call<String> updateChecklistAnswers(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_PROJECT_RESOURCES)
    Call<String> getProjectResources(@Query("access_token") String accessToken,@Query("filter[where][routeassignmentid]") int routeassignid);

    @POST(Constants.URL_POST_SURVEYS_PROMOTIONS)
    Call<String> insertSurveyPromotions(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_PROJECT_RESOURCE_LINK_VIEWS)
    Call<String> getProjectResourceLinkViews(@Query("access_token") String accessToken,@Query("filter[where][routeassignid]") int routeassignid);

    @GET(Constants.URL_GET_ITEM_CATEGORIES)
    Call<String> getItemCategories(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_ITEM_TYPES)
    Call<String> getItemTypes(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_ITEM_DEFINITIONS)
    Call<String> getsurveyItemDefinitions(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_ITEM_DEFINITIONS)
    Call<String> getBoqItemDefinitions(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_SURVEYS)
    Call<String> getPendingSurvey(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zoneid,
                                  @Query("filter[where][areaid]") int areaid,@Query("filter[where][distareaid]") int distid,
                              @Query("filter[where][pendingflag]") boolean value,@Query("filter[where][deleteFlag]") boolean value1);

    @GET(Constants.URL_GET_PROJECT_RISK)
    Call<String> getProjectRiskopen(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid,
                                @Query("filter[where][salesareaId]") int areaid,@Query("filter[where][distributionareaId]") int distributionareaId);

    @GET(Constants.URL_GET_CHANGE_REQUESTS)
    Call<String> getChangeRequestopen(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid,
                                  @Query("filter[where][salesareaId]") int areaid,@Query("filter[where][distributionareaId]") int distareaid);

    @GET(Constants.URL_GET_PROJECT_RISK)
    Call<String> getProjectRisk(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid,
    @Query("filter[where][salesareaId]") int areaid,@Query("filter[where][distributionareaId]") int distributionareaId,@Query("filter[where][completedFlag]") Boolean completedFlag);

    @GET(Constants.URL_GET_CHANGE_REQUESTS)
    Call<String> getChangeRequest(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid,
                                  @Query("filter[where][salesareaId]") int areaid,@Query("filter[where][distributionareaId]") int distareaid,@Query("filter[where][completedFlag]") Boolean completedFlag);

    @GET(Constants.URL_GET_RISK_TYPE)
    Call<String> getRiskType(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_CHANGE_CATEGORIES)
    Call<String> getChangeRequestCategories(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PRIORITY)
    Call<String> getPriority(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ISSUETYPES)
    Call<String> getIssueTypes(@Query("access_token") String accessToken);

    @POST(Constants.URL_POST_PROJECT_RISK)
    Call<String> insertProjectRisk(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_POST_CHANGE_REQUESTS)
    Call<String> insertChangeRequest(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @PUT(Constants.URL_POST_ISSUE_LIST)
    Call<String> updateIssueList(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_POST_ISSUE_LIST)
    Call<String> insertIssueList(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_DOCUMENTS)
    Call<String> getExecutionDocument(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_CHANGE_REQUESTS)
    Call<String> getChangeRequestCount(@Query("access_token") String accessToken);

    @POST(Constants.URL_POST_BOQ_HEADERS)
    Call<String> insertBoqHeaders(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_POST_BOQ_TRAILERS)
    Call<String> insertBoqTrailers(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_BOQ_HEADERS)
    Call<String> getBoqHeaders(@Query("access_token") String accessToken);


    @GET(Constants.URL_GET_PROJECT_RISK_VIEWS)
    Call<String> getProjectRiskViews(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zoneid,
                                    @Query("filter[where][salesareaid]") int areaid,@Query("filter[where][distributionareaid]") int distributionareaId,@Query("filter[where][id]") int id);

    @GET(Constants.URL_GET_CHANGE_REQUEST_VIEWS)
    Call<String> getChangeRequestViews(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zoneid,
                                     @Query("filter[where][salesareaid]") int areaid,
                                       @Query("filter[where][distributionareaid]") int distributionareaId,@Query("filter[where][id]") int id);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_SUMMARIES)
    Call<String> getRouteAssignmentsSummaries(@Query("access_token") String accessToken,@Query("filter[where][partnerId]") int partnerid);

    @GET(Constants.URL_GET_ASSIGNED_ITEMS)
    Call<String> getAssignedItems(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid,
                                    @Query("filter[where][salesareaId]") int areaid,@Query("filter[where][distributionareaId]") int distributionareaId);

    @GET(Constants.URL_GET_WARE_HOUSES)
    Call<String> getWareHouses(@Query("access_token") String accessToken,@Query("filter[where][partnerId]") int partnerid);

    @GET(Constants.URL_GET_INVENTORIES)
    Call<String> getInventories(@Query("access_token") String accessToken,@Query("filter[where][warehouseId]") int wh_id);

    @PUT(Constants.URL_PUT_INVENTORIES)
    Call<String> updateinventories(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_POST_ASSIGNED_ITEMS)
    Call<String> insertAssigneditems(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_GET_DOCUMENT_TYPES)
    Call<String> getDocumentType(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_TOUR_TYPES)
    Call<String> getTourType(@Query("access_token") String accessToken);

    @PUT(Constants.URL_PUT_SURVEYS)
    Call<String> updateSurveys(@Query("access_token") String accessToken, @Body JsonObject beneficiary);


    @GET(Constants.URL_GET_CHANGE_REQUEST_VIEWS)
    Call<String> getCRViews(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zoneid,
                                       @Query("filter[where][salesareaid]") int areaid,
                                       @Query("filter[where][distributionareaid]") int distributionareaId);

    @GET(Constants.URL_GET_SUBTASK)
    Call<String> getSubTask(@Query("access_token") String accessToken,@Query("filter[where][tasktypeid]") int tourid);

    @GET(Constants.URL_GET_TASKREMARK_LINKS)
    Call<String> getTaskRemarkLink(@Query("access_token") String accessToken,
                                          @Query("filter[where][tasktypeid]") int tasktypeid);

    @GET(Constants.URL_GET_PARTNERS)
    Call<String> getPartnerviews(@Query("access_token") String accessToken,@Query("filter[where][id]") int partner_id);


    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_PARTNER_SUMMARIES_VIEWS)
//    Call<String> getRouteAssignmentsPartnerSummariesViews(@Query("access_token") String accessToken,@Query("filter[where][partnerid]") int partnerid,@Query("filter[where][completedFlag]") Boolean completedFlag);
    Call<String> getRouteAssignmentsPartnerSummariesViews(@Query("access_token") String accessToken,@Query("filter[where][partnerid]") int partnerid,@Query("filter[where][evFlag]") String ev);


    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_PARTNER_SUMMARIES_VIEWS)
    Call<String> getRouteAssignmentsPartnerSummariesViewsId(@Query("access_token") String accessToken,@Query("filter[where][id]") int id);

    @GET(Constants.URL_GET_ROUTE_PARTNER_SALES_VIEWS)
    Call<String> getRoutePartnerSalesViews(@Query("access_token") String accessToken, @Query("filter[where][routeassignmentsummaryid]") String salesCode);

    @GET(Constants.URL_GET_ROUTE_PARTNER_SALES_VIEWS)
    Call<String> getRoutePartnerSalesViewsUsername(@Query("access_token") String accessToken, @Query("filter[where][salesCode]") String salesCode);

    @GET(Constants.URL_GET_ROUTE_PARTNER_SALES_VIEWS)
    Call<String> getRoutePartnerSalesViewsId(@Query("access_token") String accessToken, @Query("filter[where][routeassignmentsummaryid]") String id);

    @GET(Constants.URL_GET_DISTRIBUTION_AREAS)
    Call<String> getDistributionAreas(@Query("access_token") String accessToken, @Query("filter[where][id]") int dist_id);

    @GET(Constants.URL_GET_DISTRIBUTION_SUB_AREAS)
    Call<String> getDistributionSubArea(@Query("access_token") String accessToken,@Query("filter[where][id]") int id);

    @GET(Constants.URL_GET_DISTRIBUTION_SUB_AREAS)
    Call<String> getDistributionSubAreas(@Query("access_token") String accessToken,@Query("filter[where][distributionAreaId]") int dist_id);

    @GET(Constants.URL_GET_DISTRIBUTION_AREAS+"/{distareaid}")
    Call<Floor> getDistributionAreaShapes(@Path("distareaid") int dist_id, @Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PROJECT_PERCENTAGES)
    Call<String> getProjectPercentages(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zone_id);

    @GET(Constants.URL_GET_PROJECT_PROGRESS)
    Call<String> getProjectProgress(@Query("access_token") String accessToken,@Query("filter[where][projectid]") int id);

    @GET(Constants.URL_GET_PROJECT_ISSUES)
    Call<String> getProjectIssues(@Query("access_token") String accessToken,@Query("filter[where][projectid]") int id);

    @GET(Constants.URL_GET_RESOURCE_PROGRESS)
    Call<String> getResourceProgress(@Query("access_token") String accessToken,@Query("filter[where][projectid]") int id);

    @GET(Constants.URL_GET_PROJECT_RISK_VIEWS)
    Call<String> getProjectRiskViews(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int id);

    @GET(Constants.URL_GET_CHANGE_REQUEST_VIEWS)
    Call<String> getChangeRequestViews(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int id);

    @GET(Constants.URL_GET_ZONES)
    Call<String> getZones(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_SALES_AREAS)
    Call<String> getSalesAreas(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int id);

    @GET(Constants.URL_GET_SURVEYS)
    Call<String> getSurveypoints(@Query("access_token") String accessToken,@Query("filter[where][linkid]") int id);

    @GET(Constants.URL_GET_SURVEY_PROMOTIONS)
    Call<String> getSurveyPrommotions(@Query("access_token") String accessToken,@Query("filter[where][retailerid]") int linkid,@Query("filter[where][retailername]") int zoneid);

    @GET(Constants.URL_GET_DISTRIBUTION_ROUTES)
    Call<String> getDistributionroutes(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int id);

    @GET(Constants.URL_GET_ASSIGNED_ITEMS)
    Call<String> getAssignedItems(@Query("access_token") String accessToken,@Query("filter[where][routeassignmentid]") int id);

    @PUT(Constants.URL_PUT_PROMOTONS)
    Call<String> updatePromotions(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @PUT(Constants.URL_PUT_ASSIGNED_ITEMS)
    Call<String> updateAssigneditems(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @POST(Constants.URL_POST_PATROLS)
    Call<String> insertPatrols(@Query("access_token") String accessToken, @Body JsonObject beneficiary);

    @GET(Constants.URL_POST_PATROLS)
    Call<String> getPatrols(@Query("access_token") String accessToken,@Query("filter[where][projectid]") int project_id,
                            @Query("filter[where][linkid]") int link_id,@Query("filter[where][userid]") String user_id);

    @GET(Constants.URL_GET_ZONES)
    Call<String> getZone(@Query("access_token") String accessToken,@Query("filter[where][id]") int zone_id);

    @GET(Constants.URL_GET_WARE_HOUSES)
    Call<String> getPartnerWareHouse(@Query("access_token") String accessToken,@Query("filter[where][distributionAreaId]") int distareaid,@Query("filter[where][warehousetypeId]") int typeid);

    @GET(Constants.URL_GET_ROUTE_ASSIGNMENTS)
    Call<String> getRouteAssignments(@Query("access_token") String accessToken,@Query("filter[where][routeassignmentsummaryid]") int id);


    //for db
    @POST(Constants.URL_GET_USER_LOGIN_DETAILS)
    Call<String> insertUserLoginDetails(@Query("access_token") String accessToken, @Body JsonObject bulkUpdate);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getDBRouteSalesViews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getDBRouteSalesViewsUsername(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getDBRouteSalesViewsId(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getDBQARouteSalesViews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_SALES_VIEWS)
    Call<String> getDBQARouteSalesViewsRouteassignmentSummaryId(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_SALES_VIEWS)
    Call<String> getDBSalesViews(@Query("access_token") String accessToken,@Query("filter[where][id]") int partner_id);
    //Call<String> getDBSalesViews(@Query("access_token") String accessToken);

//    @GET(Constants.URL_GET_SURVEYS)
//    Call<String> getDBSurveys(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zoneid);

    //i am added
    @GET(Constants.URL_GET_SURVEYS)
    Call<String> getDBSurveys(@Query("access_token") String accessToken,@Query("filter[where][distareaid]") int distareaid);

    @GET(Constants.URL_GET_SURVEYS)
    Call<String> getDBQASurveys(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_STATUS)
    Call<String> getDBStatus(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_REMARKS)
    Call<String> getDBRemarks(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_TASK_RESOURCE_LINK_VIEWS)
    Call<String> getDBTaskResourceLinkViews(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_PONUMBERS)
    Call<String> getDBPonumbers(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PARAM_CATEGORIES)
    Call<String> getDBParamCategories(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneId);

    @GET(Constants.URL_GET_CHECKLISTS)
    Call<String> getChecklists(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneId);

    @GET(Constants.URL_GET_PROMOTIONS)
    Call<String> getDBPromotions(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zoneid);

    @GET(Constants.URL_GET_PROMOTIONS)
    Call<String> getDBQAPromotions(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_SUMMARIES_VIEWS)
//    Call<String> getRouteAssignmentsSummariesViews(@Query("access_token") String accessToken,@Query("filter[where][partnerid]") int partnerid,@Query("filter[where][completedFlag]") Boolean completedFlag);
    Call<String> getDBRouteAssignmentsSummariesViews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_SUMMARIES_VIEWS)
    Call<String> getDBRouteAssignmentsSummariesViewsId(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_SUMMARIES_VIEWS)
    Call<String> getDBQARouteAssignmentsSummariesViews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PROJECT_STATUS)
    //Call<String> getDBProjectStatuses(@Query("access_token") String accessToken);
    Call<String> getDBProjectStatuses(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_PROJECT_RESOURCES)
    Call<String> getDBProjectResources(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PROJECT_RESOURCE_LINK_VIEWS)
    Call<String> getDBProjectResourceLinkViews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ITEM_CATEGORIES)
    Call<String> getDBItemCategories(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_ITEM_TYPES)
    Call<String> getDBItemTypes(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_ITEM_DEFINITIONS)
    Call<String> getDBsurveyItemDefinitions(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_ITEM_DEFINITIONS)
    Call<String> getDBBoqItemDefinitions(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_SURVEYS)
    Call<String> getDBPendingSurvey(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PROJECT_RISK)
    Call<String> getDBProjectRiskopen(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_CHANGE_REQUESTS)
    Call<String> getDBChangeRequestopen(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_PROJECT_RISK)
    Call<String> getDBProjectRisk(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_CHANGE_REQUESTS)
    Call<String> getDBChangeRequest(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_RISK_TYPE)
    Call<String> getDBRiskType(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_CHANGE_CATEGORIES)
    Call<String> getDBChangeRequestCategories(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PRIORITY)
    Call<String> getDBPriority(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ISSUETYPES)
    Call<String> getDBIssueTypes(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_DOCUMENTS)
    Call<String> getDBExecutionDocument(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneid);

    @GET(Constants.URL_GET_CHANGE_REQUESTS)
    Call<String> getDBChangeRequestCount(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_BOQ_HEADERS)
    Call<String> getDBBoqHeaders(@Query("access_token") String accessToken);


    @GET(Constants.URL_GET_PROJECT_RISK_VIEWS)
    Call<String> getDBProjectRiskViews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_CHANGE_REQUEST_VIEWS)
    Call<String> getDBChangeRequestViews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_SUMMARIES)
    Call<String> getDBRouteAssignmentsSummaries(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ASSIGNED_ITEMS)
    Call<String> getDBAssignedItems(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_WARE_HOUSES)
    Call<String> getDBWareHouses(@Query("access_token") String accessToken,@Query("filter[where][partnerId]") int partnerid);

    @GET(Constants.URL_GET_INVENTORIES)
    Call<String> getDBInventories(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_DOCUMENT_TYPES)
    Call<String> getDBDocumentType(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_TOUR_TYPES)
    Call<String> getDBTourType(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_CHANGE_REQUEST_VIEWS)
    Call<String> getDBCRViews(@Query("access_token") String accessToken,@Query("filter[where][zoneid]") int zoneid);

    @GET(Constants.URL_GET_SUBTASK)
    Call<String> getDBSubTask(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_TASKREMARK_LINKS)
    Call<String> getDBTaskRemarkLink(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PARTNERS)
    Call<String> getDBPartnerviews(@Query("access_token") String accessToken,@Query("filter[where][id]") int partner_id);
    //Call<String> getDBPartnerviews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_PARTNER_SUMMARIES_VIEWS)
    //Call<String> getRouteAssignmentsPartnerSummariesViews(@Query("access_token") String accessToken,@Query("filter[where][partnerid]") int partnerid,@Query("filter[where][completedFlag]") Boolean completedFlag);
    Call<String> getDBRouteAssignmentsPartnerSummariesViews(@Query("access_token") String accessToken);


    @GET(Constants.URL_GET_ROUTE_ASSIGMNENT_PARTNER_SUMMARIES_VIEWS)
    Call<String> getDBRouteAssignmentsPartnerSummariesViewsId(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_PARTNER_SALES_VIEWS)
    Call<String> getDBRoutePartnerSalesViews(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_PARTNER_SALES_VIEWS)
    Call<String> getDBRoutePartnerSalesViewsUsername(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_PARTNER_SALES_VIEWS)
    Call<String> getDBRoutePartnerSalesViewsId(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_DISTRIBUTION_AREAS)
    Call<String> getDBDistributionAreas(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_DISTRIBUTION_SUB_AREAS)
    Call<String> getDBDistributionSubArea(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_DISTRIBUTION_SUB_AREAS)
    Call<String> getDBDistributionSubAreas(@Query("access_token") String accessToken);

    //distributionAreaId
    @GET(Constants.URL_GET_DISTRIBUTION_AREAS+"/{distareaid}")
    Call<Floor> getDBDistributionAreaShapes(@Path("distareaid") int dist_id, @Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PROJECT_PERCENTAGES)
    Call<String> getDBProjectPercentages(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PROJECT_PROGRESS)
    Call<String> getDBProjectProgress(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_PROJECT_ISSUES)
    Call<String> getDBProjectIssues(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_RESOURCE_PROGRESS)
    Call<String> getDBResourceProgress(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ZONES)
    Call<String> getDBZones(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_SALES_AREAS)
    Call<String> getDBSalesAreas(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_SURVEYS)
    Call<String> getDBSurveypoints(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_SURVEY_PROMOTIONS)
    Call<String> getDBSurveyPrommotions(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_DISTRIBUTION_ROUTES)
    Call<String> getDBDistributionroutes(@Query("access_token") String accessToken);

    @GET(Constants.URL_POST_PATROLS)
    Call<String> getDBPatrols(@Query("access_token") String accessToken,@Query("filter[where][userid]") Integer user_id);

    @GET(Constants.URL_GET_ZONES)
    Call<String> getDBZone(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_WARE_HOUSES)
    Call<String> getDBPartnerWareHouse(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_ROUTE_ASSIGNMENTS)
    Call<String> getDBRouteAssignments(@Query("access_token") String accessToken);

    @GET(Constants.URL_GET_DISTRIBUTION_ROUTE_VIEWS)
    Call<String> getDBDistributionRouteViews(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneId);

    @GET(Constants.URL_GET_TASK_ITEM_LINK_VIEWS)
    Call<String> getDBTaskItemLinkViews(@Query("access_token") String accessToken,@Query("filter[where][zoneId]") int zoneId);
}


