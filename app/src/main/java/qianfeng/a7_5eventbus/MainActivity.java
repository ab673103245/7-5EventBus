package qianfeng.a7_5eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private EventBus eventBus;

    // 事件总线，中午就归纳它啦！一个可以处理线程交互问题的，即可以在子线程中发送，子线程中接收，还可以在主线程中发送，子线程中接收消息。
    // 还可以发送对象，并且不需要实现Serliazable接口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = ((TextView) findViewById(R.id.tv));

        eventBus = EventBus.getDefault();
        eventBus.register(this);

    }

    public void onClick1(View view) {
        eventBus.post("nnnnnnnnnnnnnn");
    }

    public void onClick2(View view) {
        eventBus.post(new User("zhangsan","123")); // 这个eventBus区分是执行哪个方法，其实就是在执行java的最佳匹配性原则(重载方法)
    }

    @Subscribe
    public void getString(String str)
    {
        tv.setText(str);
    }

    @Subscribe(threadMode = ThreadMode.POSTING) // POSTING: 子线程给子线程发送消息时，接收的都是同一子线程
                                                // ASYNC: 子线程给子线程发送消息时，接收的并不是同一子线程。可以打印线程名看看
                                                // MAIN： 主线程
                                                // BACKGOURND: 子线程
    public void getStr(User user)
    {
        tv.setText(user.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }
}
