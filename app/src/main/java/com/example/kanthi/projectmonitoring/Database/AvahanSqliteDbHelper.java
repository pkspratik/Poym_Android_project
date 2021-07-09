package com.example.kanthi.projectmonitoring.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.kanthi.projectmonitoring.PoJo.AssignedItems;
import com.example.kanthi.projectmonitoring.PoJo.BOQHeaders;
import com.example.kanthi.projectmonitoring.PoJo.BOQTrailers;
import com.example.kanthi.projectmonitoring.PoJo.CasePackets;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqCategories;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqViews;
import com.example.kanthi.projectmonitoring.PoJo.ChangeRequests;
import com.example.kanthi.projectmonitoring.PoJo.Checklist;
import com.example.kanthi.projectmonitoring.PoJo.ChecklistAnswers;
import com.example.kanthi.projectmonitoring.PoJo.City;
import com.example.kanthi.projectmonitoring.PoJo.Cord;
import com.example.kanthi.projectmonitoring.PoJo.Country;
import com.example.kanthi.projectmonitoring.PoJo.DistributionAreas;
import com.example.kanthi.projectmonitoring.PoJo.DistributionRouteView;
import com.example.kanthi.projectmonitoring.PoJo.DistributionRoutes;
import com.example.kanthi.projectmonitoring.PoJo.DistributionSubAreas;
import com.example.kanthi.projectmonitoring.PoJo.DocumentTypes;
import com.example.kanthi.projectmonitoring.PoJo.Documents;
import com.example.kanthi.projectmonitoring.PoJo.InnerPacks;
import com.example.kanthi.projectmonitoring.PoJo.Inventories;
import com.example.kanthi.projectmonitoring.PoJo.IssueList;
import com.example.kanthi.projectmonitoring.PoJo.IssueTypes;
import com.example.kanthi.projectmonitoring.PoJo.ItemDefinition;
import com.example.kanthi.projectmonitoring.PoJo.ItemFlavours;
import com.example.kanthi.projectmonitoring.PoJo.ItemSubTypes;
import com.example.kanthi.projectmonitoring.PoJo.ItemType;
import com.example.kanthi.projectmonitoring.PoJo.ItemsCategory;
import com.example.kanthi.projectmonitoring.PoJo.MRP;
import com.example.kanthi.projectmonitoring.PoJo.ParamCategories;
import com.example.kanthi.projectmonitoring.PoJo.ParamDetails;
import com.example.kanthi.projectmonitoring.PoJo.Partners;
import com.example.kanthi.projectmonitoring.PoJo.Partnerviews;
import com.example.kanthi.projectmonitoring.PoJo.Patrols;
import com.example.kanthi.projectmonitoring.PoJo.PoNumbers;
import com.example.kanthi.projectmonitoring.PoJo.Priority;
import com.example.kanthi.projectmonitoring.PoJo.ProjectIssues;
import com.example.kanthi.projectmonitoring.PoJo.ProjectPercentages;
import com.example.kanthi.projectmonitoring.PoJo.ProjectProgress;
import com.example.kanthi.projectmonitoring.PoJo.ProjectResources;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRiskViews;
import com.example.kanthi.projectmonitoring.PoJo.ProjectStatuses;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.ResourceProgress;
import com.example.kanthi.projectmonitoring.PoJo.RiskTypes;
import com.example.kanthi.projectmonitoring.PoJo.Role;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentPartnerSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummaries;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.RoutePartnerSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.SalesViews;
import com.example.kanthi.projectmonitoring.PoJo.Sales_Area;
import com.example.kanthi.projectmonitoring.PoJo.Shape;
import com.example.kanthi.projectmonitoring.PoJo.State;
import com.example.kanthi.projectmonitoring.PoJo.Status;
import com.example.kanthi.projectmonitoring.PoJo.SubTaskTypes;
import com.example.kanthi.projectmonitoring.PoJo.SurveyPoints;
import com.example.kanthi.projectmonitoring.PoJo.SurveyPromotions;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.TaskItemLinkView;
import com.example.kanthi.projectmonitoring.PoJo.TaskRemarkLinks;
import com.example.kanthi.projectmonitoring.PoJo.TaskResourceLinkViews;
import com.example.kanthi.projectmonitoring.PoJo.TourTypes;
import com.example.kanthi.projectmonitoring.PoJo.User;
import com.example.kanthi.projectmonitoring.PoJo.WareHouses;
import com.example.kanthi.projectmonitoring.PoJo.Zones;
import com.example.kanthi.projectmonitoring.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/***
 * Created by kanthi on 21/2/18.
 */
public class AvahanSqliteDbHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "poym.db";
    private static final int DATABASE_VERSION = 1;

    Context context;


    public AvahanSqliteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, City.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Role.class);
            TableUtils.createTable(connectionSource, Country.class);
            TableUtils.createTable(connectionSource, State.class);

            TableUtils.createTable(connectionSource, AssignedItems.class);
            TableUtils.createTable(connectionSource, BOQHeaders.class);
            TableUtils.createTable(connectionSource, BOQTrailers.class);
            TableUtils.createTable(connectionSource, CasePackets.class);
            TableUtils.createTable(connectionSource, ChangeReqCategories.class);
            TableUtils.createTable(connectionSource, ChangeRequests.class);
            TableUtils.createTable(connectionSource, ChangeReqViews.class);
            TableUtils.createTable(connectionSource, Cord.class);
            //TableUtils.createTable(connectionSource, DistributionAreas.class);
            TableUtils.createTable(connectionSource, DistributionRoutes.class);
            TableUtils.createTable(connectionSource, DistributionSubAreas.class);
            TableUtils.createTable(connectionSource, Documents.class);
            TableUtils.createTable(connectionSource, DocumentTypes.class);
            TableUtils.createTable(connectionSource, InnerPacks.class);
            TableUtils.createTable(connectionSource, Inventories.class);
            TableUtils.createTable(connectionSource, IssueList.class);
            TableUtils.createTable(connectionSource, IssueTypes.class);
            TableUtils.createTable(connectionSource, ItemDefinition.class);
            TableUtils.createTable(connectionSource, ItemFlavours.class);
            TableUtils.createTable(connectionSource, ItemsCategory.class);
            TableUtils.createTable(connectionSource, ItemSubTypes.class);
            TableUtils.createTable(connectionSource, ItemType.class);
            TableUtils.createTable(connectionSource, MRP.class);
            TableUtils.createTable(connectionSource, ParamCategories.class);
            TableUtils.createTable(connectionSource, ParamDetails.class);
            TableUtils.createTable(connectionSource, Partners.class);
            TableUtils.createTable(connectionSource, Partnerviews.class);
            TableUtils.createTable(connectionSource, Patrols.class);
            TableUtils.createTable(connectionSource, PoNumbers.class);
            TableUtils.createTable(connectionSource, Priority.class);
            TableUtils.createTable(connectionSource, ProjectIssues.class);
            TableUtils.createTable(connectionSource, ProjectPercentages.class);
            TableUtils.createTable(connectionSource, ProjectProgress.class);
            TableUtils.createTable(connectionSource, ProjectRisk.class);
            TableUtils.createTable(connectionSource, ProjectRiskViews.class);
            TableUtils.createTable(connectionSource, ProjectResources.class);
            TableUtils.createTable(connectionSource, ProjectStatuses.class);
            TableUtils.createTable(connectionSource, Promotions.class);
            TableUtils.createTable(connectionSource, Remarks.class);
            TableUtils.createTable(connectionSource, ResourceProgress.class);
            TableUtils.createTable(connectionSource, RiskTypes.class);
            TableUtils.createTable(connectionSource, RouteAssignments.class);
            TableUtils.createTable(connectionSource, RouteAssignmentSummaries.class);
            TableUtils.createTable(connectionSource, RouteAssignmentSummariesViews.class);
            TableUtils.createTable(connectionSource, RouteAssignmentPartnerSummariesViews.class);
            TableUtils.createTable(connectionSource, RouteSalesViews.class);
            //TableUtils.createTable(connectionSource, RoutePartnerSalesViews.class);
            TableUtils.createTable(connectionSource, Sales_Area.class);
            TableUtils.createTable(connectionSource, SalesViews.class);
            //TODO for arraylist
            TableUtils.createTable(connectionSource, Shape.class);
            TableUtils.createTable(connectionSource, Status.class);
            TableUtils.createTable(connectionSource, SubTaskTypes.class);
            TableUtils.createTable(connectionSource, SurveyPoints.class);
            TableUtils.createTable(connectionSource, SurveyPromotions.class);
            TableUtils.createTable(connectionSource, Surveys.class);
            TableUtils.createTable(connectionSource, TaskRemarkLinks.class);
            TableUtils.createTable(connectionSource, TaskResourceLinkViews.class);
            TableUtils.createTable(connectionSource, TourTypes.class);
            TableUtils.createTable(connectionSource, WareHouses.class);
            TableUtils.createTable(connectionSource, Zones.class);
            TableUtils.createTable(connectionSource, DistributionRouteView.class);
            TableUtils.createTable(connectionSource, TaskItemLinkView.class);
            TableUtils.createTable(connectionSource, Checklist.class);
            TableUtils.createTable(connectionSource, ChecklistAnswers.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            // we added the created_date_time column in version 2
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private Dao<City, Integer> cityDao;

    public Dao<City, Integer> getCityDao() throws SQLException {
        if (cityDao == null) {
            cityDao = getDao(City.class);
        }
        return cityDao;
    }

    private RuntimeExceptionDao<City, Integer> cityRuntimeDao;

    public RuntimeExceptionDao<City, Integer> getCityRuntimeDao() throws SQLException {
        if (cityRuntimeDao == null) {
            cityRuntimeDao = getRuntimeExceptionDao(City.class);
        }
        return cityRuntimeDao;
    }

    private Dao<User, Integer> userDao;

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    private RuntimeExceptionDao<User, Integer> userRuntimeDao;

    public RuntimeExceptionDao<User, Integer> getUserRuntimeDao() throws SQLException {
        if (userRuntimeDao == null) {
            userRuntimeDao = getRuntimeExceptionDao(User.class);
        }
        return userRuntimeDao;
    }

    private Dao<Role, Integer> RoleDao;

    public Dao<Role, Integer> getRoleDao() throws SQLException {
        if (RoleDao == null) {
            RoleDao = getDao(Role.class);
        }
        return RoleDao;
    }

    private RuntimeExceptionDao<Role, Integer> RoleRuntimeDao;

    public RuntimeExceptionDao<Role, Integer> getRoleRuntimeDao() throws SQLException {
        if (RoleRuntimeDao == null) {
            RoleRuntimeDao = getRuntimeExceptionDao(Role.class);
        }
        return RoleRuntimeDao;
    }

    private Dao<Country, Integer> CountryDao;

    public Dao<Country, Integer> getCountryDao() throws SQLException {
        if (CountryDao == null) {
            CountryDao = getDao(Country.class);
        }
        return CountryDao;
    }

    private RuntimeExceptionDao<Country, Integer> CountryRuntimeDao;

    public RuntimeExceptionDao<Country, Integer> getCountryRuntimeDao() throws SQLException {
        if (CountryRuntimeDao == null) {
            CountryRuntimeDao = getRuntimeExceptionDao(Country.class);
        }
        return CountryRuntimeDao;
    }

    private Dao<State, Integer> StateDao;

    public Dao<State, Integer> getStateDao() throws SQLException {
        if (StateDao == null) {
            StateDao = getDao(State.class);
        }
        return StateDao;
    }

    private RuntimeExceptionDao<State, Integer> StateRuntimeDao;

    public RuntimeExceptionDao<State, Integer> getStateRuntimeDao() throws SQLException {
        if (StateRuntimeDao == null) {
            StateRuntimeDao = getRuntimeExceptionDao(State.class);
        }
        return StateRuntimeDao;
    }

    ///1
    private Dao<AssignedItems, Integer> AssignedItemsDao;

    public Dao<AssignedItems, Integer> getAssignedItemsDao() throws SQLException {
        if (AssignedItemsDao == null) {
            AssignedItemsDao = getDao(AssignedItems.class);
        }
        return AssignedItemsDao;
    }

    private RuntimeExceptionDao<AssignedItems, Integer> AssignedItemsRuntimeDao;

    public RuntimeExceptionDao<AssignedItems, Integer> getAssignedItemsRuntimeDao() throws SQLException {
        if (AssignedItemsRuntimeDao == null) {
            AssignedItemsRuntimeDao = getRuntimeExceptionDao(AssignedItems.class);
        }
        return AssignedItemsRuntimeDao;
    }

    ///2
    private Dao<BOQHeaders, Integer> BOQHeadersDao;

    public Dao<BOQHeaders, Integer> getBOQHeadersDao() throws SQLException {
        if (BOQHeadersDao == null) {
            BOQHeadersDao = getDao(BOQHeaders.class);
        }
        return BOQHeadersDao;
    }

    private RuntimeExceptionDao<BOQHeaders, Integer> BoqHeadersRuntimeDao;

    public RuntimeExceptionDao<BOQHeaders, Integer> getBoqHeadersRuntimeDao() throws SQLException {
        if (BoqHeadersRuntimeDao == null) {
            BoqHeadersRuntimeDao = getRuntimeExceptionDao(BOQHeaders.class);
        }
        return BoqHeadersRuntimeDao;
    }
    ///3
    private Dao<BOQTrailers, Integer> BOQTrailersDao;

    public Dao<BOQTrailers, Integer> getBOQTraiilersDao() throws SQLException {
        if (BOQTrailersDao == null) {
            BOQTrailersDao = getDao(BOQTrailers.class);
        }
        return BOQTrailersDao;
    }

    private RuntimeExceptionDao<BOQTrailers, Integer> BoqTrailersRuntimeDao;

    public RuntimeExceptionDao<BOQTrailers, Integer> getBoqTrailersRuntimeDao() throws SQLException {
        if (BoqTrailersRuntimeDao == null) {
            BoqTrailersRuntimeDao = getRuntimeExceptionDao(BOQTrailers.class);
        }
        return BoqTrailersRuntimeDao;
    }
    ///4
    private Dao<CasePackets, Integer> casePacketsDao;

    public Dao<CasePackets, Integer> getCasePacketsDao() throws SQLException {
        if (casePacketsDao == null) {
            casePacketsDao = getDao(CasePackets.class);
        }
        return casePacketsDao;
    }

    private RuntimeExceptionDao<CasePackets, Integer> casePacketsRuntimeDao;

    public RuntimeExceptionDao<CasePackets, Integer> getCasePacketsRuntimeDao() throws SQLException {
        if (casePacketsRuntimeDao == null) {
            casePacketsRuntimeDao = getRuntimeExceptionDao(CasePackets.class);
        }
        return casePacketsRuntimeDao;
    }
    ///5
    private Dao<ChangeReqCategories, Integer> ChangeReqCategoriesDao;

    public Dao<ChangeReqCategories, Integer> getChangeReqCategoriesDao() throws SQLException {
        if (ChangeReqCategoriesDao == null) {
            ChangeReqCategoriesDao = getDao(ChangeReqCategories.class);
        }
        return ChangeReqCategoriesDao;
    }

    private RuntimeExceptionDao<ChangeReqCategories, Integer> ChangeReqCategoriesRuntimeDao;

    public RuntimeExceptionDao<ChangeReqCategories, Integer> getChangeReqCategoriesRuntimeDao() throws SQLException {
        if (ChangeReqCategoriesRuntimeDao == null) {
            ChangeReqCategoriesRuntimeDao = getRuntimeExceptionDao(ChangeReqCategories.class);
        }
        return ChangeReqCategoriesRuntimeDao;
    }
    ///6
    private Dao<ChangeRequests, Integer> ChangeRequestsDao;

    public Dao<ChangeRequests, Integer> getChangeRequestsDao() throws SQLException {
        if (ChangeRequestsDao == null) {
            ChangeRequestsDao = getDao(ChangeRequests.class);
        }
        return ChangeRequestsDao;
    }

    private RuntimeExceptionDao<ChangeRequests, Integer> ChangeRequestsRuntimeDao;

    public RuntimeExceptionDao<ChangeRequests, Integer> getChangeRequestsRuntimeDao() throws SQLException {
        if (ChangeRequestsRuntimeDao == null) {
            ChangeRequestsRuntimeDao = getRuntimeExceptionDao(ChangeRequests.class);
        }
        return ChangeRequestsRuntimeDao;
    }
    ///7
    private Dao<ChangeReqViews, Integer> ChangeReqViewsDao;

    public Dao<ChangeReqViews, Integer> getChangeReqViewsDao() throws SQLException {
        if (ChangeReqViewsDao == null) {
            ChangeReqViewsDao = getDao(ChangeReqViews.class);
        }
        return ChangeReqViewsDao;
    }

    private RuntimeExceptionDao<ChangeReqViews, Integer> ChangeReqViewsRuntimeDao;

    public RuntimeExceptionDao<ChangeReqViews, Integer> getChangeReqViewsRuntimeDao() throws SQLException {
        if (ChangeReqViewsRuntimeDao == null) {
            ChangeReqViewsRuntimeDao = getRuntimeExceptionDao(ChangeReqViews.class);
        }
        return ChangeReqViewsRuntimeDao;
    }
    ///8
    private Dao<Cord, Integer> CordDao;

    public Dao<Cord, Integer> getCordDao() throws SQLException {
        if (CordDao == null) {
            CordDao = getDao(Cord.class);
        }
        return CordDao;
    }

    private RuntimeExceptionDao<Cord, Integer> CordRuntimeDao;

    public RuntimeExceptionDao<Cord, Integer> getCordRuntimeDao() throws SQLException {
        if (CordRuntimeDao == null) {
            CordRuntimeDao = getRuntimeExceptionDao(Cord.class);
        }
        return CordRuntimeDao;
    }
    /*///9
    private Dao<DistributionAreas, Integer> DistributionAreasDao;

    public Dao<DistributionAreas, Integer> getDistributionAreasDao() throws SQLException {
        if (DistributionAreasDao == null) {
            DistributionAreasDao = getDao(DistributionAreas.class);
        }
        return DistributionAreasDao;
    }

    private RuntimeExceptionDao<DistributionAreas, Integer> DistributionAreasRuntimeDao;

    public RuntimeExceptionDao<DistributionAreas, Integer> getDistributionAreasRuntimeDao() throws SQLException {
        if (DistributionAreasRuntimeDao == null) {
            DistributionAreasRuntimeDao = getRuntimeExceptionDao(DistributionAreas.class);
        }
        return DistributionAreasRuntimeDao;
    }*/
    ///10
    private Dao<DistributionRoutes, Integer> DistributionRoutesDao;

    public Dao<DistributionRoutes, Integer> getDistributionRoutesDao() throws SQLException {
        if (DistributionRoutesDao == null) {
            DistributionRoutesDao = getDao(DistributionRoutes.class);
        }
        return DistributionRoutesDao;
    }

    private RuntimeExceptionDao<DistributionRoutes, Integer> DistributionRoutesRuntimeDao;

    public RuntimeExceptionDao<DistributionRoutes, Integer> getDistributionRoutesRuntimeDao() throws SQLException {
        if (DistributionRoutesRuntimeDao == null) {
            DistributionRoutesRuntimeDao = getRuntimeExceptionDao(DistributionRoutes.class);
        }
        return DistributionRoutesRuntimeDao;
    }
    ///11
    private Dao<DistributionSubAreas, Integer> DistributionSubAreasDao;

    public Dao<DistributionSubAreas, Integer> getDistributionSubAreasDao() throws SQLException {
        if (DistributionSubAreasDao == null) {
            DistributionSubAreasDao = getDao(DistributionSubAreas.class);
        }
        return DistributionSubAreasDao;
    }

    private RuntimeExceptionDao<DistributionSubAreas, Integer> DistributionSubAreasRuntimeDao;

    public RuntimeExceptionDao<DistributionSubAreas, Integer> getDistributionSubAreasRuntimeDao() throws SQLException {
        if (DistributionSubAreasRuntimeDao == null) {
            DistributionSubAreasRuntimeDao = getRuntimeExceptionDao(DistributionSubAreas.class);
        }
        return DistributionSubAreasRuntimeDao;
    }
    ///12
    private Dao<Documents, Integer> DocumentsDao;

    public Dao<Documents, Integer> getDocumentsDao() throws SQLException {
        if (DocumentsDao == null) {
            DocumentsDao = getDao(Documents.class);
        }
        return DocumentsDao;
    }

    private RuntimeExceptionDao<Documents, Integer> DocumentsRuntimeDao;

    public RuntimeExceptionDao<Documents, Integer> getDocumentsRuntimeDao() throws SQLException {
        if (DocumentsRuntimeDao == null) {
            DocumentsRuntimeDao = getRuntimeExceptionDao(Documents.class);
        }
        return DocumentsRuntimeDao;
    }
    ///13
    private Dao<DocumentTypes, Integer> DocumentTypesDao;

    public Dao<DocumentTypes, Integer> getDocumentTypesDao() throws SQLException {
        if (DocumentTypesDao == null) {
            DocumentTypesDao = getDao(DocumentTypes.class);
        }
        return DocumentTypesDao;
    }

    private RuntimeExceptionDao<DocumentTypes, Integer> DocumentTypesRuntimeDao;

    public RuntimeExceptionDao<DocumentTypes, Integer> getDocumentTypesRuntimeDao() throws SQLException {
        if (DocumentTypesRuntimeDao == null) {
            DocumentTypesRuntimeDao = getRuntimeExceptionDao(DocumentTypes.class);
        }
        return DocumentTypesRuntimeDao;
    }
    ///14
    private Dao<InnerPacks, Integer> InnerPacksDao;

    public Dao<InnerPacks, Integer> getInnerPacksDao() throws SQLException {
        if (InnerPacksDao == null) {
            InnerPacksDao = getDao(InnerPacks.class);
        }
        return InnerPacksDao;
    }

    private RuntimeExceptionDao<InnerPacks, Integer> InnerPacksRuntimeDao;

    public RuntimeExceptionDao<InnerPacks, Integer> getInnerPacksRuntimeDao() throws SQLException {
        if (InnerPacksRuntimeDao == null) {
            InnerPacksRuntimeDao = getRuntimeExceptionDao(InnerPacks.class);
        }
        return InnerPacksRuntimeDao;
    }
    ///15
    private Dao<Inventories, Integer> InventoriesDao;

    public Dao<Inventories, Integer> getInventoriesDao() throws SQLException {
        if (InventoriesDao == null) {
            InventoriesDao = getDao(Inventories.class);
        }
        return InventoriesDao;
    }

    private RuntimeExceptionDao<Inventories, Integer> InventoriesRuntimeDao;

    public RuntimeExceptionDao<Inventories, Integer> getInventoriesRuntimeDao() throws SQLException {
        if (InventoriesRuntimeDao == null) {
            InventoriesRuntimeDao = getRuntimeExceptionDao(Inventories.class);
        }
        return InventoriesRuntimeDao;
    }
    ///16
    private Dao<IssueList, Integer> IssueListDao;

    public Dao<IssueList, Integer> getIssueListDao() throws SQLException {
        if (IssueListDao == null) {
            IssueListDao = getDao(IssueList.class);
        }
        return IssueListDao;
    }

    private RuntimeExceptionDao<IssueList, Integer> IssueListRuntimeDao;

    public RuntimeExceptionDao<IssueList, Integer> getIssueListRuntimeDao() throws SQLException {
        if (IssueListRuntimeDao == null) {
            IssueListRuntimeDao = getRuntimeExceptionDao(IssueList.class);
        }
        return IssueListRuntimeDao;
    }
    ///17
    private Dao<IssueTypes, Integer> IssueTypesDao;

    public Dao<IssueTypes, Integer> getIssueTypesDao() throws SQLException {
        if (IssueTypesDao == null) {
            IssueTypesDao = getDao(IssueTypes.class);
        }
        return IssueTypesDao;
    }

    private RuntimeExceptionDao<IssueTypes, Integer> IssueTypesRuntimeDao;

    public RuntimeExceptionDao<IssueTypes, Integer> getIssueTypesRuntimeDao() throws SQLException {
        if (IssueTypesRuntimeDao == null) {
            IssueTypesRuntimeDao = getRuntimeExceptionDao(IssueTypes.class);
        }
        return IssueTypesRuntimeDao;
    }
    ///18
    private Dao<ItemDefinition, Integer> ItemDefinitionDao;

    public Dao<ItemDefinition, Integer> getItemDefinitionDao() throws SQLException {
        if (ItemDefinitionDao == null) {
            ItemDefinitionDao = getDao(ItemDefinition.class);
        }
        return ItemDefinitionDao;
    }

    private RuntimeExceptionDao<ItemDefinition, Integer> ItemDefinitionRuntimeDao;

    public RuntimeExceptionDao<ItemDefinition, Integer> getItemDefinitionRuntimeDao() throws SQLException {
        if (ItemDefinitionRuntimeDao == null) {
            ItemDefinitionRuntimeDao = getRuntimeExceptionDao(ItemDefinition.class);
        }
        return ItemDefinitionRuntimeDao;
    }
    ///19
    private Dao<ItemFlavours, Integer> ItemFlavoursDao;

    public Dao<ItemFlavours, Integer> getItemFlavoursDao() throws SQLException {
        if (ItemFlavoursDao == null) {
            ItemFlavoursDao = getDao(ItemFlavours.class);
        }
        return ItemFlavoursDao;
    }

    private RuntimeExceptionDao<ItemFlavours, Integer> ItemFlavoursRuntimeDao;

    public RuntimeExceptionDao<ItemFlavours, Integer> getItemFlavoursRuntimeDao() throws SQLException {
        if (ItemFlavoursRuntimeDao == null) {
            ItemFlavoursRuntimeDao = getRuntimeExceptionDao(ItemFlavours.class);
        }
        return ItemFlavoursRuntimeDao;
    }
    ///20
    private Dao<ItemsCategory, Integer> ItemsCategoryDao;

    public Dao<ItemsCategory, Integer> getItemsCategoryDao() throws SQLException {
        if (ItemsCategoryDao == null) {
            ItemsCategoryDao = getDao(ItemsCategory.class);
        }
        return ItemsCategoryDao;
    }

    private RuntimeExceptionDao<ItemsCategory, Integer> ItemsCategoryRuntimeDao;

    public RuntimeExceptionDao<ItemsCategory, Integer> getItemsCategoryRuntimeDao() throws SQLException {
        if (ItemsCategoryRuntimeDao == null) {
            ItemsCategoryRuntimeDao = getRuntimeExceptionDao(ItemsCategory.class);
        }
        return ItemsCategoryRuntimeDao;
    }
    ///21
    private Dao<ItemSubTypes, Integer> ItemSubTypesDao;

    public Dao<ItemSubTypes, Integer> getItemSubTypesDao() throws SQLException {
        if (ItemSubTypesDao == null) {
            ItemSubTypesDao = getDao(ItemSubTypes.class);
        }
        return ItemSubTypesDao;
    }

    private RuntimeExceptionDao<ItemSubTypes, Integer> ItemSubTypesRuntimeDao;

    public RuntimeExceptionDao<ItemSubTypes, Integer> getItemSubTypesRuntimeDao() throws SQLException {
        if (ItemSubTypesRuntimeDao == null) {
            ItemSubTypesRuntimeDao = getRuntimeExceptionDao(ItemSubTypes.class);
        }
        return ItemSubTypesRuntimeDao;
    }
    ///22
    private Dao<ItemType, Integer> ItemTypeDao;

    public Dao<ItemType, Integer> getItemTypeDao() throws SQLException {
        if (ItemTypeDao == null) {
            ItemTypeDao = getDao(ItemType.class);
        }
        return ItemTypeDao;
    }

    private RuntimeExceptionDao<ItemType, Integer> ItemTypeRuntimeDao;

    public RuntimeExceptionDao<ItemType, Integer> getItemTypeRuntimeDao() throws SQLException {
        if (ItemTypeRuntimeDao == null) {
            ItemTypeRuntimeDao = getRuntimeExceptionDao(ItemType.class);
        }
        return ItemTypeRuntimeDao;
    }
    ///23
    private Dao<MRP, Integer> MRPDao;

    public Dao<MRP, Integer> getMRPDao() throws SQLException {
        if (MRPDao == null) {
            MRPDao = getDao(MRP.class);
        }
        return MRPDao;
    }

    private RuntimeExceptionDao<MRP, Integer> MRPRuntimeDao;

    public RuntimeExceptionDao<MRP, Integer> getMRPRuntimeDao() throws SQLException {
        if (MRPRuntimeDao == null) {
            MRPRuntimeDao = getRuntimeExceptionDao(MRP.class);
        }
        return MRPRuntimeDao;
    }
    ///24
    private Dao<ParamCategories, Integer> ParamCategoriesDao;

    public Dao<ParamCategories, Integer> getParamCategoriesDao() throws SQLException {
        if (ParamCategoriesDao == null) {
            ParamCategoriesDao = getDao(ParamCategories.class);
        }
        return ParamCategoriesDao;
    }

    private RuntimeExceptionDao<ParamCategories, Integer> ParamCategoriesRuntimeDao;

    public RuntimeExceptionDao<ParamCategories, Integer> getParamCategoriesRuntimeDao() throws SQLException {
        if (ParamCategoriesRuntimeDao == null) {
            ParamCategoriesRuntimeDao = getRuntimeExceptionDao(ParamCategories.class);
        }
        return ParamCategoriesRuntimeDao;
    }
    ///25
    private Dao<ParamDetails, Integer> ParamDetailsDao;

    public Dao<ParamDetails, Integer> getParamDetailsDao() throws SQLException {
        if (ParamDetailsDao == null) {
            ParamDetailsDao = getDao(ParamDetails.class);
        }
        return ParamDetailsDao;
    }

    private RuntimeExceptionDao<ParamDetails, Integer> ParamDetailsRuntimeDao;

    public RuntimeExceptionDao<ParamDetails, Integer> getParamDetailsRuntimeDao() throws SQLException {
        if (ParamDetailsRuntimeDao == null) {
            ParamDetailsRuntimeDao = getRuntimeExceptionDao(ParamDetails.class);
        }
        return ParamDetailsRuntimeDao;
    }
    ///26
    private Dao<Partners, Integer> PartnersDao;

    public Dao<Partners, Integer> getPartnersDao() throws SQLException {
        if (PartnersDao == null) {
            PartnersDao = getDao(Partners.class);
        }
        return PartnersDao;
    }

    private RuntimeExceptionDao<Partners, Integer> PartnersRuntimeDao;

    public RuntimeExceptionDao<Partners, Integer> getPartnersRuntimeDao() throws SQLException {
        if (PartnersRuntimeDao == null) {
            PartnersRuntimeDao = getRuntimeExceptionDao(Partners.class);
        }
        return PartnersRuntimeDao;
    }
    ///27
    private Dao<Patrols, Integer> PatrolsDao;

    public Dao<Patrols, Integer> getPatrolsDao() throws SQLException {
        if (PatrolsDao == null) {
            PatrolsDao = getDao(Patrols.class);
        }
        return PatrolsDao;
    }

    private RuntimeExceptionDao<Patrols, Integer> PatrolsRuntimeDao;

    public RuntimeExceptionDao<Patrols, Integer> getPatrolsRuntimeDao() throws SQLException {
        if (PatrolsRuntimeDao == null) {
            PatrolsRuntimeDao = getRuntimeExceptionDao(Patrols.class);
        }
        return PatrolsRuntimeDao;
    }
    ///28
    private Dao<PoNumbers, Integer> PoNumbersDao;

    public Dao<PoNumbers, Integer> getPoNumbersDao() throws SQLException {
        if (PoNumbersDao == null) {
            PoNumbersDao = getDao(PoNumbers.class);
        }
        return PoNumbersDao;
    }

    private RuntimeExceptionDao<PoNumbers, Integer> PoNumbersRuntimeDao;

    public RuntimeExceptionDao<PoNumbers, Integer> getPoNumbersRuntimeDao() throws SQLException {
        if (PoNumbersRuntimeDao == null) {
            PoNumbersRuntimeDao = getRuntimeExceptionDao(PoNumbers.class);
        }
        return PoNumbersRuntimeDao;
    }
    ///29
    private Dao<Priority, Integer> PriorityDao;

    public Dao<Priority, Integer> getPriorityDao() throws SQLException {
        if (PriorityDao == null) {
            PriorityDao = getDao(Priority.class);
        }
        return PriorityDao;
    }

    private RuntimeExceptionDao<Priority, Integer> PriorityRuntimeDao;

    public RuntimeExceptionDao<Priority, Integer> getPriorityRuntimeDao() throws SQLException {
        if (PriorityRuntimeDao == null) {
            PriorityRuntimeDao = getRuntimeExceptionDao(Priority.class);
        }
        return PriorityRuntimeDao;
    }
    ///30
    private Dao<ProjectIssues, Integer> ProjectIssuesDao;

    public Dao<ProjectIssues, Integer> getProjectIssuesDao() throws SQLException {
        if (ProjectIssuesDao == null) {
            ProjectIssuesDao = getDao(ProjectIssues.class);
        }
        return ProjectIssuesDao;
    }

    private RuntimeExceptionDao<ProjectIssues, Integer> ProjectIssuesRuntimeDao;

    public RuntimeExceptionDao<ProjectIssues, Integer> getProjectIssuesRuntimeDao() throws SQLException {
        if (ProjectIssuesRuntimeDao == null) {
            ProjectIssuesRuntimeDao = getRuntimeExceptionDao(ProjectIssues.class);
        }
        return ProjectIssuesRuntimeDao;
    }
    ///31
    private Dao<ProjectPercentages, Integer> ProjectPercentagesDao;

    public Dao<ProjectPercentages, Integer> getProjectPercentagesDao() throws SQLException {
        if (ProjectPercentagesDao == null) {
            ProjectPercentagesDao = getDao(ProjectPercentages.class);
        }
        return ProjectPercentagesDao;
    }

    private RuntimeExceptionDao<ProjectPercentages, Integer> ProjectPercentagesRuntimeDao;

    public RuntimeExceptionDao<ProjectPercentages, Integer> getProjectPercentagesRuntimeDao() throws SQLException {
        if (ProjectPercentagesRuntimeDao == null) {
            ProjectPercentagesRuntimeDao = getRuntimeExceptionDao(ProjectPercentages.class);
        }
        return ProjectPercentagesRuntimeDao;
    }
    ///32
    private Dao<ProjectProgress, Integer> ProjectProgressDao;

    public Dao<ProjectProgress, Integer> getProjectProgressDao() throws SQLException {
        if (ProjectProgressDao == null) {
            ProjectProgressDao = getDao(ProjectProgress.class);
        }
        return ProjectProgressDao;
    }

    private RuntimeExceptionDao<ProjectProgress, Integer> ProjectProgressRuntimeDao;

    public RuntimeExceptionDao<ProjectProgress, Integer> getProjectProgressRuntimeDao() throws SQLException {
        if (ProjectProgressRuntimeDao == null) {
            ProjectProgressRuntimeDao = getRuntimeExceptionDao(ProjectProgress.class);
        }
        return ProjectProgressRuntimeDao;
    }
    ///33
    private Dao<ProjectResources, Integer> ProjectResourcesDao;

    public Dao<ProjectResources, Integer> getProjectResourcesDao() throws SQLException {
        if (ProjectResourcesDao == null) {
            ProjectResourcesDao = getDao(ProjectResources.class);
        }
        return ProjectResourcesDao;
    }

    private RuntimeExceptionDao<ProjectResources, Integer> ProjectResourcesRuntimeDao;

    public RuntimeExceptionDao<ProjectResources, Integer> getProjectResourcesRuntimeDao() throws SQLException {
        if (ProjectResourcesRuntimeDao == null) {
            ProjectResourcesRuntimeDao = getRuntimeExceptionDao(ProjectResources.class);
        }
        return ProjectResourcesRuntimeDao;
    }
    ///34
    private Dao<ProjectRisk, Integer> ProjectRiskDao;

    public Dao<ProjectRisk, Integer> getProjectRiskDao() throws SQLException {
        if (ProjectRiskDao == null) {
            ProjectRiskDao = getDao(ProjectRisk.class);
        }
        return ProjectRiskDao;
    }

    private RuntimeExceptionDao<ProjectRisk, Integer> ProjectRiskRuntimeDao;

    public RuntimeExceptionDao<ProjectRisk, Integer> getProjectRiskRuntimeDao() throws SQLException {
        if (ProjectRiskRuntimeDao == null) {
            ProjectRiskRuntimeDao = getRuntimeExceptionDao(ProjectRisk.class);
        }
        return ProjectRiskRuntimeDao;
    }
    ///35
    private Dao<ProjectRiskViews, Integer> ProjectRiskViewsDao;

    public Dao<ProjectRiskViews, Integer> getProjectRiskViewsDao() throws SQLException {
        if (ProjectRiskViewsDao == null) {
            ProjectRiskViewsDao = getDao(ProjectRiskViews.class);
        }
        return ProjectRiskViewsDao;
    }

    private RuntimeExceptionDao<ProjectRiskViews, Integer> ProjectRiskViewsRuntimeDao;

    public RuntimeExceptionDao<ProjectRiskViews, Integer> getProjectRiskViewsRuntimeDao() throws SQLException {
        if (ProjectRiskViewsRuntimeDao == null) {
            ProjectRiskViewsRuntimeDao = getRuntimeExceptionDao(ProjectRiskViews.class);
        }
        return ProjectRiskViewsRuntimeDao;
    }
    ///36
    private Dao<ProjectStatuses, Integer> ProjectStatusesDao;

    public Dao<ProjectStatuses, Integer> getProjectStatusesDao() throws SQLException {
        if (ProjectStatusesDao == null) {
            ProjectStatusesDao = getDao(ProjectStatuses.class);
        }
        return ProjectStatusesDao;
    }

    private RuntimeExceptionDao<ProjectStatuses, Integer> ProjectStatusesRuntimeDao;

    public RuntimeExceptionDao<ProjectStatuses, Integer> getProjectStatusesRuntimeDao() throws SQLException {
        if (ProjectStatusesRuntimeDao == null) {
            ProjectStatusesRuntimeDao = getRuntimeExceptionDao(ProjectStatuses.class);
        }
        return ProjectStatusesRuntimeDao;
    }
    ///37
    private Dao<Promotions, Integer> PromotionsDao;

    public Dao<Promotions, Integer> getPromotionsDao() throws SQLException {
        if (PromotionsDao == null) {
            PromotionsDao = getDao(Promotions.class);
        }
        return PromotionsDao;
    }

    private RuntimeExceptionDao<Promotions, Integer> PromotionsRuntimeDao;

    public RuntimeExceptionDao<Promotions, Integer> getPromotionsRuntimeDao() throws SQLException {
        if (PromotionsRuntimeDao == null) {
            PromotionsRuntimeDao = getRuntimeExceptionDao(Promotions.class);
        }
        return PromotionsRuntimeDao;
    }
    ///38
    private Dao<Remarks, Integer> RemarksDao;

    public Dao<Remarks, Integer> getRemarksDao() throws SQLException {
        if (RemarksDao == null) {
            RemarksDao = getDao(Remarks.class);
        }
        return RemarksDao;
    }

    private RuntimeExceptionDao<Remarks, Integer> RemarksRuntimeDao;

    public RuntimeExceptionDao<Remarks, Integer> getRemarksRuntimeDao() throws SQLException {
        if (RemarksRuntimeDao == null) {
            RemarksRuntimeDao = getRuntimeExceptionDao(Remarks.class);
        }
        return RemarksRuntimeDao;
    }
    ///39
    private Dao<ResourceProgress, Integer> ResourceProgressDao;

    public Dao<ResourceProgress, Integer> getResourceProgressDao() throws SQLException {
        if (ResourceProgressDao == null) {
            ResourceProgressDao = getDao(ResourceProgress.class);
        }
        return ResourceProgressDao;
    }

    private RuntimeExceptionDao<ResourceProgress, Integer> ResourceProgressRuntimeDao;

    public RuntimeExceptionDao<ResourceProgress, Integer> getResourceProgressRuntimeDao() throws SQLException {
        if (ResourceProgressRuntimeDao == null) {
            ResourceProgressRuntimeDao = getRuntimeExceptionDao(ResourceProgress.class);
        }
        return ResourceProgressRuntimeDao;
    }
    ///40
    private Dao<RiskTypes, Integer> RiskTypesDao;

    public Dao<RiskTypes, Integer> getRiskTypesDao() throws SQLException {
        if (RiskTypesDao == null) {
            RiskTypesDao = getDao(RiskTypes.class);
        }
        return RiskTypesDao;
    }

    private RuntimeExceptionDao<RiskTypes, Integer> RiskTypesRuntimeDao;

    public RuntimeExceptionDao<RiskTypes, Integer> getRiskTypesRuntimeDao() throws SQLException {
        if (RiskTypesRuntimeDao == null) {
            RiskTypesRuntimeDao = getRuntimeExceptionDao(RiskTypes.class);
        }
        return RiskTypesRuntimeDao;
    }
    ///41
    private Dao<RouteAssignments, Integer> RouteAssignmentsDao;

    public Dao<RouteAssignments, Integer> getRouteAssignmentsDao() throws SQLException {
        if (RouteAssignmentsDao == null) {
            RouteAssignmentsDao = getDao(RouteAssignments.class);
        }
        return RouteAssignmentsDao;
    }

    private RuntimeExceptionDao<RouteAssignments, Integer> RouteAssignmentsRuntimeDao;

    public RuntimeExceptionDao<RouteAssignments, Integer> getRouteAssignmentsRuntimeDao() throws SQLException {
        if (RouteAssignmentsRuntimeDao == null) {
            RouteAssignmentsRuntimeDao = getRuntimeExceptionDao(RouteAssignments.class);
        }
        return RouteAssignmentsRuntimeDao;
    }
    ///42
    private Dao<RouteAssignmentSummaries, Integer> RouteAssignmentSummariesDao;

    public Dao<RouteAssignmentSummaries, Integer> getRouteAssignmentSummariesDao() throws SQLException {
        if (RouteAssignmentSummariesDao == null) {
            RouteAssignmentSummariesDao = getDao(RouteAssignmentSummaries.class);
        }
        return RouteAssignmentSummariesDao;
    }

    private RuntimeExceptionDao<RouteAssignmentSummaries, Integer> RouteAssignmentSummariesRuntimeDao;

    public RuntimeExceptionDao<RouteAssignmentSummaries, Integer> getRouteAssignmentSummariesRuntimeDao() throws SQLException {
        if (RouteAssignmentSummariesRuntimeDao == null) {
            RouteAssignmentSummariesRuntimeDao = getRuntimeExceptionDao(RouteAssignmentSummaries.class);
        }
        return RouteAssignmentSummariesRuntimeDao;
    }
    ///43
    private Dao<RouteAssignmentSummariesViews, Integer> RouteAssignmentSummariesViewsDao;

    public Dao<RouteAssignmentSummariesViews, Integer> getRouteAssignmentSummariesViewsDao() throws SQLException {
        if (RouteAssignmentSummariesViewsDao == null) {
            RouteAssignmentSummariesViewsDao = getDao(RouteAssignmentSummariesViews.class);
        }
        return RouteAssignmentSummariesViewsDao;
    }

    private RuntimeExceptionDao<RouteAssignmentSummariesViews, Integer> RouteAssignmentSummariesViewsRuntimeDao;

    public RuntimeExceptionDao<RouteAssignmentSummariesViews, Integer> getRouteAssignmentSummariesViewsRuntimeDao() throws SQLException {
        if (RouteAssignmentSummariesViewsRuntimeDao == null) {
            RouteAssignmentSummariesViewsRuntimeDao = getRuntimeExceptionDao(RouteAssignmentSummariesViews.class);
        }
        return RouteAssignmentSummariesViewsRuntimeDao;
    }
    ///44
    private Dao<RouteSalesViews, Integer> RouteSalesViewsDao;

    public Dao<RouteSalesViews, Integer> getRouteSalesViewsDao() throws SQLException {
        if (RouteSalesViewsDao == null) {
            RouteSalesViewsDao = getDao(RouteSalesViews.class);
        }
        return RouteSalesViewsDao;
    }

    private RuntimeExceptionDao<RouteSalesViews, Integer> RouteSalesViewsRuntimeDao;

    public RuntimeExceptionDao<RouteSalesViews, Integer> getRouteSalesViewsRuntimeDao() throws SQLException {
        if (RouteSalesViewsRuntimeDao == null) {
            RouteSalesViewsRuntimeDao = getRuntimeExceptionDao(RouteSalesViews.class);
        }
        return RouteSalesViewsRuntimeDao;
    }
    ///45
    private Dao<Sales_Area, Integer> Sales_AreaDao;

    public Dao<Sales_Area, Integer> getSales_AreaDao() throws SQLException {
        if (Sales_AreaDao == null) {
            Sales_AreaDao = getDao(Sales_Area.class);
        }
        return Sales_AreaDao;
    }

    private RuntimeExceptionDao<Sales_Area, Integer> Sales_AreaRuntimeDao;

    public RuntimeExceptionDao<Sales_Area, Integer> getSales_AreaRuntimeDao() throws SQLException {
        if (Sales_AreaRuntimeDao == null) {
            Sales_AreaRuntimeDao = getRuntimeExceptionDao(Sales_Area.class);
        }
        return Sales_AreaRuntimeDao;
    }
    ///46
    private Dao<SalesViews, Integer> SalesViewsDao;

    public Dao<SalesViews, Integer> getSalesViewsDao() throws SQLException {
        if (SalesViewsDao == null) {
            SalesViewsDao = getDao(SalesViews.class);
        }
        return SalesViewsDao;
    }

    private RuntimeExceptionDao<SalesViews, Integer> SalesViewsRuntimeDao;

    public RuntimeExceptionDao<SalesViews, Integer> getSalesViewsRuntimeDao() throws SQLException {
        if (SalesViewsRuntimeDao == null) {
            SalesViewsRuntimeDao = getRuntimeExceptionDao(SalesViews.class);
        }
        return SalesViewsRuntimeDao;
    }
    ///47
    private Dao<Shape, Integer> ShapeDao;

    public Dao<Shape, Integer> getShapeDao() throws SQLException {
        if (ShapeDao == null) {
            ShapeDao = getDao(Shape.class);
        }
        return ShapeDao;
    }

    private RuntimeExceptionDao<Shape, Integer> ShapeRuntimeDao;

    public RuntimeExceptionDao<Shape, Integer> getShapeRuntimeDao() throws SQLException {
        if (ShapeRuntimeDao == null) {
            ShapeRuntimeDao = getRuntimeExceptionDao(Shape.class);
        }
        return ShapeRuntimeDao;
    }
    ///48
    private Dao<Status, Integer> StatusDao;

    public Dao<Status, Integer> getStatusDao() throws SQLException {
        if (StatusDao == null) {
            StatusDao = getDao(Status.class);
        }
        return StatusDao;
    }

    private RuntimeExceptionDao<Status, Integer> StatusRuntimeDao;

    public RuntimeExceptionDao<Status, Integer> getStatusRuntimeDao() throws SQLException {
        if (StatusRuntimeDao == null) {
            StatusRuntimeDao = getRuntimeExceptionDao(Status.class);
        }
        return StatusRuntimeDao;
    }
    ///49
    private Dao<SubTaskTypes, Integer> SubTaskTypesDao;

    public Dao<SubTaskTypes, Integer> getSubTaskTypesDao() throws SQLException {
        if (SubTaskTypesDao == null) {
            SubTaskTypesDao = getDao(SubTaskTypes.class);
        }
        return SubTaskTypesDao;
    }

    private RuntimeExceptionDao<SubTaskTypes, Integer> SubTaskTypesRuntimeDao;

    public RuntimeExceptionDao<SubTaskTypes, Integer> getSubTaskTypesRuntimeDao() throws SQLException {
        if (SubTaskTypesRuntimeDao == null) {
            SubTaskTypesRuntimeDao = getRuntimeExceptionDao(SubTaskTypes.class);
        }
        return SubTaskTypesRuntimeDao;
    }
    ///50
    private Dao<SurveyPoints, Integer> SurveyPointsDao;

    public Dao<SurveyPoints, Integer> getSurveyPointsDao() throws SQLException {
        if (SurveyPointsDao == null) {
            SurveyPointsDao = getDao(SurveyPoints.class);
        }
        return SurveyPointsDao;
    }

    private RuntimeExceptionDao<SurveyPoints, Integer> SurveyPointsRuntimeDao;

    public RuntimeExceptionDao<SurveyPoints, Integer> getSurveyPointsRuntimeDao() throws SQLException {
        if (SurveyPointsRuntimeDao == null) {
            SurveyPointsRuntimeDao = getRuntimeExceptionDao(SurveyPoints.class);
        }
        return SurveyPointsRuntimeDao;
    }
    ///51
    private Dao<SurveyPromotions, Integer> SurveyPromotionsDao;

    public Dao<SurveyPromotions, Integer> getSurveyPromotionsDao() throws SQLException {
        if (SurveyPromotionsDao == null) {
            SurveyPromotionsDao = getDao(SurveyPromotions.class);
        }
        return SurveyPromotionsDao;
    }

    private RuntimeExceptionDao<SurveyPromotions, Integer> SurveyPromotionsRuntimeDao;

    public RuntimeExceptionDao<SurveyPromotions, Integer> getSurveyPromotionsRuntimeDao() throws SQLException {
        if (SurveyPromotionsRuntimeDao == null) {
            SurveyPromotionsRuntimeDao = getRuntimeExceptionDao(SurveyPromotions.class);
        }
        return SurveyPromotionsRuntimeDao;
    }
    ///52
    private Dao<Surveys, Integer> SurveysDao;

    public Dao<Surveys, Integer> getSurveysDao() throws SQLException {
        if (SurveysDao == null) {
            SurveysDao = getDao(Surveys.class);
        }
        return SurveysDao;
    }

    private RuntimeExceptionDao<Surveys, Integer> SurveysRuntimeDao;

    public RuntimeExceptionDao<Surveys, Integer> getSurveysRuntimeDao() throws SQLException {
        if (SurveysRuntimeDao == null) {
            SurveysRuntimeDao = getRuntimeExceptionDao(Surveys.class);
        }
        return SurveysRuntimeDao;
    }
    ///53
    private Dao<TaskRemarkLinks, Integer> TaskRemarkLinksDao;

    public Dao<TaskRemarkLinks, Integer> getTaskRemarkLinksDao() throws SQLException {
        if (TaskRemarkLinksDao == null) {
            TaskRemarkLinksDao = getDao(TaskRemarkLinks.class);
        }
        return TaskRemarkLinksDao;
    }

    private RuntimeExceptionDao<TaskRemarkLinks, Integer> TaskRemarkLinksRuntimeDao;

    public RuntimeExceptionDao<TaskRemarkLinks, Integer> getTaskRemarkLinksRuntimeDao() throws SQLException {
        if (TaskRemarkLinksRuntimeDao == null) {
            TaskRemarkLinksRuntimeDao = getRuntimeExceptionDao(TaskRemarkLinks.class);
        }
        return TaskRemarkLinksRuntimeDao;
    }
    ///54
    private Dao<TaskResourceLinkViews, Integer> TaskResourceLinkViewsDao;

    public Dao<TaskResourceLinkViews, Integer> getTaskResourceLinkViewsDao() throws SQLException {
        if (TaskResourceLinkViewsDao == null) {
            TaskResourceLinkViewsDao = getDao(TaskResourceLinkViews.class);
        }
        return TaskResourceLinkViewsDao;
    }

    private RuntimeExceptionDao<TaskResourceLinkViews, Integer> TaskResourceLinkViewsRuntimeDao;

    public RuntimeExceptionDao<TaskResourceLinkViews, Integer> getTaskResourceLinkViewsRuntimeDao() throws SQLException {
        if (TaskResourceLinkViewsRuntimeDao == null) {
            TaskResourceLinkViewsRuntimeDao = getRuntimeExceptionDao(TaskResourceLinkViews.class);
        }
        return TaskResourceLinkViewsRuntimeDao;
    }
    ///55
    private Dao<TourTypes, Integer> TourTypesDao;

    public Dao<TourTypes, Integer> getTourTypesDao() throws SQLException {
        if (TourTypesDao == null) {
            TourTypesDao = getDao(TourTypes.class);
        }
        return TourTypesDao;
    }

    private RuntimeExceptionDao<TourTypes, Integer> TourTypesRuntimeDao;

    public RuntimeExceptionDao<TourTypes, Integer> getTourTypesRuntimeDao() throws SQLException {
        if (TourTypesRuntimeDao == null) {
            TourTypesRuntimeDao = getRuntimeExceptionDao(TourTypes.class);
        }
        return TourTypesRuntimeDao;
    }
    ///56
    private Dao<WareHouses, Integer> WareHousesDao;

    public Dao<WareHouses, Integer> getWareHousesDao() throws SQLException {
        if (WareHousesDao == null) {
            WareHousesDao = getDao(WareHouses.class);
        }
        return WareHousesDao;
    }

    private RuntimeExceptionDao<WareHouses, Integer> WareHousesRuntimeDao;

    public RuntimeExceptionDao<WareHouses, Integer> getWareHousesRuntimeDao() throws SQLException {
        if (WareHousesRuntimeDao == null) {
            WareHousesRuntimeDao = getRuntimeExceptionDao(WareHouses.class);
        }
        return WareHousesRuntimeDao;
    }
    ///57
    private Dao<Zones, Integer> ZonesDao;

    public Dao<Zones, Integer> getZonesDao() throws SQLException {
        if (ZonesDao == null) {
            ZonesDao = getDao(Zones.class);
        }
        return ZonesDao;
    }

    private RuntimeExceptionDao<Zones, Integer> ZonesRuntimeDao;

    public RuntimeExceptionDao<Zones, Integer> getZonesRuntimeDao() throws SQLException {
        if (ZonesRuntimeDao == null) {
            ZonesRuntimeDao = getRuntimeExceptionDao(Zones.class);
        }
        return ZonesRuntimeDao;
    }
    ///58
    private Dao<Partnerviews, Integer> PartnerviewsDao;

    public Dao<Partnerviews, Integer> getPartnerviewsDao() throws SQLException {
        if (PartnerviewsDao == null) {
            PartnerviewsDao = getDao(Partnerviews.class);
        }
        return PartnerviewsDao;
    }

    private RuntimeExceptionDao<Partnerviews, Integer> PartnerviewsRuntimeDao;

    public RuntimeExceptionDao<Partnerviews, Integer> getPartnerviewsRuntimeDao() throws SQLException {
        if (PartnerviewsRuntimeDao == null) {
            PartnerviewsRuntimeDao = getRuntimeExceptionDao(Partnerviews.class);
        }
        return PartnerviewsRuntimeDao;
    }

    /*///59
    private Dao<RoutePartnerSalesViews, Integer> RoutePartnerSalesViewsDao;

    public Dao<RoutePartnerSalesViews, Integer> getRoutePartnerSalesViewsDao() throws SQLException {
        if (RoutePartnerSalesViewsDao == null) {
            RoutePartnerSalesViewsDao = getDao(RoutePartnerSalesViews.class);
        }
        return RoutePartnerSalesViewsDao;
    }

    private RuntimeExceptionDao<RoutePartnerSalesViews, Integer> RoutePartnerSalesViewsRuntimeDao;

    public RuntimeExceptionDao<RoutePartnerSalesViews, Integer> getRoutePartnerSalesViewsRuntimeDao() throws SQLException {
        if (RoutePartnerSalesViewsRuntimeDao == null) {
            RoutePartnerSalesViewsRuntimeDao = getRuntimeExceptionDao(RoutePartnerSalesViews.class);
        }
        return RoutePartnerSalesViewsRuntimeDao;
    }*/

    ///60
    private Dao<RouteAssignmentPartnerSummariesViews, Integer> RouteAssignmentPartnerSummariesViewsDao;

    public Dao<RouteAssignmentPartnerSummariesViews, Integer> getRouteAssignmentPartnerSummariesViewsDao() throws SQLException {
        if (RouteAssignmentPartnerSummariesViewsDao == null) {
            RouteAssignmentPartnerSummariesViewsDao = getDao(RouteAssignmentPartnerSummariesViews.class);
        }
        return RouteAssignmentPartnerSummariesViewsDao;
    }

    private RuntimeExceptionDao<RouteAssignmentPartnerSummariesViews, Integer> RouteAssignmentPartnerSummariesViewsRuntimeDao;

    public RuntimeExceptionDao<RouteAssignmentPartnerSummariesViews, Integer> getRouteAssignmentPartnerSummariesViewsRuntimeDao() throws SQLException {
        if (RouteAssignmentPartnerSummariesViewsRuntimeDao == null) {
            RouteAssignmentPartnerSummariesViewsRuntimeDao = getRuntimeExceptionDao(RouteAssignmentPartnerSummariesViews.class);
        }
        return RouteAssignmentPartnerSummariesViewsRuntimeDao;
    }


    ///61
    private Dao<TaskItemLinkView, Integer> TaskItemLinkViewDao;

    public Dao<TaskItemLinkView, Integer> getTaskItemLinkViewDao() throws SQLException {
        if (TaskItemLinkViewDao == null) {
            TaskItemLinkViewDao = getDao(TaskItemLinkView.class);
        }
        return TaskItemLinkViewDao;
    }

    private RuntimeExceptionDao<TaskItemLinkView, Integer> TaskItemLinkViewRuntimeDao;

    public RuntimeExceptionDao<TaskItemLinkView, Integer> getTaskItemLinkViewRuntimeDao() throws SQLException {
        if (TaskItemLinkViewRuntimeDao == null) {
            TaskItemLinkViewRuntimeDao = getRuntimeExceptionDao(TaskItemLinkView.class);
        }
        return TaskItemLinkViewRuntimeDao;
    }


    ///62
    private Dao<DistributionRouteView, Integer> DistributionRouteViewDao;

    public Dao<DistributionRouteView, Integer> getDistributionRouteViewDao() throws SQLException {
        if (DistributionRouteViewDao == null) {
            DistributionRouteViewDao = getDao(DistributionRouteView.class);
        }
        return DistributionRouteViewDao;
    }

    private RuntimeExceptionDao<DistributionRouteView, Integer> DistributionRouteViewRuntimeDao;

    public RuntimeExceptionDao<DistributionRouteView, Integer> getDistributionRouteViewRuntimeDao() throws SQLException {
        if (DistributionRouteViewRuntimeDao == null) {
            DistributionRouteViewRuntimeDao = getRuntimeExceptionDao(DistributionRouteView.class);
        }
        return DistributionRouteViewRuntimeDao;
    }


    ///63
    private Dao<Checklist, Integer> ChecklistDao;

    public Dao<Checklist, Integer> getChecklistDao() throws SQLException {
        if (ChecklistDao == null) {
            ChecklistDao = getDao(Checklist.class);
        }
        return ChecklistDao;
    }

    private RuntimeExceptionDao<Checklist, Integer> ChecklistRuntimeDao;

    public RuntimeExceptionDao<Checklist, Integer> getChecklistRuntimeDao() throws SQLException {
        if (ChecklistRuntimeDao == null) {
            ChecklistRuntimeDao = getRuntimeExceptionDao(Checklist.class);
        }
        return ChecklistRuntimeDao;
    }


    ///64
    private Dao<ChecklistAnswers, Integer> ChecklistAnswersDao;

    public Dao<ChecklistAnswers, Integer> getChecklistAnswersDao() throws SQLException {
        if (ChecklistAnswersDao == null) {
            ChecklistAnswersDao = getDao(ChecklistAnswers.class);
        }
        return ChecklistAnswersDao;
    }

    private RuntimeExceptionDao<ChecklistAnswers, Integer> ChecklistAnswersRuntimeDao;

    public RuntimeExceptionDao<ChecklistAnswers, Integer> getChecklistAnswersRuntimeDao() throws SQLException {
        if (ChecklistAnswersRuntimeDao == null) {
            ChecklistAnswersRuntimeDao = getRuntimeExceptionDao(ChecklistAnswers.class);
        }
        return ChecklistAnswersRuntimeDao;
    }
}
