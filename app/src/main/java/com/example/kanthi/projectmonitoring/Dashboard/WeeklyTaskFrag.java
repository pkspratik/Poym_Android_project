package com.example.kanthi.projectmonitoring.Dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.Adapters.WeeklyTask_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.PoJo.ActualDays;
import com.example.kanthi.projectmonitoring.PoJo.Days;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummaries;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyTaskFrag extends Fragment {
    private TextView field_mgr, field_code, field_location, field_desig;
    private RecyclerView recyclerView;
    private WeeklyTask_Rv_Adapter adapter;
    LinearLayoutManager layoutManager;
    List<RouteSalesViews> mListRouteSalesView;
    List<RouteAssignmentSummariesViews> mrouteAssignmentSummaryViewses;
    List<RouteAssignmentSummaries> mrouteAssignmentSummaries;
    ArrayList<Days> al;
    int dd, m, yy;
    ArrayList<ActualDays> ad;
    private Days days;
    private ActualDays actualDays;
    List<RouteAssignments> mrouteAssignments;
    int routeAssignmentid;
    int partnerid;

    AvahanSqliteDbHelper mDbHelper;

    ProgressDialog mprogress;

    public WeeklyTaskFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weekly_task, container, false);

        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_task);
        partnerid = AppPreferences.getPartnerid(getActivity());
        //Toast.makeText(getActivity(), ""+AppPreferences.getStartDateFlag(getActivity()), Toast.LENGTH_SHORT).show();
        mprogress = new ProgressDialog(getActivity());
        mprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mprogress.setMessage("Loading..");
        mprogress.setIndeterminate(true);
        mprogress.setCancelable(false);
        mprogress.show();
        new FetchDetailsFromDbTask().execute();
        return v;
    }

    public void findDays(String st_date, String e_date, Float to_target, String tourtype, String totalActual, boolean startFlag, long routeAssignId, String fromdate) {
        String startdate = st_date;
        String enddate = e_date;
        Log.e("StartDate", st_date);
        al = new ArrayList<Days>();
        Log.e("submitvalue", "" + startFlag + "" + routeAssignId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //Date start = sdf.parse(startdate);
            Calendar start = Calendar.getInstance();
            start.setTime(sdf.parse(startdate));
            //Date end = sdf.parse(enddate);
            Calendar end = Calendar.getInstance();
            end.setTime(sdf.parse(enddate));
            int workingDays = 0;
            while (!start.after(end))//removed ; (semi-colon)
            {
                //int day = start.getDay();
                int day = start.get(Calendar.DAY_OF_WEEK);
                //if ((day != Calendar.SATURDAY) || (day != Calendar.SUNDAY)) if it's //sunday, != to Saturday is true
                if ((day != Calendar.SUNDAY)) {
                    System.out.println(start.getTime().toString());
                    //Log.d("day",start.getTime().toString());
                    String d = start.getTime().toString();
                    workingDays++;
                    days = new Days();
                    days.setDays(d.substring(0, 10));
                    days.setGmt(start.getTime().toString());
                    days.setTotaltarget(to_target);
                    days.setTourtype(tourtype);
                    days.setTotalactual(Float.parseFloat(totalActual));
                    days.setStartFlag(startFlag);
                    days.setRouteassignId(routeAssignId);
                    days.setRouteAssigndate(fromdate);
                    Log.e("fromdate",""+fromdate);
                    //days.setRoutesalesviewsId(routesalesviewsId);
                    al.add(days);
                }
                //System.out.println(workingDays);//moved
                Log.d("workingdays", String.valueOf(workingDays));
                start.add(Calendar.DATE, 1);//removed comment tags
            }
            System.out.println(workingDays);
            findDays1(workingDays);
            Log.d("workingdays1", String.valueOf(workingDays));
            AppPreferences.setWorkingdays(getActivity(), workingDays);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void findDays1(int count) {
        final Calendar calendar = Calendar.getInstance();
        yy = calendar.get(Calendar.YEAR);
        final int mm = calendar.get(Calendar.MONTH);
        m = mm + 1;
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        int da = dd - 1;
        //Toast.makeText(mContext, ""+dd+"-"+m+"-"+yy, Toast.LENGTH_SHORT).show();
        String startdate = yy + "-" + m + "-" + da;
        int dd1 = da + count;
        String enddate = yy + "-" + m + "-" + dd1;
        ad = new ArrayList<ActualDays>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //Date start = sdf.parse(startdate);
            Calendar start = Calendar.getInstance();
            start.setTime(sdf.parse(startdate));
            //Date end = sdf.parse(enddate);
            Calendar end = Calendar.getInstance();
            end.setTime(sdf.parse(enddate));
            int workingDays = 0;
            while (!start.after(end))//removed ; (semi-colon)
            {
                //int day = start.getDay();
                int day = start.get(Calendar.DAY_OF_WEEK);
                //if ((day != Calendar.SATURDAY) || (day != Calendar.SUNDAY)) if it's //sunday, != to Saturday is true
                if ((day != Calendar.SUNDAY)) {
                    System.out.println(start.getTime().toString());
                    Log.d("day", start.getTime().toString());
                    String d = start.getTime().toString();
                    workingDays++;
                    actualDays = new ActualDays();
                    actualDays.setActualdays(start.getTime().toString());
                    ad.add(actualDays);
                }
                //System.out.println(workingDays);//moved
                Log.d("workingdays", String.valueOf(workingDays));
                start.add(Calendar.DATE, 1);//removed comment tags
            }
            System.out.println(workingDays);
            Log.d("workingdays1", String.valueOf(workingDays));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                RuntimeExceptionDao<RouteAssignments, Integer> RouteAssignmentsDao = mDbHelper.getRouteAssignmentsRuntimeDao();
                QueryBuilder<RouteAssignments, Integer> routeAssignmentsQueryBuilder = RouteAssignmentsDao.queryBuilder();
                routeAssignmentsQueryBuilder.where().eq("routeassignmentsummaryid", AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                PreparedQuery<RouteAssignments> preparedQuery = routeAssignmentsQueryBuilder.prepare();
                mrouteAssignments = RouteAssignmentsDao.query(preparedQuery);

                RuntimeExceptionDao<RouteSalesViews, Integer> routeSalesViewsDao = mDbHelper.getRouteSalesViewsRuntimeDao();
                Where<RouteSalesViews,Integer> where = routeSalesViewsDao.queryBuilder().where();
                where.and(where.eq("routeassignmentsummaryid", AppPreferences.getRouteAssignmentSummaryId(getActivity())),
                        where.eq("rowclicked", false));
                mListRouteSalesView=where.query();

                //QueryBuilder<RouteSalesViews, Integer> routeSalesViewsBuilder = routeSalesViewsDao.queryBuilder();
                //routeSalesViewsBuilder.where().eq("routeassignmentsummaryid", AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                //PreparedQuery<RouteSalesViews> preparedQuery1 = routeSalesViewsBuilder.prepare();
                //mListRouteSalesView = routeSalesViewsDao.query(preparedQuery1);


                RuntimeExceptionDao<RouteAssignmentSummariesViews, Integer> summariesViewsRuntimeDao = mDbHelper.getRouteAssignmentSummariesViewsRuntimeDao();
                QueryBuilder<RouteAssignmentSummariesViews, Integer> summariesViewsBuilder = summariesViewsRuntimeDao.queryBuilder();
                summariesViewsBuilder.where().eq("id", AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                PreparedQuery<RouteAssignmentSummariesViews> preparedQuery2 = summariesViewsBuilder.prepare();
                mrouteAssignmentSummaryViewses = summariesViewsRuntimeDao.query(preparedQuery2);

                RuntimeExceptionDao<RouteAssignmentSummaries, Integer> summariesDoa = mDbHelper.getRouteAssignmentSummariesRuntimeDao();
                QueryBuilder<RouteAssignmentSummaries, Integer> summariesQueryBuilder = summariesDoa.queryBuilder();
                //summariesQueryBuilder.where().eq("partnerId", AppPreferences.getPartnerid(getActivity()));
                summariesQueryBuilder.where().eq("id", AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                PreparedQuery<RouteAssignmentSummaries> preparedQuery3 = summariesQueryBuilder.prepare();
                mrouteAssignmentSummaries = summariesDoa.query(preparedQuery3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mprogress.dismiss();
            if (mrouteAssignmentSummaries.size() > 0) {
                //AppPreferences.setRouteAssignmentSummaryId(getActivity(),mrouteAssignmentSummaries.get(mrouteAssignmentSummaries.size()-1).getPartnerId());
                AppPreferences.setActualStartDate(getActivity(), mrouteAssignmentSummaries.get(mrouteAssignmentSummaries.size() - 1).getActualstartdate() == null ? "null" : mrouteAssignmentSummaries.get(mrouteAssignmentSummaries.size() - 1).getActualstartdate());
                AppPreferences.setActualEndDate(getActivity(), mrouteAssignmentSummaries.get(mrouteAssignmentSummaries.size() - 1).getActualenddate() == null ? "null" : mrouteAssignmentSummaries.get(mrouteAssignmentSummaries.size() - 1).getActualenddate());
                AppPreferences.setDistributionSubAreaId(getActivity(), mrouteAssignmentSummaries.get(mrouteAssignmentSummaries.size() - 1).getDistributionSubAreaId() == null || mrouteAssignmentSummaries.get(mrouteAssignmentSummaries.size() - 1).getDistributionSubAreaId() == 0 ? "null" : String.valueOf(mrouteAssignmentSummaries.get(mrouteAssignmentSummaries.size() - 1).getDistributionSubAreaId()));
            }
            if (mrouteAssignmentSummaryViewses.size() > 0) {
                if (mListRouteSalesView.size() > 0) {
                    Log.e("routeviews", "Notcalling");
                    //String fromdate=mrouteAssignmentSummaryViewses.get(mrouteAssignmentSummaryViewses.size()-1).getFromdate();
                    //String todate=mrouteAssignmentSummaryViewses.get(mrouteAssignmentSummaryViewses.size()-1).getTodate();

                    String fromdate = mListRouteSalesView.get(mListRouteSalesView.size() - 1).getAssigndate();
                    //TODO-String fromdate = mListRouteSalesView.get(mListRouteSalesView.size()-1).getAssigndate();
                    String todate = mrouteAssignmentSummaryViewses.get(0).getTodate();
                    String tourtype = mrouteAssignmentSummaryViewses.get(0).getTourtype();
                    String total_actual = String.valueOf(mrouteAssignmentSummaryViewses.get(0).getTotalactual());
                    Float to_target = mrouteAssignmentSummaryViewses.get(0).getTotaltarget();
                    int con_date = Integer.parseInt(fromdate.substring(8, 10));
                    int final_date = con_date + 1;
                    String st_date = fromdate.substring(0, 8) + final_date;
                    int from = Integer.parseInt(fromdate.substring(8, 10));
                    int fro_date = from;
                    String summary_st_date = fromdate.substring(0, 8) + fro_date;

                    int to_da = Integer.parseInt(todate.substring(8, 10));
                    int to_date = to_da + 1;
                    String e_date = todate.substring(0, 8) + to_date;
                    //Toast.makeText(getActivity(),"Greater::"+AppUtilities.getDateTime1(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), ""+AppUtilities.getDateTime(), Toast.LENGTH_SHORT).show();
                    //Log.e("submitvalue",""+mListRouteSalesView.get(mListRouteSalesView.size() - 1).getSubmitflag());
                    findDays(mListRouteSalesView.get(mListRouteSalesView.size() - 1).getSubmitflag()
                                    == null ? summary_st_date : st_date, e_date, to_target, tourtype, total_actual, mListRouteSalesView.get(mListRouteSalesView.size() - 1).getSubmitflag() == null ? true : false,
                            mListRouteSalesView.get(mListRouteSalesView.size() - 1).getSubmitflag() == null ? mListRouteSalesView.get(mListRouteSalesView.size() - 1).getId() : 0, fromdate);
                    if (al != null && ad != null) {
                        adapter = new WeeklyTask_Rv_Adapter(al, mrouteAssignmentSummaryViewses, AppUtilities.getDateTime().split("T")[0], partnerid, mrouteAssignmentSummaries, ad, mrouteAssignments, mDbHelper);
                        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter.notifyDataSetChanged();
                    }
                }
                if (mListRouteSalesView.size() <= 0) {
                    Log.e("routeviews", "calling");
                    String fromdate = mrouteAssignmentSummaryViewses.get(0).getFromdate();
                    String todate = mrouteAssignmentSummaryViewses.get(0).getTodate();
                    String tourtype = mrouteAssignmentSummaryViewses.get(0).getTourtype();
                    Float to_target = mrouteAssignmentSummaryViewses.get(0).getTotaltarget();
                    String totalActual = "0";

                    int from = Integer.parseInt(fromdate.substring(8, 10));
                    int fro_date = from + 1;

                    int to = Integer.parseInt(todate.substring(8, 10));
                    int to_date = to + 1;

                    String st_date = fromdate.substring(0, 8) + fro_date;
                    String e_date = todate.substring(0, 8) + to_date;
                    findDays(st_date, e_date, to_target, tourtype, totalActual, false, 0, fromdate);
                    if (al != null && ad != null) {
                        adapter = new WeeklyTask_Rv_Adapter(al, mrouteAssignmentSummaryViewses, AppUtilities.getDateTime().split("T")[0], partnerid, mrouteAssignmentSummaries, ad, mrouteAssignments, mDbHelper);
                        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter.notifyDataSetChanged();
                    }
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