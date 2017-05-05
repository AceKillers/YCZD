package zz.zept.yczd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zz.zept.yczd.R;
import zz.zept.yczd.bean.Company;
import zz.zept.yczd.bean.ShishiInfo;
import zz.zept.yczd.db.CompanyDBAction;
import zz.zept.yczd.utils.CallServer;
import zz.zept.yczd.utils.ChartUtil;
import zz.zept.yczd.utils.HttpResponseListener;
import zz.zept.yczd.utils.StatusBarCompat;
import zz.zept.yczd.utils.ToastUtils;
import zz.zept.yczd.utils.Utils;
import zz.zept.yczd.view.BarChart04View;
import zz.zept.yczd.view.CompanyPopWindow;
import zz.zept.yczd.view.LineChartView;

import static zz.zept.yczd.utils.ChartUtil.getTodayZero;

/**
 * Created by HanChenxi on 2017/4/28.
 */

public class ShishiActivity extends Activity {
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.rb6)
    RadioButton rb6;
    @BindView(R.id.rb7)
    RadioButton rb7;
    @BindView(R.id.rb8)
    RadioButton rb8;
    @BindView(R.id.rb9)
    RadioButton rb9;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.layout)
    LinearLayout layout;
    private LineChartView lineChartView;
    private LinearLayout.LayoutParams layoutParams;
    private List<Company> companyList = new ArrayList<>();
    private CompanyDBAction dbAction;
    private CompanyPopWindow popWindow;
    private String companyId;
    private List<ShishiInfo> listRecods;
    private BarChart04View barChart04View;
    private String expr;
    private long endTime = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shishi);
        ButterKnife.bind(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_blue));
        dbAction = CompanyDBAction.getInstance(this);
        companyList = dbAction.searchCompany();
        companyId = companyList.get(0).getFACTORYID();
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        initListener();
        getData();
    }

    @OnClick({R.id.company, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company:
                popWindow = new CompanyPopWindow(this, companyList, company);
                popWindow.setOnItemClick(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        companyId = companyList.get(i).getFACTORYID();
                        company.setText(companyList.get(i).getFACTORYNAME());
                        popWindow.dissmiss();
                    }
                });
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void initListener() {
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                getData();
                switch (i) {
                    case R.id.rb1:
                        break;
                    case R.id.rb2:
                        break;
                    case R.id.rb3:
                        break;
                    case R.id.rb4:
                        break;
                    case R.id.rb5:
                        break;
                    case R.id.rb6:
                        break;
                    case R.id.rb7:
                        break;
                    case R.id.rb8:
                        break;
                    case R.id.rb9:
                        break;
                }
            }
        });
    }

    private void getData() {
        CallServer callServer = CallServer.getRequestInstance();
        Request<String> request = NoHttp.createStringRequest("http://192.168.66.31:81/API/Advanced/getPIDataByExpr", RequestMethod.POST);
        request.add("expr", getExpr());
        request.add("startTime", ChartUtil.getTime(getTodayZero()));
        request.add("endTime", ChartUtil.getTime(endTime));
        request.add("interval", (endTime - ChartUtil.getTodayZero()) / 20 * 1000 + "s");
        request.setTag(this);
        HttpResponseListener.HttpListener<String> callback = new HttpResponseListener.HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Utils.closeWaiting();
                String json = response.get();
                if (!TextUtils.isEmpty(json)) {
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if (jsonObject.get("code").toString().contains("success")) {
                        listRecods = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<ArrayList<ShishiInfo>>() {
                        }.getType());
                        if (listRecods != null && listRecods.size() > 0) {
                            layout.removeAllViews();
                            if (rb1.isChecked() || rb2.isChecked() || rb4.isChecked() || rb5.isChecked() || rb6.isChecked() || rb7.isChecked() || rb8.isChecked() || rb9.isChecked()) {
                                showLineChart();
                            }
                            if (rb3.isChecked()) {
                                showBarChart();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Utils.closeWaiting();
                ToastUtils.showToast(ShishiActivity.this, "服务器繁忙,稍后再试");
            }
        };
        Utils.showWaiting(ShishiActivity.this);
        callServer.add(12312, request, callback);
    }

    private void showLineChart() {
        ArrayList<Double> data = new ArrayList<>();
        List<String> label = new ArrayList<>();
        String unit = "";
        for (int i = 0; i < listRecods.size(); i++) {
            data.add(Double.parseDouble(listRecods.get(i).getpValue()));
            label.add(listRecods.get(i).getLocalDate());

        }
        lineChartView = new LineChartView(ShishiActivity.this, Math.ceil(ChartUtil.getMax(data)), label, data, unit);
        layout.addView(lineChartView, layoutParams);
    }

    private void showBarChart() {
        ArrayList<Double> data = new ArrayList<>();
        List<String> label = new ArrayList<>();
        for (int i = 0; i < listRecods.size(); i++) {
            data.add(Double.parseDouble(listRecods.get(i).getpValue()));
            label.add(listRecods.get(i).getLocalDate());
        }
        barChart04View = new BarChart04View(ShishiActivity.this, Math.ceil(ChartUtil.getMax(data)), label, data, "万/kWh");
        layout.addView(barChart04View, layoutParams);
    }

    private String getExpr() {
        if (rb1.isChecked()) {
            if ("平顶山分公司".equals(company.getText().toString())) {
                expr = "if 'PDSFD_1000_01_FDJ_10CGB01-MW' <10 then if 'PDSFD_1000_02_FDJ_20CGB01-MW' <10 then 0 else 'PDSFD_1000_02_FDJ_20CGB01-MW'/1030 else if 'PDSFD_1000_02_FDJ_20CGB01-MW' < 10 then 'PDSFD_1000_01_FDJ_10CGB01-MW'/1030 else ('PDSFD_1000_01_FDJ_10CGB01-MW'+ 'PDSFD_1000_02_FDJ_20CGB01-MW')/2060";
            } else if ("开封分公司".equals(company.getText().toString())) {
                expr = "if 'JT_KFFD_630_01_0100001_1DCSOPC_AI02154' <10 then if 'JT_KFFD_630_02_0100001_2DCSOPC_AI02154' <10 then 0 else 'JT_KFFD_630_02_0100001_2DCSOPC_AI02154'/630 else if 'JT_KFFD_630_02_0100001_2DCSOPC_AI02154' < 10 then 'JT_KFFD_630_01_0100001_1DCSOPC_AI02154'/630 else ('JT_KFFD_630_01_0100001_1DCSOPC_AI02154'+ 'JT_KFFD_630_02_0100001_2DCSOPC_AI02154')/1260";
            } else if ("新乡豫新公司".equals(company.getText().toString())) {
                expr = "if 'JT_YXFD_330_06_0100001_SIS.U06DCSAI.GENP1' <10 then if 'JT_YXFD_330_07_0100001_SIS.U07DCSAI.GENP1' <10 then 0 else 'JT_YXFD_330_07_0100001_SIS.U07DCSAI.GENP1'/330 else if 'JT_YXFD_330_07_0100001_SIS.U07DCSAI.GENP1' < 10 then 'JT_YXFD_330_06_0100001_SIS.U06DCSAI.GENP1'/330 else ('JT_YXFD_330_06_0100001_SIS.U06DCSAI.GENP1'+ 'JT_YXFD_330_07_0100001_SIS.U07DCSAI.GENP1')/660";
            } else if ("平东热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_PDRD_210_06_0100001_60S1FW1' <10 then if 'JT_PDRD_210_07_0100001_70S1FW1' <10 then 0 else 'JT_PDRD_210_07_0100001_70S1FW1'/210 else if 'JT_PDRD_210_07_0100001_70S1FW1' < 10 then 'JT_PDRD_210_06_0100001_60S1FW1'/210 else ('JT_PDRD_210_06_0100001_60S1FW1'+ 'JT_PDRD_210_07_0100001_70S1FW1')/420";
            } else if ("南阳热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_NYRD_210_01_0100001_W3.UNIT1.SE1_F_A15' <10 then if 'JT_NYRD_210_02_0100001_W3.UNIT2.SE1_F_A15' <10 then 0 else 'JT_NYRD_210_02_0100001_W3.UNIT2.SE1_F_A15'/210 else if 'JT_NYRD_210_02_0100001_W3.UNIT2.SE1_F_A15' < 10 then 'JT_NYRD_210_01_0100001_W3.UNIT1.SE1_F_A15'/210 else ('JT_NYRD_210_01_0100001_W3.UNIT1.SE1_F_A15'+ 'JT_NYRD_210_02_0100001_W3.UNIT2.SE1_F_A15')/420";
            } else if ("郑州燃气公司".equals(company.getText().toString())) {
                expr = "if 'JT_ZZRJ_390_01_0100001_10MBY10CE901&XQ01' <10 then if 'JT_ZZRJ_390_02_0100001_20MBY10CE901&XQ01' <10 then 0 else 'JT_ZZRJ_390_02_0100001_20MBY10CE901&XQ01'/390 else if 'JT_ZZRJ_390_02_0100001_20MBY10CE901&XQ01' < 10 then 'JT_ZZRJ_390_01_0100001_10MBY10CE901&XQ01'/390 else ('JT_ZZRJ_390_01_0100001_10MBY10CE901&XQ01'+ 'JT_ZZRJ_390_02_0100001_20MBY10CE901&XQ01')/780";
            }
        }
        if (rb2.isChecked()) {
            if ("平顶山分公司".equals(company.getText().toString())) {
                expr = "if 'PDSFD_1000_01_FDJ_10CGB01-MW' <10 then if 'PDSFD_1000_02_FDJ_20CGB01-MW' <10 then 0 else 'PDSFD_1000_02_FDJ_20CGB01-MW' else if 'PDSFD_1000_02_FDJ_20CGB01-MW' < 10 then 'PDSFD_1000_01_FDJ_10CGB01-MW' else 'PDSFD_1000_01_FDJ_10CGB01-MW'+ 'PDSFD_1000_02_FDJ_20CGB01-MW'";
            } else if ("开封分公司".equals(company.getText().toString())) {
                expr = "if 'JT_KFFD_630_01_0100001_1DCSOPC_AI02154' <10 then if 'JT_KFFD_630_02_0100001_2DCSOPC_AI02154' <10 then 0 else 'JT_KFFD_630_02_0100001_2DCSOPC_AI02154' else if 'JT_KFFD_630_02_0100001_2DCSOPC_AI02154' < 10 then 'JT_KFFD_630_01_0100001_1DCSOPC_AI02154' else 'JT_KFFD_630_01_0100001_1DCSOPC_AI02154'+ 'JT_KFFD_630_02_0100001_2DCSOPC_AI02154'";
            } else if ("新乡豫新公司".equals(company.getText().toString())) {
                expr = "if 'JT_YXFD_330_06_0100001_SIS.U06DCSAI.GENP1' <10 then if 'JT_YXFD_330_07_0100001_SIS.U07DCSAI.GENP1' <10 then 0 else 'JT_YXFD_330_07_0100001_SIS.U07DCSAI.GENP1' else if 'JT_YXFD_330_07_0100001_SIS.U07DCSAI.GENP1' < 10 then 'JT_YXFD_330_06_0100001_SIS.U06DCSAI.GENP1' else 'JT_YXFD_330_06_0100001_SIS.U06DCSAI.GENP1'+ 'JT_YXFD_330_07_0100001_SIS.U07DCSAI.GENP1'";
            } else if ("平东热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_PDRD_210_06_0100001_60S1FW1' <10 then if 'JT_PDRD_210_07_0100001_70S1FW1' <10 then 0 else 'JT_PDRD_210_07_0100001_70S1FW1' else if 'JT_PDRD_210_07_0100001_70S1FW1' < 10 then 'JT_PDRD_210_06_0100001_60S1FW1' else 'JT_PDRD_210_06_0100001_60S1FW1'+ 'JT_PDRD_210_07_0100001_70S1FW1'";
            } else if ("南阳热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_NYRD_210_01_0100001_W3.UNIT1.SE1_F_A15' <10 then if 'JT_NYRD_210_02_0100001_W3.UNIT2.SE1_F_A15' <10 then 0 else 'JT_NYRD_210_02_0100001_W3.UNIT2.SE1_F_A15' else if 'JT_NYRD_210_02_0100001_W3.UNIT2.SE1_F_A15' < 10 then 'JT_NYRD_210_01_0100001_W3.UNIT1.SE1_F_A15' else 'JT_NYRD_210_01_0100001_W3.UNIT1.SE1_F_A15'+ 'JT_NYRD_210_02_0100001_W3.UNIT2.SE1_F_A15'";
            } else if ("郑州燃气公司".equals(company.getText().toString())) {
                expr = "if 'JT_ZZRJ_390_01_0100001_10MBY10CE901&XQ01' <10 then if 'JT_ZZRJ_390_02_0100001_20MBY10CE901&XQ01' <10 then 0 else 'JT_ZZRJ_390_02_0100001_20MBY10CE901&XQ01' else if 'JT_ZZRJ_390_02_0100001_20MBY10CE901&XQ01' < 10 then 'JT_ZZRJ_390_01_0100001_10MBY10CE901&XQ01' else 'JT_ZZRJ_390_01_0100001_10MBY10CE901&XQ01'+ 'JT_ZZRJ_390_02_0100001_20MBY10CE901&XQ01'";
            }
        }
        if (rb7.isChecked()) {
            if ("平顶山分公司".equals(company.getText().toString())) {
                expr = "if 'PDSFD_1000_01_TL_TL_10HTA30CQ001' <10 then if 'PDSFD_1000_02_TL_TL_20HTA30CQ001' <10 then 0 else 'PDSFD_1000_02_TL_TL_20HTA30CQ001' else if 'PDSFD_1000_02_TL_TL_20HTA30CQ001' < 10 then 'PDSFD_1000_01_TL_TL_10HTA30CQ001' else ('PDSFD_1000_01_TL_TL_10HTA30CQ001'+ 'PDSFD_1000_02_TL_TL_20HTA30CQ001')/2";
            } else if ("开封分公司".equals(company.getText().toString())) {
                expr = "if 'KFFD_630_01_FWOPC_AIT0044' <10 then if 'KFFD_630_02_FWOPC_AIT0298' <10 then 0 else 'KFFD_630_02_FWOPC_AIT0298' else if 'KFFD_630_02_FWOPC_AIT0298' < 10 then 'KFFD_630_01_FWOPC_AIT0044' else ('KFFD_630_01_FWOPC_AIT0044'+ 'KFFD_630_02_FWOPC_AIT0298')/2";
            } else if ("新乡豫新公司".equals(company.getText().toString())) {
                expr = "if 'JT_YXFD_330_06_0900004_SIS.TL.6ELZS601' <10 then if 'JT_YXFD_330_07_0900004_SIS.TL.7ELFG70S' <10 then 0 else 'JT_YXFD_330_07_0900004_SIS.TL.7ELFG70S' else if 'JT_YXFD_330_07_0900004_SIS.TL.7ELFG70S' < 10 then 'JT_YXFD_330_06_0900004_SIS.TL.6ELZS601' else ('JT_YXFD_330_06_0900004_SIS.TL.6ELZS601'+ 'JT_YXFD_330_07_0900004_SIS.TL.7ELFG70S')/2";
            } else if ("平东热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_PDRD_210_06_0900003_60CQ20BY1' <10 then if 'JT_PDRD_210_07_0900003_70CQ40BY1' <10 then 0 else 'JT_PDRD_210_07_0900003_70CQ40BY1' else if 'JT_PDRD_210_07_0900003_70CQ40BY1' < 10 then 'JT_PDRD_210_06_0900003_60CQ20BY1' else ('JT_PDRD_210_06_0900003_60CQ20BY1'+ 'JT_PDRD_210_07_0900003_70CQ40BY1')/2";
            } else if ("南阳热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_NYRD_210_01_0900016_W3.FGD.A04P529O1' <10 then if 'JT_NYRD_210_02_0900016_W3.FGD.ZS_JYSO2' <10 then 0 else 'JT_NYRD_210_02_0900016_W3.FGD.ZS_JYSO2' else if 'JT_NYRD_210_02_0900016_W3.FGD.ZS_JYSO2' < 10 then 'JT_NYRD_210_01_0900016_W3.FGD.A04P529O1' else ('JT_NYRD_210_01_0900016_W3.FGD.A04P529O1'+ 'JT_NYRD_210_02_0900016_W3.FGD.ZS_JYSO2')/2";
            } else if ("郑州燃气公司".equals(company.getText().toString())) {
                expr = "";
            }
        }
        if (rb8.isChecked()) {
            if ("平顶山分公司".equals(company.getText().toString())) {
                expr = "if 'PDSFD_1000_01_TL_TL_10FGDAO013' <10 then if 'PDSFD_1000_02_TL_TL_20FGDAO013' <10 then 0 else 'PDSFD_1000_02_TL_TL_20FGDAO013' else if 'PDSFD_1000_02_TL_TL_20FGDAO013' < 10 then 'PDSFD_1000_01_TL_TL_10FGDAO013' else ('PDSFD_1000_01_TL_TL_10FGDAO013'+ 'PDSFD_1000_02_TL_TL_20FGDAO013')/2";
            } else if ("开封分公司".equals(company.getText().toString())) {
                expr = "if 'KFFD_630_01_FWOPC_AIT0046' <10 then if 'KFFD_630_02_FWOPC_AIT0300' <10 then 0 else 'KFFD_630_02_FWOPC_AIT0300' else if 'KFFD_630_02_FWOPC_AIT0300' < 10 then 'KFFD_630_01_FWOPC_AIT0046' else ('KFFD_630_01_FWOPC_AIT0046'+ 'KFFD_630_02_FWOPC_AIT0300')/2";
            } else if ("新乡豫新公司".equals(company.getText().toString())) {
                expr = "if 'JT_YXFD_330_06_0900007_SIS.TL.6ELZS602' <10 then if 'JT_YXFD_330_07_0900007_SIS.TL.7ELZS702' <10 then 0 else 'JT_YXFD_330_07_0900007_SIS.TL.7ELZS702' else if 'JT_YXFD_330_07_0900007_SIS.TL.7ELZS702' < 10 then 'JT_YXFD_330_06_0900007_SIS.TL.6ELZS602' else ('JT_YXFD_330_06_0900007_SIS.TL.6ELZS602'+ 'JT_YXFD_330_07_0900007_SIS.TL.7ELZS702')/2";
            } else if ("平东热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_PDRD_210_06_0900003_60CQ20BY1' <10 then if 'JT_PDRD_210_07_0900003_70CQ40BY1' <10 then 0 else 'JT_PDRD_210_07_0900003_70CQ40BY1' else if 'JT_PDRD_210_07_0900003_70CQ40BY1' < 10 then 'JT_PDRD_210_06_0900003_60CQ20BY1' else ('JT_PDRD_210_06_0900003_60CQ20BY1'+ 'JT_PDRD_210_07_0900003_70CQ40BY1')/2";
            } else if ("南阳热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_NYRD_210_01_0900016_W3.FGD.A04P529O1' <10 then if 'JT_NYRD_210_02_0900016_W3.FGD.ZS_JYSO2' <10 then 0 else 'JT_NYRD_210_02_0900016_W3.FGD.ZS_JYSO2' else if 'JT_NYRD_210_02_0900016_W3.FGD.ZS_JYSO2' < 10 then 'JT_NYRD_210_01_0900016_W3.FGD.A04P529O1' else ('JT_NYRD_210_01_0900016_W3.FGD.A04P529O1'+ 'JT_NYRD_210_02_0900016_W3.FGD.ZS_JYSO2')/2";
            } else if ("郑州燃气公司".equals(company.getText().toString())) {
                expr = "";
            }
        }
        if (rb9.isChecked()) {
            if ("平顶山分公司".equals(company.getText().toString())) {
                expr = "if 'PDSFD_1000_01_TL_10FGDAO015' <10 then if 'PDSFD_1000_02_TL_20FGDAO015' <10 then 0 else 'PDSFD_1000_02_TL_20FGDAO015' else if 'PDSFD_1000_02_TL_20FGDAO015' < 10 then 'PDSFD_1000_01_TL_10FGDAO015' else ('PDSFD_1000_01_TL_10FGDAO015'+ 'PDSFD_1000_02_TL_20FGDAO015')/2";
            } else if ("开封分公司".equals(company.getText().toString())) {
                expr = "if 'KFFD_630_01_FWOPC_AIT0047' <10 then if 'KFFD_630_02_FWOPC_AIT0301' <10 then 0 else 'KFFD_630_02_FWOPC_AIT0301' else if 'KFFD_630_02_FWOPC_AIT0301' < 10 then 'KFFD_630_01_FWOPC_AIT0047' else ('KFFD_630_01_FWOPC_AIT0047'+ 'KFFD_630_02_FWOPC_AIT0301')/2";
            } else if ("新乡豫新公司".equals(company.getText().toString())) {
                expr = "if 'JT_YXFD_330_06_0900006_SIS.TL.6ELF604S' <10 then if 'JT_YXFD_330_07_0900006_SIS.TL.7ELF704' <10 then 0 else 'JT_YXFD_330_07_0900006_SIS.TL.7ELF704' else if 'JT_YXFD_330_07_0900006_SIS.TL.7ELF704' < 10 then 'JT_YXFD_330_06_0900006_SIS.TL.6ELF604S' else ('JT_YXFD_330_06_0900006_SIS.TL.6ELF604S'+ 'JT_YXFD_330_07_0900006_SIS.TL.7ELF704')/2";
            } else if ("平东热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_PDRD_210_06_0900005_60TLFEN' <10 then if 'JT_PDRD_210_07_0900004_70CQ40BY2' <10 then 0 else 'JT_PDRD_210_07_0900004_70CQ40BY2' else if 'JT_PDRD_210_07_0900004_70CQ40BY2' < 10 then 'JT_PDRD_210_06_0900005_60TLFEN' else ('JT_PDRD_210_06_0900005_60TLFEN'+ 'JT_PDRD_210_07_0900004_70CQ40BY2')/2";
            } else if ("南阳热电公司".equals(company.getText().toString())) {
                expr = "if 'JT_NYRD_210_01_0900018_W3.FGD.A04P529O2' <10 then if 'JT_NYRD_210_02_0900018_W3.FGD.ZS_JYYC' <10 then 0 else 'JT_NYRD_210_02_0900018_W3.FGD.ZS_JYYC' else if 'JT_NYRD_210_02_0900018_W3.FGD.ZS_JYYC' < 10 then 'JT_NYRD_210_01_0900018_W3.FGD.A04P529O2' else ('JT_NYRD_210_01_0900018_W3.FGD.A04P529O2'+ 'JT_NYRD_210_02_0900018_W3.FGD.ZS_JYYC')/2";
            } else if ("郑州燃气公司".equals(company.getText().toString())) {
                expr = "";
            }
        }
        return expr;
    }
}
