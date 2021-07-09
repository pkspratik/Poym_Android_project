package com.example.kanthi.projectmonitoring.Dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Execution_Document_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.RouteSalesViewsAdapter;
import com.example.kanthi.projectmonitoring.Adapters.Survey_Points_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.AssignedItems;
import com.example.kanthi.projectmonitoring.PoJo.DocumentTypes;
import com.example.kanthi.projectmonitoring.PoJo.Documents;
import com.example.kanthi.projectmonitoring.PoJo.Inventories;
import com.example.kanthi.projectmonitoring.PoJo.ItemDefinition;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.TaskRemarkLinks;
import com.example.kanthi.projectmonitoring.PoJo.TourTypes;
import com.example.kanthi.projectmonitoring.PoJo.WareHouses;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kanthi on 3/19/2018.
 */

public class ExecutionDocument_Popup extends DialogFragment {
    Dialog d=null;
    ProgressDialog progres;
    RecyclerView rev_doc;
    TextView cls_popup;
    private List<Documents> mdocuments;
    private List<DocumentTypes> mdocumenttypes;
    private List<TourTypes> mTourtypes;

    AvahanSqliteDbHelper mDbHelper;
    public ExecutionDocument_Popup() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ab=new AlertDialog.Builder(getActivity());
        View v=getActivity().getLayoutInflater().inflate(R.layout.document_popup,null);
        ab.setView(v);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        rev_doc= v.findViewById(R.id.rv_exe_document);
        cls_popup= v.findViewById(R.id.close_popup);
        d=ab.create();
        //LandingFragment fragment = (LandingFragment) getTargetFragment();
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        new FetchDetailsFromDbTask().execute();
        /*ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getExecutionDocument(AppPreferences.getUserId(getActivity()),AppPreferences.getZoneId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Documents> documents = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Documents>>() {
                        }.getType());
                mdocuments = documents;
                *//*if(mdocuments.size()>0){
                    networkDocumenttypes();
                }*//*
                networkDocumenttypes();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });*/
        cls_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        return d;
    }

    private void networkDocumenttypes() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDocumentType(AppPreferences.getUserId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<DocumentTypes> doc_types = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<DocumentTypes>>() {
                        }.getType());
                mdocumenttypes = doc_types;
                /*if(mdocumenttypes.size()>0){
                    networkTourtypes();
                }*/
                networkTourtypes();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkTourtypes() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getTourType(AppPreferences.getUserId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<TourTypes> tourTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<TourTypes>>() {
                        }.getType());
                mTourtypes = tourTypes;
                progres.dismiss();
                if (mTourtypes.size()>0) {
                    for (Documents item : mdocuments) {
                        for (int i = 0; i < mdocumenttypes.size(); i++) {
                            int docname = Integer.parseInt(item.getDocumentname());
                            if (docname == mdocumenttypes.get(i).getId()) {
                                item.setDocname(mdocumenttypes.get(i).getName());
                                break;
                            }
                        }
                        for (int j = 0; j < mTourtypes.size(); j++) {
                            if (item.getTasktypeId() == mTourtypes.get(j).getId()) {
                                item.setTaskname(mTourtypes.get(j).getName());
                                break;
                            }
                        }
                    }
                    if(mdocuments.size()>0){
                        Execution_Document_Rv_Adapter adapter=new Execution_Document_Rv_Adapter(mdocuments);
                        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                        rev_doc.setAdapter(adapter);
                        rev_doc.setLayoutManager(manager);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                RuntimeExceptionDao<Documents, Integer> DocumentsDoa = mDbHelper.getDocumentsRuntimeDao();
                QueryBuilder<Documents, Integer> documentsQueryBuilder = DocumentsDoa.queryBuilder();
                documentsQueryBuilder.where().eq("zoneId",AppPreferences.getZoneId(getActivity()));
                PreparedQuery<Documents> preparedQuery = documentsQueryBuilder.prepare();
                mdocuments = DocumentsDoa.query(preparedQuery);

                RuntimeExceptionDao<DocumentTypes, Integer> DocumentTypesDoa = mDbHelper.getDocumentTypesRuntimeDao();
                mdocumenttypes = DocumentTypesDoa.queryForAll();

                RuntimeExceptionDao<TourTypes, Integer> TourTypesDoa = mDbHelper.getTourTypesRuntimeDao();
                mTourtypes = TourTypesDoa.queryForAll();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            if (mTourtypes.size()>0) {
                for (Documents item : mdocuments) {
                    for (int i = 0; i < mdocumenttypes.size(); i++) {
                        int docname = Integer.parseInt(item.getDocumentname());
                        if (docname == mdocumenttypes.get(i).getId()) {
                            item.setDocname(mdocumenttypes.get(i).getName());
                            break;
                        }
                    }
                    for (int j = 0; j < mTourtypes.size(); j++) {
                        if (item.getTasktypeId() == mTourtypes.get(j).getId()) {
                            item.setTaskname(mTourtypes.get(j).getName());
                            break;
                        }
                    }
                }
                if(mdocuments.size()>0){
                    Execution_Document_Rv_Adapter adapter=new Execution_Document_Rv_Adapter(mdocuments);
                    LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    rev_doc.setAdapter(adapter);
                    rev_doc.setLayoutManager(manager);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
    }
}

