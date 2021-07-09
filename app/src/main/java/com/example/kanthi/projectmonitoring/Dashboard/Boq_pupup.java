package com.example.kanthi.projectmonitoring.Dashboard;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.AssignedItem_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.QAAssignedItem_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.ResourceList_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.TaskRemarks_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.AssignedItems;
import com.example.kanthi.projectmonitoring.PoJo.Inventories;
import com.example.kanthi.projectmonitoring.PoJo.ItemDefinition;
import com.example.kanthi.projectmonitoring.PoJo.ProjectResources;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.TaskRemarkLinks;
import com.example.kanthi.projectmonitoring.PoJo.TaskResourceLinkViews;
import com.example.kanthi.projectmonitoring.PoJo.WareHouses;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Boq_pupup extends DialogFragment {
    private TextView or_title,or_subtype;
    private RecyclerView rv_Assignitem;
    private Button bt_ok,bt_cancel;
    AlertDialog mDialog=null;
    private List<ItemDefinition> mitemdefinition;
    private List<WareHouses> mwarehouses;
    private List<AssignedItems> massignedItems;
    private List<Inventories> minventories;
    private AssignedItems assignedItems;
    private int routepartnerid,warehouseid=0;
    int trailerCount=0,qaassignitemCount=0;
    int count=0;
    private ProgressBar progress_Bar;
    private ArrayList<Inventories> minventoryarray;
    private ArrayList<AssignedItems> assignedItemsArrayList;
    private ArrayList<Inventories> minventory;
    private List<AssignedItems> massigneditem;
    private ArrayList<AssignedItems> massigneditemQAarry;
    ProgressDialog Dialog1;

    AvahanSqliteDbHelper mDbHelper;
    public Boq_pupup() {
        // Required empty public constructor
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ab=new AlertDialog.Builder(getActivity());
        View v=getActivity().getLayoutInflater().inflate(R.layout.execution_order_popup,null);
        ab.setView(v);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        or_title= v.findViewById(R.id.order_title);
        or_subtype= v.findViewById(R.id.order_subtask);
        progress_Bar=v.findViewById(R.id.progressbar);
        rv_Assignitem = v.findViewById(R.id.rvadditem);
        bt_ok =  v.findViewById(R.id.popup_ok);
        bt_cancel = v.findViewById(R.id.popup_cancel);
        mDialog=ab.create();
        progress_Bar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.poym), PorterDuff.Mode.MULTIPLY);
        Dialog1 = new ProgressDialog(getActivity());
        Dialog1.setMessage("Loading...");
        Dialog1.setCancelable(false);
        or_subtype.setText(AppPreferences.getSubTaskType(getActivity())==null?"":AppPreferences.getSubTaskType(getActivity()));

        //networkitemdefinitions();

        Dialog1.show();
        progress_Bar.setVisibility(View.VISIBLE);

        new FetchDetailsFromDbTask().execute();
        //Toast.makeText(getActivity(), ""+AppPreferences.getRouteAssignmentSummaryId(getActivity()), Toast.LENGTH_SHORT).show();
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                /*android.app.AlertDialog.Builder builder1=new android.app.AlertDialog.Builder(ProjectMonitorActivity.this);
                View v = LayoutInflater.from(ProjectMonitorActivity.this).inflate(R.layout.confirmpopup, null);
                builder1.setView(v);
                TextView tv_message= (TextView) v.findViewById(R.id.confirm_message);
                Button bt_ok= (Button) v.findViewById(R.id.confirm_ok);
                Button bt_cancel= (Button) v.findViewById(R.id.confirm_cancel);
                final android.app.AlertDialog mDialog = builder1.create();
                bt_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSearchOptionsDialog.dismiss();
                        mDialog.dismiss();
                    }
                });
                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                tv_message.setText(" Complete This Order ?");
                builder1.setCancelable(false);
                mDialog.show();*/
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long masterid = System.currentTimeMillis();
                minventory = new ArrayList<Inventories>();
                minventoryarray=new ArrayList<Inventories>();
                assignedItemsArrayList=new ArrayList<AssignedItems>();
                /// for qa
                massigneditem=new ArrayList<AssignedItems>();
                //massigneditemQAarry=new ArrayList<AssignedItems>();
                if(AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41){
                    try {
                        for(AssignedItems items : massignedItems){
                            if(items.getItemapproved()!=null){
                                massigneditem.add(items);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(massigneditem.size()<=0) {
                        android.app.AlertDialog.Builder builder1=new android.app.AlertDialog.Builder(getActivity());
                        View v1 = LayoutInflater.from(getActivity()).inflate(R.layout.confirmpopup, null);
                        builder1.setView(v1);
                        TextView tv_message= (TextView) v1.findViewById(R.id.confirm_message);
                        Button bt_ok= (Button) v1.findViewById(R.id.confirm_ok);
                        Button bt_cancel= (Button) v1.findViewById(R.id.confirm_cancel);
                        final android.app.AlertDialog boqdailog = builder1.create();
                        bt_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                boqdailog.dismiss();
                            }
                        });
                        bt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AppPreferences.setBoqQAFlag(getActivity(),true);
                                boqdailog.dismiss();
                                mDialog.dismiss();
                            }
                        });
                        tv_message.setText("Enter BOQ Items to Continue..");
                        builder1.setCancelable(false);
                        boqdailog.show();
                    }else{
                        for(int i=0;i<massigneditem.size();i++){
                            /*massigneditem.get(i).setQatime(massigneditem.get(i).getItemapproved());
                            massigneditem.get(i).setId(massigneditem.get(i).getItemid());
                            massigneditemQAarry.add(massigneditem.get(i));*/
                            try {
                                RuntimeExceptionDao<AssignedItems,Integer> assignedItems=mDbHelper.getAssignedItemsRuntimeDao();
                                UpdateBuilder<AssignedItems,Integer> updateBuilder=assignedItems.updateBuilder();
                                updateBuilder.where().eq("id",massigneditem.get(i).getItemid());
                                updateBuilder.updateColumnValue("qatime",massigneditem.get(i).getItemapproved());
                                updateBuilder.updateColumnValue("updateflag",true);

                                updateBuilder.update();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        AppPreferences.setBoqQAFlag(getActivity(),true);
                        mDialog.dismiss();
                        //putQaAssignitems();
                    }
                }else if(AppPreferences.getGroupId(getActivity())==14||AppPreferences.getGroupId(getActivity())==29||AppPreferences.getGroupId(getActivity())==17||AppPreferences.getGroupId(getActivity())==18){
                    try {
                        for (Inventories inventory : minventories) {
                            if (inventory.getEnteredquantity() != null) {
                                minventory.add(inventory);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(minventory.size()<=0) {
                        android.app.AlertDialog.Builder builder1=new android.app.AlertDialog.Builder(getActivity());
                        View v1 = LayoutInflater.from(getActivity()).inflate(R.layout.confirmpopup, null);
                        builder1.setView(v1);
                        TextView tv_message= (TextView) v1.findViewById(R.id.confirm_message);
                        Button bt_ok= (Button) v1.findViewById(R.id.confirm_ok);
                        Button bt_cancel= (Button) v1.findViewById(R.id.confirm_cancel);
                        final android.app.AlertDialog boqdailog = builder1.create();
                        bt_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                boqdailog.dismiss();
                            }
                        });
                        bt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AppPreferences.setBoqFlag(getActivity(),true);
                                boqdailog.dismiss();
                                mDialog.dismiss();
                            }
                        });
                        tv_message.setText("Enter BOQ Items to Continue..");
                        builder1.setCancelable(false);
                        boqdailog.show();
                    }else {
                        for(int i=0;i<minventory.size();i++){
                            try {
                                RuntimeExceptionDao<Inventories,Integer> inventoriesDao = mDbHelper.getInventoriesRuntimeDao();
                                UpdateBuilder<Inventories,Integer> updateBuilder=inventoriesDao.updateBuilder();
                                updateBuilder.updateColumnValue("quantitytransfer",(minventory.get(i).getQuantitytransfer()!=null?minventory.get(i).getQuantitytransfer():0)+minventory.get(i).getEnteredquantity());
                                updateBuilder.updateColumnValue("updateflag",true);
                                updateBuilder.where().eq("id",minventory.get(i).getInventoryid());
                                updateBuilder.update();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            minventory.get(i).setQuantitytransfer((minventory.get(i).getQuantitytransfer()!=null?minventory.get(i).getQuantitytransfer():0)+minventory.get(i).getEnteredquantity());
                            minventory.get(i).setId(minventory.get(i).getInventoryid());
                            minventoryarray.add(minventory.get(i));
                        }
                        Log.d("inventoryarray",""+minventoryarray.size());
                        for(int j=0;j<minventoryarray.size();j++){
                            assignedItems=new AssignedItems();
                            assignedItems.setId(System.currentTimeMillis());
                            assignedItems.setZoneId(AppPreferences.getZoneId(getActivity()));
                            assignedItems.setSalesAreaId(AppPreferences.getPrefAreaId(getActivity()));
                            assignedItems.setDistributionAreaId(AppPreferences.getDist_Area_Id(getActivity()));
                            assignedItems.setPartnerId(AppPreferences.getEmployeeId(getActivity()));
                            assignedItems.setWarehousetype("e");
                            assignedItems.setTasktypeid(AppPreferences.getTourTypeId(getActivity()));
                            assignedItems.setSubtasktypeid(AppPreferences.getSubTaskId(getActivity()));
                            assignedItems.setAreaid(AppPreferences.getSubTypeId(getActivity()));
                            assignedItems.setLinkid(minventory.get(j).getId());
                            assignedItems.setManagerid(AppPreferences.getSaleMngrIdId(getActivity()));
                            assignedItems.setWarehouseid(warehouseid);
                            assignedItems.setItemdefinitionid(minventory.get(j).getItemnameid());
                            assignedItems.setQuantityinput(minventories.get(j).getQuantityinput());
                            assignedItems.setQuantitytransfer(String.valueOf(minventory.get(j).getEnteredquantity()));
                            assignedItems.setDate(AppUtilities.getDateTime());
                            assignedItems.setRouteassignmentid(AppPreferences.getRouteAssignmentId(getActivity()));
                            assignedItems.setRouteassignmentsummaryid(AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                            assignedItems.setLatitude(AppPreferences.getImageLatitude(getActivity()));
                            assignedItems.setLongitude(AppPreferences.getImageLongitude(getActivity()));
                            assignedItems.setInsertFlag(true);
                            assignedItemsArrayList.add(assignedItems);
                            /*try {
                                RuntimeExceptionDao<AssignedItems,Integer> assignedItemsRuntimeDao=mDbHelper.getAssignedItemsRuntimeDao();
                                assignedItemsRuntimeDao.create(assignedItems);
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/

                        }
                        insertassigneditems();
                        //putinventories();
                        AppPreferences.setBoqFlag(getActivity(),true);
                        mDialog.dismiss();
                    }
                }else if(AppPreferences.getGroupId(getActivity())==39){
                    mDialog.dismiss();
                }
            }
        });
        return mDialog;
    }

    private void insertassigneditems(){
        Log.d("assigneditems",""+assignedItemsArrayList.size());
        for(int i=0;i<=assignedItemsArrayList.size();i++){
            try {
                RuntimeExceptionDao<AssignedItems,Integer> assignedItemsRuntimeDao=mDbHelper.getAssignedItemsRuntimeDao();
                assignedItemsRuntimeDao.create(assignedItemsArrayList.get(i));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void networkitemdefinitions(){
        Dialog1.show();
        progress_Bar.setVisibility(View.VISIBLE);
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getBoqItemDefinitions(AppPreferences.getUserId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ItemDefinition> itemDefinitions = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ItemDefinition>>() {
                        }.getType());
                mitemdefinition = itemDefinitions;
                if(AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41||AppPreferences.getGroupId(getActivity())==39){
                    networkassigneditems();
                }else{
                    networkwarehouses();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void networkassigneditems(){
        ProjectMonitorNetworkServices service2 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call2 = service2.getAssignedItems(AppPreferences.getUserId(getActivity()), (int) AppPreferences.getRouteAssignmentId(getActivity()));
        call2.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<AssignedItems> assignedItems = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<AssignedItems>>() {
                        }.getType());
                massignedItems = assignedItems;
                Dialog1.dismiss();
                progress_Bar.setVisibility(View.GONE);
                if (massignedItems.size()>0){
                    for(AssignedItems assignedItems1:massignedItems){
                        for(int i=0;i<mitemdefinition.size();i++){
                            if(assignedItems1.getItemdefinitionid()==mitemdefinition.get(i).getId()){
                                assignedItems1.setItemname(mitemdefinition.get(i).getName());
                                break;
                            }
                        }
                    }
                }
                QAAssignedItem_Rv_Adapter adapter = new QAAssignedItem_Rv_Adapter(massignedItems);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                rv_Assignitem.setAdapter(adapter);
                rv_Assignitem.setLayoutManager(layoutManager);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    private void networkwarehouses(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 =null;
        if(AppPreferences.getGroupId(getActivity())==17 || AppPreferences.getGroupId(getActivity())==18){
            call1= service1.getPartnerWareHouse(AppPreferences.getUserId(getActivity()),AppPreferences.getDist_Area_Id(getActivity()),11);
        }else{
            call1= service1.getWareHouses(AppPreferences.getUserId(getActivity()),AppPreferences.getEmployeeId(getActivity()));
        }if(call1!=null){
            call1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<WareHouses> wareHouses = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<WareHouses>>() {
                            }.getType());
                    mwarehouses = wareHouses;
                    if(AppPreferences.getGroupId(getActivity())==17 || AppPreferences.getGroupId(getActivity())==18){
                        warehouseid = mwarehouses.get(0).getId();
                    }else{
                        for(int i=0;i<mwarehouses.size();i++){
                            warehouseid = mwarehouses.get(i).getId();
                        }
                    }
                    networkinventory();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else {
            Dialog1.dismiss();
            Toast.makeText(getActivity(), getString(R.string.check_groupid), Toast.LENGTH_SHORT).show();
        }
    }

    private void networkinventory(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getInventories(AppPreferences.getUserId(getActivity()),warehouseid);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Inventories> inventories = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Inventories>>() {
                        }.getType());
                minventories = inventories;
                Dialog1.dismiss();
                progress_Bar.setVisibility(View.GONE);
                if (minventories.size() > 0) {
                    for (Inventories items : minventories) {
                        for (int i = 0; i < mitemdefinition.size(); i++) {
                            if (items.getItemdefinitionId() == mitemdefinition.get(i).getId()) {
                                items.setItemname(mitemdefinition.get(i).getName());
                                break;
                            }
                        }
                    }
                }
                AssignedItem_Rv_Adapter adapter = new AssignedItem_Rv_Adapter(minventories, mDialog);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                rv_Assignitem.setAdapter(adapter);
                rv_Assignitem.setLayoutManager(layoutManager);
                rv_Assignitem.setItemViewCacheSize(minventories.size());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void putinventories(){
        Dialog1.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson= parser.parse(new Gson().toJson(
                    minventoryarray.get(trailerCount), Inventories.class)).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateinventories(AppPreferences.getUserId(getActivity()), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    trailerCount++;
                    if(trailerCount<minventoryarray.size()){
                        putinventories();
                    }else {
                        postAssignedItems();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Dialog1.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void postAssignedItems(){
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson= parser.parse(new Gson().toJson(
                    assignedItemsArrayList.get(count), AssignedItems.class)).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        benjson.remove("id");
        call = service.insertAssigneditems(AppPreferences.getUserId(getActivity()), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    count++;
                    if(count<assignedItemsArrayList.size()){
                        postAssignedItems();
                    }else {
                        Dialog1.dismiss();
                        AppPreferences.setBoqFlag(getActivity(),true);
                        mDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Dialog1.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void putQaAssignitems(){
        Dialog1.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson= parser.parse(new Gson().toJson(
                    massigneditemQAarry.get(qaassignitemCount), AssignedItems.class)).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateAssigneditems(AppPreferences.getUserId(getActivity()), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    qaassignitemCount++;
                    if(qaassignitemCount<massigneditemQAarry.size()){
                        Log.d("qacount",""+qaassignitemCount);
                        putQaAssignitems();
                    }else {
                        Dialog1.dismiss();
                        AppPreferences.setBoqQAFlag(getActivity(),true);
                        mDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Dialog1.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                RuntimeExceptionDao<ItemDefinition,Integer> itemDefinitionsDoa =mDbHelper.getItemDefinitionRuntimeDao();
                mitemdefinition = itemDefinitionsDoa.queryForAll();

                if(AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41||AppPreferences.getGroupId(getActivity())==39){
                    RuntimeExceptionDao<AssignedItems, Integer> assignedItemsDoa = mDbHelper.getAssignedItemsRuntimeDao();
                    QueryBuilder<AssignedItems, Integer> assignedItemsQueryBuilder = assignedItemsDoa.queryBuilder();
                    assignedItemsQueryBuilder.where().eq("routeassignmentid",AppPreferences.getRouteAssignmentId(getActivity()));
                    PreparedQuery<AssignedItems> preparedQuery = assignedItemsQueryBuilder.prepare();
                    massignedItems = assignedItemsDoa.query(preparedQuery);
                }else{
                    if(AppPreferences.getGroupId(getActivity())==17 || AppPreferences.getGroupId(getActivity())==18){
                        RuntimeExceptionDao<WareHouses, Integer> wareHousesDoa = mDbHelper.getWareHousesRuntimeDao();
                        Where<WareHouses, Integer> where = wareHousesDoa.queryBuilder().where();
                        where.and(where.eq("distributionAreaId",AppPreferences.getDist_Area_Id(getActivity())),
                                where.eq("warehousetypeId",11));
                        //wareHousesQueryBuilder.where().eq("distributionAreaId",AppPreferences.getDist_Area_Id(getActivity()));
                        //wareHousesQueryBuilder.where().eq("warehousetypeId",11);
                        //PreparedQuery<WareHouses> preparedQuery1 = wareHousesQueryBuilder.prepare();
                        mwarehouses = where.query();
                    }else{
                        RuntimeExceptionDao<WareHouses, Integer> wareHousesDoa = mDbHelper.getWareHousesRuntimeDao();
                        mwarehouses = wareHousesDoa.queryForAll();
                    }

                    Log.e("warehouse size",String.valueOf(mwarehouses.size()));
                    if(AppPreferences.getGroupId(getActivity())==17 || AppPreferences.getGroupId(getActivity())==18){
                        warehouseid = mwarehouses.get(0).getId();
                    }else{
                        for(int i=0;i<mwarehouses.size();i++){
                            warehouseid = mwarehouses.get(i).getId();
                        }
                    }
                }
                Log.e("warehouseid",String.valueOf(warehouseid));
                if(warehouseid!=0){
                    Log.e("getTourTypeId",String.valueOf(AppPreferences.getTourTypeId(getActivity())));
                    RuntimeExceptionDao<Inventories, Integer> inventoriesDoa = mDbHelper.getInventoriesRuntimeDao();

                    Where<Inventories, Integer> where = inventoriesDoa.queryBuilder().where();
                    where.and(where.eq("warehouseId",warehouseid),
                            where.eq("tourtypeid",AppPreferences.getTourTypeId(getActivity())));
                    List<Inventories> tempinventories = where.query();

//                    QueryBuilder<Inventories, Integer> inventoriesQueryBuilder = inventoriesDoa.queryBuilder();
//                    inventoriesQueryBuilder.where().eq("warehouseId",warehouseid);
//                    PreparedQuery<Inventories> preparedQuery = inventoriesQueryBuilder.prepare();
//                    List<Inventories> tempinventories = inventoriesDoa.query(preparedQuery);
                    minventories = new ArrayList<>();
                    for(int i=0;i<tempinventories.size();i++){
                        if(Integer.parseInt(tempinventories.get(i).getQuantityinput())>tempinventories.get(i).getQuantitytransfer()){
                            minventories.add(tempinventories.get(i));
                        }
                    }
                    Log.e("temp inv size",String.valueOf(tempinventories.size()));
                    Log.e("inv size",String.valueOf(minventories.size()));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Dialog1.dismiss();
            progress_Bar.setVisibility(View.GONE);
            try{
                if(AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41||AppPreferences.getGroupId(getActivity())==39){
                    if (massignedItems.size()>0){
                        for(AssignedItems assignedItems1:massignedItems){
                            for(int i=0;i<mitemdefinition.size();i++){
                                if(assignedItems1.getItemdefinitionid().equals(mitemdefinition.get(i).getId())){
                                    assignedItems1.setItemname(mitemdefinition.get(i).getName());
                                    break;
                                }
                            }
                        }
                        QAAssignedItem_Rv_Adapter adapter = new QAAssignedItem_Rv_Adapter(massignedItems);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rv_Assignitem.setAdapter(adapter);
                        rv_Assignitem.setLayoutManager(layoutManager);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    if (minventories.size() > 0) {
                        for (Inventories items : minventories) {
                            for (int i = 0; i < mitemdefinition.size(); i++) {
                                if (items.getItemdefinitionId().equals(mitemdefinition.get(i).getId())) {
                                    items.setItemname(mitemdefinition.get(i).getName());
                                    break;
                                }
                            }
                        }
                        AssignedItem_Rv_Adapter adapter1 = new AssignedItem_Rv_Adapter(minventories, mDialog);
                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rv_Assignitem.setAdapter(adapter1);
                        rv_Assignitem.setLayoutManager(layoutManager1);
                        adapter1.notifyDataSetChanged();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
