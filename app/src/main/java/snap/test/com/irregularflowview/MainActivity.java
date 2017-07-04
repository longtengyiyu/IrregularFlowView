package snap.test.com.irregularflowview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IrregularFlowView irregularFlowView;
    List<String> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listData.add("java");
        listData.add("android");
        listData.add("c sharp");
        listData.add("dont net");
        listData.add("中文设计");
        listData.add("英语测试的市场的撒");
        listData.add("重生传说的擦");
        irregularFlowView = (IrregularFlowView) findViewById(R.id.iifv);
        irregularFlowView.setFlowViewData(listData);
    }
}
